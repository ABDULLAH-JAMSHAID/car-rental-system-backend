package Controller.Auth;

import Handler.AppException;
import Model.User;
import Service.AuthService;
import Utill.JsonResponse;
import com.google.gson.Gson;
import jakarta.mail.MessagingException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
@WebServlet(name = "Register", urlPatterns = "api/register")
public class Register extends BaseServlet {

    private  final Gson gson=new Gson();
    private final AuthService authService=new AuthService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try(BufferedReader reader=req.getReader()){

            User user=gson.fromJson(reader, User.class);

            String fullName = user.getFull_name();
            String email = user.getEmail();
            String password = user.getPassword();
            String phone = user.getPhone();

            if (fullName ==null || email == null || password== null || phone==null){
                JsonResponse.badRequest(resp,"Missing Fields");
                return;
            }
            boolean ok=authService.saveUser(fullName,email,password,phone);
            if (ok){
                JsonResponse.ok(resp,"User Registered Successfully. Please Verify Your Email" );
            }
        } catch (MessagingException e) {
            throw  new AppException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to send verification email");
        }

    }
}
