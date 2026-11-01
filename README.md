# Bus Ticket Booking System Backend

- **Student Name:** Ndagijimana Cedric
- **Student ID:** 27655
- **Project Name:** Bus Ticket Booking System

---

## Steps to Run the API

### Step 1: Clone or Navigate to the Project
Open your terminal (PowerShell, Command Prompt, or Bash) and navigate to the project directory:

---

### Step 2: Database Configuration
1. Start your local PostgreSQL server.
2. Create the database named `bus_ticket_booking_system` (via `psql` or pgAdmin):
   ```sql
   CREATE DATABASE bus_ticket_booking_system;
   ```
3. Check [`src/main/resources/application.properties`](src/main/resources/application.properties) and ensure your database credentials match:
   ```properties
   spring.application.name=bus-ticket-booking-system
   spring.datasource.url=jdbc:postgresql://localhost:5432/bus_ticket_booking_system
   spring.datasource.username=postgres
   spring.datasource.password=12345
   spring.datasource.driver-class-name=org.postgresql.Driver

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true

   server.port=8080
   ```

---

### Step 3: Populate Seed Data (Optional but Recommended)
A SQL seed script is provided in [`seed_data.sql`](seed_data.sql) containing sample routes, buses, drivers, passengers, bookings, and luggage:

```cmd
psql -U postgres -d bus_ticket_booking_system -f seed_data.sql
```
*(Or open `seed_data.sql` in pgAdmin / DBeaver and execute its contents).*

---

### Step 4: Run the Application

#### Option A: Windows Command Prompt (CMD)
```cmd
set JAVA_HOME=C:\Program Files\Java\jdk-25
set PATH=C:\Program Files\Java\jdk-25\bin;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\WindowsPowerShell\v1.0\
mvnw.cmd spring-boot:run
```

#### Option B: Windows PowerShell
```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-25"
$env:PATH = "C:\Program Files\Java\jdk-25\bin;$env:PATH"
.\mvnw.cmd spring-boot:run
```

#### Option C: macOS / Linux / Git Bash
```bash
export JAVA_HOME="/path/to/jdk-25"
./mvnw spring-boot:run
```

#### Option D: Directly in VS Code
1. Open the file `src/main/java/rw/auca/bus_ticket_booking_system/BusTicketBookingSystemApplication.java`.
2. Click **Run** above the `main` method.

---

### Step 5: Verify the Application is Running
Once compilation finishes and the application starts, you will see:
```text
Tomcat started on port 8080 (http) with context path '/'
Started BusTicketBookingSystemApplication in ... seconds
```
The API is now live and accessible at:
 **`http://localhost:8080`**

---

### Step 6: How to Stop the Application
To shut down the server gracefully, click in the terminal running Spring Boot and press:
```text
Ctrl + C
```
Then type `Y` when prompted `Terminate batch job (Y/N)?`.

---

## Testing with Postman

A complete, pre-configured collection containing all **43 test requests** is included in the project:
 **[`bus-ticket-booking-system-midterm.postman_collection.json`](bus-ticket-booking-system-midterm.postman_collection.json)**

### How to Import and Test:
1. Open **Postman** (or the Postman VS Code Extension).
2. Click **Import** (top-left).
3. Select the file `bus-ticket-booking-system-midterm.postman_collection.json`.
4. The collection **Bus Ticket Booking System Mid-Term API** will load with organized requests.
5. With the backend running, click any request (e.g., `POST A2 - Save Route` or `GET A2 - Get All Routes`) and click **Send**.

---

## API Endpoints Reference

### Part A: Domain Model & Standard CRUD
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/{resource}/save` | Create a new entity (Route, Bus, Driver, Passenger, TicketBooking) |
| `GET` | `/api/{resource}/all` | Retrieve all records for a resource |
| `GET` | `/api/{resource}/{id}` | Find an entity by UUID |
| `PUT` | `/api/{resource}/update/{id}` | Update an existing entity by UUID |
| `DELETE` | `/api/{resource}/delete/{id}` | Delete an entity by UUID |

*`{resource}` can be: `routes`, `buses`, `drivers`, `passengers`, or `ticket-bookings`.*

### Part B: Derived Queries
| Method | Endpoint | Query Parameter | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/passengers/by-last-name` | `?lastName=...` | Find all passengers with a matching last name |
| `GET` | `/api/ticket-bookings/by-status` | `?status=...` | Find bookings by status (`BOOKED`, `PAID`, `BOARDED`, `CANCELLED`) |
| `GET` | `/api/ticket-bookings/between` | `?start=...&end=...` | Find bookings scheduled between two dates (`YYYY-MM-DD`) |

### Part C: JPQL with Joins & Relationships
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/drivers/by-route?name=...` | Find all drivers assigned to a specific route name |
| `GET` | `/api/buses/free` | Find buses with no drivers assigned |
| `GET` | `/api/routes/unused` | Find routes that currently have no drivers assigned |
| `GET` | `/api/passengers/of-driver/{driverId}` | Find distinct passengers booked with a specific driver |

### Part D: Business Rules Validation
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/ticket-bookings/save` | Validate passenger exists, driver exists, seat not already taken |
| `PATCH` | `/api/ticket-bookings/{id}/status?status=...` | Transition status with lifecycle validation (`BOOKED` $\rightarrow$ `PAID` $\rightarrow$ `BOARDED`) |
| `PUT` | `/api/drivers/{driverId}/routes/{routeId}` | Assign a route to a driver |
| `PATCH` | `/api/drivers/{driverId}/bus?busNumber=...` | Assign/change a driver's assigned bus by bus number |

### Part E: LuggageItem Entity
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/luggage-items/save?ticketBookingId=...` | Add luggage linked to a ticket booking |
| `GET` | `/api/luggage-items/passenger/{nationalId}` | Find all luggage items belonging to a passenger's national ID |
| `DELETE` | `/api/luggage-items/delete/{id}` | Remove a luggage item by ID |

### Part F: Reports, Statistics & Bulk Updates
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/ticket-bookings/stats/by-status` | Group and count total bookings by their status |
| `PATCH` | `/api/ticket-bookings/cancel-day?driverId=...&date=...` | Bulk cancel all bookings for a given driver on a specific date |

### Bonus Endpoints
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/dashboard/summary` | Real-time system metrics (total buses, drivers, routes, passengers, bookings) |
| `GET` | `/api/ticket-bookings/page?page=0&size=5&sort=travelDate,desc` | Paginated and sorted list of ticket bookings |

