package Repository;

import Handler.AppException;

import Model.ProfileImage;
import Utill.DBConnection;
import Utill.sql;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.*;

public class ProfileImageRepository {

    private final DataSource ds = DBConnection.getDataSource();

    // 🔹 Save new user image
    public int save(ProfileImage image) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.addUserImage)) {

            ps.setInt(1, image.getUserId());
            ps.setString(2, image.getImageUrl());
            ps.setTimestamp(3, image.getUploadedAt());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Database error while saving profile image: " + e.getMessage());
        }
        return -1;
    }

    // 🔹 Find image by user ID
    public ProfileImage findByUserId(int userId) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.getUserImageByUserId)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ProfileImage img = new ProfileImage();
                img.setId(rs.getInt("id"));
                img.setUserId(rs.getInt("user_id"));
                img.setImageUrl(rs.getString("image_url"));
                img.setUploadedAt(rs.getTimestamp("uploaded_at"));
                return img;
            }

        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Database error while fetching profile image: " + e.getMessage());
        }
        return null;
    }

    // 🔹 Delete by user ID
    public boolean deleteByUserId(int userId) {

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.deleteProfileImageById)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Database error while deleting profile image: " + e.getMessage());
        }
    }
}
