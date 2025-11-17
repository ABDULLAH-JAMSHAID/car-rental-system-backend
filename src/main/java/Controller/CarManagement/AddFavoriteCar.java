package Controller.CarManagement;

import Annotation.RequiresPermission;
import Controller.Auth.BaseServlet;
import DTO.CarDTO.FavoriteDTO;
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

@WebServlet("/api/favorites/add")
public class AddFavoriteCar extends BaseServlet {

    private final CarService carService=new CarService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @RequiresPermission(Permissions.ADD_CAR_TO_FAVORITES)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


            // Read JSON body
            FavoriteDTO dto = mapper.readValue(req.getReader(), FavoriteDTO.class);

            // Call service
            boolean ok=carService.addFavorite(dto.getUserId(), dto.getCarId());

            if (ok){
                JsonResponse.ok(resp, "Car added to favorites successfully");
                return;
            }else {
                JsonResponse.error(resp, HttpServletResponse.SC_BAD_REQUEST, "Failed to add car to favorites");
            }

    }
}
