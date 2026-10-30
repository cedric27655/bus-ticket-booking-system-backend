package rw.auca.bus_ticket_booking_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rw.auca.bus_ticket_booking_system.entity.Driver;
import rw.auca.bus_ticket_booking_system.service.DriverService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/save")
    public ResponseEntity<String> saveDriver(@RequestBody Driver driver) {
        String response = driverService.saveDriver(driver);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Driver>> getAllDrivers() {
        return new ResponseEntity<>(driverService.getAllDrivers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDriverById(@PathVariable UUID id) {
        Optional<Driver> optional = driverService.getDriverById(id);
        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The driver with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDriver(@PathVariable UUID id, @RequestBody Driver driver) {
        String response = driverService.updateDriver(id, driver);
        if (response.equals("The driver with that id does not exist")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDriver(@PathVariable UUID id) {
        String response = driverService.deleteDriver(id);
        if (response.equals("The driver with that id does not exist")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/by-route")
    public ResponseEntity<List<Driver>> getDriversByRoute(@RequestParam String name) {
        return new ResponseEntity<>(driverService.getDriversByRoute(name), HttpStatus.OK);
    }

    @PutMapping("/{driverId}/routes/{routeId}")
    public ResponseEntity<String> addRouteToDriver(@PathVariable UUID driverId, @PathVariable UUID routeId) {
        String response = driverService.addRouteToDriver(driverId, routeId);
        if (response.equals("The driver with that id does not exist") || response.equals("That route does not exist")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (response.equals("The driver already has that route")) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PatchMapping("/{driverId}/bus")
    public ResponseEntity<String> changeBus(@PathVariable UUID driverId, @RequestParam int busNumber) {
        String response = driverService.changeBus(driverId, busNumber);
        if (response.equals("The driver with that id does not exist") || response.equals("The bus with that busNumber does not exist")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (response.equals("That bus is taken by another driver")) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
