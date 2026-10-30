package rw.auca.bus_ticket_booking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.auca.bus_ticket_booking_system.entity.Passenger;
import java.util.List;
import java.util.UUID;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, UUID> {

    List<Passenger> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT tb.passenger FROM TicketBooking tb WHERE tb.driver.id = :driverId")
    List<Passenger> findPassengersByDriverId(@Param("driverId") UUID driverId);
}
