package rw.auca.bus_ticket_booking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.auca.bus_ticket_booking_system.entity.LuggageItem;
import rw.auca.bus_ticket_booking_system.entity.TicketBooking;
import java.util.List;
import java.util.UUID;

@Repository
public interface LuggageItemRepository extends JpaRepository<LuggageItem, UUID> {

    @Query("SELECT l FROM LuggageItem l WHERE l.ticketBooking.passenger.nationalId = :nationalId ORDER BY l.loadedDate DESC")
    List<LuggageItem> findLuggageByPassengerNationalId(@Param("nationalId") String nationalId);

    boolean existsByTicketBooking(TicketBooking ticketBooking);
}
