package rw.auca.bus_ticket_booking_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.bus_ticket_booking_system.entity.Passenger;
import rw.auca.bus_ticket_booking_system.repository.DriverRepository;
import rw.auca.bus_ticket_booking_system.repository.PassengerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private DriverRepository driverRepository;

    public String savePassenger(Passenger passenger) {
        passengerRepository.save(passenger);
        return "Passenger saved successfully";
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Optional<Passenger> getPassengerById(UUID id) {
        return passengerRepository.findById(id);
    }

    public String updatePassenger(UUID id, Passenger passenger) {
        Optional<Passenger> optional = passengerRepository.findById(id);
        if (optional.isEmpty()) {
            return "The passenger with that id does not exist";
        }
        Passenger existing = optional.get();
        existing.setFirstName(passenger.getFirstName());
        existing.setLastName(passenger.getLastName());
        existing.setNationalId(passenger.getNationalId());
        existing.setPhone(passenger.getPhone());
        passengerRepository.save(existing);
        return "Passenger updated successfully";
    }

    public String deletePassenger(UUID id) {
        Optional<Passenger> optional = passengerRepository.findById(id);
        if (optional.isEmpty()) {
            return "The passenger with that id does not exist";
        }
        passengerRepository.delete(optional.get());
        return "Passenger deleted successfully";
    }

    public List<Passenger> getPassengersByLastName(String lastName) {
        return passengerRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public boolean driverExists(UUID driverId) {
        return driverRepository.existsById(driverId);
    }

    public List<Passenger> getPassengersByDriverId(UUID driverId) {
        return passengerRepository.findPassengersByDriverId(driverId);
    }
}
