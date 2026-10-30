package rw.auca.bus_ticket_booking_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rw.auca.bus_ticket_booking_system.entity.LuggageItem;
import rw.auca.bus_ticket_booking_system.service.LuggageItemService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/luggage-items")
public class LuggageItemController {

    @Autowired
    private LuggageItemService luggageItemService;

    @PostMapping("/save")
    public ResponseEntity<String> saveLuggageItem(
            @RequestParam UUID ticketBookingId,
            @RequestBody LuggageItem luggageItem) {
        String response = luggageItemService.saveLuggageItem(ticketBookingId, luggageItem);
        if (response.equals("The ticket booking with that id does not exist")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (response.equals("Luggage items are only for BOARDED ticket bookings")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<LuggageItem>> getAllLuggageItems() {
        return new ResponseEntity<>(luggageItemService.getAllLuggageItems(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLuggageItemById(@PathVariable UUID id) {
        Optional<LuggageItem> optional = luggageItemService.getLuggageItemById(id);
        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The luggage item with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/passenger/{nationalId}")
    public ResponseEntity<List<LuggageItem>> getLuggageByPassengerNationalId(@PathVariable String nationalId) {
        return new ResponseEntity<>(luggageItemService.getLuggageByPassengerNationalId(nationalId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLuggageItem(@PathVariable UUID id) {
        String response = luggageItemService.deleteLuggageItem(id);
        if (response.equals("The luggage item with that id does not exist")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
