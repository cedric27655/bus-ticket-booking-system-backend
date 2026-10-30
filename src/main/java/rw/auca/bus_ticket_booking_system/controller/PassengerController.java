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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rw.auca.bus_ticket_booking_system.entity.Passenger;
import rw.auca.bus_ticket_booking_system.service.PassengerService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @PostMapping("/save")
    public ResponseEntity<String> savePassenger(@RequestBody Passenger passenger) {
        String response = passengerService.savePassenger(passenger);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        return new ResponseEntity<>(passengerService.getAllPassengers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPassengerById(@PathVariable UUID id) {
        Optional<Passenger> optional = passengerService.getPassengerById(id);
        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The passenger with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePassenger(@PathVariable UUID id, @RequestBody Passenger passenger) {
        String response = passengerService.updatePassenger(id, passenger);
        if (response.equals("The passenger with that id does not exist")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePassenger(@PathVariable UUID id) {
        String response = passengerService.deletePassenger(id);
        if (response.equals("The passenger with that id does not exist")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<List<Passenger>> getPassengersByLastName(@RequestParam String lastName) {
        return new ResponseEntity<>(passengerService.getPassengersByLastName(lastName), HttpStatus.OK);
    }

    @GetMapping("/of-driver/{driverId}")
    public ResponseEntity<?> getPassengersOfDriver(@PathVariable UUID driverId) {
        if (!passengerService.driverExists(driverId)) {
            return new ResponseEntity<>("The driver with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(passengerService.getPassengersByDriverId(driverId), HttpStatus.OK);
    }
}
