# Bus Ticket Booking System Backend

- **Student Name:** Ndagijimana Cedric
- **Student ID:** 27655
- **Project:** Bus Ticket Booking System
- **Course:** INSY 8322 Web Technology and Internet
- **Institution:** Adventist University of Central Africa (AUCA)

## Overview

Bus Ticket Booking System Backend is built with Spring Boot, Spring Data JPA, and PostgreSQL. It manages routes, buses, drivers, passengers, ticket bookings, luggage items, business validations, and statistical reporting.

## Prerequisites

- Java 21 or Java 25
- PostgreSQL 16+
- Maven wrapper (included) or Maven 3.9+
- Postman (for testing endpoints)

## Database Configuration

Create the database in PostgreSQL:

```sql
CREATE DATABASE bus_ticket_booking_system;
```

Update `src/main/resources/application.properties` if needed:

```properties
spring.application.name=bus-ticket-booking-system
spring.datasource.url=jdbc:postgresql://localhost:5432/bus_ticket_booking_system
spring.datasource.username=postgres
spring.datasource.password=12345
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## Running the Application

1. Clone or navigate to the repository directory:
   ```bash
   cd bus-ticket-booking-system
   ```

2. Compile and run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   Or on Windows:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

3. The server runs at `http://localhost:8080`.

## Seed Data

To populate the required seed data, execute the `seed_data.sql` script into PostgreSQL:

```bash
psql -U postgres -d bus_ticket_booking_system -f seed_data.sql
```

## Postman Collection

The file `bus-ticket-booking-system-midterm.postman_collection.json` is located in the root of the project. Import this file into Postman to run every requirement and error case.

## Summary of API Endpoints

### Part A: Domain Model and CRUD
- `POST /api/{path}/save`
- `GET /api/{path}/all`
- `GET /api/{path}/{id}`
- `PUT /api/{path}/update/{id}`
- `DELETE /api/{path}/delete/{id}`
*(where `{path}` is `routes`, `buses`, `drivers`, `passengers`, or `ticket-bookings`)*

### Part B: Derived Queries
- `GET /api/passengers/by-last-name?lastName=...`
- `GET /api/ticket-bookings/by-status?status=...`
- `GET /api/ticket-bookings/between?start=...&end=...`

### Part C: JPQL with Joins
- `GET /api/drivers/by-route?name=...`
- `GET /api/buses/free`
- `GET /api/routes/unused`
- `GET /api/passengers/of-driver/{driverId}`

### Part D: Business Rules
- `POST /api/ticket-bookings/save`
- `PATCH /api/ticket-bookings/{id}/status?status=...`
- `PUT /api/drivers/{driverId}/routes/{routeId}`
- `PATCH /api/drivers/{driverId}/bus?busNumber=...`

### Part E: LuggageItem Entity
- `POST /api/luggage-items/save?ticketBookingId=...`
- `GET /api/luggage-items/passenger/{nationalId}`
- `DELETE /api/luggage-items/delete/{id}`

### Part F: Reports and Bulk Updates
- `GET /api/ticket-bookings/stats/by-status`
- `PATCH /api/ticket-bookings/cancel-day?driverId=...&date=...`

### Bonus
- `GET /api/dashboard/summary`
- `GET /api/ticket-bookings/page?page=...&size=...&sort=...`
