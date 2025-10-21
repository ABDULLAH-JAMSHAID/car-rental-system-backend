package Controller.CarManagement;

import DTO.CarDTO.FavoriteDTO;
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
public class AddFavoriteCar extends HttpServlet {

    private final CarService carService=new CarService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
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
