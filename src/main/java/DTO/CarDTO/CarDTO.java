package DTO.CarDTO;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class CarDTO {

    private int id;
    private String registrationNo;
    private String name;
    private String type;
    private Integer capacity;
    private String fuelCapacity;
    private String transmission;
    private String description;
    private double pricePerDay;
    private String status;
    private Timestamp createdAt;

    // Related data
    private List<String> imageUrls;
    private List<ReviewDTO> reviews;

    // ✅ Constructors
    public CarDTO() {}

    public CarDTO(int id, String registrationNo, String name, String type, Integer capacity,
                  String fuelCapacity, String transmission, String description, double pricePerDay,
                  String status, Timestamp createdAt, List<String> imageUrls, List<ReviewDTO> reviews) {
        this.id = id;
        this.registrationNo = registrationNo;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.fuelCapacity = fuelCapacity;
        this.transmission = transmission;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.status = status;
        this.createdAt = createdAt;
        this.imageUrls = imageUrls;
        this.reviews = reviews;
    }

    // ✅ Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRegistrationNo() { return registrationNo; }
    public void setRegistrationNo(String registrationNo) { this.registrationNo = registrationNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public String getFuelCapacity() { return fuelCapacity; }
    public void setFuelCapacity(String fuelCapacity) { this.fuelCapacity = fuelCapacity; }

    public String getTransmission() { return transmission; }
    public void setTransmission(String transmission) { this.transmission = transmission; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

    public List<ReviewDTO> getReviews() { return reviews; }
    public void setReviews(List<ReviewDTO> reviews) { this.reviews = reviews; }
}
