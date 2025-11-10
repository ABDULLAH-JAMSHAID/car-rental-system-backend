package Controller.CarManagement;

import Annotation.RequiresPermission;
import Controller.Auth.BaseServlet;
import DTO.CarDTO.CarDTO;
import Enums.Permissions;
import Service.CarService;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/favorites/get/*")
public class GetFavoriteCars extends BaseServlet {

    private final CarService carService = new CarService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @RequiresPermission(Permissions.GET_FAVORITE_CARS)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


            // ✅ Read userId from query parameter
        String pathInfo = req.getPathInfo(); // e.g., "/5"
        if (pathInfo == null || pathInfo.equals("/")) {
            JsonResponse.badRequest(resp, "User ID is required in the URL, e.g. /api/user/profile-image/5");
            return;
        }

        int userId = Integer.parseInt(pathInfo.substring(1));

        // ✅ Fetch favorite cars of user
            List<CarDTO> favorites = carService.getFavoriteCarsByUser(userId);

            if (favorites==null){
                JsonResponse.notFound(resp, "No favorite cars found for the user");
                return;
            }
            JsonResponse.ok(resp, favorites);


    }
}
