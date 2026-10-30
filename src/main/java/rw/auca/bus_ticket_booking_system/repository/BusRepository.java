package rw.auca.bus_ticket_booking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.auca.bus_ticket_booking_system.entity.Bus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusRepository extends JpaRepository<Bus, UUID> {

    @Query("SELECT b FROM Bus b WHERE NOT EXISTS (SELECT d FROM Driver d WHERE d.bus = b) ORDER BY b.busNumber ASC")
    List<Bus> findFreeBuses();

    Optional<Bus> findByBusNumber(int busNumber);
}
