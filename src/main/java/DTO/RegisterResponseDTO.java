package DTO;

public class RegisterResponseDTO {
    private Integer userId;
    private String fullName;
    private String email;
    private String message;

    public RegisterResponseDTO(){

    }

    public RegisterResponseDTO(Integer userId, String fullName, String email, String message) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.message = message;
    }

    // ✅ Getters and Setters
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
