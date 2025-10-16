package Controller.CarManagement;

import Controller.Auth.BaseServlet;
import DTO.CarDTO.CarRequestDTO;
import Service.CarService;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddCar", urlPatterns = "/api/addCar")
public class AddCar extends BaseServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CarService carService=new CarService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CarRequestDTO body= objectMapper.readValue(req.getInputStream(), CarRequestDTO.class);

        if (body.getName()==null || body.getName().isEmpty()
        || body.getType()==null || body.getType().isEmpty()
        || body.getCapacity()==null|| body.getFuel_capacity()==null || body.getFuel_capacity().isEmpty()
        || body.getTransmission()==null || body.getTransmission().isEmpty()
        || body.getDescription()==null || body.getDescription().isEmpty()
        || body.getPrize_per_day()==null || body.getStatus()==null ){
            JsonResponse.badRequest(resp,"Missing Required Fields");
            return;
        }

            CarRequestDTO created=carService.addCar(body);

            if (created==null){
                JsonResponse.serverError(resp,"Failed to Add Car");
                return;
            }
            JsonResponse.created(resp,created);



    }
}
