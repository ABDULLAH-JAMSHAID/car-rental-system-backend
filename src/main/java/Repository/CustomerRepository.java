package Repository;

import DTO.CustomerDTO.CustomerRequestDTO;
import DTO.CustomerDTO.CustomerResponseDTO;
import Handler.AppException;
import Utill.DBConnection;
import Utill.sql;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepository {

    private final DataSource ds= DBConnection.getDataSource();


    public CustomerRequestDTO findById(String customerId) {

        try(Connection connection=ds.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql.findById)){
            preparedStatement.setString(1,customerId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                CustomerRequestDTO customer=new CustomerRequestDTO();
                customer.setId(resultSet.getInt("id"));
                customer.setFull_name(resultSet.getString("full_name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setAddress(resultSet.getString("address"));
                customer.setGender(resultSet.getBoolean("gender"));
                customer.setLicense_no(resultSet.getString("license_no"));
                customer.setActive(resultSet.getBoolean("isActive"));
                customer.setVerified(resultSet.getBoolean("isVerified"));
                customer.setCreated_at(resultSet.getTimestamp("created_at").toLocalDateTime());
                return customer;

            }
            return null;


        }catch (SQLException e){
        throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Database error: "+e.getMessage());
        }
    }
    public CustomerResponseDTO findCustomerProfileById(int customerId) {


        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql.getProfileById)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                CustomerResponseDTO dto = new CustomerResponseDTO();
                dto.setId(rs.getInt("id"));
                dto.setFullName(rs.getString("full_name"));
                dto.setEmail(rs.getString("email"));
                dto.setPhone(rs.getString("phone"));
                dto.setAddress(rs.getString("address"));
                dto.setGender(rs.getBoolean("gender"));
                dto.setLicenseNo(rs.getString("license_number"));
                dto.setActive(rs.getBoolean("is_active"));
                dto.setVerified(rs.getBoolean("is_verified"));
                dto.setProfileImageUrl(rs.getString("image_url"));
                return dto;
            }
            return null;

        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

}
