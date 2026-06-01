# Koinovio - Building Management System

Koinovio is a microservices-based service management system for companies that provide maintenance services for residential buildings. The system handles tenant and building management, monthly expense tracking, automated bill generation, and email notification of tenants.

---

## Architecture Overview

The system consists of two independent microservices that communicate asynchronously via RabbitMQ:

```
Management Service (PostgreSQL)
        |
        | publishes message
        v
    RabbitMQ
        |
        | consumes message
        v
Billing Service (MongoDB)
        |
        | sends email
        v
    Mailpit (SMTP)
```

---

## Services

### Management Service (Port 8080)

Handles all building, apartment, and tenant management. Exposes a REST API for administrators to manage the building portfolio and submit monthly expenses.

**Database:** PostgreSQL

**Key entities:**
- Building — a building with a number of floors and apartments
- Apartment — belongs to a building, optionally has one active tenant
- Tenant — a person renting an apartment, supports soft delete via isActive flag

**Key endpoints:**

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | /buildings                    | Retrieve all buildings |
| POST   | /buildings                    | Create a building with its apartments and tenants |
| POST   | /buildings/{id}/expenses      | Submit expenses for  a specific building |
| GET    | /buildings/{id}               | Retrieve details of a specific building by ID |
| GET    | /buildings/{id}/apartments    | Retrieve all apartments for a specific building |
| POST   | /tenants                      | Create a new tenant |
| DELETE | /tenants/{id}                 | Delete a tenant by ID |
| POST   | /auth/register                | Register a new user account |
| POST   | /auth/login                   | Authenticate a user and return an access token |

**Expense submission flow:**
1. Admin submits a building's monthly expenses via POST /buildings/{id}/expenses
2. The service validates that the submission is for the correct next month using the last_expense_submission field on the Building entity
3. A message is published to RabbitMQ containing building info, apartment details, tenant emails, and the expense breakdown
4. The building's last_expense_submission field is updated

---

### Billing Service (Port 8081)

Consumes expense messages from RabbitMQ, calculates each tenant's share of the expenses, generates a PDF bill, saves it to MongoDB, and sends it to the tenant via email.

**Database:** MongoDB

**Key collection:**
- bills — stores one bill document per tenant per month, including a breakdown of charges (BillItems) and the total amount due

**Bill generation flow:**
1. Message consumed from RabbitMQ
2. Idempotency check — if bills already exist for this building/month/year, skip processing
3. Calculate each tenant's share per expense item
4. Save bill documents to MongoDB
5. Generate a PDF for each bill using iText
6. Send PDF as email attachment via SMTP (Mailpit in development)

---

## Technology Stack

| Component | Technology |
|-----------|------------|
| Backend | Spring Boot 4.0, Java 21 |
| Management DB | PostgreSQL |
| Billing DB | MongoDB |
| Message Broker | RabbitMQ |
| ORM | Spring Data JPA / Hibernate |
| Object Mapping | MapStruct |
| PDF Generation | iText 5 |
| Email | Spring Mail + Mailpit (dev) |
| Security | Spring Security + JWT |
| API Documentation | SpringDoc OpenAPI (Swagger UI) |
| Boilerplate reduction | Lombok |

---

## Prerequisites

- Java 21
- Maven
- Docker

---

## Running the Project

### Step 1 - Start infrastructure with Docker Compose

Start all required infrastructure services:
```
docker compose up -d
```

This will start:

RabbitMQ
PostgreSQL
MongoDB
Mailpit
```

### Step 2 - Run the services

Run the management service:
```
cd management-service
mvn spring-boot:run
```

Run the billing service:
```
cd billing-service
mvn spring-boot:run
```

---

## Useful URLs

| Service | URL |
|---------|-----|
| Management Service API | http://localhost:8080 |
| Swagger UI (Management) | http://localhost:8080/swagger-ui/index.html |
| Billing Service API | http://localhost:8081 |
| RabbitMQ Dashboard | http://localhost:15672 (guest/guest) |
| Mailpit Inbox | http://localhost:8025 |

---

## Key Design Decisions

**Soft delete for tenants** — tenants are never deleted from the database. When a tenant stops renting, their isActive flag is set to false and their apartment reference is cleared. This preserves historical billing data.

**Asynchronous billing** — the management service does not wait for bills to be generated. It publishes a message to RabbitMQ and returns immediately. The billing service processes it independently.

**Idempotent bill processing** — the billing service checks whether bills already exist for a given building/month/year before processing, preventing duplicate bills if a message is requeued by RabbitMQ.

**Separate databases per service** — the management service owns a PostgreSQL database and the billing service owns a MongoDB database. Services never share a database, which is a core microservices principle.

**DTO separation** — API response DTOs and messaging DTOs are kept separate. This prevents tight coupling between the HTTP API contract and the RabbitMQ message contract.
