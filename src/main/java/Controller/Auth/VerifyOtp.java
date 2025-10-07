package Controller.Auth;

import DTO.VerifyOtpDTO;
import Service.AuthService;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet(name = "VerifyOtp", urlPatterns = "/api/verify-otp")
public class VerifyOtp extends BaseServlet {

    private final ObjectMapper mapper=new ObjectMapper();
    private final AuthService authService=new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        VerifyOtpDTO body=mapper.readValue(req.getReader(), VerifyOtpDTO.class);

        String email=body.getEmail();
        String otp=body.getOtp();

        if (email==null || email.isEmpty() || otp==null || otp.isEmpty()){
            JsonResponse.badRequest(resp,"Missing Fields");
            return;
        }
        boolean verified=authService.verifyOtp(email,otp);
        if (verified){
            JsonResponse.ok(resp,"Email Verified Successfully, You Can Now Login");
        }

    }
}
