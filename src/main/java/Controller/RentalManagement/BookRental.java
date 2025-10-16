package Controller.RentalManagement;

import Model.Rental;
import Service.RentalService;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


@WebServlet("/api/rentals/book")
public class BookRental extends HttpServlet {

    private final RentalService rentalService = new RentalService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Claims claims = (Claims) req.getAttribute("claims");
        if (claims == null) {
            JsonResponse.unauthorized(resp, "Unauthorized request or missing claims");
            return;
        }

        // 2️⃣ User ID extract karo
        int userId = Integer.parseInt(claims.get("user_id").toString());

            // Parse JSON body into Rental object
            Rental rental = objectMapper.readValue(req.getInputStream(), Rental.class);
            rental.setUserId(userId);


            // Process booking
        try {
            rentalService.bookRental(rental);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Send success response
            JsonResponse.ok(resp, "Car booked successfully!");


    }
}
