package DTO.RentalDTO;

import Enums.CarStatus;

public class RentalRequestDTO {

    private CarStatus status;

    public RentalRequestDTO() {
    }

    public RentalRequestDTO(CarStatus status) {
        this.status = status;
    }
    public CarStatus getStatus() {
        return status;
    }
    public void setStatus(CarStatus status) {
        this.status = status;
    }

}
