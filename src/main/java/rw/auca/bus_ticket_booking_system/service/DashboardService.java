package rw.auca.bus_ticket_booking_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.bus_ticket_booking_system.entity.TicketStatus;
import rw.auca.bus_ticket_booking_system.repository.BusRepository;
import rw.auca.bus_ticket_booking_system.repository.DriverRepository;
import rw.auca.bus_ticket_booking_system.repository.LuggageItemRepository;
import rw.auca.bus_ticket_booking_system.repository.PassengerRepository;
import rw.auca.bus_ticket_booking_system.repository.TicketBookingRepository;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private TicketBookingRepository ticketBookingRepository;

    @Autowired
    private LuggageItemRepository luggageItemRepository;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("passengers", passengerRepository.count());
        summary.put("drivers", driverRepository.count());
        summary.put("freeBuses", (long) busRepository.findFreeBuses().size());
        summary.put("bookingsToday", ticketBookingRepository.countByTravelDate(LocalDate.now()));
        summary.put("booked", ticketBookingRepository.countByStatus(TicketStatus.BOOKED));
        summary.put("luggageItems", luggageItemRepository.count());
        return summary;
    }
}
