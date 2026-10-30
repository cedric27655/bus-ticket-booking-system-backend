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
import rw.auca.bus_ticket_booking_system.entity.Bus;
import rw.auca.bus_ticket_booking_system.service.BusService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    @Autowired
    private BusService busService;

    @PostMapping("/save")
    public ResponseEntity<String> saveBus(@RequestBody Bus bus) {
        String response = busService.saveBus(bus);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bus>> getAllBuses() {
        return new ResponseEntity<>(busService.getAllBuses(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBusById(@PathVariable UUID id) {
        Optional<Bus> optional = busService.getBusById(id);
        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The bus with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBus(@PathVariable UUID id, @RequestBody Bus bus) {
        String response = busService.updateBus(id, bus);
        if (response.equals("The bus with that id does not exist")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBus(@PathVariable UUID id) {
        String response = busService.deleteBus(id);
        if (response.equals("The bus with that id does not exist")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/free")
    public ResponseEntity<List<Bus>> getFreeBuses() {
        return new ResponseEntity<>(busService.getFreeBuses(), HttpStatus.OK);
    }
}
