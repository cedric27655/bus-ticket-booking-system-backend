package rw.auca.bus_ticket_booking_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.auca.bus_ticket_booking_system.entity.Route;
import rw.auca.bus_ticket_booking_system.service.RouteService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping("/save")
    public ResponseEntity<String> saveRoute(@RequestBody Route route) {
        String response = routeService.saveRoute(route);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Route>> getAllRoutes() {
        return new ResponseEntity<>(routeService.getAllRoutes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRouteById(@PathVariable UUID id) {
        Optional<Route> optional = routeService.getRouteById(id);
        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The route with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRoute(@PathVariable UUID id, @RequestBody Route route) {
        String response = routeService.updateRoute(id, route);
        if (response.equals("The route with that id does not exist")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoute(@PathVariable UUID id) {
        String response = routeService.deleteRoute(id);
        if (response.equals("The route with that id does not exist")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/unused")
    public ResponseEntity<List<Route>> getUnusedRoutes() {
        return new ResponseEntity<>(routeService.getUnusedRoutes(), HttpStatus.OK);
    }
}
