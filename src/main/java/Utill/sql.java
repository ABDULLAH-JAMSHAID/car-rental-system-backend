package Utill;

public class sql {

    public static final String find="Select * from crs.users where email= ? OR phone =?";

    public static final String saveUser="INSERT INTO crs.users(full_name,email,password,phone) VALUES (?,?,?,?) RETURNING id";

    public static final String saveOtp="insert into crs.otp_verifications(user_id,otp_code,expires_at) values(?,?,?)";

    public static final String otpRecord="SELECT * FROM crs.otp_verifications WHERE user_id = ? ORDER BY created_at DESC LIMIT 1";

    public static final String markUserAsVerified="UPDATE crs.users SET is_verified=? where id=?";

    public static final String deleteOtp="delete FROM crs.otp_verifications WHERE user_id = ?";

    public static final String getUserPermissions="SELECT p.permission_name FROM crs.roles r " +
            "JOIN crs.role_permissions rp ON r.id = rp.role_id " +
            "JOIN crs.permissions p ON rp.permission_id = p.id " +
            "WHERE r.id = ?";

    public static final String assignRole="insert into crs.user_roles VALUES(?,?)";

    public static final String getRoleByUserId="SELECT r.role_name\n" +
            "FROM crs.user_roles ur\n" +
            "JOIN crs.roles r ON ur.role_id = r.id\n" +
            "WHERE ur.user_id = ?;";


}
