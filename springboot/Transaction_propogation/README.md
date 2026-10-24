title: 'Spring Boot Transactions in Production: 8 Patterns, the Real Bugs They Prevent, and a Demo You Can Run'
subtitle: Your code has @Transactional on it. That does not mean your data is safe.
tags:
- Java
- Microservices
- Software Development
- Software Engineering
- Backend Development
source_url: https://cdinuwan.medium.com/spring-boot-transactions-in-production-8-patterns-the-real-bugs-they-prevent-and-a-demo-you-can-d5d56717df81
---

# Spring Boot Transactions in Production: 8 Patterns, the Real Bugs They Prevent, and a Demo You Can Run

*Your code has @Transactional on it. That does not mean your data is safe.*

*Published Oct 17, 2026 · Free: Yes*

Most transaction tutorials put `@Transactional` on a method and stop there. In production, the problems look different:

- A customer is charged, but the order is not saved.
- A "payment declined" message goes out, but the money is gone anyway.
- One slow payment gateway takes down the whole service.
- Two people buy the last item in stock, and both succeed.
- A customer gets an "order confirmed" email for an order that does not exist.
- Kafka redelivers a message and an account is credited three times.

Every one of these is a transaction problem. For each one, this article shows the real-life problem, why it happens, the fix, and a curl command you can run against a working demo to see it happen.

### The demo project

Everything here comes from one small Spring Boot app. Each pattern has endpoints that show the **broken** version and the **fixed** version side by side. It uses in-memory H2, so there is no Docker and no database to set up.

- Spring Boot 4.1.1, Java 21, Spring Data JPA, H2
- 21 integration tests prove every behaviour shown here
- Source code: [github.com/ChanukD/springboot-transaction-patterns](https://github.com/ChanukD/springboot-transaction-patterns)

```bash
tx-patterns-demo/
├── demo.sh            # runs every scenario
└── src/main/java/com/demo/tx/
    ├── basics/        1. proxy traps
    ├── propagation/   2. REQUIRES_NEW audit trail
    ├── readonly/      3. read-only transactions
    ├── template/      4. slow payment gateway
    ├── locking/       5. optimistic vs pessimistic
    ├── events/        6. @TransactionalEventListener
    ├── outbox/        7. transactional outbox
    └── idempotency/   8. idempotent consumer
git clone https://github.com/ChanukD/springboot-transaction-patterns
cd springboot-transaction-patterns
./mvnw spring-boot:run
./demo.sh
# reset: account #1 "alice" with 1000.00
curl -X POST localhost:8080/demo/reset
```

### The most useful setting for learning transactions

```ini
logging.level.org.springframework.orm.jpa.JpaTransactionManager=DEBUG
spring.jpa.open-in-view=false
```

The first line logs every time Spring starts, joins, pauses, commits or rolls back a transaction. You will see lines like `Creating new transaction`, `Participating in existing transaction` and `Rolling back JPA transaction`. That makes the invisible visible.

The second line matters in production. Spring Boot turns `open-in-view` on by default, which keeps a database connection open for the whole HTTP request, including JSON rendering. Turn it off in every service.

Every demo endpoint returns the account balance before and after. If the balance changed, the work was committed. If not, it was rolled back.

### First: how @Transactional really works

Spring does not change your class. It wraps your bean in a **proxy**. When another bean calls your method, the call goes through the proxy:

```sql
Caller ─► [Proxy: begin] ─► your method ─► [Proxy: commit/rollback]
```

Almost every transaction bug comes from one of three facts:

- Only calls that **go through the proxy** get a transaction.
- The proxy decides commit or rollback from the **exception that leaves the method**.
- Methods called inside a transaction **share it** by default.

### 1. Proxy traps: three ways @Transactional silently does nothing

### Bug 1a: Self-invocation

> **_Real-life problem:_**_ Checkout debits the wallet, then calls the shipping service, which is down. The API returns an error, so the customer tries again. They are charged twice and have no order. The code has @Transactional, so everyone in code review thought it was safe._

**Why it happens:** the method calls another method of the same class through `this`. That call never goes through the proxy, so `@Transactional` is ignored.

```kotlin
@Service
public class BasicsService {
  // no @Transactional; the controller calls this
  public void checkoutSelfInvocation(Long id, BigDecimal amt) {
    this.debitThenFail(id, amt);      // skips the proxy
  }
  @Transactional                      // ignored for the call above
  public void debitThenFail(Long id, BigDecimal amt) {
    Account account = accounts.findById(id).orElseThrow();
    account.debit(amt);
    accounts.save(account);           // commits on its own
    throw new IllegalStateException("shipping service down");
  }
}
curl -X POST localhost:8080/demo/basics/self-invocation
{ "outcome": "threw IllegalStateException: shipping service down",
  "balanceBefore": 1000.00,
  "balanceAfter":   900.00 }     ← money gone, request failed
```

**Fix:** move the transactional method into another bean, so the call goes through a proxy.

```typescript
@Service
public class AccountTxOps {
  @Transactional
  public void debitThenFail(Long id, BigDecimal amt) { ... }
}
// in BasicsService
public void checkoutViaProxy(Long id, BigDecimal amt) {
  txOps.debitThenFail(id, amt);       // through the proxy
}
curl -X POST localhost:8080/demo/basics/self-invocation-fixed
{ "balanceBefore": 900.00, "balanceAfter": 900.00 }  ← rolled back
```

You can also put `@Transactional` on the public entry method, or use `TransactionTemplate` (pattern 4). Avoid injecting a bean into itself to work around this. It hides the design problem.

### Bug 1b: Checked exceptions commit

> **_Real-life problem:_**_ your code throws a checked PaymentDeclinedException. The customer sees "card declined", but the debit made earlier in the same method was committed. Support tickets say "my card was declined but I was charged"._

**Why it happens:** by default Spring rolls back only for `RuntimeException` and `Error`. A checked exception means commit. The rule comes from old EJB conventions, and it surprises almost everyone.

```java
public class PaymentDeclinedException extends Exception { }
@Transactional
public void debitThenDecline(Long id, BigDecimal amt)
    throws PaymentDeclinedException {
  accounts.findById(id).orElseThrow().debit(amt);
  throw new PaymentDeclinedException("card declined"); // COMMIT
}
curl -X POST localhost:8080/demo/basics/checked-exception
{ "outcome": "threw PaymentDeclinedException: card declined",
  "balanceBefore": 900.00, "balanceAfter": 800.00 } ← charged
```

**Fix:**

```java
@Transactional(rollbackFor = Exception.class)
public void debitThenDecline(...) throws PaymentDeclinedException
curl -X POST localhost:8080/demo/basics/checked-exception-fixed
{ "balanceBefore": 800.00, "balanceAfter": 800.00 } ← rolled back
```

A team rule that works well: use unchecked exceptions for business errors. If you must use checked ones, always add `rollbackFor`. Some teams create a meta-annotation such as `@ServiceTransactional` that includes it, so nobody forgets.

### Bug 1c: "I caught the exception. Why did everything roll back?"

> **_Real-life problem:_**_ checkout calls an optional loyalty-bonus service, wrapped in try/catch: "if the bonus fails, just continue". Then the loyalty service has a bad day and every checkout fails with UnexpectedRollbackException. Nobody understands why, because the exception was caught._

**Why it happens:** the inner method is `@Transactional` (REQUIRED by default), so it joins the outer transaction. When it throws, the proxy marks the **shared** transaction as rollback-only. Catching the exception does not remove that mark. At commit, Spring rolls back and throws.

```scss
@Transactional
public void checkoutWithOptionalBonus(Long id, BigDecimal amt) {
  accounts.findById(id).orElseThrow().debit(amt);
  try {
    txOps.applyLoyaltyBonus(id);   // @Transactional, throws
  } catch (IllegalStateException e) {
    log.warn("Bonus failed, continuing: {}", e.getMessage());
  }
}   // commit → UnexpectedRollbackException
curl -X POST localhost:8080/demo/basics/unexpected-rollback
{ "outcome": "threw UnexpectedRollbackException: Transaction
   silently rolled back because it has been marked as
   rollback-only",
  "balanceBefore": 800.00, "balanceAfter": 800.00 }
```

The transaction log tells the story:

```sql
Creating new transaction [...checkoutWithOptionalBonus]
Participating in existing transaction
Participating transaction failed - marking existing
  transaction as rollback-only
Bonus failed, continuing: loyalty service rejected the bonus
Committing JPA transaction ...  → UnexpectedRollbackException
```

**Fixes, pick one:**

- If the optional step does not need the transaction, call it after the commit.
- If it writes to the database on its own, give it `REQUIRES_NEW` (pattern 2), so its failure only rolls back its own work.
- If it must never break the caller, catch the exception inside the inner method, or use `noRollbackFor`.

### Bonus trap: fields on a proxied bean are null

I hit this one myself while building the demo. A controller read a counter field directly from a `@Transactional` service:

```csharp
txService.optimisticAttempts.set(0);  // NullPointerException
```

The controller holds the CGLIB proxy, not the real object. The proxy is a subclass with its own empty fields, and only method calls are forwarded. Always use methods, never fields, on Spring beans.

### 2. REQUIRES_NEW: work that must survive a rollback

> **_Real-life problem:_**_ compliance and fraud teams need a record of every withdrawal attempt, especially failed ones, because many failed attempts in a row is a fraud signal. But the audit row is written in the same transaction as the withdrawal. When the withdrawal fails, the audit row is rolled back too, and the fraud team sees nothing._

**Propagation** decides what happens when a transactional method is called while a transaction already exists:

- **REQUIRED** (default): join the current transaction, or start one. Use for normal business methods.
- **REQUIRES_NEW**: pause the current transaction and start a separate one. Use for audit logs, failure records and number allocation.
- **NESTED**: a savepoint inside the current transaction. Use for batch imports that skip bad rows. Works with JDBC; limited with JPA.
- **MANDATORY**: fail if there is no transaction. Use for helpers that must never run alone.
- **NOT_SUPPORTED**: pause the transaction and run without one. Use for slow external calls.
- **SUPPORTS** and **NEVER**: join if present, or fail if present. Rarely needed.

```kotlin
@Service
public class AuditService {
  @Transactional                     // joins the caller's tx
  public void recordInCallerTx(String msg) {
    audits.save(new AuditLog("REQUIRED", msg));
  }
  // own transaction, commits immediately
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void recordIndependently(String msg) {
    audits.save(new AuditLog("REQUIRES_NEW", msg));
  }
}
@Service
public class WithdrawalService {
  @Transactional
  public void withdraw(Long id, BigDecimal amt) {
    String msg = "attempt: withdraw %s from %d".formatted(amt, id);
    audit.recordIndependently(msg);
    audit.recordInCallerTx(msg);
    accounts.findById(id).orElseThrow().debit(amt); // may throw
  }
}
```

Try one withdrawal that works and one that fails:

```bash
curl -X POST "localhost:8080/demo/propagation/withdraw?amount=100"
curl -X POST "localhost:8080/demo/propagation/withdraw?amount=999999"
curl localhost:8080/demo/propagation/audit
[ { "propagation": "REQUIRES_NEW", "message": "...withdraw 100" },
  { "propagation": "REQUIRED",     "message": "...withdraw 100" },
  { "propagation": "REQUIRES_NEW", "message": "...withdraw 999999" } ]
```

The failed withdrawal kept only its REQUIRES_NEW row. The log shows exactly what happened:

```sql
Creating new transaction [...WithdrawalService.withdraw]
Suspending current transaction, creating new transaction
  [...AuditService.recordIndependently]
Committing JPA transaction ...
Resuming suspended transaction after completion of inner
Participating in existing transaction
Initiating transaction rollback
```

### Watch out: REQUIRES_NEW can freeze your service

While the inner transaction runs, the outer one is paused but **still holds its database connection**. One request now needs two connections at the same time.

Say the HikariCP pool has 10 connections and 10 requests arrive together. Each takes one connection for the outer transaction, and the pool is empty. Each then waits for a second connection for REQUIRES_NEW, and nobody can continue. After the connection timeout (30 seconds by default) they all fail with "Connection is not available, request timed out".

Use REQUIRES_NEW only for small, fast writes, size the pool for it, and never nest REQUIRES_NEW inside REQUIRES_NEW.

### 3. Read-only transactions

> **_Real-life problem:_**_ a "get profile" endpoint formats the name for display by calling setOwner(name.toUpperCase()) on the entity. The entity is managed by Hibernate, so the change is saved at commit. Customer names in the database slowly turn UPPERCASE, and nobody wrote an UPDATE._

With `readOnly = true`, Hibernate skips dirty checking and does not flush, so accidental changes are never written. It is also faster and uses less memory on large reads, because Hibernate keeps no snapshot copies of entities.

```less
@Service
@Transactional(readOnly = true)   // class default: read-only
public class ProfileService {
  public String displayName(Long id) {
    Account a = accounts.findById(id).orElseThrow();
    a.setOwner(a.getOwner().toUpperCase()); // NOT saved
    return a.getOwner();
  }
  @Transactional                   // write methods opt out
  public String rename(Long id, String owner) {
    accounts.findById(id).orElseThrow().setOwner(owner);
    return owner;                  // saved by dirty checking
  }
}
curl -X POST localhost:8080/demo/readonly/modify-in-readonly
{ "valueInsideMethod": "ALICE", "valueInDatabase": "alice" }
curl -X POST "localhost:8080/demo/readonly/rename?owner=bob"
{ "valueInDatabase": "bob" }
```

In production you can also route read-only transactions to a **read replica** (Aurora, Cloud SQL) with `AbstractRoutingDataSource` and `LazyConnectionDataSourceProxy`. Replicas lag a little, so don't read from one right after a write in the same flow.

The real fix for the UPPERCASE bug is to map to a DTO for display instead of changing entities. `readOnly` is a safety net.

### 4. TransactionTemplate: keep slow remote calls out of the transaction

> **_Real-life problem:_**_ the payment gateway (Stripe, Adyen, a bank API) slows down from 200 ms to 5 seconds. Your payment method is @Transactional and calls the gateway inside it, so each request holds a database connection for 5 seconds. The connection pool runs out, and every endpoint in the service starts failing, including ones that have nothing to do with payments._

> _There is a second problem. When the gateway call fails, the transaction rolls back the PENDING payment row too. Now there is no record that you tried to charge the customer._

The bad version:

```java
@Transactional
public PayResult payInsideTx(BigDecimal amt, boolean decline) {
  Payment p = payments.save(Payment.pending(amt));
  var result = gateway.charge(amt, decline); // 1.5 s, conn held
  p.complete(result.reference());
  return new PayResult(p, result.dbTxOpenDuringCall());
}
```

The good version: two short transactions with the remote call in between. `TransactionTemplate` is auto-configured by Spring Boot, so just inject it.

```java
public PayResult pay(BigDecimal amt, boolean decline) {
  // tx 1: record the attempt, committed before the gateway call
  Long id = tx.execute(s ->
      payments.save(Payment.pending(amt)).getId());
  ChargeResult result;
  try {
    result = gateway.charge(amt, decline);  // no tx, no conn
  } catch (RuntimeException e) {
    tx.executeWithoutResult(s ->
        payments.findById(id).orElseThrow().fail());
    return new PayResult(payments.findById(id).orElseThrow(), false);
  }
  // tx 2: record the result
  tx.executeWithoutResult(s ->
      payments.findById(id).orElseThrow()
              .complete(result.reference()));
  return new PayResult(payments.findById(id).orElseThrow(),
                       result.dbTxOpenDuringCall());
}
```

The fake gateway in the demo checks `TransactionSynchronizationManager.isActualTransactionActive()` during the call, so we can prove where the transaction was:

```bash
curl -X POST "localhost:8080/demo/payments/inside-tx?amount=50"
{ "payment": { "status": "COMPLETED" },
  "dbTransactionOpenDuringGatewayCall": true }
curl -X POST "localhost:8080/demo/payments/inside-tx?amount=60&decline=true"
{ "error": "gateway declined charge of 60" }   ← no PENDING row left
curl -X POST "localhost:8080/demo/payments/split-tx?amount=80&decline=true"
{ "payment": { "amount": 80.00, "status": "FAILED" },
  "dbTransactionOpenDuringGatewayCall": false }
```

**The trade-off:** splitting the transaction means you lose all-or-nothing. If the app crashes after the gateway succeeds but before tx 2, the row stays PENDING while the customer was charged. Handle that with an **idempotency key** sent to the gateway (use the payment id), so a retry never charges twice, and a **reconciliation job** that checks old PENDING rows with the gateway. That is still far better than a frozen service and missing records.

The rule: never make network calls (HTTP, Kafka, S3, email) inside a database transaction, and give transactions on hot tables a timeout, such as `@Transactional(timeout = 5)`.

### 5. Locking: when two requests update the same row

> **_Real-life problem:_**_ a flash sale has one item left. Two users click Buy at the same moment. Both read stock = 1, both subtract 1, both save stock = 0. You sold two items and had one. The same thing happens with wallet balances, seat booking and coupon limits. This is a lost update, and the default isolation level (READ COMMITTED) does not prevent it._

### Option A: optimistic locking (the default choice)

Add a version column. Hibernate adds `WHERE version = ?` to every UPDATE. If someone else changed the row first, no row is updated and Spring throws `ObjectOptimisticLockingFailureException`.

```kotlin
@Entity
public class Account {
  @Id private Long id;
  private BigDecimal balance;
  @Version private Long version;
}
@Transactional
public void debitOptimistic(Long id, BigDecimal amt) {
  accounts.findById(id).orElseThrow().debit(amt);
}
```

The demo fires 8 debits of 10.00 at the same account at the same moment. Exact counts change between runs because thread timing is random.

```bash
curl -X POST localhost:8080/demo/locking/optimistic-no-retry
{ "requests": 8, "succeeded": 1, "failed": 7,
  "balanceBefore": 1000.00, "balanceAfter": 990.00,
  "errors": ["7 x ObjectOptimisticLockingFailureException"] }
```

No update was lost, so the data is correct. But 7 of 8 users got an error. Add a retry:

```kotlin
@Service
public class RetryingAccountService {
  @Retryable(includes = ObjectOptimisticLockingFailureException.class,
             maxRetries = 10, delay = 20, multiplier = 2,
             maxDelay = 500, jitter = 10)
  public void debit(Long id, BigDecimal amt) {
    txService.debitOptimistic(id, amt);   // a DIFFERENT bean
  }
}
curl -X POST localhost:8080/demo/locking/optimistic-retry
{ "requests": 8, "succeeded": 8, "failed": 0,
  "totalAttempts": 30,
  "balanceBefore": 1000.00, "balanceAfter": 920.00 }
```

The retry must sit **outside** the transaction, in a different bean. If retry and `@Transactional` are on the same method, you may retry inside a transaction already marked rollback-only, with stale data in the Hibernate session. Each retry has to start a fresh transaction and read the new version.

### Option B: pessimistic locking (for hot rows)

Look at `totalAttempts: 30` above. Eight requests needed 30 attempts. When many requests fight over the same row, optimistic locking wastes work, and it is better to lock the row and make the others wait:

```kotlin
public interface AccountRepository
    extends JpaRepository<Account, Long> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)   // SELECT ... FOR UPDATE
  @QueryHints(@QueryHint(
      name = "jakarta.persistence.lock.timeout", value = "3000"))
  @Query("select a from Account a where a.id = :id")
  Optional<Account> findForUpdate(Long id);
}
@Transactional(timeout = 10)
public void debitPessimistic(Long id, BigDecimal amt) {
  accounts.findForUpdate(id).orElseThrow().debit(amt);
}
curl -X POST localhost:8080/demo/locking/pessimistic
{ "requests": 8, "succeeded": 8, "failed": 0,
  "balanceBefore": 1000.00, "balanceAfter": 920.00 }
```

### Which one?

- **Optimistic (@Version)** fits when conflicts are rare, like profile or order edits. It costs nothing without a conflict. The risk is retry storms under heavy contention, so always retry outside the transaction with backoff and jitter.
- **Pessimistic (FOR UPDATE)** fits when conflicts are common, like a stock counter or a busy wallet. Requests wait in line. The risks are deadlocks, long waits and pool exhaustion, so always set a lock timeout, keep transactions short and lock rows in a fixed order.
- **An atomic SQL update** is often best for simple counters: `UPDATE product SET stock = stock - 1 WHERE id = ? AND stock > 0`, then check that one row changed. No lock is held while Java code runs, and no retry is needed.

On isolation levels: PostgreSQL, Oracle and SQL Server default to READ COMMITTED, and MySQL InnoDB to REPEATABLE READ. SERIALIZABLE prevents the most, but you must retry serialization failures. In practice, keep the database default and protect the specific rows that need it.

### 6. Transactional events: side effects only after commit

> **_Real-life problem:_**_ the order service saves an order, publishes an OrderPlaced event, and a listener sends "Your order is confirmed!". Later in the same method a stock check fails and the transaction rolls back. The customer has a confirmation email for an order that does not exist._

**Why it happens:** a normal `@EventListener` runs immediately, when `publishEvent()` is called. At that moment nobody knows whether the transaction will commit.

```typescript
@Transactional
public CustomerOrder placeOrder(String item, boolean fail) {
  CustomerOrder order = orders.save(new CustomerOrder(item));
  events.publishEvent(new OrderPlaced(order.getId(), item));
  if (fail) throw new IllegalStateException("stock check failed");
  return order;
}
@Component
public class OrderEventListeners {
  @EventListener                      // BAD for side effects
  public void emailNow(OrderPlaced e) { ... }
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void emailAfterCommit(OrderPlaced e) { ... }  // GOOD
  @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
  public void onRollback(OrderPlaced e) { ... }
}
```

First an order that succeeds, then one that rolls back:

```perl
curl -X POST "localhost:8080/demo/events/order?item=laptop"
{ "ordersInDatabase": 1,
  "notificationsSent": [
    "[@EventListener] email: order #1 (laptop) confirmed",
    "[AFTER_COMMIT] email: order #1 (laptop) confirmed" ] }
curl -X POST "localhost:8080/demo/events/order?item=camera&fail=true"
{ "error": "stock check failed after the event was published",
  "ordersInDatabase": 1,
  "notificationsSent": [
    "[@EventListener] email: order #2 (camera) confirmed",
    "[AFTER_ROLLBACK] order #2 (camera) rolled back" ] }
```

Order #2 was never saved, but the plain `@EventListener` had already "sent" the email. The `AFTER_COMMIT` listener correctly did nothing.

Things to watch:

- **AFTER_COMMIT events are not durable.** If the pod is killed between the commit and the listener, the email is never sent. For events you cannot lose, use the outbox (pattern 7).
- **Database writes inside an AFTER_COMMIT listener are silently lost** unless the listener has `@Transactional(propagation = REQUIRES_NEW)`, because the original transaction has already committed.
- The listener runs on the same thread and delays the HTTP response. For slow work add `@Async`, and accept that failures are no longer visible to the caller.
- Good uses: cache eviction, metrics, search-index updates and nice-to-have notifications.

### 7. Transactional outbox: database and Kafka without losing events

> **_Real-life problem (the dual write):_**_ the order service must save the order and publish OrderPlaced to Kafka for inventory, billing and shipping. Save then send: the database commits, Kafka is down or the pod restarts, and the event is lost, so billing never invoices. Send then save: Kafka gets the event, the commit fails, and shipping ships an order that does not exist._

A database and Kafka cannot commit together without distributed transactions (2PC/XA), which are slow, fragile and unsupported by most cloud services.

**The fix:** write the event to an `outbox_event` table in the same local transaction as the order. A separate relay reads the table and publishes to Kafka.

```sql
Request ─► [ one DB tx: INSERT order + INSERT outbox_event ]
                           │
            relay (poller or Debezium CDC)
                           ▼
                Kafka ─► inventory, billing, shipping
```

Writing the event has no Kafka call at all:

```typescript
@Transactional
public CustomerOrder placeOrder(String item, boolean fail) {
  CustomerOrder order = orders.save(new CustomerOrder(item));
  String payload = json.writeValueAsString(
      Map.of("orderId", order.getId(), "item", item));
  outbox.save(new OutboxEvent("orders.placed",
      String.valueOf(order.getId()), payload));
  if (fail) throw new IllegalStateException("validation failed");
  return order;
}
```

The relay locks a batch with `SKIP LOCKED`, so several pods can run it without stepping on each other:

```python
@Query(value = """
    SELECT * FROM outbox_event
    WHERE published_at IS NULL
    ORDER BY id
    LIMIT :batchSize
    FOR UPDATE SKIP LOCKED
    """, nativeQuery = true)
List<OutboxEvent> lockNextBatch(int batchSize);
@Transactional(timeout = 30)
public int relayBatch() {
  int published = 0;
  for (OutboxEvent e : outbox.lockNextBatch(50)) {
    e.recordAttempt();
    try {
      publisher.publish(e.getTopic(), e.getMessageKey(),
                        e.getEventId(), e.getPayload());
      e.markPublished();
      published++;
    } catch (RuntimeException ex) {
      break;   // keep order; the rest stay pending
    }
  }
  return published;
}
@Scheduled(fixedDelayString = "${demo.outbox.poll-ms:3000}")
public void poll() { relay.relayBatch(); }
```

In a real service, `publish()` wraps `kafkaTemplate.send(...).get(5, SECONDS)`. Sending synchronously matters: if Kafka rejects the message, the row must not be marked as published.

Yes, this relay makes a network call inside a transaction, which pattern 4 warned against. Here it is a small, bounded background batch with a timeout, not a user request, so it is a deliberate trade-off. Debezium avoids it completely.

Now watch it survive a Kafka outage:

```bash
# 1. Validation fails: order and event roll back together
curl -X POST "localhost:8080/demo/outbox/order?item=phone&fail=true"
{ "ordersInDatabase": 0, "outboxRows": 0 }
# 2. Kafka goes down
curl -X POST "localhost:8080/demo/outbox/broker?down=true"
# 3. Orders still work. Customers notice nothing.
curl -X POST "localhost:8080/demo/outbox/order?item=phone"
curl -X POST "localhost:8080/demo/outbox/order?item=tablet"
{ "brokerDown": true, "ordersInDatabase": 2,
  "outboxPending": 2, "deliveredToBroker": [] }
# 4. Kafka returns; the scheduler catches up in seconds
curl -X POST "localhost:8080/demo/outbox/broker?down=false"
curl localhost:8080/demo/outbox
{ "outboxPending": 0,
  "deliveredToBroker": [
    { "topic": "orders.placed", "key": "4", ... },
    { "topic": "orders.placed", "key": "5", ... } ] }
```

A production checklist for the outbox:

- **Delivery is at-least-once.** If the relay crashes after sending but before commit, the event is sent again, so consumers must be idempotent (pattern 8). That is why each row has a unique event id.
- **Polling or CDC:** polling is simple and fine for most loads. For high volume or low latency, use Debezium with its Outbox Event Router, which reads the database log instead of polling.
- **Ordering:** use the order id as the Kafka key, so events for one order land in one partition, in order.
- **Cleanup:** delete or archive published rows after a few days, or the table grows forever.
- **Monitoring:** alert on the age of the oldest pending row. That is your "events are stuck" signal.
- **Kafka transactions** give exactly-once only inside Kafka (consume, process, produce). They do not make a database write and a Kafka send atomic, and `ChainedKafkaTransactionManager` is deprecated. The outbox is the answer.

### 8. Idempotent consumer: receive twice, apply once

> **_Real-life problem:_**_ a payment provider sends a PaymentReceived webhook. Your service credits the account but responds slowly, so the provider times out and sends it again. A Kafka rebalance or an SQS visibility timeout causes the same thing. The account is credited three times. Every at-least-once system does this sooner or later._

The naive handler:

```csharp
@Transactional
public void applyNaive(PaymentReceived event) {
  accounts.findById(event.accountId()).orElseThrow()
          .credit(event.amount());
}
curl -X POST \
  "localhost:8080/demo/idempotency/naive?eventId=pay-001&deliveries=3"
{ "outcomes": ["APPLIED", "APPLIED", "APPLIED"],
  "balanceBefore": 1000.00, "balanceAfter": 1300.00,
  "expectedAfter": 1100.00 }
```

**The fix:** record the event id in a table with a unique constraint, in the same transaction as the business change, and insert it first.

```less
@Entity
public class ProcessedMessage {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true)
  private String eventId;
  private Instant processedAt;
}
@Transactional
public void applyOnce(PaymentReceived event) {
  // INSERT now; a duplicate fails here, before the credit
  processed.saveAndFlush(new ProcessedMessage(event.eventId()));
  accounts.findById(event.accountId()).orElseThrow()
          .credit(event.amount());
}
```

The entry point is deliberately not transactional, because it must catch the duplicate after the transaction has rolled back:

```kotlin
@Component
public class PaymentReceivedConsumer {
  @Retryable(includes = PessimisticLockingFailureException.class,
             maxRetries = 5, delay = 50, jitter = 25)
  public Outcome onMessage(PaymentReceived event) {
    try {
      handler.applyOnce(event);
      return Outcome.APPLIED;
    } catch (DataIntegrityViolationException duplicate) {
      return Outcome.DUPLICATE_SKIPPED;  // ack: already handled
    }
  }
}
curl -X POST \
  "localhost:8080/demo/idempotency/safe?eventId=pay-002&deliveries=3"
{ "outcomes": ["APPLIED", "DUPLICATE_SKIPPED", "DUPLICATE_SKIPPED"],
  "balanceBefore": 1000.00, "balanceAfter": 1100.00 }
# five copies at the same moment
curl -X POST "localhost:8080/demo/idempotency/safe\
?eventId=pay-003&deliveries=5&concurrent=true"
{ "outcomes": ["DUPLICATE_SKIPPED", "APPLIED",
               "DUPLICATE_SKIPPED", "DUPLICATE_SKIPPED",
               "DUPLICATE_SKIPPED"],
  "balanceBefore": 1100.00, "balanceAfter": 1200.00 }
```

**Why not "check if it exists, then insert"?** Two copies arriving together both check, both see "not processed", and both apply. Only the database's unique constraint is safe under concurrency, and the concurrent run above proves it.

**Why the retry?** When two copies hit the unique index at the same moment, some databases report a lock error instead of a duplicate-key error. Retrying turns that into a clean duplicate.

On PostgreSQL you can use `INSERT ... ON CONFLICT DO NOTHING` and check the row count instead of catching an exception. Keep processed ids longer than your longest redelivery window, then clean them up. The id must come from the producer (the outbox event id or the provider's webhook id), never be generated by the consumer.

### 9. Saga: transactions across services

> **_Real-life problem:_**_ placing an order touches three services: inventory reserves stock, payment charges the card, shipping books a courier. Each has its own database. If the courier booking fails, the customer must be refunded and the stock released, and no single transaction covers all three._

A saga splits the flow into local transactions. Each step has a **compensating action** that undoes it:

1. Create the order as PENDING. Compensation: cancel the order.
2. Reserve stock. Compensation: release the stock.
3. Charge the payment. Compensation: refund it.
4. Book the courier. This is the last step, so there is nothing to undo.

If step 4 fails, the saga runs refund, then release stock, then cancel order.

- **Choreography:** services react to each other's events. Fine for two or three simple steps, but the full flow is hard to see.
- **Orchestration:** one orchestrator tells each service what to do. Better for longer flows, error handling and monitoring. Tools include Temporal, Camunda and AWS Step Functions.

A saga is built from the earlier patterns: each step is a local `@Transactional` method, publishes through the outbox, and has an idempotent consumer. Sagas give eventual consistency, not isolation, so use a status such as PENDING to stop other requests from acting on a half-finished order. The saga is not in the demo because it needs several services.

### Production checklist

- `spring.jpa.open-in-view=false`
- `@Transactional` on the service layer, not on controllers
- No `this.method()` calls to transactional methods
- Business exceptions are unchecked, or `rollbackFor = Exception.class` is set
- No exceptions swallowed inside transactional methods
- No HTTP, Kafka, S3 or email calls inside a database transaction
- `readOnly = true` on query methods
- `@Version` on entities updated concurrently, with retry outside the transaction
- Every pessimistic lock and every transaction has a timeout
- REQUIRES_NEW used sparingly, with the pool sized for it
- Side effects run after commit, or go through the outbox
- Every message consumer is idempotent

### Quick decision guide

- **Normal create or update in one service:** `@Transactional`
- **Queries and reports:** `readOnly = true`, optionally a read replica
- **A record that must survive a rollback:** REQUIRES_NEW, small and fast only
- **A slow external call in the middle of database work:** `TransactionTemplate` with short transactions and reconciliation
- **Concurrent updates with few conflicts:** `@Version` plus retry with backoff
- **Concurrent updates on a hot row:** a pessimistic lock with a timeout, or an atomic SQL update
- **A side effect after commit, where losing one is acceptable:** `@TransactionalEventListener(AFTER_COMMIT)`
- **A database write plus an event that must not be lost:** transactional outbox, with Debezium at scale
- **Any at-least-once message or webhook:** an idempotent consumer keyed on a unique event id
- **A business flow across services:** a saga, orchestrated for anything complex

### Spring Boot 3 or 4?

The demo uses Spring Boot 4.1, and everything here also works on Boot 3 with three changes:

- **Retry:** Boot 4 (Spring Framework 7) has `@Retryable` built in, enabled with `@EnableResilientMethods`. On Boot 3, add `spring-retry` and AOP, use `@EnableRetry`, and write `@Retryable(retryFor = ..., maxAttempts = ..., backoff = @Backoff(...))`.
- **Web starter:** `spring-boot-starter-webmvc` on Boot 4, `spring-boot-starter-web` on Boot 3.
- **JSON:** Jackson 3 (`JsonMapper`) on Boot 4, Jackson 2 (`ObjectMapper`) on Boot 3.

### Wrapping up

`@Transactional` is easy to add and easy to get wrong. The bugs it causes rarely show up in unit tests. They show up under load, during outages, and when messages arrive twice. Each one has a well-known fix, and once you have seen the bug happen, you remember it.

Clone the demo, turn on the transaction log, and break things on purpose. That is the fastest way to build the instinct.

**Source code:** [github.com/ChanukD/springboot-transaction-patterns](https://github.com/ChanukD/springboot-transaction-patterns)
