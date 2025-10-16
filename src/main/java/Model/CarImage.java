package Model;


import java.sql.Timestamp;


public class CarImage {
    private int id;
    private int carId;
    private String imageUrl;
    private boolean isMain;
    private Timestamp uploadedAt;

    public CarImage() {}

    public CarImage(int carId, String imageUrl, boolean isMain) {
        this.carId = carId;
        this.imageUrl = imageUrl;
        this.isMain = isMain;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isMain() { return isMain; }
    public void setMain(boolean main) { isMain = main; }

    public Timestamp getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(Timestamp uploadedAt) { this.uploadedAt = uploadedAt; }
}
