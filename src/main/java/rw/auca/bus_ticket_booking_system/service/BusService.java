package rw.auca.bus_ticket_booking_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.bus_ticket_booking_system.entity.Bus;
import rw.auca.bus_ticket_booking_system.repository.BusRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    public String saveBus(Bus bus) {
        busRepository.save(bus);
        return "Bus saved successfully";
    }

    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    public Optional<Bus> getBusById(UUID id) {
        return busRepository.findById(id);
    }

    public String updateBus(UUID id, Bus bus) {
        Optional<Bus> optional = busRepository.findById(id);
        if (optional.isEmpty()) {
            return "The bus with that id does not exist";
        }
        Bus existing = optional.get();
        existing.setBusNumber(bus.getBusNumber());
        existing.setName(bus.getName());
        busRepository.save(existing);
        return "Bus updated successfully";
    }

    public String deleteBus(UUID id) {
        Optional<Bus> optional = busRepository.findById(id);
        if (optional.isEmpty()) {
            return "The bus with that id does not exist";
        }
        busRepository.delete(optional.get());
        return "Bus deleted successfully";
    }

    public List<Bus> getFreeBuses() {
        return busRepository.findFreeBuses();
    }
}
