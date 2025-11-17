package Controller.CarManagement;

import Annotation.RequiresPermission;
import Controller.Auth.BaseServlet;
import DTO.CarDTO.CarRequestDTO;
import Enums.Permissions;
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
    @RequiresPermission(Permissions.GET_ALL_CARS)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<CarRequestDTO> allCars= carService.getAllCars();
        if (allCars==null || allCars.isEmpty()){
            JsonResponse.notFound(resp,"No Cars Found");
            return;
        }
        JsonResponse.ok(resp,allCars);
    }
}
