package Model;

import java.sql.Timestamp;

public class ProfileImage {
    private int id;
    private int userId;
    private String imageUrl;
    private Timestamp uploadedAt;

    public ProfileImage() {}

    public ProfileImage(int id, int userId, String imageUrl, Timestamp uploadedAt) {
        this.id = id;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.uploadedAt = uploadedAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Timestamp getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(Timestamp uploadedAt) { this.uploadedAt = uploadedAt; }
}
