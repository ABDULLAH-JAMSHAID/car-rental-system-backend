package Controller.ProfileImage;

import Annotation.RequiresPermission;
import Controller.Auth.BaseServlet;
import Enums.Permissions;
import Repository.AuthRepository;
import Service.ProfileImageService;
import Utill.GetUserIdOfCurrentLogin;
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
private final AuthRepository authRepository=new AuthRepository();
    @Override
    @RequiresPermission(Permissions.ADD_PROFILE_IMAGE)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int userId = Integer.parseInt(req.getParameter("userId"));
        Part filePart = req.getPart("image");

        int loginUserId=GetUserIdOfCurrentLogin.getUserIdFromRequest(req);

        String role=authRepository.getRoleByUserId(loginUserId);

        if (role!="ADMIN" && loginUserId!=userId){
            JsonResponse.forbidden(resp, "You don't have permission to upload image for this user");
            return;
        }

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
