# Finding the Nth highest salary in Oracle

In Oracle SQL, you can find the Nth highest salary using `DENSE_RANK()`, a correlated subquery, or `FETCH`. The best method depends on whether you want distinct salary amounts or the Nth row.

## 1. Using `DENSE_RANK()` — Recommended

Find the Nth highest distinct salary.

```
SELECT empID, empName, amount
FROM (
    SELECT e.empID,
           e.empName,
           s.amount,
           DENSE_RANK() OVER (
               ORDER BY s.amount DESC
           ) AS rnk
    FROM Employee e
    JOIN Salary s
        ON e.empID = s.empID
)
WHERE rnk = &N;
```

Replace `&N` with the required rank when prompted by Oracle SQL Developer or SQL\*Plus.

For example, to find the 3rd highest salary, use `rnk = 3`.

## 2. Using a subquery with `DISTINCT`

```
SELECT MAX(amount) AS nth_highest_salary
FROM Salary
WHERE amount < (
    SELECT MAX(amount)
    FROM Salary
    WHERE amount < (
        SELECT MAX(amount)
        FROM Salary
    )
);
```

This example finds the 3rd highest distinct salary amount. For a general Nth salary, the query becomes more complicated.

## 3. Using `ROW_NUMBER()`

```
SELECT empID, empName, amount
FROM (
    SELECT e.empID,
           e.empName,
           s.amount,
           ROW_NUMBER() OVER (
               ORDER BY s.amount DESC
           ) AS rn
    FROM Employee e
    JOIN Salary s
        ON e.empID = s.empID
)
WHERE rn = &N;
```

This returns exactly one row for rank N, assuming enough rows exist. If salaries tie, it still assigns different row numbers, so which employee gets which number may be arbitrary unless you add a tie-breaker.

## Difference between the methods

| Function       | How ties are handled                  | Result                                                         |
| -------------- | ------------------------------------- | -------------------------------------------------------------- |
| `DENSE_RANK()` | Same rank for equal salaries; no gaps | Nth distinct salary, including all employees tied at that rank |
| `RANK()`       | Same rank for ties; gaps afterward    | Competition ranking                                            |
| `ROW_NUMBER()` | Different number for every row        | Nth row, not necessarily Nth distinct salary                   |

Interview tip: If asked, “Find the 2nd highest salary in Oracle,” a common answer is:

```
SELECT MAX(amount)
FROM Salary
WHERE amount < (SELECT MAX(amount) FROM Salary);
```

This returns the second-highest distinct salary amount, or `NULL` if there is no lower salary.

---

The most optimized and recommended way in Oracle SQL is `DENSE_RANK()` when you need the Nth highest distinct salary and the employee details.

### Recommended query

```
SELECT empID, empName, amount
FROM (
    SELECT e.empID,
           e.empName,
           s.amount,
           DENSE_RANK() OVER (
               ORDER BY s.amount DESC
           ) AS rnk
    FROM Employee e
    JOIN Salary s
      ON e.empID = s.empID
)
WHERE rnk = :N;
```

Replace `:N` with a bind variable containing the desired rank, such as `3`.

### Why this approach?

- Handles ties: Returns all employees sharing the Nth-highest salary.
- Single ranking operation: Avoids repeatedly executing nested subqueries.
- Readable and maintainable: Easy to adapt for other ranking requirements.

### If you only need the salary amount

For the Nth-highest distinct salary, this is also a good option:

```
SELECT DISTINCT amount
FROM Salary
ORDER BY amount DESC
OFFSET :N_MINUS_1 ROWS
FETCH NEXT 1 ROW ONLY;
```

Here, `:N_MINUS_1` is `N - 1`. This syntax requires Oracle 12c or later. For example, for the third-highest salary, use `OFFSET 2 ROWS`.

### Performance considerations

There is no universally fastest query; performance depends on table size, indexes, data distribution, and the execution plan.

- For employee details and ties, start with `DENSE_RANK()`.
- For just one distinct salary amount, `DISTINCT` with `OFFSET/FETCH` is straightforward, but may require sorting and duplicate elimination.
- For large tables, inspect the execution plan using `DBMS_XPLAN.DISPLAY_CURSOR` and benchmark with representative data.

Interview answer: Use `DENSE_RANK()` for the Nth-highest salary when ties must be included.
