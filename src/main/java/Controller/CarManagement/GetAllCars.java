package Controller.CarManagement;

import Controller.Auth.BaseServlet;
import DTO.CarDTO.CarRequestDTO;
import Service.CarService;
import Utill.JsonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "GetAllCars", urlPatterns = "/api/getAllCars")
public class GetAllCars extends BaseServlet {

    private final CarService carService=new CarService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<CarRequestDTO> allCars= carService.getAllCars();
        if (allCars==null || allCars.isEmpty()){
            JsonResponse.notFound(resp,"No Cars Found");
            return;
        }
        JsonResponse.ok(resp,allCars);
    }
}
