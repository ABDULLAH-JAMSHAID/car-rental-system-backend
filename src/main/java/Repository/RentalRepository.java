package Repository;


import Enums.CarStatus;
import Handler.AppException;
import Model.Car;
import Model.Rental;
import Utill.DBConnection;
import Utill.sql;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;

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
}
