package Controller.Auth;

import DTO.AuthDTO.ResendOtpDTO;
import Service.AuthService;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ResendOtp", urlPatterns = "/api/resend-otp")
public class ResendOtp extends BaseServlet{

    private final ObjectMapper mapper  =new ObjectMapper();
    private final AuthService authService=new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        ResendOtpDTO resendOtp=mapper.readValue(req.getReader(), ResendOtpDTO.class);

        String email=resendOtp.getEmail();

        if (email==null || email.isEmpty()){
            JsonResponse.badRequest(resp,"Missing Fields");
            return;
        }
        try {
            boolean resend=authService.resendOtp(email);
            if (resend){
                JsonResponse.ok(resp,"OTP Resent Successfully");
            } else {
                JsonResponse.badRequest(resp,"Failed to Resend OTP, Please Try Again Later");
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
