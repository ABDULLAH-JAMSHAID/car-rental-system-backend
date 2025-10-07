package Model;

import java.sql.Timestamp;

public class OtpRecord {

    private Integer id;
    private Integer user_id;
    private String otp_code;
    private Timestamp expires_at;
    private Boolean is_used;
    private Timestamp created_at;

    public OtpRecord() {
    }
    public OtpRecord(Integer user_id, String otp_code, Timestamp expires_at, Boolean is_used, Timestamp created_at) {
        this.user_id = user_id;
        this.otp_code = otp_code;
        this.expires_at = expires_at;
        this.is_used = is_used;
        this.created_at = created_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getOtp_code() {
        return otp_code;
    }

    public void setOtp_code(String otp_code) {
        this.otp_code = otp_code;
    }

    public Timestamp getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(Timestamp expires_at) {
        this.expires_at = expires_at;
    }

    public Boolean getIs_used() {
        return is_used;
    }

    public void setIs_used(Boolean is_used) {
        this.is_used = is_used;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
