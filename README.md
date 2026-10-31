# Bus Ticket Booking System Backend

- **Student Name:** Ndagijimana Cedric
- **Student ID:** 27655
- **Project:** Bus Ticket Booking System

## Overview

Bus Ticket Booking System Backend is built with Spring Boot, Spring Data JPA, and PostgreSQL. It manages routes, buses, drivers, passengers, ticket bookings, luggage items, business validations, and statistical reporting.

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

