package DTO.CarDTO;

import java.time.LocalDateTime;

public class ReviewResponseDTO {
    private int rating;
    private String comment;
    private String userName;
    private LocalDateTime createdAt;

    // Getters & Setters
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
