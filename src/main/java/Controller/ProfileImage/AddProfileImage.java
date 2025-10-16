package Controller.ProfileImage;

import Controller.Auth.BaseServlet;
import Service.ProfileImageService;
import Utill.JsonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Map;

@WebServlet("/api/user/profile-image/upload")
@MultipartConfig
public class AddProfileImage extends BaseServlet {

private final ProfileImageService profileImageService=new ProfileImageService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int userId = Integer.parseInt(req.getParameter("userId"));
        Part filePart = req.getPart("image");

        String fileName = filePart.getSubmittedFileName();

        // upload new image (auto deletes old one)
        int imageId = profileImageService.uploadProfileImage(userId, filePart.getInputStream(), fileName);

        JsonResponse.created(resp, Map.of(
                "success", true,
                "message", "Profile image uploaded successfully",
                "imageId", imageId
        ));
    }
}
