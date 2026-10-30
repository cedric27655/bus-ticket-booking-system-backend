package rw.auca.bus_ticket_booking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.auca.bus_ticket_booking_system.entity.Route;
import java.util.List;
import java.util.UUID;

@Repository
public interface RouteRepository extends JpaRepository<Route, UUID> {

    @Query("SELECT r FROM Route r WHERE r.drivers IS EMPTY")
    List<Route> findUnusedRoutes();
}
