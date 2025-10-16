package Controller.Auth;

import DTO.AuthDTO.LoginRequestDTO;
import DTO.AuthDTO.LoginResponseDTO;
import Service.AuthService;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet(name = "Login", urlPatterns = "/api/login")
public class Login extends BaseServlet{

    private final ObjectMapper mapper=new ObjectMapper();
    private final AuthService authService=new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LoginRequestDTO body=mapper.readValue(req.getReader(), LoginRequestDTO.class);

        String email=body.getEmail();
        String password=body.getPassword();

        if (email==null || email.isEmpty() || password==null || password.isEmpty()){
            JsonResponse.badRequest(resp,"Missing Fields");
            return;
        }
        LoginResponseDTO responseDTO =authService.login(email,password);

        Cookie refreshTokenCookie = new Cookie("refreshToken", responseDTO.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false); // true only in production HTTPS
        refreshTokenCookie.setPath("/"); // available for all API routes
        refreshTokenCookie.setMaxAge((int) (responseDTO.getRefreshTokenExpiresIn() / 1000));
        resp.addCookie(refreshTokenCookie);

        if (responseDTO==null){
            JsonResponse.unauthorized(resp,"Invalid Credentials or Email Not Verified");
            return;
        }
        JsonResponse.ok(resp,responseDTO);
    }
}
