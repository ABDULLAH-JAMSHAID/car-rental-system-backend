package DTO.RentalDTO;


import Enums.CarStatus;
import Model.Car;

public class CancelResponseDTO {
    private int rentalId;
    private CarStatus status;
    private String message;

    public CancelResponseDTO(int rentalId, CarStatus status, String message) {
        this.rentalId = rentalId;
        this.status = status;
        this.message = message;
    }

    // Getters

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public CarStatus getStatus() {
        return status;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

