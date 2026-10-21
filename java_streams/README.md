# Java Stream Intermediate Operations: Simple Examples

Intermediate operations return a new stream and are **lazy**: nothing runs until a terminal operation (like `toList()` or `forEach`) is called.

**Common imports:**
```java
import java.util.*;
import java.util.stream.*;
```

---

## 1. `filter(Predicate)`: keep elements that match

```java
List<Integer> nums = List.of(1, 2, 3, 4, 5, 6);
List<Integer> even = nums.stream()
    .filter(n -> n % 2 == 0)
    .toList();
System.out.println(even);   // [2, 4, 6]
```

## 2. `map(Function)`: transform each element 1-to-1

```java
List<String> names = List.of("java", "spring", "stream");
List<String> upper = names.stream()
    .map(String::toUpperCase)
    .toList();
System.out.println(upper);   // [JAVA, SPRING, STREAM]
```

## 3. `flatMap(Function)`: transform and flatten nested streams

```java
List<List<Integer>> nested = List.of(List.of(1, 2), List.of(3, 4), List.of(5));
List<Integer> flat = nested.stream()
    .flatMap(List::stream)
    .toList();
System.out.println(flat);   // [1, 2, 3, 4, 5]
```

## 4. `distinct()`: remove duplicates

```java
List<Integer> nums = List.of(1, 2, 2, 3, 3, 3, 4);
List<Integer> unique = nums.stream()
    .distinct()
    .toList();
System.out.println(unique);   // [1, 2, 3, 4]
```

## 5. `sorted()` / `sorted(Comparator)`: sort elements

```java
List<Integer> nums = List.of(5, 3, 8, 1);

List<Integer> asc = nums.stream().sorted().toList();
System.out.println(asc);    // [1, 3, 5, 8]

List<Integer> desc = nums.stream()
    .sorted(Comparator.reverseOrder())
    .toList();
System.out.println(desc);   // [8, 5, 3, 1]
```

## 6. `limit(n)`: take the first n elements

```java
List<Integer> firstThree = Stream.of(10, 20, 30, 40, 50)
    .limit(3)
    .toList();
System.out.println(firstThree);   // [10, 20, 30]
```

## 7. `skip(n)`: discard the first n elements

```java
List<Integer> rest = Stream.of(10, 20, 30, 40, 50)
    .skip(2)
    .toList();
System.out.println(rest);   // [30, 40, 50]
```

Combine `skip` and `limit` for pagination:
```java
Stream.of(1,2,3,4,5,6,7,8,9,10)
    .skip(3).limit(4)
    .toList();   // [4, 5, 6, 7]
```

## 8. `peek(Consumer)`: look at elements (mainly for debugging)

```java
List<Integer> result = Stream.of(1, 2, 3)
    .peek(n -> System.out.println("Before: " + n))
    .map(n -> n * 10)
    .peek(n -> System.out.println("After: " + n))
    .toList();
// Before: 1, After: 10, Before: 2, After: 20, Before: 3, After: 30
```

## 9. `takeWhile(Predicate)` (Java 9+): take until condition fails

```java
List<Integer> result = Stream.of(1, 2, 3, 10, 4, 5)
    .takeWhile(n -> n < 5)
    .toList();
System.out.println(result);   // [1, 2, 3]
```

## 10. `dropWhile(Predicate)` (Java 9+): skip until condition fails

```java
List<Integer> result = Stream.of(1, 2, 3, 10, 4, 5)
    .dropWhile(n -> n < 5)
    .toList();
System.out.println(result);   // [10, 4, 5]
```

## 11. `mapToInt` / `mapToDouble` / `mapToLong`: convert to primitive streams

```java
List<String> words = List.of("a", "bb", "ccc");
int totalLength = words.stream()
    .mapToInt(String::length)
    .sum();
System.out.println(totalLength);   // 6
```

## 12. `boxed()`: convert a primitive stream back to objects

```java
List<Integer> list = IntStream.rangeClosed(1, 5)
    .boxed()
    .toList();
System.out.println(list);   // [1, 2, 3, 4, 5]
```

## 13. `mapToObj()`: convert a primitive stream to objects

```java
String result = IntStream.rangeClosed(1, 3)
    .mapToObj(i -> "Item" + i)
    .collect(Collectors.joining(", "));
System.out.println(result);   // Item1, Item2, Item3
```

## 14. `asLongStream()` / `asDoubleStream()`: widen primitive streams

```java
double[] d = IntStream.of(1, 2, 3)
    .asDoubleStream()
    .toArray();
System.out.println(Arrays.toString(d));   // [1.0, 2.0, 3.0]
```

## 15. `mapMulti()` (Java 16+): emit zero or more elements per input

```java
List<Integer> result = Stream.of(1, 2, 3)
    .<Integer>mapMulti((n, sink) -> {
        sink.accept(n);
        sink.accept(n * 10);
    })
    .toList();
System.out.println(result);   // [1, 10, 2, 20, 3, 30]
```

## 16. `parallel()` / `sequential()` / `unordered()` / `onClose()`: stream mode controls

```java
int sum = IntStream.rangeClosed(1, 100)
    .parallel()
    .sum();
System.out.println(sum);   // 5050

List<Integer> list = Stream.of(3, 1, 2)
    .parallel()
    .sequential()
    .toList();
```

---

## Chaining Example

```java
List<String> result = Stream.of("apple", "banana", "cherry", "avocado", "apple", "blueberry")
    .filter(s -> s.length() > 5)     // keep longer words
    .map(String::toUpperCase)         // convert to uppercase
    .distinct()                       // remove duplicates
    .sorted()                         // sort alphabetically
    .limit(3)                         // first 3
    .toList();
System.out.println(result);   // [AVOCADO, BANANA, BLUEBERRY]
```

---

## Quick Summary

| Operation | Purpose | Stateful? | Short-circuit? |
|---|---|---|---|
| `filter` | Keep matching | No | No |
| `map` | Transform 1-to-1 | No | No |
| `flatMap` | Transform and flatten | No | No |
| `mapMulti` | Emit 0..n per element | No | No |
| `peek` | Debug/inspect | No | No |
| `distinct` | Remove duplicates | Yes | No |
| `sorted` | Sort | Yes | No |
| `skip` | Discard first n | Yes | No |
| `limit` | Take first n | Yes | Yes |
| `takeWhile` | Take while true | Yes | Yes |
| `dropWhile` | Drop while true | Yes | No |
| `boxed`, `mapToObj`, `mapToInt` | Convert stream types | No | No |


# Java Stream Intermediate Operations: Some more

---

## 1. `mapToLong()` / `mapToDouble()`
I only showed `mapToInt` earlier.

```java
List<String> words = List.of("a", "bb", "ccc");

long total = words.stream().mapToLong(String::length).sum();
System.out.println(total);   // 6

double avg = words.stream().mapToDouble(String::length).average().orElse(0);
System.out.println(avg);     // 2.0
```

## 2. `flatMapToInt()` / `flatMapToLong()` / `flatMapToDouble()`
Flatten into a primitive stream, avoiding boxing.

```java
int sum = Stream.of(new int[]{1, 2}, new int[]{3, 4})
    .flatMapToInt(Arrays::stream)
    .sum();
System.out.println(sum);   // 10

List<String> chars = Stream.of("abc", "de")
    .flatMapToInt(String::chars)
    .mapToObj(c -> String.valueOf((char) c))
    .toList();
System.out.println(chars); // [a, b, c, d, e]
```

## 3. `mapMultiToInt()` / `mapMultiToLong()` / `mapMultiToDouble()` (Java 16+)
Primitive versions of `mapMulti`.

```java
int total = Stream.of("1,2", "3")
    .mapMultiToInt((s, sink) -> {
        for (String p : s.split(",")) sink.accept(Integer.parseInt(p));
    })
    .sum();
System.out.println(total);   // 6
```

## 4. `unordered()`
In my last answer I listed it but gave no example. It tells the stream that encounter order doesn't matter, which can speed up parallel `distinct()` and `limit()`.

```java
List<Integer> result = IntStream.rangeClosed(1, 1000)
    .boxed()
    .parallel()
    .unordered()
    .distinct()
    .limit(5)
    .toList();   // any 5 elements, order not guaranteed
```

## 5. `onClose(Runnable)`
Also listed without an example. It registers a handler that runs when the stream is closed.

```java
try (Stream<String> s = Stream.of("a", "b")
        .onClose(() -> System.out.println("Stream closed"))) {
    s.forEach(System.out::println);
}
// a
// b
// Stream closed
```

## 6. `parallel()` and `sequential()` separately
The last one wins for the whole pipeline.

```java
Stream.of(1, 2, 3).parallel().sequential().isParallel();  // false
Stream.of(1, 2, 3).sequential().parallel().isParallel();  // true
```

## 7. `gather()` (Java 24+)
This is the newest intermediate operation, which lets you write custom intermediate operations. The built-in `Gatherers` cover common cases.

```java
import java.util.stream.Gatherers;

// Fixed windows
Stream.of(1, 2, 3, 4, 5).gather(Gatherers.windowFixed(2)).toList();
// [[1, 2], [3, 4], [5]]

// Sliding windows
Stream.of(1, 2, 3, 4, 5).gather(Gatherers.windowSliding(3)).toList();
// [[1, 2, 3], [2, 3, 4], [3, 4, 5]]

// Running total
Stream.of(1, 2, 3, 4).gather(Gatherers.scan(() -> 0, (acc, n) -> acc + n)).toList();
// [1, 3, 6, 10]
```

---

## Primitive-Stream Variants of Common Operations
`IntStream`, `LongStream` and `DoubleStream` have their own `map`, `filter`, `distinct`, `sorted`, `limit`, `skip` and `peek`, and they work like the object versions without boxing.

```java
IntStream.rangeClosed(1, 10)
    .filter(n -> n % 2 == 1)
    .map(n -> n * n)
    .boxed()
    .toList();   // [1, 9, 25, 49, 81]
```

## Related Static Helpers
These aren't intermediate operations, but they are often asked about with them.

```java
// concat: combine two streams
Stream.concat(Stream.of(1, 2), Stream.of(3, 4)).toList();   // [1, 2, 3, 4]

// ofNullable (Java 9+): empty stream if null
Stream.ofNullable(null).count();   // 0
```

---

## Complete List of Intermediate Operations

| Category | Operations |
|---|---|
| Filtering | `filter`, `distinct`, `limit`, `skip`, `takeWhile`, `dropWhile` |
| Mapping | `map`, `mapToInt`, `mapToLong`, `mapToDouble`, `mapToObj`, `boxed`, `asLongStream`, `asDoubleStream` |
| Flattening | `flatMap`, `flatMapToInt`, `flatMapToLong`, `flatMapToDouble` |
| Multi-emit | `mapMulti`, `mapMultiToInt`, `mapMultiToLong`, `mapMultiToDouble` |
| Ordering | `sorted`, `unordered` |
| Debug | `peek` |
| Mode/lifecycle | `parallel`, `sequential`, `onClose` |
| Custom (Java 24+) | `gather` |


# Java Stream Terminal Operations: One Example Each

Terminal operations trigger the pipeline and produce a result or side effect. After one runs, the stream is closed and cannot be reused.

**Common imports:**
```java
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
```

---

## 1. `forEach(Consumer)`: perform an action on each element

```java
Stream.of("a", "b", "c").forEach(System.out::println);
// a
// b
// c
```

## 2. `forEachOrdered(Consumer)`: same, but respects encounter order in parallel streams

```java
Stream.of(1, 2, 3, 4).parallel().forEachOrdered(System.out::println);
// 1 2 3 4 (always in order)
```

## 3. `collect(Collector)`: gather into a container

```java
List<String> list = Stream.of("x", "y", "z").collect(Collectors.toList());
System.out.println(list);   // [x, y, z]
```

## 4. `collect(supplier, accumulator, combiner)`: three-argument form

```java
ArrayList<String> list = Stream.of("a", "b")
    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
System.out.println(list);   // [a, b]
```

## 5. `toList()` (Java 16+): unmodifiable list

```java
List<Integer> list = Stream.of(3, 1, 2).toList();
System.out.println(list);   // [3, 1, 2]
```

## 6. `toArray()`: convert to an array

```java
String[] arr = Stream.of("a", "b", "c").toArray(String[]::new);
System.out.println(Arrays.toString(arr));   // [a, b, c]
```

## 7. `reduce(identity, BinaryOperator)`: combine with a starting value

```java
int sum = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
System.out.println(sum);   // 10
```

## 8. `reduce(BinaryOperator)`: combine without identity, returns Optional

```java
Optional<Integer> max = Stream.of(4, 9, 2).reduce(Integer::max);
System.out.println(max.get());   // 9
```

## 9. `reduce(identity, accumulator, combiner)`: when result type differs

```java
int totalLength = Stream.of("a", "bb", "ccc")
    .reduce(0, (acc, s) -> acc + s.length(), Integer::sum);
System.out.println(totalLength);   // 6
```

## 10. `count()`: number of elements

```java
long count = Stream.of("a", "b", "c").count();
System.out.println(count);   // 3
```

## 11. `min(Comparator)`: smallest element

```java
Optional<Integer> min = Stream.of(5, 2, 8).min(Comparator.naturalOrder());
System.out.println(min.get());   // 2
```

## 12. `max(Comparator)`: largest element

```java
Optional<String> longest = Stream.of("hi", "hello", "hey")
    .max(Comparator.comparingInt(String::length));
System.out.println(longest.get());   // hello
```

## 13. `findFirst()`: first element in encounter order

```java
Optional<Integer> first = Stream.of(10, 20, 30).findFirst();
System.out.println(first.get());   // 10
```

## 14. `findAny()`: any element (better for parallel streams)

```java
Optional<Integer> any = Stream.of(10, 20, 30).parallel().findAny();
System.out.println(any.isPresent());   // true
```

## 15. `anyMatch(Predicate)`: is at least one element matching?

```java
boolean result = Stream.of(1, 2, 3).anyMatch(n -> n > 2);
System.out.println(result);   // true
```

## 16. `allMatch(Predicate)`: do all elements match?

```java
boolean result = Stream.of(2, 4, 6).allMatch(n -> n % 2 == 0);
System.out.println(result);   // true
```

## 17. `noneMatch(Predicate)`: does no element match?

```java
boolean result = Stream.of(1, 3, 5).noneMatch(n -> n % 2 == 0);
System.out.println(result);   // true
```

## 18. `iterator()`: get an Iterator over the elements

```java
Iterator<String> it = Stream.of("a", "b").iterator();
while (it.hasNext()) System.out.println(it.next());
// a
// b
```

## 19. `spliterator()`: get a Spliterator (advanced use)

```java
Spliterator<Integer> sp = Stream.of(1, 2, 3).spliterator();
sp.forEachRemaining(System.out::println);
// 1 2 3
```

---

## Primitive Stream Terminal Operations

`IntStream`, `LongStream` and `DoubleStream` add these.

### 20. `sum()`
```java
int sum = IntStream.rangeClosed(1, 5).sum();
System.out.println(sum);   // 15
```

### 21. `average()`: returns OptionalDouble
```java
double avg = IntStream.of(2, 4, 6).average().orElse(0);
System.out.println(avg);   // 4.0
```

### 22. `summaryStatistics()`: count, sum, min, max, average in one pass
```java
IntSummaryStatistics stats = IntStream.of(3, 7, 1, 9).summaryStatistics();
System.out.println(stats);
// IntSummaryStatistics{count=4, sum=20, min=1, average=5.000000, max=9}
```

### 23. `min()` / `max()` (no comparator needed)
```java
int max = IntStream.of(3, 7, 1).max().getAsInt();
System.out.println(max);   // 7
```

---

## `collect()` with Common Collectors

`collect` is the most flexible terminal operation. These are the collectors you will use most.

```java
List<String> words = List.of("apple", "banana", "avocado", "cherry");

// toSet
Set<Integer> lengths = words.stream().map(String::length).collect(Collectors.toSet());

// toMap
Map<String, Integer> map = words.stream()
    .collect(Collectors.toMap(w -> w, String::length));

// joining
String joined = words.stream().collect(Collectors.joining(", "));
// apple, banana, avocado, cherry

// groupingBy
Map<Character, List<String>> byFirst = words.stream()
    .collect(Collectors.groupingBy(w -> w.charAt(0)));
// {a=[apple, avocado], b=[banana], c=[cherry]}

// partitioningBy
Map<Boolean, List<String>> parts = words.stream()
    .collect(Collectors.partitioningBy(w -> w.length() > 5));

// counting
long n = words.stream().collect(Collectors.counting());

// summingInt / averagingInt
int totalLen = words.stream().collect(Collectors.summingInt(String::length));
double avgLen = words.stream().collect(Collectors.averagingInt(String::length));

// toUnmodifiableList / toUnmodifiableSet / toUnmodifiableMap
List<String> immutable = words.stream().collect(Collectors.toUnmodifiableList());

// teeing (Java 12+)
double avgViaTeeing = words.stream().collect(Collectors.teeing(
    Collectors.summingInt(String::length),
    Collectors.counting(),
    (sum, c) -> (double) sum / c));
```

---

## Summary Table

| Operation | Returns | Short-circuit? |
|---|---|---|
| `forEach` / `forEachOrdered` | `void` | No |
| `collect` | Container | No |
| `toList` / `toArray` | List / array | No |
| `reduce` | T or `Optional<T>` | No |
| `count` | `long` | No |
| `min` / `max` | `Optional<T>` | No |
| `findFirst` / `findAny` | `Optional<T>` | Yes |
| `anyMatch` / `allMatch` / `noneMatch` | `boolean` | Yes |
| `iterator` / `spliterator` | Iterator / Spliterator | N/A |
| `sum` / `average` / `summaryStatistics` | Primitive results (Int/Long/Double streams only) | No |

**Key rule:** a stream pipeline runs only when a terminal operation is called, and a stream can have only one terminal operation.


# Java `collect()` Operations: Some more

Earlier I covered `toList`, `toSet`, `toMap`, `joining`, `groupingBy`, `partitioningBy`, `counting`, `summingInt`, `averagingInt`, `toUnmodifiableList` and `teeing`. These were missing or only shown in passing.

**Setup:**
```java
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

record Emp(String name, String dept, double salary, int age) {}

List<Emp> emps = List.of(
    new Emp("Asha",  "IT", 90000, 30),
    new Emp("Ravi",  "IT", 75000, 26),
    new Emp("Meena", "HR", 60000, 35),
    new Emp("Neha",  "HR", 65000, 28)
);
```

---

## 1. `toCollection(Supplier)`: collect into a specific collection type
```java
TreeSet<String> sorted = emps.stream()
    .map(Emp::name)
    .collect(Collectors.toCollection(TreeSet::new));
System.out.println(sorted);   // [Asha, Meena, Neha, Ravi]
```

## 2. `toUnmodifiableSet()` / `toUnmodifiableMap()`
```java
Set<String> depts = emps.stream().map(Emp::dept)
    .collect(Collectors.toUnmodifiableSet());

Map<String, Double> m = emps.stream()
    .collect(Collectors.toUnmodifiableMap(Emp::name, Emp::salary));
```

## 3. `toConcurrentMap()`: thread-safe map for parallel streams
```java
Map<String, Double> cm = emps.parallelStream()
    .collect(Collectors.toConcurrentMap(Emp::name, Emp::salary));
```

## 4. `summingLong()` / `summingDouble()`
```java
double totalSalary = emps.stream().collect(Collectors.summingDouble(Emp::salary));
System.out.println(totalSalary);   // 290000.0

long totalAge = emps.stream().collect(Collectors.summingLong(Emp::age));
System.out.println(totalAge);      // 119
```

## 5. `averagingLong()` / `averagingDouble()`
```java
double avgSalary = emps.stream().collect(Collectors.averagingDouble(Emp::salary));
System.out.println(avgSalary);   // 72500.0
```

## 6. `summarizingInt()` / `summarizingLong()` / `summarizingDouble()`
Count, sum, min, max and average in one pass.
```java
IntSummaryStatistics s = emps.stream()
    .collect(Collectors.summarizingInt(Emp::age));
System.out.println(s.getMin() + " " + s.getMax() + " " + s.getAverage());
// 26 35 29.75
```

## 7. `minBy()` / `maxBy()`
```java
Optional<Emp> top = emps.stream()
    .collect(Collectors.maxBy(Comparator.comparingDouble(Emp::salary)));
System.out.println(top.get().name());   // Asha

Optional<Emp> youngest = emps.stream()
    .collect(Collectors.minBy(Comparator.comparingInt(Emp::age)));
System.out.println(youngest.get().name());   // Ravi
```

## 8. `mapping(Function, downstream)`: transform before collecting
```java
Map<String, List<String>> namesByDept = emps.stream()
    .collect(Collectors.groupingBy(Emp::dept,
             Collectors.mapping(Emp::name, Collectors.toList())));
// {HR=[Meena, Neha], IT=[Asha, Ravi]}
```

## 9. `filtering(Predicate, downstream)` (Java 9+)
Unlike a `filter()` before grouping, this **keeps the group key even if empty**.
```java
Map<String, List<String>> highEarners = emps.stream()
    .collect(Collectors.groupingBy(Emp::dept,
             Collectors.filtering(e -> e.salary() > 70000,
             Collectors.mapping(Emp::name, Collectors.toList()))));
// {HR=[], IT=[Asha, Ravi]}
```

## 10. `flatMapping(Function, downstream)` (Java 9+)
```java
Map<String, Set<Character>> lettersByDept = emps.stream()
    .collect(Collectors.groupingBy(Emp::dept,
             Collectors.flatMapping(e -> e.name().chars().mapToObj(c -> (char) c),
             Collectors.toSet())));
```

## 11. `collectingAndThen(downstream, finisher)`: post-process the result
```java
List<String> unmodifiable = emps.stream()
    .map(Emp::name)
    .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));

// Unwrap an Optional from maxBy
Map<String, Emp> topPerDept = emps.stream()
    .collect(Collectors.groupingBy(Emp::dept,
             Collectors.collectingAndThen(
                 Collectors.maxBy(Comparator.comparingDouble(Emp::salary)),
                 Optional::get)));
```

## 12. `reducing()`: three forms
```java
// (a) With identity
double total = emps.stream()
    .collect(Collectors.reducing(0.0, Emp::salary, Double::sum));

// (b) Without identity, returns Optional
Optional<Emp> oldest = emps.stream()
    .collect(Collectors.reducing((a, b) -> a.age() > b.age() ? a : b));

// (c) Inside groupingBy
Map<String, Double> deptTotal = emps.stream()
    .collect(Collectors.groupingBy(Emp::dept,
             Collectors.reducing(0.0, Emp::salary, Double::sum)));
// {HR=125000.0, IT=165000.0}
```

## 13. `groupingBy()` with a map factory (three-argument form)
```java
Map<String, Long> sortedGroups = emps.stream()
    .collect(Collectors.groupingBy(Emp::dept, TreeMap::new, Collectors.counting()));
// {HR=2, IT=2} (sorted by key)
```

## 14. `groupingByConcurrent()`: for parallel streams
```java
ConcurrentMap<String, List<Emp>> cg = emps.parallelStream()
    .collect(Collectors.groupingByConcurrent(Emp::dept));
```

## 15. `partitioningBy()` with a downstream collector
```java
Map<Boolean, Long> counts = emps.stream()
    .collect(Collectors.partitioningBy(e -> e.age() >= 30, Collectors.counting()));
// {false=2, true=2}
```

## 16. `toMap()` three- and four-argument forms
```java
// Merge duplicate keys
Map<String, Double> sumByDept = emps.stream()
    .collect(Collectors.toMap(Emp::dept, Emp::salary, Double::sum));

// Merge + custom map type
Map<String, Double> ordered = emps.stream()
    .collect(Collectors.toMap(Emp::dept, Emp::salary, Double::sum, LinkedHashMap::new));
```

## 17. `joining()`: all three forms
```java
Stream.of("a", "b", "c").collect(Collectors.joining());              // abc
Stream.of("a", "b", "c").collect(Collectors.joining(", "));          // a, b, c
Stream.of("a", "b", "c").collect(Collectors.joining(", ", "[", "]")); // [a, b, c]
```

## 18. `Collector.of()`: build your own custom collector
```java
Collector<String, StringBuilder, String> concat = Collector.of(
    StringBuilder::new,                 // supplier
    (sb, s) -> sb.append(s).append("|"), // accumulator
    StringBuilder::append,              // combiner
    StringBuilder::toString             // finisher
);
System.out.println(Stream.of("x", "y", "z").collect(concat));   // x|y|z|
```

## 19. Nested downstream collectors (multi-level)
```java
Map<String, Map<Boolean, List<String>>> nested = emps.stream()
    .collect(Collectors.groupingBy(Emp::dept,
             Collectors.partitioningBy(e -> e.salary() > 70000,
             Collectors.mapping(Emp::name, Collectors.toList()))));
// {HR={false=[Meena, Neha], true=[]}, IT={false=[], true=[Asha, Ravi]}}
```

---

## Complete `Collectors` Reference

| Category | Collectors |
|---|---|
| To collection | `toList`, `toSet`, `toCollection`, `toUnmodifiableList`, `toUnmodifiableSet` |
| To map | `toMap` (2/3/4 args), `toUnmodifiableMap`, `toConcurrentMap` |
| Grouping | `groupingBy` (1/2/3 args), `groupingByConcurrent`, `partitioningBy` (1/2 args) |
| Numeric | `counting`, `summingInt/Long/Double`, `averagingInt/Long/Double`, `summarizingInt/Long/Double` |
| Min/Max | `minBy`, `maxBy` |
| Strings | `joining` (0/1/3 args) |
| Downstream adapters | `mapping`, `filtering`, `flatMapping`, `collectingAndThen`, `reducing` (1/2/3 args) |
| Combine | `teeing` (Java 12+) |
| Custom | `Collector.of` |

## Note on the 3-argument `collect()`
This one was covered earlier, not a `Collectors` method:
```java
stream.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
```
