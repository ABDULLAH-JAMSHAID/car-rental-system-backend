package Controller.ProfileImage;

import Controller.Auth.BaseServlet;
import Service.ProfileImageService;
import Utill.JsonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/user/profile-image/*")
public class DeleteProfileImage extends BaseServlet {

    private final ProfileImageService userImageService = new ProfileImageService();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Extract userId from URL path
        String pathInfo = req.getPathInfo(); // e.g., "/5"
        if (pathInfo == null || pathInfo.equals("/")) {
            JsonResponse.badRequest(resp, "User ID is required in the URL, e.g. /api/user/profile-image/5");
            return;
        }

        int userId = Integer.parseInt(pathInfo.substring(1)); // remove "/"

        boolean deleted = userImageService.deleteProfileImage(userId);

        if (deleted) {
            JsonResponse.ok(resp, Map.of(
                    "success", true,
                    "message", "Profile image deleted successfully"
            ));
        } else {
            JsonResponse.notFound(resp, "Profile image not found or already deleted");
        }
    }
}
