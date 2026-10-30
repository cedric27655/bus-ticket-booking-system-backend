package rw.auca.bus_ticket_booking_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.auca.bus_ticket_booking_system.entity.Bus;
import rw.auca.bus_ticket_booking_system.entity.Driver;
import rw.auca.bus_ticket_booking_system.entity.Route;
import rw.auca.bus_ticket_booking_system.repository.BusRepository;
import rw.auca.bus_ticket_booking_system.repository.DriverRepository;
import rw.auca.bus_ticket_booking_system.repository.RouteRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private BusRepository busRepository;

    public String saveDriver(Driver driver) {
        if (driver.getBus() != null && driver.getBus().getId() != null) {
            Optional<Bus> busOpt = busRepository.findById(driver.getBus().getId());
            busOpt.ifPresent(driver::setBus);
        }
        driverRepository.save(driver);
        return "Driver saved successfully";
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Optional<Driver> getDriverById(UUID id) {
        return driverRepository.findById(id);
    }

    public String updateDriver(UUID id, Driver driver) {
        Optional<Driver> optional = driverRepository.findById(id);
        if (optional.isEmpty()) {
            return "The driver with that id does not exist";
        }
        Driver existing = optional.get();
        existing.setFirstName(driver.getFirstName());
        existing.setLastName(driver.getLastName());
        existing.setPhone(driver.getPhone());
        if (driver.getBus() != null && driver.getBus().getId() != null) {
            busRepository.findById(driver.getBus().getId()).ifPresent(existing::setBus);
        }
        driverRepository.save(existing);
        return "Driver updated successfully";
    }

    public String deleteDriver(UUID id) {
        Optional<Driver> optional = driverRepository.findById(id);
        if (optional.isEmpty()) {
            return "The driver with that id does not exist";
        }
        driverRepository.delete(optional.get());
        return "Driver deleted successfully";
    }

    public List<Driver> getDriversByRoute(String name) {
        return driverRepository.findDriversByRouteName(name);
    }

    public String addRouteToDriver(UUID driverId, UUID routeId) {
        Optional<Driver> driverOpt = driverRepository.findById(driverId);
        if (driverOpt.isEmpty()) {
            return "The driver with that id does not exist";
        }
        Optional<Route> routeOpt = routeRepository.findById(routeId);
        if (routeOpt.isEmpty()) {
            return "That route does not exist";
        }
        Driver driver = driverOpt.get();
        Route route = routeOpt.get();

        for (Route r : driver.getRoutes()) {
            if (r.getId().equals(routeId)) {
                return "The driver already has that route";
            }
        }

        driver.getRoutes().add(route);
        driverRepository.save(driver);
        return "Route added to the driver";
    }

    public String changeBus(UUID driverId, int busNumber) {
        Optional<Driver> driverOpt = driverRepository.findById(driverId);
        if (driverOpt.isEmpty()) {
            return "The driver with that id does not exist";
        }
        Optional<Bus> busOpt = busRepository.findByBusNumber(busNumber);
        if (busOpt.isEmpty()) {
            return "The bus with that busNumber does not exist";
        }
        Bus bus = busOpt.get();
        if (driverRepository.existsByBus(bus)) {
            return "That bus is taken by another driver";
        }
        Driver driver = driverOpt.get();
        driver.setBus(bus);
        driverRepository.save(driver);
        return "Bus changed successfully";
    }
}
