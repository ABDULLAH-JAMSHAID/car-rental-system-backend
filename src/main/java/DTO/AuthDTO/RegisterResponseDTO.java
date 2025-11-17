package DTO.AuthDTO;

public class RegisterResponseDTO {

    private String message;   // 👈 sabse upar
    private Integer userId;
    private String fullName;
    private String email;

    public RegisterResponseDTO() {}

    public RegisterResponseDTO(String message, Integer userId, String fullName, String email) {
        this.message = message;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
    }

    // Getters & Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
