# Multi-Database Spring Boot Application

This project is a Spring Boot REST API that connects to multiple
databases (**PostgreSQL** and **Oracle**), manages schema
changes using **Liquibase**, and provides documentation
with **OpenAPI/Swagger**. It includes integration and unit
tests using **Testcontainers**.

---

## Features

- Java 17, Gradle Build
- Spring Boot (REST API)
- Multi-Database support (PostgreSQL, Oracle)
- Liquibase for DB migrations (separate changelogs per DB)
- Swagger/OpenAPI documentation
- Unit and integration tests (Testcontainers)
- Docker-compatible setup

---

###  Prerequisites

- Java 17+
- Docker
- Gradle

---

### Run Locally

- **Build the project with Gradle**
- **Start the required databases using Docker Compose**
- **Run the Spring Boot application**

```
.\gradlew clean build -x test
docker compose up -d
.\gradlew bootRun 
```
Once started, the API will be available at: http://localhost:8080/api/users

Swagger UI: http://localhost:8080/swagger-ui.html

---

### API Documentation
- OpenAPI: Auto-generated via Springdoc
- Access via: http://localhost:8080/swagger-ui.html

---

### Testing 
Application is fully covered with Unit and Integration testing ensuring database 
connections to both Postgres and Oracle DB's. Liquibase manages both databases
separately using Testcontainers. 
 
```
.\gradlew test
```

---

### Database Migrations
Managed via Liquibase
- Separate changelogs:
    - db/oracle-init-changelog.xml
    - db/postgres-init-changelog.xml

- Auto-run at application startup (in main app)
- Disabled in test profile; configured manually for Testcontainers

---

### Authors
Kostiantyn Naumenko
