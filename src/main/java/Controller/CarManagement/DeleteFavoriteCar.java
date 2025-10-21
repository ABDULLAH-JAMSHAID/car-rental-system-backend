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

@WebServlet("/api/favorites/remove")
public class DeleteFavoriteCar extends HttpServlet {

    private final CarService carService = new CarService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Read JSON body
        FavoriteDTO dto = mapper.readValue(req.getReader(), FavoriteDTO.class);

        boolean ok = carService.removeFavorite(dto.getUserId(), dto.getCarId());

        if (ok) {
            JsonResponse.ok(resp, "Car removed from favorites successfully");
        } else {
            JsonResponse.error(resp, HttpServletResponse.SC_BAD_REQUEST, "Failed to remove car from favorites");
        }
    }
}
