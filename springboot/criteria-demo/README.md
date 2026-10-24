# Spring Boot JPA Criteria API demo (H2)

Requirements: JDK 17+, Maven 3.9+

    mvn spring-boot:run

On startup `DataLoader` seeds H2 and `DemoRunner` prints every scenario to the console
(Hibernate SQL is logged too, so you can see what each Criteria query generates).

REST:
- http://localhost:8080/api/employees/search?name=a&department=Sales&minSalary=50000
- http://localhost:8080/api/employees/dept-stats?minHeadcount=2
- http://localhost:8080/api/employees/salary-stats

H2 console: http://localhost:8080/h2-console  (JDBC URL `jdbc:h2:mem:demo`, user `sa`, empty password)
For a file-based DB, switch the datasource URL in application.properties.
