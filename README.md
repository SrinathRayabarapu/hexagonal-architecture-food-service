# Hexagonal Architecture Food Order Service

[![CI](https://github.com/SrinathRayabarapu/hexagonal-architecture-food-service/actions/workflows/ci.yml/badge.svg)](https://github.com/SrinathRayabarapu/hexagonal-architecture-food-service/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

A small **Spring Boot** service that demonstrates **hexagonal architecture** (ports and adapters) for a food-order workflow. The domain core stays independent of REST, JPA, Kafka, and other infrastructure concerns.

## Tech stack

| Layer | Choice |
|-------|--------|
| Runtime | Java 21 |
| Framework | Spring Boot 3.5 |
| Persistence | Spring Data JPA + MySQL 8 |
| API docs | springdoc OpenAPI |
| Build | Maven |
| Local DB | Docker Compose |

## Architecture

```text
┌─────────────────────────────────────────────────────────────┐
│                     Input adapters                          │
│   REST (OrderController)   │   Kafka (optional, kafka profile)│
└──────────────┬──────────────────────────────┬───────────────┘
               │                              │
               ▼                              ▼
┌──────────────────────────────────────────────────────────────┐
│  Input ports: PlaceOrderUsecasePort, TrackOrderUsecasePort   │
└──────────────────────────────┬───────────────────────────────┘
                               │
                               ▼
┌──────────────────────────────────────────────────────────────┐
│              Domain: OrderService (core logic)             │
└──────────────────────────────┬───────────────────────────────┘
                               │
                               ▼
┌──────────────────────────────────────────────────────────────┐
│           Output port: OrderRepositoryPort                   │
└──────────────────────────────┬───────────────────────────────┘
                               │
                               ▼
┌──────────────────────────────────────────────────────────────┐
│  Output adapters: JpaOrderRepository (active)                │
│                   MongoOrderRepository (reference stub)      │
└──────────────────────────────────────────────────────────────┘
```

### Diagrams

**Project structure**

![Project structure](https://github.com/user-attachments/assets/8c459cc3-0db7-4266-b9ee-b344c69ede24)

**Architectural view**

![Architectural diagram](https://github.com/user-attachments/assets/9b506791-c06e-4c93-8136-9ffe9cdb8809)

## Prerequisites

- **JDK 21**
- **Maven 3.9+**
- **Docker** (for local MySQL)

## Quick start

### 1. Start MySQL

```bash
docker compose up -d
```

This starts MySQL on port `3306` with database `food_order_db`. Defaults match `env.example`.

### 2. Run the application

```bash
mvn spring-boot:run
```

The API listens on **http://localhost:9191**.

### 3. Try the API

**Place an order**

```bash
curl -X POST http://localhost:9191/orders \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": "order-1",
    "customerName": "Alex",
    "restaurantName": "Pizza Place",
    "item": "Margherita"
  }'
```

**Track an order**

```bash
curl http://localhost:9191/orders/track/order-1
```

### 4. OpenAPI / Swagger UI

- Swagger UI: http://localhost:9191/swagger-ui.html
- OpenAPI JSON: http://localhost:9191/api-docs

## Configuration

Settings are in `src/main/resources/application.properties`. Sensitive values use environment variables with local defaults:

| Variable | Default | Description |
|----------|---------|-------------|
| `DB_URL` | `jdbc:mysql://localhost:3306/food_order_db?...` | JDBC URL |
| `DB_USERNAME` | `root` | Database user |
| `DB_PASSWORD` | `passw@rd` | Database password |
| `SERVER_PORT` | `9191` | HTTP port |
| `JPA_SHOW_SQL` | `false` | Log SQL statements |

Copy `env.example` to `.env` when overriding Docker Compose variables locally.

## Project layout

```text
src/main/java/com/javatechie/
├── FoodOrderServiceApplication.java
├── adapter/
│   ├── input/rest/          # REST API adapter
│   ├── input/kafka/         # Kafka adapter (kafka profile only)
│   └── output/              # JPA persistence adapter
├── config/                  # Spring wiring for domain beans
└── domain/
    ├── dto/                 # Domain model
    ├── port/input/          # Driving ports (use cases)
    ├── port/output/         # Driven ports (repository, notifications)
    └── service/             # Domain logic
```

## Build and test

```bash
# Compile and run all tests (uses in-memory H2)
mvn verify

# Run only unit/integration tests
mvn test
```

Tests use **H2 in-memory** so CI and local runs do not require MySQL. Use Docker MySQL when running the app itself.

## Optional: Kafka input adapter

The Kafka consumer is behind the `kafka` Spring profile and is not active by default. To experiment:

1. Add `spring-kafka` to `pom.xml`
2. Start the app with `-Dspring.profiles.active=kafka`
3. Uncomment `@KafkaListener` in `OrderKafkaConsumer`

## License

[MIT](LICENSE)
