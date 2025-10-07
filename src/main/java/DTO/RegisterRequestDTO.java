package DTO;

public class RegisterRequestDTO {

    private String full_name;
    private String email;
    private String password;
    private String phone;

    public RegisterRequestDTO() {
    }
    public RegisterRequestDTO(String full_name, String email, String password, String phone) {
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
