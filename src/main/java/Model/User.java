package Model;

import Enums.Gender;

import java.time.LocalDateTime;

public class User {

    private Integer id;

    private String full_name;

    private String email;

    private String password;

    private String phone;

    private String address;

    private Gender gender;

    private String license_number;

    private boolean is_active;

    private boolean is_verified;

    private LocalDateTime created_at;

    private LocalDateTime deleted_at;

    public User(){
    super();
    }

    public User(Integer id, String full_name, String email, String password, String phone, String address, Gender gender, String license_number, boolean is_active, boolean is_verified, LocalDateTime created_at, LocalDateTime deleted_at) {
        this.id = id;
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.license_number = license_number;
        this.is_active = is_active;
        this.is_verified = is_verified;
        this.created_at = created_at;
        this.deleted_at = deleted_at;
    }

    public User(String full_name, String email, String password, String phone) {
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLicense_number() {
        return license_number;
    }

    public void setLicense_number(String license_number) {
        this.license_number = license_number;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(LocalDateTime deleted_at) {
        this.deleted_at = deleted_at;
    }
}
