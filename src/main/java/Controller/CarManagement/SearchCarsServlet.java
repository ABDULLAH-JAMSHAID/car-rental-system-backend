package Controller.CarManagement;


import DTO.CarDTO.CarDTO;
import DTO.CarDTO.CarSearchDTO;
import Service.CarService;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/api/cars/search")
public class SearchCarsServlet extends HttpServlet {

    private final CarService carService = new CarService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            // 1️⃣ Read DTO from request body (JSON)
            CarSearchDTO requestDTO = mapper.readValue(req.getReader(), CarSearchDTO.class);

            // 2️⃣ Convert dates to Timestamp
            Timestamp pickupDate = Timestamp.valueOf(requestDTO.getPickupDate() + " 00:00:00");
            Timestamp dropoffDate = Timestamp.valueOf(requestDTO.getDropoffDate() + " 00:00:00");

            // 3️⃣ Call service layer
            List<CarDTO> cars = carService.searchCars(
                    pickupDate,
                    dropoffDate,
                    requestDTO.getCarType(),
                    requestDTO.getMinPrice(),
                    requestDTO.getMaxPrice()
            );
            if (cars==null ||cars.isEmpty()) {
                JsonResponse.notFound(resp,"No Cars Found");
            }
            JsonResponse.ok(resp,cars);

        }
    }

