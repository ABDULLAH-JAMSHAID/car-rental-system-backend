package Controller.RentalManagement;

import Annotation.RequiresPermission;
import Controller.Auth.BaseServlet;
import DTO.RentalDTO.RentalResponseDTO;
import Enums.Permissions;
import Handler.AppException;
import Service.RentalService;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/customer/rentals")
public class RentalHistory extends BaseServlet {

    private final RentalService rentalService=new RentalService();
    private final ObjectMapper mapper=new ObjectMapper();

    @Override
    @RequiresPermission(Permissions.VIEW_RENTAL_HISTORY)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Claims claims = (Claims) req.getAttribute("claims");
        if (claims == null) {
            throw new AppException(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }

        // ✅ Safe parsing
        int id = Integer.parseInt(claims.get("user_id").toString());
        System.out.println("Fetching rental history for userId = " + id);

        List<RentalResponseDTO> rentals = rentalService.getUserRentals(id);

        if (rentals == null || rentals.isEmpty()) {
            JsonResponse.error(resp, HttpServletResponse.SC_NOT_FOUND, "No History Found");
            return;
        }

        JsonResponse.ok(resp, rentals);
    }
}
