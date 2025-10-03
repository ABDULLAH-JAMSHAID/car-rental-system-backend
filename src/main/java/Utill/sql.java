package Utill;

public class sql {

    public static final String find="Select * from crs.users where email= ? OR phone =?";

    public static final String saveUser="INSERT INTO crs.users(full_name,email,password,phone) VALUES (?,?,?,?) RETURNING id";

    public static final String saveOtp="insert into crs.otp_verifications(user_id,otp_code,expires_at) values(?,?,?)";

}
