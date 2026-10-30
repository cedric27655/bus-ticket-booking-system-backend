package rw.auca.bus_ticket_booking_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.bus_ticket_booking_system.entity.LuggageItem;
import rw.auca.bus_ticket_booking_system.entity.TicketBooking;
import rw.auca.bus_ticket_booking_system.entity.TicketStatus;
import rw.auca.bus_ticket_booking_system.repository.LuggageItemRepository;
import rw.auca.bus_ticket_booking_system.repository.TicketBookingRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LuggageItemService {

    @Autowired
    private LuggageItemRepository luggageItemRepository;

    @Autowired
    private TicketBookingRepository ticketBookingRepository;

    public String saveLuggageItem(UUID ticketBookingId, LuggageItem luggageItem) {
        Optional<TicketBooking> bookingOpt = ticketBookingRepository.findById(ticketBookingId);
        if (bookingOpt.isEmpty()) {
            return "The ticket booking with that id does not exist";
        }
        TicketBooking booking = bookingOpt.get();
        if (booking.getStatus() != TicketStatus.BOARDED) {
            return "Luggage items are only for BOARDED ticket bookings";
        }
        luggageItem.setLoadedDate(LocalDate.now());
        luggageItem.setTicketBooking(booking);
        luggageItemRepository.save(luggageItem);
        return "Luggage item saved successfully";
    }

    public List<LuggageItem> getAllLuggageItems() {
        return luggageItemRepository.findAll();
    }

    public Optional<LuggageItem> getLuggageItemById(UUID id) {
        return luggageItemRepository.findById(id);
    }

    public List<LuggageItem> getLuggageByPassengerNationalId(String nationalId) {
        return luggageItemRepository.findLuggageByPassengerNationalId(nationalId);
    }

    public String deleteLuggageItem(UUID id) {
        Optional<LuggageItem> optional = luggageItemRepository.findById(id);
        if (optional.isEmpty()) {
            return "The luggage item with that id does not exist";
        }
        luggageItemRepository.delete(optional.get());
        return "Luggage item deleted successfully";
    }
}
