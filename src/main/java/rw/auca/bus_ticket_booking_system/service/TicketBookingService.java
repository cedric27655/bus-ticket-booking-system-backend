package rw.auca.bus_ticket_booking_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.auca.bus_ticket_booking_system.entity.TicketBooking;
import rw.auca.bus_ticket_booking_system.entity.TicketStatus;
import rw.auca.bus_ticket_booking_system.repository.DriverRepository;
import rw.auca.bus_ticket_booking_system.repository.LuggageItemRepository;
import rw.auca.bus_ticket_booking_system.repository.PassengerRepository;
import rw.auca.bus_ticket_booking_system.repository.TicketBookingRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketBookingService {

    @Autowired
    private TicketBookingRepository ticketBookingRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private LuggageItemRepository luggageItemRepository;

    public String saveTicketBooking(TicketBooking ticketBooking) {
        if (ticketBooking.getTravelDate() != null && ticketBooking.getTravelDate().isBefore(LocalDate.now())) {
            return "Ticket booking date cannot be in the past";
        }
        if (ticketBooking.getPassenger() != null && ticketBooking.getPassenger().getId() != null) {
            passengerRepository.findById(ticketBooking.getPassenger().getId()).ifPresent(ticketBooking::setPassenger);
        }
        if (ticketBooking.getDriver() != null && ticketBooking.getDriver().getId() != null) {
            driverRepository.findById(ticketBooking.getDriver().getId()).ifPresent(ticketBooking::setDriver);
        }
        if (ticketBooking.getDriver() != null && ticketBookingRepository.existsByDriverAndTravelDateAndStatusNot(ticketBooking.getDriver(), ticketBooking.getTravelDate(), TicketStatus.CANCELLED)) {
            return "Driver already has a ticket booking on that date";
        }
        ticketBooking.setStatus(TicketStatus.BOOKED);
        ticketBookingRepository.save(ticketBooking);
        return "Ticket booking saved successfully";
    }

    public List<TicketBooking> getAllTicketBookings() {
        return ticketBookingRepository.findAll();
    }

    public Optional<TicketBooking> getTicketBookingById(UUID id) {
        return ticketBookingRepository.findById(id);
    }

    public String updateTicketBooking(UUID id, TicketBooking ticketBooking) {
        Optional<TicketBooking> optional = ticketBookingRepository.findById(id);
        if (optional.isEmpty()) {
            return "The ticket booking with that id does not exist";
        }
        TicketBooking existing = optional.get();
        existing.setTravelDate(ticketBooking.getTravelDate());
        existing.setSeatNumber(ticketBooking.getSeatNumber());
        existing.setDestination(ticketBooking.getDestination());
        if (ticketBooking.getPassenger() != null && ticketBooking.getPassenger().getId() != null) {
            passengerRepository.findById(ticketBooking.getPassenger().getId()).ifPresent(existing::setPassenger);
        }
        if (ticketBooking.getDriver() != null && ticketBooking.getDriver().getId() != null) {
            driverRepository.findById(ticketBooking.getDriver().getId()).ifPresent(existing::setDriver);
        }
        ticketBookingRepository.save(existing);
        return "Ticket booking updated successfully";
    }

    public String deleteTicketBooking(UUID id) {
        Optional<TicketBooking> optional = ticketBookingRepository.findById(id);
        if (optional.isEmpty()) {
            return "The ticket booking with that id does not exist";
        }
        TicketBooking existing = optional.get();
        if (luggageItemRepository.existsByTicketBooking(existing)) {
            return "That ticket booking has luggage items, delete them first";
        }
        ticketBookingRepository.delete(existing);
        return "Ticket booking deleted successfully";
    }

    public List<TicketBooking> getTicketBookingsByStatus(TicketStatus status) {
        return ticketBookingRepository.findByStatusOrderByTravelDateAsc(status);
    }

    public List<TicketBooking> getTicketBookingsBetweenDates(LocalDate start, LocalDate end) {
        return ticketBookingRepository.findByTravelDateBetweenOrderByTravelDateAsc(start, end);
    }

    public String updateStatus(UUID id, TicketStatus status) {
        Optional<TicketBooking> optional = ticketBookingRepository.findById(id);
        if (optional.isEmpty()) {
            return "The ticket booking with that id does not exist";
        }
        TicketBooking booking = optional.get();
        TicketStatus current = booking.getStatus();

        boolean allowed = false;
        if (current == TicketStatus.BOOKED && (status == TicketStatus.PAID || status == TicketStatus.CANCELLED)) {
            allowed = true;
        } else if (current == TicketStatus.PAID && (status == TicketStatus.BOARDED || status == TicketStatus.CANCELLED)) {
            allowed = true;
        }

        if (!allowed) {
            return "Cannot change status from " + current + " to " + status;
        }

        booking.setStatus(status);
        ticketBookingRepository.save(booking);
        return "Ticket booking status updated successfully";
    }

    public List<Object[]> getStatsByStatus() {
        return ticketBookingRepository.countTicketBookingsByStatus();
    }

    @Transactional
    public String cancelDay(UUID driverId, LocalDate date) {
        int count = ticketBookingRepository.cancelTicketBookingsForDriverOnDate(driverId, date);
        return count + " ticket bookings cancelled";
    }

    public Page<TicketBooking> getPage(Pageable pageable) {
        return ticketBookingRepository.findAll(pageable);
    }
}
