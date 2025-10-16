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
@WebServlet(name = "DeleteCar", urlPatterns = "/api/deleteCar/*")
public class DeleteCar extends BaseServlet {

    private final CarService carService=new CarService();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo=req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            JsonResponse.badRequest(resp, "Missing registration number in URL");
            return;
        }
        String registrationNo = pathInfo.substring(1);
        CarRequestDTO carRequestDTO =carService.deleteCarByRegistrationNo(registrationNo);
        if (carRequestDTO==null) {
            JsonResponse.notFound(resp, "No Car Found With This Registration Number");
            return;
        }
        JsonResponse.ok(resp,"Car Deleted Successfully");

    }
}
