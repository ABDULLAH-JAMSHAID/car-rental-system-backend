package Repository;

import Handler.AppException;
import Model.User;
import Utill.sql;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AuthRepository {

    private final DataSource ds= com.ums.app.util.DBConnection.getDataSource();

    public boolean find(String email,String phone){

        try(Connection connection=ds.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql.find)){
            preparedStatement.setString(1,email);
            preparedStatement.setString(2, phone);
            ResultSet rs=preparedStatement.executeQuery();

            if (rs.next()){
                return true;
            }
        }catch (SQLException e){
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Database error: "+e.getMessage());
        }
        return false;
    }

    public Integer saveUser(String fullName, String email, String password, String phone){

        try(Connection connection=ds.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(sql.saveUser)){

            preparedStatement.setString(1,fullName);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);
            preparedStatement.setString(4,phone);

            ResultSet rs=preparedStatement.executeQuery();

            if (rs.next()){
                return rs.getInt("id");
            }

        } catch (SQLException e) {
           throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Database error: "+e.getMessage());
        }
        return null;
    }

    public boolean saveOtp(Integer id, String otp, LocalDateTime expiry){

        try(Connection connection=ds.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(sql.saveOtp)){

            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,otp);
            preparedStatement.setObject(3,expiry);
            int rows=preparedStatement.executeUpdate();
            if (rows>0){
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Database error: "+e.getMessage());
        }
    }

}
