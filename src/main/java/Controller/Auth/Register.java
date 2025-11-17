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

    private final ObjectMapper mapper = new ObjectMapper();
    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        RegisterRequestDTO body = mapper.readValue(req.getReader(), RegisterRequestDTO.class);

        String fullName = body.getFull_name();
        String email = body.getEmail();
        String password = body.getPassword();
        String phone = body.getPhone();

        // ❌ Empty fields validation
        if (fullName == null || fullName.isEmpty() ||
                email == null || email.isEmpty() ||
                password == null || password.isEmpty() ||
                phone == null || phone.isEmpty()) {

            JsonResponse.badRequest(resp, "Missing Fields");
            return;
        }

        // ❌ Email validation: must end with @gmail.com
        if (!email.toLowerCase().endsWith("@gmail.com")) {
            JsonResponse.badRequest(resp, "Email must be a valid Gmail address ending with @gmail.com");
            return;
        }

        // ❌ Password validation
        // ^ → start of string
        // (?=.*[A-Z]) → at least 1 uppercase
        // (?=.*[0-9]) → at least 1 digit
        // (?=.*[@$!%*?&]) → at least 1 special character
        // .{8,} → minimum 8 characters
        if (!password.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&]).{8,}$")) {
            JsonResponse.badRequest(resp, "Password must be 8+ characters, start with uppercase, contain a number and one special character");
            return;
        }

        // ❌ Phone number validation (11 digits, only numbers)
        if (!phone.matches("^[0-9]{11}$")) {
            JsonResponse.badRequest(resp, "Phone number must be 11 digits and numeric only");
            return;
        }

        // ✔ If all validation passes
        try {
            RegisterResponseDTO responseDTO = authService.saveUser(fullName, email, password, phone);
            JsonResponse.created(resp, responseDTO);

        } catch (MessagingException e) {
            throw new AppException(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Failed to send verification OTP");
        }
    }
}
