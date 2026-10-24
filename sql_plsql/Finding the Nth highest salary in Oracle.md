# Finding the Nth highest salary in Oracle

You can find the highest-salaried employee(s) for each month, along with their salary amount, by joining `Employee` and `Salary` and using a subquery or a window function.

### SQL Query (using `MAX()`)

```
SELECT e.empID, e.empName, s.month, s.amount
FROM Employee e
JOIN Salary s
    ON e.empID = s.empID
WHERE s.amount = (
    SELECT MAX(s2.amount)
    FROM Salary s2
    WHERE s2.month = s.month
)
ORDER BY s.month;
```

### Explanation

1. The subquery finds the maximum salary amount for each month.
2. The outer query retrieves the employee details whose salary matches that maximum.
3. If multiple employees tie for the highest salary in a month, all of them are returned.

### Example output

Suppose the tables contain:

Employee

| empID | empName |
| ----- | ------- |
| 1     | Rahul   |
| 2     | Priya   |
| 3     | Amit    |

Salary

| empID | month | amount |
| ----- | ----- | ------ |
| 1     | Jan   | 50000  |
| 2     | Jan   | 60000  |
| 3     | Jan   | 60000  |
| 1     | Feb   | 55000  |
| 2     | Feb   | 45000  |
| 3     | Feb   | 65000  |

Result

| empID | empName | month | amount |
| ----- | ------- | ----- | ------ |
| 2     | Priya   | Jan   | 60000  |
| 3     | Amit    | Jan   | 60000  |
| 3     | Amit    | Feb   | 65000  |

Note: If your `month` column contains values such as `Jan` across multiple years, include a `year` column in the grouping and subquery conditions to calculate the highest salary separately for each month of each year.



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
