package rw.auca.bus_ticket_booking_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rw.auca.bus_ticket_booking_system.entity.TicketBooking;
import rw.auca.bus_ticket_booking_system.entity.TicketStatus;
import rw.auca.bus_ticket_booking_system.service.TicketBookingService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/ticket-bookings")
public class TicketBookingController {

    @Autowired
    private TicketBookingService ticketBookingService;

    @PostMapping("/save")
    public ResponseEntity<String> saveTicketBooking(@RequestBody TicketBooking ticketBooking) {
        String response = ticketBookingService.saveTicketBooking(ticketBooking);
        if (response.equals("Ticket booking date cannot be in the past")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else if (response.equals("Driver already has a ticket booking on that date")) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TicketBooking>> getAllTicketBookings() {
        return new ResponseEntity<>(ticketBookingService.getAllTicketBookings(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketBookingById(@PathVariable UUID id) {
        Optional<TicketBooking> optional = ticketBookingService.getTicketBookingById(id);
        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The ticket booking with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTicketBooking(@PathVariable UUID id, @RequestBody TicketBooking ticketBooking) {
        String response = ticketBookingService.updateTicketBooking(id, ticketBooking);
        if (response.equals("The ticket booking with that id does not exist")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTicketBooking(@PathVariable UUID id) {
        String response = ticketBookingService.deleteTicketBooking(id);
        if (response.equals("The ticket booking with that id does not exist")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (response.equals("That ticket booking has luggage items, delete them first")) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<TicketBooking>> getTicketBookingsByStatus(@RequestParam TicketStatus status) {
        return new ResponseEntity<>(ticketBookingService.getTicketBookingsByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/between")
    public ResponseEntity<List<TicketBooking>> getTicketBookingsBetween(
            @RequestParam String start,
            @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(ticketBookingService.getTicketBookingsBetweenDates(startDate, endDate), HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam TicketStatus status) {
        String response = ticketBookingService.updateStatus(id, status);
        if (response.equals("The ticket booking with that id does not exist")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (response.startsWith("Cannot change status from")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> getStatsByStatus() {
        return new ResponseEntity<>(ticketBookingService.getStatsByStatus(), HttpStatus.OK);
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID driverId, @RequestParam String date) {
        LocalDate travelDate = LocalDate.parse(date);
        String response = ticketBookingService.cancelDay(driverId, travelDate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<TicketBooking>> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "travelDate,desc") String sort) {
        String[] sortParts = sort.split(",");
        String property = sortParts[0];
        Sort.Direction direction = (sortParts.length > 1 && sortParts[1].equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, property));
        return new ResponseEntity<>(ticketBookingService.getPage(pageable), HttpStatus.OK);
    }
}
