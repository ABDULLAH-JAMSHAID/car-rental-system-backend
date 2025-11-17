package Repository;


import DTO.CarDTO.CarDTO;
import DTO.CarDTO.CarRequestDTO;
import DTO.CarDTO.ReviewDTO;
import DTO.CarDTO.ReviewResponseDTO;
import DTO.CustomerDTO.CustomerRequestDTO;
import DTO.RentalDTO.*;
import Enums.CarStatus;
import Handler.AppException;
import Model.Car;
import Model.Rental;
import Utill.DBConnection;
import Utill.sql;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalRepository {

    private final DataSource ds= DBConnection.getDataSource();

    public boolean insertRental(Rental rental) throws SQLException {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.INSERT_RENTAL)) {

            ps.setInt(1, rental.getCarId());
            ps.setInt(2, rental.getUserId());
            ps.setString(3, rental.getPickupLocation());
            ps.setTimestamp(4, rental.getPickupDate());
            ps.setTimestamp(5, rental.getDropoffDate());
            ps.setInt(6,rental.getTotalDays());
            ps.setDouble(7, rental.getTotalPrice());
            ps.setString(8, rental.getStatus());

            return ps.executeUpdate() > 0;
        }
    }

    public Car findCarById(int carId) throws SQLException {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.FIND_CAR_BY_ID)) {

            ps.setInt(1, carId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("id"));
                car.setRegistration_no(rs.getString("registration_no"));
                car.setName(rs.getString("name"));
                car.setType(rs.getString("type"));
                car.setCapacity(rs.getInt("capacity"));
                car.setFuel_capacity(rs.getString("fuel_capacity"));
                car.setTransmission(rs.getString("transmission"));
                car.setDescription(rs.getString("description"));
                car.setPrize_per_day(rs.getDouble("price_per_day"));
                car.setStatus(CarStatus.valueOf(rs.getString("status")));
                car.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
                return car;
            }
        }
        return null; // Not found
    }

    public boolean updateRentalStatus(int rentalId, CarStatus status) {

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.UPDATE_RENTAL_STATUS)) {

            ps.setString(1, status.name());
            ps.setInt(2, rentalId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Database error while updating rental status: " + e.getMessage());
        }
    }

    public boolean checkRentalExists(int rentalId) {

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.CHECK_RENTAL_EXISTS)) {

            ps.setInt(1, rentalId);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Database error while checking rental existence: " + e.getMessage());
        }
    }

    public void updateCarStatus(int rentalId, CarStatus carStatus) {

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.updateCarStatus)) {

            ps.setString(1, carStatus.name());
            ps.setInt(2, rentalId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Database error while updating car status: " + e.getMessage());
        }
    }

    public Rental getRentalById(int rentalId) {

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.getRentalById)) {

            ps.setInt(1, rentalId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Rental r = new Rental();
                r.setId(rs.getInt("id"));
                r.setCarId(rs.getInt("car_id"));
                r.setUserId(rs.getInt("user_id"));
                r.setPickupDate(rs.getTimestamp("pickup_date"));
                r.setDropoffDate(rs.getTimestamp("dropoff_date"));
                r.setTotalDays(rs.getInt("total_days"));
                r.setTotalPrice(rs.getDouble("total_price"));
                r.setStatus(rs.getString("status"));
                return r;
            }

        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }

        return null;
    }


    public void addFine(int rentalId, double amount, String reason) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.addFine)) {
            ps.setInt(1, rentalId);
            ps.setDouble(2, amount);
            ps.setString(3, reason);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void cancelRental(int rentalId) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.cancelRental)) {
            ps.setInt(1, rentalId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AppException(500, "Error updating rental status: " + e.getMessage());
        }
    }


    public List<RentalResponseDTO> getRentalHistoryByUserId(int userId) throws AppException {
        List<RentalResponseDTO> rentals = new ArrayList<>();

        String sql = """
            SELECT r.id AS rental_id, r.pickup_location, r.pickup_date, r.dropoff_date,
                   r.total_days, r.total_price, r.status, r.created_at,
                   c.id AS car_id, c.registration_no, c.name, c.type, c.capacity,
                   c.fuel_capacity, c.transmission, c.description, c.price_per_day,
                   c.status AS car_status, c.created_at AS car_created_at
            FROM crs.rentals r
            JOIN crs.cars c ON r.car_id = c.id
            WHERE r.user_id = ?
            ORDER BY r.created_at DESC
        """;

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int carId = rs.getInt("car_id");

                // ✅ Fetch images and reviews
                List<String> imageUrls = getCarImages(conn, carId);
                List<ReviewDTO> reviews = getCarReviews(conn, carId);

                // ✅ Build CarDTO
                CarDTO car = new CarDTO(
                        carId,
                        rs.getString("registration_no"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getInt("capacity"),
                        rs.getString("fuel_capacity"),
                        rs.getString("transmission"),
                        rs.getString("description"),
                        rs.getDouble("price_per_day"),
                        rs.getString("car_status"),
                        rs.getTimestamp("car_created_at"),
                        imageUrls,
                        reviews
                );

                // ✅ Build RentalResponseDTO
                RentalResponseDTO rental = new RentalResponseDTO();
                rental.setId(rs.getInt("rental_id"));
                rental.setPickupLocation(rs.getString("pickup_location"));
                rental.setPickupDate(rs.getTimestamp("pickup_date"));
                rental.setDropoffDate(rs.getTimestamp("dropoff_date"));
                rental.setTotalDays(rs.getInt("total_days"));
                rental.setTotalPrice(rs.getDouble("total_price"));
                rental.setStatus(rs.getString("status"));
                rental.setCreatedAt(rs.getTimestamp("created_at"));
                rental.setCar(car);

                rentals.add(rental);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new AppException(500, "Error fetching rental history");
        }

        return rentals;
    }

    // ✅ Helper: Get car images
    private List<String> getCarImages(Connection conn, int carId) throws SQLException {
        List<String> images = new ArrayList<>();
        String sql = "SELECT image_url FROM crs.car_images WHERE car_id = ? ORDER BY uploaded_at DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                images.add(rs.getString("image_url"));
            }
        }
        return images;
    }

    // ✅ Helper: Get car reviews (with reviewerName)
    private List<ReviewDTO> getCarReviews(Connection conn, int carId) throws SQLException {
        List<ReviewDTO> reviews = new ArrayList<>();
        String sql = """
            SELECT r.rating, r.comment, u.full_name AS reviewer_name
            FROM crs.reviews r
            JOIN crs.users u ON r.user_id = u.id
            WHERE r.car_id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReviewDTO review = new ReviewDTO();
                review.setRating(rs.getInt("rating"));
                review.setComment(rs.getString("comment"));
                review.setReviewerName(rs.getString("reviewer_name"));
                reviews.add(review);
            }
        }
        return reviews;
    }

    public List<PendingRentalDTO> getPendingRentals(CarStatus status) throws AppException {
        List<PendingRentalDTO> rentals = new ArrayList<>();

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.getPendingRentals)) {
            ps.setString(1,status.name());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PendingRentalDTO rental = new PendingRentalDTO();
                rental.setId(rs.getInt("rental_id"));
                rental.setPickupLocation(rs.getString("pickup_location"));
                rental.setPickupDate(rs.getTimestamp("pickup_date").toLocalDateTime());
                rental.setDropoffDate(rs.getTimestamp("dropoff_date").toLocalDateTime());
                rental.setTotalDays(rs.getInt("total_days"));
                rental.setTotalPrice(rs.getDouble("total_price"));
                rental.setStatus(rs.getString("status"));
                rental.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                // Car info
                CarRequestDTO car = new CarRequestDTO();
                car.setId(rs.getInt("car_id"));
                car.setName(rs.getString("car_name"));
                car.setRegistration_no(rs.getString("registration_no"));
                car.setType(rs.getString("car_type"));
                car.setCapacity(rs.getInt("capacity"));
                car.setFuel_capacity(rs.getString("fuel_capacity"));
                car.setTransmission(rs.getString("transmission"));
                car.setPrize_per_day(rs.getDouble("price_per_day"));
                rental.setCar(car);

                // Customer info
                CustomerRequestDTO customer = new CustomerRequestDTO();
                customer.setId(rs.getInt("user_id"));
                customer.setFull_name(rs.getString("full_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setGender(rs.getBoolean("gender"));
                customer.setLicense_no(rs.getString("license_number"));
                customer.setVerified(rs.getBoolean("is_verified"));
                rental.setCustomer(customer);

                rentals.add(rental);
            }

            if (rentals.isEmpty()) {
                return null;
            }

            return rentals;

        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Database Error");
        }
    }

    public List<OverdueRentalDTO> getOverdueRentals()  {
        List<OverdueRentalDTO> list = new ArrayList<>();


        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.getOverdueRentals);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                OverdueRentalDTO dto = new OverdueRentalDTO();
                dto.setRentalId(rs.getInt("rental_id"));
                dto.setCarName(rs.getString("car_name"));
                dto.setUserName(rs.getString("user_name"));
                dto.setDropOffDate(rs.getDate("dropOff_date").toLocalDate());
                dto.setDaysOverdue(rs.getInt("days_overdue"));
                dto.setEstimatedPenalty(rs.getDouble("estimated_penalty"));
                list.add(dto);
            }
            return list;
        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Internal Error");
        }

    }

    public CustomerDashboardDTO getCustomerDashboard(int userId)  {
        CustomerDashboardDTO dto = new CustomerDashboardDTO();



        try (Connection conn = ds.getConnection()) {
            // Total rentals
            try (PreparedStatement ps = conn.prepareStatement(sql.totalRentalsQuery)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) dto.setTotalRentalsMade(rs.getInt(1));
            }

            // Active rentals
            try (PreparedStatement ps = conn.prepareStatement(sql.activeRentalsQuery)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) dto.setActiveRentalsCount(rs.getInt(1));
            }

            // Total amount spent
            try (PreparedStatement ps = conn.prepareStatement(sql.totalSpentQuery)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) dto.setTotalAmountSpent(rs.getDouble(1));
            }

            // Last rented car
            try (PreparedStatement ps = conn.prepareStatement(sql.lastCarQuery)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    dto.setLastCarName(rs.getString("name"));
                    dto.setLastDropoffDate(rs.getString("dropoff_date"));
                    dto.setLastCarPricePerDay(rs.getDouble("price_per_day"));
                }
            }
        }catch (SQLException e){
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Database Error");
        }

        return dto;
    }

    public AdminDashboardDTO getDashboardStats() {
        AdminDashboardDTO dto = new AdminDashboardDTO();



        try (Connection conn = ds.getConnection()) {

            try (PreparedStatement ps = conn.prepareStatement(sql.totalCarsQuery);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) dto.setTotalCars(rs.getInt(1));
            }

            try (PreparedStatement ps = conn.prepareStatement(sql.totalUsersQuery);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) dto.setTotalUsers(rs.getInt(1));
            }

            try (PreparedStatement ps = conn.prepareStatement(sql.totalRentalQuery);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) dto.setTotalRentals(rs.getInt(1));
            }

            try (PreparedStatement ps = conn.prepareStatement(sql.activeRentalQuery);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) dto.setActiveRentals(rs.getInt(1));
            }

            try (PreparedStatement ps = conn.prepareStatement(sql.overdueRentalsQuery);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) dto.setOverdueRentals(rs.getInt(1));
            }

            try (PreparedStatement ps = conn.prepareStatement(sql.totalRevenueQuery);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) dto.setTotalRevenue(rs.getDouble(1));
            }

            try (PreparedStatement ps = conn.prepareStatement(sql.mostRentedCarQuery);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) dto.setMostRentedCar(rs.getString("name"));
            }

            try (PreparedStatement ps = conn.prepareStatement(sql.topRatedCarQuery);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) dto.setTopRatedCar(rs.getString("name"));
            }
        }catch (SQLException e){
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Database Error");
        }

        return dto;
    }


}
