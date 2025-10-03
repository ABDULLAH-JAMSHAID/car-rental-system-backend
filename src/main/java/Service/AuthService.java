package Service;

import Handler.AppException;
import Model.User;
import Repository.AuthRepository;
import Utill.MailService;
import Utill.OtpUtil;
import Utill.PasswordUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

public class AuthService {

    private final AuthRepository authRepository=new AuthRepository();
    private final MailService mailService=new MailService();

    public boolean saveUser(String fullName, String email, String password,String phone) throws MessagingException {

        boolean ok=authRepository.find(email,phone);

        if (ok){
            throw new AppException(HttpServletResponse.SC_CONFLICT,"Email Already Exists");
        }

        // If not exists, proceed to save the user

        String hash_password= PasswordUtil.hash(password);

        Integer user_id=authRepository.saveUser(fullName,email,hash_password,phone);

        if (user_id==null){
            return false;
        }

        String otp= OtpUtil.generateOtp();
        LocalDateTime expiry=LocalDateTime.now().plusMinutes(5);

        boolean otpSaved=authRepository.saveOtp(user_id,otp,expiry);

        if (!otpSaved){
            throw new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to save OTP");
        }
        // Here, you would typically send the OTP to the user's email or phone number.
        mailService.sendOtp(email,otp);

        return true;

    }
}
