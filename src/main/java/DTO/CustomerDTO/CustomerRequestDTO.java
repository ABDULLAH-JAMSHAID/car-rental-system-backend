package DTO.CustomerDTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CustomerRequestDTO {


    private Integer id;

    private String full_name;

    private String email;

    private String password;

    private String phone;

    private String address;

    private boolean gender;

    private String license_no;

    private boolean isActive;

    private boolean isVerified;

    private LocalDateTime created_at;

    private LocalDateTime deleted_at;

    public CustomerRequestDTO() {
    }


    public CustomerRequestDTO(String full_name, String email, String phone, String address, boolean gender, boolean isActive, String license_no, boolean isVerified, String password) {
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.isActive = isActive;
        this.license_no = license_no;
        this.isVerified = isVerified;
        this.password = password;
    }

    public CustomerRequestDTO(String full_name, String email, String phone, String address, boolean gender, String password, String license_no, boolean isActive, LocalDateTime deleted_at, boolean isVerified) {
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.password = password;
        this.license_no = license_no;
        this.isActive = isActive;
        this.deleted_at = deleted_at;
        this.isVerified = isVerified;
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

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
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
