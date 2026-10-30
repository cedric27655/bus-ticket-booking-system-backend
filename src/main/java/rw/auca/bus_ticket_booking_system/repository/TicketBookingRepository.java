package rw.auca.bus_ticket_booking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.auca.bus_ticket_booking_system.entity.Driver;
import rw.auca.bus_ticket_booking_system.entity.TicketBooking;
import rw.auca.bus_ticket_booking_system.entity.TicketStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TicketBookingRepository extends JpaRepository<TicketBooking, UUID> {

    List<TicketBooking> findByStatusOrderByTravelDateAsc(TicketStatus status);

    List<TicketBooking> findByTravelDateBetweenOrderByTravelDateAsc(LocalDate start, LocalDate end);

    boolean existsByDriverAndTravelDateAndStatusNot(Driver driver, LocalDate travelDate, TicketStatus status);

    @Query("SELECT tb.status, COUNT(tb) FROM TicketBooking tb GROUP BY tb.status")
    List<Object[]> countTicketBookingsByStatus();

    @Modifying
    @Query("UPDATE TicketBooking tb SET tb.status = rw.auca.bus_ticket_booking_system.entity.TicketStatus.CANCELLED WHERE tb.driver.id = :driverId AND tb.travelDate = :travelDate AND tb.status != rw.auca.bus_ticket_booking_system.entity.TicketStatus.BOARDED")
    int cancelTicketBookingsForDriverOnDate(@Param("driverId") UUID driverId, @Param("travelDate") LocalDate travelDate);

    long countByTravelDate(LocalDate travelDate);

    long countByStatus(TicketStatus status);
}
