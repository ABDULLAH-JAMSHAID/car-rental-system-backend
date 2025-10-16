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
    public static final String addCar="insert into crs.cars(name,type,capacity,fuel_capacity,transmission,description,price_per_day,status) values(?,?,?,?,?,?,?,?) RETURNING id,created_at";

    public static final String updateCarRegistrationNo="update crs.cars set registration_no=? where id=?";

    public static final String getCarByRegistrationNo =
            "SELECT c.id AS car_id, c.registration_no, c.name, c.type, c.capacity, " +
                    "c.fuel_capacity, c.transmission, c.description, c.price_per_day, " +
                    "c.status, c.created_at, " +
                    "ci.id AS image_id, ci.image_url, ci.is_main " +
                    "FROM crs.cars c " +
                    "LEFT JOIN crs.car_images ci ON c.id = ci.car_id " +
                    "WHERE c.registration_no = ? " +
                    "AND c.status NOT IN ('DELETED', 'INACTIVE');";


    public static final String updateCar="update crs.cars set name=?,type=?,capacity =?,fuel_capacity=?,transmission=?,description=?,price_per_day=?,status=? where registration_no=?";

    public static final String getAllCars =
            "SELECT c.id AS car_id, " +
                    "       c.registration_no, " +
                    "       c.name, " +
                    "       c.type, " +
                    "       c.capacity, " +
                    "       c.fuel_capacity, " +
                    "       c.transmission, " +
                    "       c.description, " +
                    "       c.price_per_day, " +
                    "       c.status, " +
                    "       c.created_at, " +
                    "       ci.image_url " +
                    "FROM crs.cars c " +
                    "LEFT JOIN crs.car_images ci ON c.id = ci.car_id " +
                    "WHERE c.status NOT IN ('DELETED', 'INACTIVE') " +
                    "ORDER BY c.created_at DESC;";

    public static final String deleteCarByRegistrationNo="delete from crs.cars where registration_no=?";

    public static final String findById="select * from crs.users where id=?";

    public static final String addCarImages = "INSERT INTO crs.car_images (car_id, image_url, is_main) " +
            "VALUES (?, ?, ?) RETURNING id";

    public static final String getCarImages= "SELECT * FROM crs.car_images WHERE car_id = ?";


    public static final String getCarImageById =
            "SELECT * FROM crs.car_images WHERE id = ?";

    public static final String updateCarImage =
            "UPDATE crs.car_images SET image_url = ?, is_main = ?, uploaded_at = ? WHERE id = ?";

    public static final String unset="UPDATE crs.car_images SET is_main = FALSE WHERE car_id = ?";

    public static final String setMain="UPDATE crs.car_images SET is_main = TRUE WHERE id = ?";

    public static final String findCarImageById= "SELECT * FROM crs.car_images WHERE id = ?";

    public static final String deleteCarImageById="DELETE FROM crs.car_images WHERE id = ?";

    public static final String addUserImage =
            "INSERT INTO crs.user_images (user_id, image_url, uploaded_at) VALUES (?, ?, ?) RETURNING id";

    public static final String getUserImageByUserId =
            "SELECT * FROM crs.user_images WHERE user_id = ?";

    public static final String deleteProfileImageById = "DELETE FROM crs.user_images WHERE user_id = ?";

    public static final String getProfileById = """
        SELECT u.id, u.full_name, u.email, u.phone, u.address,\s
               u.gender, u.license_number, u.is_active, u.is_verified,
               i.image_url
        FROM crs.users u
        LEFT JOIN crs.user_images i ON u.id = i.user_id
        WHERE u.id = ?
   \s""";

    public static final String INSERT_RENTAL =
            "INSERT INTO crs.rentals (car_id, user_id, pickup_location, pickup_date, dropoff_date,total_days, total_price, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?,?)";

    public static final String GET_CAR_PRICE_PER_DAY =
            "SELECT price_per_day FROM crs.cars WHERE id = ?";

    public static final String FIND_CAR_BY_ID="select * from crs.cars where id=?";



}
