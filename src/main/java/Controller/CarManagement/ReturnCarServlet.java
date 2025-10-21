package Controller.CarManagement;

import DTO.RentalDTO.ReturnSummaryDTO;
import Service.RentalService;
import Utill.JsonResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/customer/rentals/return/*")
public class ReturnCarServlet extends HttpServlet {

    private final RentalService rentalService = new RentalService();

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {

            // rentalId URL se nikalo
        String pathInfo = req.getPathInfo(); // e.g., "/5"
        if (pathInfo == null || pathInfo.equals("/")) {
            JsonResponse.badRequest(resp, "Rental ID is required in the URL, e.g. /api/user/profile-image/5");
            return;
        }

        int rentalId = Integer.parseInt(pathInfo.substring(1));

            // Call service layer
         ReturnSummaryDTO summaryDTO= rentalService.returnCar(rentalId);

            JsonResponse.ok(resp, summaryDTO);

    }
}

