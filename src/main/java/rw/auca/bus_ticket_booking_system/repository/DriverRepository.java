package rw.auca.bus_ticket_booking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.auca.bus_ticket_booking_system.entity.Bus;
import rw.auca.bus_ticket_booking_system.entity.Driver;
import java.util.List;
import java.util.UUID;

@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {

    @Query("SELECT d FROM Driver d JOIN d.routes r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Driver> findDriversByRouteName(@Param("name") String name);

    boolean existsByBus(Bus bus);
}
