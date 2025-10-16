package Repository;

import DTO.CarDTO.CarRequestDTO;
import Enums.CarStatus;
import Handler.AppException;
import Utill.DBConnection;
import Utill.sql;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {

    private final DataSource ds= DBConnection.getDataSource();


    public String generateRegistrationNo(String carName, int id) {
        String[] words = carName.trim().toUpperCase().split("\\s+");
        StringBuilder prefix = new StringBuilder();

        for (int i = 0; i < Math.min(2, words.length); i++) {
            prefix.append(words[i], 0, Math.min(3, words[i].length()));
            if (i == 0 && words.length > 1) prefix.append("-");
        }

        return prefix + "-" + String.format("%04d", id);
    }

    public CarRequestDTO addCar(CarRequestDTO body){

        try(Connection connection=ds.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql.addCar)){

            preparedStatement.setString(1,body.getName());
            preparedStatement.setString(2,body.getType());
            preparedStatement.setInt(3,body.getCapacity());
            preparedStatement.setString(4,body.getFuel_capacity());
            preparedStatement.setString(5,body.getTransmission());
            preparedStatement.setString(6,body.getDescription());
            preparedStatement.setDouble(7,body.getPrize_per_day());
            preparedStatement.setString(8,body.getStatus().name());
            ResultSet rs= preparedStatement.executeQuery();

            if (rs.next()){
                int id=rs.getInt("id");
                LocalDateTime created_at=rs.getTimestamp("created_at").toLocalDateTime();
                String registration_no=generateRegistrationNo(body.getName(),id);
                body.setId(id);
                body.setRegistration_no(registration_no);
                body.setCreated_at(created_at);

                try(PreparedStatement ps=connection.prepareStatement(sql.updateCarRegistrationNo)){
                    ps.setString(1,registration_no);
                    ps.setInt(2,id);
                    ps.executeUpdate();
                }

                return body;
            }


        }catch (SQLException e){
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Internal hgdServer Error");
        }
        return null;
    }

    public CarRequestDTO findByRegistrationNo(String registrationNo) {
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.getCarByRegistrationNo)) {

            preparedStatement.setString(1, registrationNo);
            ResultSet rs = preparedStatement.executeQuery();

            CarRequestDTO car = null;
            List<String> imageUrls = new ArrayList<>();

            while (rs.next()) {
                if (car == null) {
                    car = new CarRequestDTO();
                    car.setId(rs.getInt("car_id"));  // renamed in query
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
                }

                String imageUrl = rs.getString("image_url");
                if (imageUrl != null) {
                    imageUrls.add(imageUrl);
                }
            }

            if (car != null) {
                car.setImageUrls(imageUrls); // new field in DTO
            }

            return car;

        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }


    public CarRequestDTO updateCar(CarRequestDTO car,String registration_no) {

        try(Connection connection=ds.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(sql.updateCar)){

            preparedStatement.setString(1,car.getName());
            preparedStatement.setString(2,car.getType());
            preparedStatement.setInt(3,car.getCapacity());
            preparedStatement.setString(4,car.getFuel_capacity());
            preparedStatement.setString(5,car.getTransmission());
            preparedStatement.setString(6,car.getDescription());
            preparedStatement.setDouble(7,car.getPrize_per_day());
            preparedStatement.setString(8,car.getStatus().name());
            preparedStatement.setString(9,car.getRegistration_no());

            int rows=preparedStatement.executeUpdate();
            if (rows>0){
                return car;
            }
        }catch (SQLException e){
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Internal upd Server Error");
        }
        return null;
    }

    public List<CarRequestDTO> getAllCars() {
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.getAllCars)) {

            ResultSet rs = preparedStatement.executeQuery();
            List<CarRequestDTO> cars = new ArrayList<>();

            CarRequestDTO car = null;
            int lastCarId = -1;
            List<String> imageUrls = new ArrayList<>();

            while (rs.next()) {
                int carId = rs.getInt("car_id");

                // Agar new car mili to previous car finalize karen
                if (car == null || carId != lastCarId) {
                    if (car != null) {
                        car.setImageUrls(imageUrls);
                        cars.add(car);
                    }

                    car = new CarRequestDTO();
                    car.setId(carId);
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

                    imageUrls = new ArrayList<>();
                    lastCarId = carId;
                }

                // Image add karen
                String imageUrl = rs.getString("image_url");
                if (imageUrl != null) {
                    imageUrls.add(imageUrl);
                }
            }

            // Last car finalize karen
            if (car != null) {
                car.setImageUrls(imageUrls);
                cars.add(car);
            }

            return cars;

        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Internal getAllCars Server Error: " + e.getMessage());
        }
    }


}