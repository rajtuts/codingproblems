Employee {empID(pk), empName, DOB};
Salary {empID(fk), month, amount};

Highest salaried employee names for each month with amount details
---
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


