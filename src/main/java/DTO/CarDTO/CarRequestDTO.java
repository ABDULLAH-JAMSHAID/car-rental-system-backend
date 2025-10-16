package DTO.CarDTO;

import Enums.CarStatus;
import java.time.LocalDateTime;
import java.util.List;

public class CarRequestDTO {

    private Integer id;
    private String registration_no;
    private String name;
    private String type;
    private Integer capacity;
    private String fuel_capacity;
    private String transmission;
    private String description;
    private Double prize_per_day;
    private CarStatus status;
    private LocalDateTime created_at;

    // ✅ NEW FIELD: Multiple image URLs (main + others)
    private List<String> imageUrls;

    public CarRequestDTO() {}

    public CarRequestDTO(Integer id, String registration_no, String name, String type, Integer capacity,
                         String fuel_capacity, String transmission, String description, Double prize_per_day,
                         CarStatus status, LocalDateTime created_at, List<String> imageUrls) {
        this.id = id;
        this.registration_no = registration_no;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.fuel_capacity = fuel_capacity;
        this.transmission = transmission;
        this.description = description;
        this.prize_per_day = prize_per_day;
        this.status = status;
        this.created_at = created_at;
        this.imageUrls = imageUrls;
    }

    public CarRequestDTO(String registration_no, String name, String type, Integer capacity,
                         String fuel_capacity, String transmission, String description,
                         Double prize_per_day, CarStatus status, List<String> imageUrls) {
        this.registration_no = registration_no;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.fuel_capacity = fuel_capacity;
        this.transmission = transmission;
        this.description = description;
        this.prize_per_day = prize_per_day;
        this.status = status;
        this.imageUrls = imageUrls;
    }

    // ✅ Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistration_no() {
        return registration_no;
    }

    public void setRegistration_no(String registration_no) {
        this.registration_no = registration_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getFuel_capacity() {
        return fuel_capacity;
    }

    public void setFuel_capacity(String fuel_capacity) {
        this.fuel_capacity = fuel_capacity;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrize_per_day() {
        return prize_per_day;
    }

    public void setPrize_per_day(Double prize_per_day) {
        this.prize_per_day = prize_per_day;
    }

    public CarStatus getStatus() {
        return status;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
