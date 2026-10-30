package rw.auca.bus_ticket_booking_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "luggage_item")
public class LuggageItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String itemName;

    private String description;

    private int weightKg;

    private LocalDate loadedDate;

    @ManyToOne
    @JoinColumn(name = "ticket_booking_id")
    private TicketBooking ticketBooking;

    public LuggageItem() {
    }

    public LuggageItem(String itemName, String description, int weightKg, LocalDate loadedDate, TicketBooking ticketBooking) {
        this.itemName = itemName;
        this.description = description;
        this.weightKg = weightKg;
        this.loadedDate = loadedDate;
        this.ticketBooking = ticketBooking;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(int weightKg) {
        this.weightKg = weightKg;
    }

    public LocalDate getLoadedDate() {
        return loadedDate;
    }

    public void setLoadedDate(LocalDate loadedDate) {
        this.loadedDate = loadedDate;
    }

    public TicketBooking getTicketBooking() {
        return ticketBooking;
    }

    public void setTicketBooking(TicketBooking ticketBooking) {
        this.ticketBooking = ticketBooking;
    }
}
