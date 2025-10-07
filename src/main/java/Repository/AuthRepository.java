package Repository;

import Enums.Permissions;
import Handler.AppException;
import Model.OtpRecord;
import Model.User;
import Utill.DBConnection;
import Utill.sql;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class AuthRepository {

    private final DataSource ds= DBConnection.getDataSource();

    public User find(String email,String phone){

        try(Connection connection=ds.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql.find)){
            preparedStatement.setString(1,email);
            preparedStatement.setString(2, phone);
            ResultSet rs=preparedStatement.executeQuery();

            User user;

            if (rs.next()){
                user=new User();
                user.setId(rs.getInt("id"));
                user.setFull_name(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setIs_verified(rs.getBoolean("is_verified"));
                return user;

            }
        }catch (SQLException e){
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Database error: "+e.getMessage());
        }
        return null;
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

    public OtpRecord verifyOtp(Integer user_id){

        try(Connection connection=ds.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(sql.otpRecord)){
            preparedStatement.setInt(1,user_id);
            ResultSet rs=preparedStatement.executeQuery();
            OtpRecord otpRecord;

            while (rs.next()){
                otpRecord=new OtpRecord();
                otpRecord.setId(rs.getInt("id"));
                otpRecord.setUser_id(rs.getInt("user_id"));
                otpRecord.setOtp_code(rs.getString("otp_code"));
                otpRecord.setExpires_at(rs.getTimestamp("expires_at"));
                otpRecord.setIs_used(rs.getBoolean("is_used"));
                otpRecord.setCreated_at(rs.getTimestamp("created_at"));
                return otpRecord;
            }

    }catch (SQLException e){
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Database error: "+e.getMessage());
        }
        return null;
    }

    public boolean assignRole(int userId,int roleId) {

        try(Connection connection =ds.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql.assignRole)){

            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,roleId);

            int rows=preparedStatement.executeUpdate();
            if (rows>0){
                return true;
            }

        }
        catch (SQLException e){
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Database error: "+e.getMessage());
        }
        return false;
    }

    public boolean markUserAsVerified(Integer userId,Boolean trueOrFalse){

        try(Connection connection=ds.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(sql.markUserAsVerified)){

            preparedStatement.setBoolean(1,trueOrFalse);
            preparedStatement.setInt(2,userId);
           int rows= preparedStatement.executeUpdate();
           if (rows>0){
               return true;
           }else return false;

        }catch (SQLException e){
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Database error: "+e.getMessage());
        }
    }

    public boolean deleteOtp(Integer user_id){

        try(Connection connection=ds.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(sql.deleteOtp)){
            preparedStatement.setInt(1,user_id);
            int rows=preparedStatement.executeUpdate();
            if (rows>0){
                return true;
            }else return false;

        }catch (SQLException e){
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Database error: "+e.getMessage());
        }

    }

    public String getRoleByUserId(Integer user_id){
        try(Connection connection=ds.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(sql.getRoleByUserId)){

            preparedStatement.setInt(1,user_id);
            ResultSet rs=preparedStatement.executeQuery();

            if (rs.next()){
                return rs.getString("role_name");
            }
            return null;

        }catch (SQLException e){
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Database error: "+e.getMessage());
        }
    }

    public List<String> getUserPermissions(Integer user_id){
        try(Connection connection=ds.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(sql.getUserPermissions)){

            preparedStatement.setInt(1,user_id);
            ResultSet rs=preparedStatement.executeQuery();
            List<String> permissions=new java.util.ArrayList<>();

            while (rs.next()){
                permissions.add(rs.getString("permission_name"));
            }
            return permissions;

        }catch (SQLException e){
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Database error: "+e.getMessage());
        }
    }

    public boolean userHasPermission(int userId, Permissions requiredPermission) {
        Set<String> userPermissions = new HashSet<>();

        try ( Connection connection=ds.getConnection();
              PreparedStatement stmt = connection.prepareStatement(sql.getUserPermissions)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userPermissions.add(rs.getString("permission_name").toUpperCase(Locale.ROOT));
            }
        } catch (SQLException e) {
            // Handle exception
            e.printStackTrace();
            return false;
        }

        return userPermissions.contains(requiredPermission.name().toUpperCase());
    }
}
