package Controller.CarManagement;


import Annotation.RequiresPermission;
import DTO.CarDTO.ReviewResponseDTO;
import Enums.Permissions;
import Service.CarService;
import Utill.GetUserIdOfCurrentLogin;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Controller.Auth.BaseServlet;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/customer/cars/reviews/*")
public class GetReviewOfCar extends BaseServlet {

    private final CarService carService=new CarService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @RequiresPermission(Permissions.GET_CAR_REVIEWS)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int carId = GetUserIdOfCurrentLogin.extractIdFromUrl(req);
            // 2️⃣ Fetch reviews from service
            List<ReviewResponseDTO> reviews = carService.getReviewsByCarId(carId);

            // 3️⃣ Handle empty results
            if (reviews == null || reviews.isEmpty()) {
                JsonResponse.notFound(resp,"No Reviews Found");
                return;
            }
            JsonResponse.ok(resp,reviews);

    }
}
