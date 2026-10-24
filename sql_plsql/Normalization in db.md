# Database Normalization (DBMS)

Normalization is the process of organizing data in database tables to reduce data redundancy and prevent insertion, update, and deletion anomalies.

The main goal is to store data efficiently while maintaining data consistency.

## Why do we need normalization?

Suppose we have this table:

| EmpID | EmpName | DeptID | DeptName |
| ----- | ------- | ------ | -------- |
| 1     | Rahul   | 10     | IT       |
| 2     | Priya   | 10     | IT       |
| 3     | Amit    | 20     | HR       |

Here, `DeptName` is repeated for every employee in the same department.

Problems:

- Update anomaly: If the IT department name changes, multiple rows must be updated.
- Insertion anomaly: We cannot store a department until an employee exists, depending on the table design.
- Deletion anomaly: Deleting the last employee in a department could accidentally remove the department information.

Normalization solves these problems by splitting the data into related tables.

## Types of normalization

## 1NF

First Normal Form

Each column must contain a single, atomic value. No repeating groups or multiple values in one cell.

Example: Store phone numbers in separate rows instead of `9876, 8765` in one cell.

## 2NF

Second Normal Form

Must be in 1NF, and every non-key attribute must depend on the whole candidate key, not just part of a composite key.

Removes partial dependencies.

## 3NF

Third Normal Form

Must be in 2NF, and non-key attributes must not depend transitively on a candidate key.

Removes transitive dependencies between non-key attributes.

## BCNF

Boyce–Codd Normal Form

For every non-trivial functional dependency \\(X \to Y\\), \\(X\\) must be a superkey.

A stricter version of 3NF.

## 4NF

Fourth Normal Form

Must be in BCNF and eliminate non-trivial multivalued dependencies unless the determinant is a superkey.

## 5NF

Fifth Normal Form

Eliminates redundancy caused by join dependencies that cannot be resolved by simpler decompositions.

## Example: Converting a table to 3NF

Original table:

`Employee(EmpID, EmpName, DeptID, DeptName)`

Functional dependencies:

- `EmpID → EmpName, DeptID`
- `DeptID → DeptName`

Because `DeptName` depends on `DeptID`, rather than directly on the employee key, there is a transitive dependency.

Decompose the table into:

Employee

| EmpID (PK) | EmpName | DeptID (FK) |
| ---------- | ------- | ----------- |
| 1          | Rahul   | 10          |
| 2          | Priya   | 10          |
| 3          | Amit    | 20          |

Department

| DeptID (PK) | DeptName |
| ----------- | -------- |
| 10          | IT       |
| 20          | HR       |

Now, each department name is stored only once, and the tables are connected through `DeptID`.

## Quick interview revision

| Normal form | Main rule                                                              |
| ----------- | ---------------------------------------------------------------------- |
| 1NF         | Atomic values; no repeating groups                                     |
| 2NF         | No partial dependency on a composite candidate key                     |
| 3NF         | No transitive dependency of non-key attributes on a candidate key      |
| BCNF        | Every determinant of a non-trivial functional dependency is a superkey |
| 4NF         | No problematic multivalued dependencies                                |
| 5NF         | No problematic join dependencies                                       |

Interview tip: Most practical database designs focus on 1NF, 2NF, and 3NF, with BCNF used when additional dependency-related anomalies remain. Normalization is about dependencies and keys, not merely splitting large tables into smaller ones.

---

Let's understand “non-key attributes must not depend transitively on a candidate key” with a simple example.

## 1. What is a transitive dependency?

A transitive dependency occurs when a key determines one attribute, and that attribute determines another attribute.

For example:

\\[ A \rightarrow B \rightarrow C \\]

This means:

- A determines B.
- B determines C.
- Therefore, A indirectly determines C.

If `A` is a candidate key and `B` is a non-key attribute, this can create a transitive dependency involving another non-key attribute `C`.

## 2. Example using an Employee table

Suppose we have this table:

| EmpID (PK) | EmpName | DeptID | DeptName |
| ---------- | ------- | ------ | -------- |
| 101        | Rahul   | D1     | IT       |
| 102        | Priya   | D2     | HR       |
| 103        | Amit    | D1     | IT       |

Assume these functional dependencies:

\\[ EmpID \rightarrow DeptID \\]

\\[ DeptID \rightarrow DeptName \\]

Therefore:

\\[ EmpID \rightarrow DeptID \rightarrow DeptName \\]

Here, `EmpID` determines `DeptName` indirectly through `DeptID`.

- `EmpID` is the candidate key.
- `DeptID` is a non-key attribute.
- `DeptName` is a non-key attribute.

So, `DeptName` has a transitive dependency on `EmpID`.

## 3. How do we fix it in 3NF?

Split the original table into two tables.

### Employee

| EmpID (PK) | EmpName | DeptID (FK) |
| ---------- | ------- | ----------- |
| 101        | Rahul   | D1          |
| 102        | Priya   | D2          |
| 103        | Amit    | D1          |

### Department

| DeptID (PK) | DeptName |
| ----------- | -------- |
| D1          | IT       |
| D2          | HR       |

Now:

- In `Employee`, `EmpID → EmpName, DeptID`.
- In `Department`, `DeptID → DeptName`.

The department name is stored only once per department, reducing redundancy.

## 4. Remember this for interviews

Third Normal Form (3NF): A table must be in 2NF, and no non-prime attribute should depend transitively on a candidate key through another non-superkey attribute.

An easy way to remember it:

- 1NF: One value per cell.
- 2NF: No partial dependency on a candidate key.
- 3NF: No transitive dependency of non-prime attributes on a candidate key.

One important detail: a transitive dependency is not automatically a 3NF violation in every possible case. The formal 3NF rule allows a functional dependency \\(X \rightarrow A\\) if \\(X\\) is a superkey or \\(A\\) is a prime attribute (part of some candidate key).
