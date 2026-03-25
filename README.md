# Order Processing Kafka Microservices

This project demonstrates an event-driven microservices architecture using Spring Boot and Apache Kafka for processing orders, payments, and notifications.

## Architecture

- **Order Service**: Receives order requests, saves to database, and publishes order events to Kafka.
- **Payment Service**: Consumes order events, processes payments, and publishes payment events.
- **Notification Service**: Consumes payment events and sends notifications.

## Technologies

- Java 17
- Spring Boot (Web, Data JPA, Kafka)
- Apache Kafka
- H2 Database
- Docker

## Prerequisites

- Java 17
- Gradle
- Docker

## Setup and Run

1. **Start Kafka and Zookeeper**:
   ```bash
   docker-compose up -d
   ```

2. **Run the Services**:
   - Order Service: `cd order-service && ./gradlew bootRun` (port 8081)
   - Payment Service: `cd payment-service && ./gradlew bootRun` (port 8082)
   - Notification Service: `cd notification-service && ./gradlew bootRun` (port 8083)

## API Usage

### Create Order
```bash
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{"product": "Laptop", "quantity": 1, "price": 1000.0}'
```

## Topics

- `order-topic`: For order events
- `payment-topic`: For payment events

## Database

Each service uses H2 in-memory database. Console available at `/h2-console`.