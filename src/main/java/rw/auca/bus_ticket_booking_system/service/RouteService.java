package rw.auca.bus_ticket_booking_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.bus_ticket_booking_system.entity.Route;
import rw.auca.bus_ticket_booking_system.repository.RouteRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public String saveRoute(Route route) {
        routeRepository.save(route);
        return "Route saved successfully";
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Optional<Route> getRouteById(UUID id) {
        return routeRepository.findById(id);
    }

    public String updateRoute(UUID id, Route route) {
        Optional<Route> optional = routeRepository.findById(id);
        if (optional.isEmpty()) {
            return "The route with that id does not exist";
        }
        Route existing = optional.get();
        existing.setName(route.getName());
        routeRepository.save(existing);
        return "Route updated successfully";
    }

    public String deleteRoute(UUID id) {
        Optional<Route> optional = routeRepository.findById(id);
        if (optional.isEmpty()) {
            return "The route with that id does not exist";
        }
        routeRepository.delete(optional.get());
        return "Route deleted successfully";
    }

    public List<Route> getUnusedRoutes() {
        return routeRepository.findUnusedRoutes();
    }
}
