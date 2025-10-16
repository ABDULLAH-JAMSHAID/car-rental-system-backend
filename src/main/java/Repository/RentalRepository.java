package Repository;


import Enums.CarStatus;
import Model.Car;
import Model.Rental;
import Utill.DBConnection;
import Utill.sql;

import javax.sql.DataSource;
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
}
