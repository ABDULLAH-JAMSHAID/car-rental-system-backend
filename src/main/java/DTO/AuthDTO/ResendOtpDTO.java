package DTO.AuthDTO;

public class ResendOtpDTO{

    private String email;

    public ResendOtpDTO() {
    }

    public ResendOtpDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
