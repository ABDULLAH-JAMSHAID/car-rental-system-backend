package Service;


import DTO.LoginResponseDTO;
import DTO.RegisterResponseDTO;
import Handler.AppException;
import Model.OtpRecord;
import Model.User;
import Repository.AuthRepository;
import Utill.MailService;
import Utill.OtpUtil;
import Utill.PasswordUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthService {

    private final AuthRepository authRepository=new AuthRepository();
    private final MailService mailService=new MailService();

    public RegisterResponseDTO saveUser(String fullName, String email, String password, String phone) throws MessagingException {

        User user=authRepository.find(email,phone);

        if (user!=null){
            throw new AppException(HttpServletResponse.SC_CONFLICT,"Email Already Exists");
        }

        // If not exists, proceed to save the user

        String hash_password= PasswordUtil.hash(password);

        Integer user_id=authRepository.saveUser(fullName,email,hash_password,phone);

        if (user_id==null){
            return null;
        }

        boolean assigned=authRepository.assignRole(user_id,2); // 2 for customer

        String otp= OtpUtil.generateOtp();
        LocalDateTime expiry=LocalDateTime.now().plusMinutes(5);

        boolean otpSaved=authRepository.saveOtp(user_id,otp,expiry);

        if (!otpSaved){
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to save OTP");
        }
        // Here, you would typically send the OTP to the user's email or phone number.
        mailService.sendOtp(email,otp);
        RegisterResponseDTO responseDTO=new RegisterResponseDTO();
        responseDTO.setUserId(user_id);
        responseDTO.setFullName(fullName);
        responseDTO.setEmail(email);
        responseDTO.setMessage("User Registered Successfully. Please verify your email using the OTP sent to your email.");
        return responseDTO;

    }

    public boolean verifyOtp(String email,String otp){

        User user=authRepository.find(email,null);
        if (user==null){
            throw new AppException(HttpServletResponse.SC_NOT_FOUND,"User Not Found");
        }
        OtpRecord record =authRepository.verifyOtp(user.getId());
        if (record!=null && record.getOtp_code().equals(otp) && !record.getExpires_at().before(new Timestamp(System.currentTimeMillis()))){
            boolean verified=authRepository.markUserAsVerified(user.getId(),true);
            boolean otpDeleted=authRepository.deleteOtp(user.getId());
            if (verified && otpDeleted){
                return true;
            }else {
                throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to verify user");
            }
        }
        return false;
    }

    public LoginResponseDTO login(String email, String password) {

        User user = authRepository.find(email, null);

        if (user == null) {
            throw new AppException(HttpServletResponse.SC_NOT_FOUND, "User Not Found");
        }
        if (!user.isIs_verified()) {
            throw new AppException(HttpServletResponse.SC_FORBIDDEN, "Please verify your email first");
        }
        boolean ok = PasswordUtil.verify(password, user.getPassword());
        if (!ok) {
            throw new AppException(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials");
        }
        String Role=authRepository.getRoleByUserId(user.getId());
        if (Role==null){
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"User Role Not Found");
        }
        List<String> permissions = authRepository.getUserPermissions(user.getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user.getId());
        claims.put("full_name", user.getFull_name());
        claims.put("email", user.getEmail());

        String accessToken = Utill.JwtUtil.generateAccessToken(user.getEmail(), claims);
        String refreshToken = Utill.JwtUtil.generateRefreshToken(user.getEmail());

        return new LoginResponseDTO("Login Successful",user.getId(), user.getFull_name(), user.getEmail(), accessToken, 900000, refreshToken, 604800000,Role,permissions);
    }
}
