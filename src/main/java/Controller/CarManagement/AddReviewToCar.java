package Controller.CarManagement;


import Annotation.RequiresPermission;
import DTO.CarDTO.ReviewRequestDTO;
import Enums.Permissions;
import Handler.AppException;
import Service.CarService;
import Utill.JsonResponse;
import Utill.GetUserIdOfCurrentLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Controller.Auth.BaseServlet;

import java.io.IOException;

@WebServlet("/api/customer/cars/reviews")
public class AddReviewToCar extends BaseServlet {

    private final CarService carService = new CarService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @RequiresPermission(Permissions.ADD_REVIEW_TO_CAR)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // 1️⃣ Parse JSON body → carId, rating, comment
            ReviewRequestDTO review = mapper.readValue(req.getReader(), ReviewRequestDTO.class);

            // 2️⃣ Token se userId nikalna (utility class se)
            int userId = GetUserIdOfCurrentLogin.getUserIdFromRequest(req);



            // 4️⃣ Call service
          boolean ok=  carService.addReview(userId, review);

            // 5️⃣ Send response
            if (ok){
                JsonResponse.ok(resp, "Review added successfully");
            }
            else JsonResponse.notFound(resp,"Failed To Add review");

        } catch (AppException e) {
            JsonResponse.error(resp, e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            JsonResponse.serverError(resp, "Unexpected error: " + e.getMessage());
        }
    }
}
