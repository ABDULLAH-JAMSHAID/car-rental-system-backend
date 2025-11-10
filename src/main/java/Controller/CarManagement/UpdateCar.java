package Controller.CarManagement;

import Annotation.RequiresPermission;
import Controller.Auth.BaseServlet;
import DTO.CarDTO.CarRequestDTO;
import Enums.Permissions;
import Service.CarService;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateCar", urlPatterns = "/api/updateCar/*")
public class UpdateCar extends BaseServlet {

    private final ObjectMapper objectMapper=new ObjectMapper();
    private final CarService carService=new CarService();

    @Override
    @RequiresPermission(Permissions.UPDATE_CAR_DETAILS)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CarRequestDTO body= objectMapper.readValue(req.getInputStream(), CarRequestDTO.class);

        String pathInfo=req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            JsonResponse.badRequest(resp, "Missing registration number in URL");
            return;
        }
        String registrationNo = pathInfo.substring(1);

        CarRequestDTO updated=carService.updateCar(body,registrationNo);
        if (updated==null){
            JsonResponse.notFound(resp,"No Car Found With This Registration Number");
            return;
        }
        JsonResponse.ok(resp,updated);
    }
}
