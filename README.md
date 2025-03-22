# 📝 To-Do List Application

This is a backend implementation of a simple To-Do List application built with **Spring Boot** as part of a midterm exam project.

## 📌 Features

- CRUD operations for tasks
- Role-based access control using JWT
- H2 in-memory database (PostgreSQL-ready)
- Input validation with Hibernate Validator
- Exception handling with Spring MVC
- OpenAPI + SwaggerUI for API documentation
- Unit & integration testing using JUnit5 and MockMVC

## 🚀 Tech Stack

- Java 17
- Spring Boot 3.2.1
- Spring Data JPA
- Spring Security (JWT)
- Lombok
- MapStruct
- H2 Database (default)
- Swagger/OpenAPI (springdoc-openapi)
- JUnit 5, Mockito

## 🗃️ Database

- Default: **H2 (in-memory)**, preconfigured for testing and development.
- Optional: PostgreSQL can be used by switching profiles (not enabled in current build).

## 🔐 Authentication

- JWT-based authentication
- Public endpoints:
  - `/api/v1/auth/**`
  - `GET /api/v1/tasks`
  - `POST /api/v1/tasks` (temporarily open for testing)

## 📄 API Documentation

- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## 🧪 Tests

- ✅ Repository tests
- ✅ DTO & Mapper tests
- ✅ Service layer tests
- ✅ Integration test (`GET`, `POST`)
- ✅ Controller tests (for unprotected endpoints)
- ⚠️ Skipped some JWT-protected tests due to token validation complexity in test context

## 📂 Project Structure

```
├── config
│   └── ApplicationConfig.java
├── domain
│   └── task, user, exception
├── repository
├── service
├── web
│   └── controller, mappers, security
├── dto
│   └── task, auth
├── test
```

## 📄 SRS (Software Requirements Specification)

Located in `/docs/SRS.pdf` or available upon request.

## ✅ How to Run

1. Clone the repo
2. Run `mvn clean install`
3. Launch with `mvn spring-boot:run`
4. Visit [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
