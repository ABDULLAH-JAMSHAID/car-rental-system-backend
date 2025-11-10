package DTO.RentalDTO;

import java.sql.Timestamp;
import DTO.CarDTO.CarDTO;

public class RentalResponseDTO {
    private int id;
    private Timestamp pickupDate;
    private Timestamp dropoffDate;
    private String pickupLocation;
    private int totalDays;
    private double totalPrice;
    private String status;
    private Timestamp createdAt;

    private CarDTO car; // ✅ Nested car info (with images + reviews)

    public RentalResponseDTO() {}

    public RentalResponseDTO(int id, Timestamp pickupDate, Timestamp dropoffDate,
                             String pickupLocation, int totalDays, double totalPrice,
                             String status, Timestamp createdAt, CarDTO car) {
        this.id = id;
        this.pickupDate = pickupDate;
        this.dropoffDate = dropoffDate;
        this.pickupLocation = pickupLocation;
        this.totalDays = totalDays;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.car = car;
    }

    public RentalResponseDTO(int id, String pickupLocation, Timestamp pickupDate, Timestamp dropoffDate, int totalDays, double totalPrice, String status, Timestamp createdAt, CarDTO car) {
    }


    // ✅ Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Timestamp getPickupDate() { return pickupDate; }
    public void setPickupDate(Timestamp pickupDate) { this.pickupDate = pickupDate; }

    public Timestamp getDropoffDate() { return dropoffDate; }
    public void setDropoffDate(Timestamp dropoffDate) { this.dropoffDate = dropoffDate; }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

    public int getTotalDays() { return totalDays; }
    public void setTotalDays(int totalDays) { this.totalDays = totalDays; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public CarDTO getCar() { return car; }
    public void setCar(CarDTO car) { this.car = car; }
}
