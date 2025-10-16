package Controller.Auth;


import DTO.AuthDTO.RegisterRequestDTO;
import DTO.AuthDTO.RegisterResponseDTO;
import Handler.AppException;
import Service.AuthService;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(name = "Register", urlPatterns = "/api/register")
public class Register extends BaseServlet {

    private final ObjectMapper mapper=new ObjectMapper();
    private final AuthService authService=new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        RegisterRequestDTO body=mapper.readValue(req.getReader(), RegisterRequestDTO.class);

        if (body.getFull_name()==null || body.getFull_name().isEmpty() ||body.getEmail()==null || body.getEmail().isEmpty() || body.getPassword()==null || body.getPassword().isEmpty()||
        body.getPhone()==null || body.getPhone().isEmpty()){
            JsonResponse.badRequest(resp,"Missing Fields");
            return;
        }


        try {
            RegisterResponseDTO responseDTO=authService.saveUser(body.getFull_name(), body.getEmail(), body.getPassword(), body.getPhone());

            JsonResponse.created(resp,responseDTO);
        } catch (MessagingException e) {
            throw new AppException(HttpServletResponse.SC_SERVICE_UNAVAILABLE,"Failed to send verification OTP");
        }


    }
}
