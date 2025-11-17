package Controller.CarManagement;

import Annotation.RequiresPermission;
import Controller.Auth.BaseServlet;
import DTO.CarDTO.FavoriteDTO;
import Enums.Permissions;
import Repository.AuthRepository;
import Service.CarService;
import Utill.GetUserIdOfCurrentLogin;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/favorites/remove")
public class DeleteFavoriteCar extends BaseServlet {

    private final CarService carService = new CarService();
    private final ObjectMapper mapper = new ObjectMapper();
    private final AuthRepository authRepository=new AuthRepository();

    @Override
    @RequiresPermission(Permissions.DELETE_FAVORITE_CAR)
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Read JSON body
        FavoriteDTO dto = mapper.readValue(req.getReader(), FavoriteDTO.class);
        int userId= dto.getUserId();
        int loginUserId= GetUserIdOfCurrentLogin.getUserIdFromRequest(req);

        String role=authRepository.getRoleByUserId(loginUserId);

        if (role!="ADMIN" && loginUserId!=userId){
            JsonResponse.forbidden(resp, "You don't have permission to do this task");
            return;
        }

        boolean ok = carService.removeFavorite(dto.getUserId(), dto.getCarId());

        if (ok) {
            JsonResponse.ok(resp, "Car removed from favorites successfully");
        } else {
            JsonResponse.error(resp, HttpServletResponse.SC_BAD_REQUEST, "Failed to remove car from favorites");
        }
    }
}
