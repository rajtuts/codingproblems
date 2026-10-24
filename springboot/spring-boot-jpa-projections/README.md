# Spring Boot JPA Projections (H2)

Requirements: JDK 17+, Maven 3.6+

Run:
    mvn spring-boot:run

- Console output shows all 11 projection scenarios (CommandLineRunner).
- REST: http://localhost:8080/api/employees/summary
        http://localhost:8080/api/employees/stats
        http://localhost:8080/api/employees/dept/Engineering?view=dto
- H2 console: http://localhost:8080/h2-console
  JDBC URL: jdbc:h2:mem:projdb   user: sa   password: (empty)
