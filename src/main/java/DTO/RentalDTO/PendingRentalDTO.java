package DTO.RentalDTO;

import DTO.CarDTO.CarRequestDTO;
import DTO.CustomerDTO.CustomerRequestDTO;
import java.time.LocalDateTime;

public class PendingRentalDTO {
    private int id;
    private String pickupLocation;
    private LocalDateTime pickupDate;
    private LocalDateTime dropoffDate;
    private int totalDays;
    private double totalPrice;
    private String status;
    private LocalDateTime createdAt;

    private CarRequestDTO car;
    private CustomerRequestDTO customer;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

    public LocalDateTime getPickupDate() { return pickupDate; }
    public void setPickupDate(LocalDateTime pickupDate) { this.pickupDate = pickupDate; }

    public LocalDateTime getDropoffDate() { return dropoffDate; }
    public void setDropoffDate(LocalDateTime dropoffDate) { this.dropoffDate = dropoffDate; }

    public int getTotalDays() { return totalDays; }
    public void setTotalDays(int totalDays) { this.totalDays = totalDays; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public CarRequestDTO getCar() { return car; }
    public void setCar(CarRequestDTO car) { this.car = car; }

    public CustomerRequestDTO getCustomer() { return customer; }
    public void setCustomer(CustomerRequestDTO customer) { this.customer = customer; }
}
