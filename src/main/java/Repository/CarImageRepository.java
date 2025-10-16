package Repository;

import Handler.AppException;
import Model.CarImage;
import Utill.DBConnection;
import Utill.sql;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarImageRepository {

    private final DataSource ds = DBConnection.getDataSource();

    // 🔹 Existing method — save image
    public int save(CarImage image) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql.addCarImages)) {

            ps.setInt(1, image.getCarId());
            ps.setString(2, image.getImageUrl());
            ps.setBoolean(3, image.isMain());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
        return -1;
    }

    // 🔹 Existing method — get images for car
    public List<CarImage> findByCarId(int carId) {
        List<CarImage> list = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql.getCarImages)) {

            ps.setInt(1, carId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CarImage image = new CarImage();
                image.setId(rs.getInt("id"));
                image.setCarId(rs.getInt("car_id"));
                image.setImageUrl(rs.getString("image_url"));
                image.setMain(rs.getBoolean("is_main"));
                image.setUploadedAt(rs.getTimestamp("uploaded_at"));
                list.add(image);
            }

        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
        return list;
    }

    // 🆕 New method — set selected image as main
    public void updateMainImage(int carId, int imageId) {
        try (Connection connection = ds.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement unset = connection.prepareStatement(
                    sql.unset)) {
                unset.setInt(1, carId);
                unset.executeUpdate();
            }

            try (PreparedStatement setMain = connection.prepareStatement(
                    sql.setMain)) {
                setMain.setInt(1, imageId);
                setMain.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Database error while updating main image: " + e.getMessage());
        }
    }

    // 🔹 Find image by ID (for file path)
    public CarImage findById(int imageId) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql.findCarImageById)) {

            ps.setInt(1, imageId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                CarImage image = new CarImage();
                image.setId(rs.getInt("id"));
                image.setCarId(rs.getInt("car_id"));
                image.setImageUrl(rs.getString("image_url"));
                image.setMain(rs.getBoolean("is_main"));
                image.setUploadedAt(rs.getTimestamp("uploaded_at"));
                return image;
            }
            return null;

        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    // 🔹 Delete image by ID
    public boolean deleteById(int imageId) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql.deleteCarImageById)) {

            ps.setInt(1, imageId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

}
