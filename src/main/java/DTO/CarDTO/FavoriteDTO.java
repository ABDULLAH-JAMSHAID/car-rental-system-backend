package DTO.CarDTO;

public class FavoriteDTO {
    private int userId;
    private int carId;

    public FavoriteDTO() {}

    public FavoriteDTO(int userId, int carId) {
        this.userId = userId;
        this.carId = carId;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }
}
