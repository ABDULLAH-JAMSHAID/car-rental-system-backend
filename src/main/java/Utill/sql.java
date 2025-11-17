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


    public static final String UPDATE_RENTAL_STATUS ="UPDATE crs.rentals SET status = ? WHERE id = ?";
    public static final String CHECK_RENTAL_EXISTS = "select * from crs.rentals where id=?";
    public static final String updateCarStatus = "UPDATE crs.cars SET status = ? WHERE id = (SELECT car_id FROM crs.rentals WHERE id = ?)";

    public static final String SEARCH_CARS_QUERY = """
    SELECT 
        c.id AS car_id,
        c.registration_no,
        c.name,
        c.type,
        c.capacity,
        c.fuel_capacity,
        c.transmission,
        c.description,
        c.price_per_day,
        c.created_at,
        c.status,
        ci.image_url,
        r.rating,
        r.comment,
        u.full_name AS reviewer_name
    FROM crs.cars c
    LEFT JOIN crs.car_images ci ON c.id = ci.car_id
    LEFT JOIN crs.reviews r ON c.id = r.car_id
    LEFT JOIN crs.users u ON r.user_id = u.id
    WHERE 
        c.id NOT IN (
            SELECT re.car_id 
            FROM crs.rentals re 
            WHERE (re.pickup_date, re.dropoff_date) OVERLAPS (?, ?)
        )
        AND (? IS NULL OR c.type = ?)
        AND c.price_per_day BETWEEN ? AND ?
        AND c.status = 'AVAILABLE'
    ORDER BY c.price_per_day ASC
""";

    public static final String rentalHistory = """
                SELECT
                    r.id AS rental_id,
                    r.pickup_date,
                    r.dropoff_date,
                    r.pickup_location,
                    r.total_days,
                    r.total_price,
                    r.status AS rental_status,
                    r.created_at AS rental_created_at,

                    c.id AS car_id,
                    c.registration_no,
                    c.name AS car_name,
                    c.type AS car_type,
                    c.capacity,
                    c.fuel_capacity,
                    c.transmission,
                    c.description,
                    c.price_per_day,
                    c.status AS car_status,
                    c.created_at AS car_created_at,

                    STRING_AGG(DISTINCT ci.image_url, ',') AS image_urls,
                    STRING_AGG(
                        DISTINCT CONCAT(rw.rating, '|', rw.comment, '|', u.full_name, '|', rw.created_at),
                        ';'
                    ) AS review_data

                FROM crs.rentals r
                JOIN crs.cars c ON r.car_id = c.id
                LEFT JOIN crs.car_images ci ON ci.car_id = c.id
                LEFT JOIN crs.reviews rw ON rw.car_id = c.id
                LEFT JOIN crs.users u ON rw.user_id = u.id
                WHERE r.user_id = ?
                GROUP BY r.id, c.id
                ORDER BY r.created_at DESC
                """;


    public static final String INSERT_FAVORITE_SQL =
            "INSERT INTO crs.favorites (user_id, car_id) VALUES (?, ?)";
    public static final String DELETE_FAVORITE_SQL =
            "DELETE FROM crs.favorites WHERE user_id = ? AND car_id = ?";

    public static final String viewFavoriteCars = """
            SELECT c.id AS car_id, c.name, c.type, c.capacity, c.transmission,
                   c.fuel_capacity, c.description, c.price_per_day,
                   ci.image_url, ci.is_main,
                   r.id AS review_id, r.rating, r.comment, r.created_at,
                   u.full_name AS reviewer_name
            FROM crs.favorites f
            JOIN crs.cars c ON f.car_id = c.id
            LEFT JOIN crs.car_images ci ON c.id = ci.car_id
            LEFT JOIN crs.reviews r ON c.id = r.car_id
            LEFT JOIN crs.users u ON r.user_id = u.id
            WHERE f.user_id = ?
            ORDER BY c.id, r.created_at DESC
        """;

    public static final String GET_FAVORITE_CARS_BY_USER = """
    SELECT 
        c.id AS car_id,
        c.registration_no,
        c.name,
        c.type,
        c.capacity,
        c.fuel_capacity,
        c.transmission,
        c.description,
        c.price_per_day,
        c.created_at,
        c.status,
        ci.image_url,
        r.rating,
        r.comment,
        u.full_name AS reviewer_name
    FROM crs.favorites f
    INNER JOIN crs.cars c ON f.car_id = c.id
    LEFT JOIN crs.car_images ci ON c.id = ci.car_id
    LEFT JOIN crs.reviews r ON c.id = r.car_id
    LEFT JOIN crs.users u ON r.user_id = u.id
    WHERE f.user_id = ?
    ORDER BY c.created_at DESC
""";

    public static final String getRentalById = "SELECT id, car_id, user_id, pickup_date, dropoff_date, total_days, total_price, status FROM crs.rentals WHERE id = ?";

    public static final String addFine = "INSERT INTO crs.fines (rental_id, fine_amount, reason) VALUES (?, ?, ?)";

    public static final String cancelRental = "UPDATE crs.rentals SET status = 'CANCELLED' WHERE id = ?";

    public static final String getPendingRentals = """
    SELECT\s
                                              r.id AS rental_id,
                                              r.pickup_location,
                                              r.pickup_date,
                                              r.dropoff_date,
                                              r.total_days,
                                              r.total_price,
                                              r.status,
                                              r.created_at,
            
                                              c.id AS car_id,
                                              c.name AS car_name,
                                              c.registration_no,
                                              c.type AS car_type,
                                              c.capacity,
                                              c.fuel_capacity,
                                              c.transmission,
                                              c.description AS car_description,
                                              c.status AS car_status,
                                              c.price_per_day,
                                              c.created_at AS car_created_at,
            
                                              u.id AS user_id,
                                              u.full_name,
                                              u.email,
                                              u.phone,
                                              u.address,
                                              u.gender,
                                              u.license_number,
                                              u.is_verified,
                                              u.is_active,
                                              u.created_at AS user_created_at
                                          FROM crs.rentals r
                                          JOIN crs.cars c ON r.car_id = c.id
                                          JOIN crs.users u ON r.user_id = u.id
                                          WHERE r.status = ?
                                          ORDER BY r.created_at DESC;
            
""";


    public static final String INSERT_REVIEW = """
        INSERT INTO crs.reviews (car_id, user_id, rating, comment, created_at)
        VALUES (?, ?, ?, ?, NOW())
    """;

    public static final String GET_REVIEWS_BY_CAR_ID = """
        SELECT 
            r.rating,
            r.comment,
            r.created_at,
            u.full_name AS user_name
        FROM crs.reviews r
        JOIN crs.users u ON r.user_id = u.id
        WHERE r.car_id = ?
        ORDER BY r.created_at DESC
    """;

    public static final String getOverdueRentals = """
        SELECT 
            r.id AS rental_id,
            c.name AS car_name,
            u.full_name AS user_name,
            r.dropOff_date,
            (CURRENT_DATE - r.dropOff_date) AS days_overdue,
            ((CURRENT_DATE - r.dropOff_date) * c.price_per_day * 0.10) AS estimated_penalty
        FROM crs.rentals r
        JOIN crs.cars c ON r.car_id = c.id
        JOIN crs.users u ON r.user_id = u.id
        WHERE r.dropOff_date < CURRENT_DATE
          AND r.status = 'RENTED';
    """;

    public static final  String totalRentalsQuery = "SELECT COUNT(*) FROM crs.rentals WHERE user_id = ?";
    public static final String activeRentalsQuery = "SELECT COUNT(*) FROM crs.rentals WHERE user_id = ? AND status = 'RENTED'";
    public static final String totalSpentQuery = "SELECT COALESCE(SUM(total_price), 0) FROM crs.rentals WHERE user_id = ?";
    public static final String lastCarQuery = """
        SELECT c.name, r.dropoff_date, c.price_per_day
        FROM crs.rentals r
        JOIN crs.cars c ON r.car_id = c.id
        WHERE r.user_id = ?
        ORDER BY r.dropoff_date DESC
        LIMIT 1
    """;

    public static final String totalCarsQuery = "SELECT COUNT(*) FROM crs.cars";
    public static final String totalUsersQuery = "SELECT COUNT(*) FROM crs.users";
    public static final String totalRentalQuery = "SELECT COUNT(*) FROM crs.rentals";
    public static final String activeRentalQuery = "SELECT COUNT(*) FROM crs.rentals WHERE status = 'RENTED'";
    public static final String overdueRentalsQuery = "SELECT COUNT(*) FROM crs.rentals WHERE dropoff_date < CURRENT_DATE AND status = 'RENTED'";
    public static final String totalRevenueQuery = "SELECT COALESCE(SUM(total_price), 0) FROM crs.rentals";

    public static final String mostRentedCarQuery = """
            SELECT c.name 
            FROM crs.rentals r
            JOIN crs.cars c ON r.car_id = c.id
            GROUP BY c.id, c.name
            ORDER BY COUNT(r.id) DESC
            LIMIT 1
        """;

    public static final String topRatedCarQuery = """
            SELECT c.name
            FROM crs.reviews rv
            JOIN crs.cars c ON rv.car_id = c.id
            GROUP BY c.id, c.name
            ORDER BY AVG(rv.rating) DESC
            LIMIT 1
        """;
    public static final String findRoleIdByUserId="SELECT role_id FROM crs.user_roles WHERE user_id = ?";

}
