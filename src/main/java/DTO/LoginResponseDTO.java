package DTO;

import java.util.List;

public class LoginResponseDTO {
    private String message;
    private Integer userId;
    private String fullName;
    private String email;
    private String accessToken;
    private long accessTokenExpiresIn;   // in seconds or milliseconds
    private String refreshToken;
    private long refreshTokenExpiresIn;
    private String role;
    private List<String> permissions;

    // ✅ Constructor (with role & permissions added)
    public LoginResponseDTO(String message,Integer userId, String fullName, String email,
                            String accessToken, long accessTokenExpiresIn,
                            String refreshToken, long refreshTokenExpiresIn,
                            String role, List<String> permissions
                            ) {
        this.message = message;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.accessToken = accessToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
        this.role = role;
        this.permissions = permissions;

    }

    // ✅ Getters and Setters
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public long getAccessTokenExpiresIn() { return accessTokenExpiresIn; }
    public void setAccessTokenExpiresIn(long accessTokenExpiresIn) { this.accessTokenExpiresIn = accessTokenExpiresIn; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public long getRefreshTokenExpiresIn() { return refreshTokenExpiresIn; }
    public void setRefreshTokenExpiresIn(long refreshTokenExpiresIn) { this.refreshTokenExpiresIn = refreshTokenExpiresIn; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
