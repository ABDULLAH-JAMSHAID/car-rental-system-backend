package Controller.CarManagement;

import Annotation.RequiresPermission;
import Controller.Auth.BaseServlet;
import DTO.RentalDTO.ReturnSummaryDTO;
import Enums.Permissions;
import Repository.AuthRepository;
import Service.RentalService;
import Utill.GetUserIdOfCurrentLogin;
import Utill.JsonResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/customer/rentals/return/*")
public class ReturnCarServlet extends BaseServlet {

    private final RentalService rentalService = new RentalService();
    private final AuthRepository authRepository=new AuthRepository();

    @Override
    @RequiresPermission(Permissions.RETURN_CAR)
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {

            // rentalId URL se nikalo
        String pathInfo = req.getPathInfo(); // e.g., "/5"
        if (pathInfo == null || pathInfo.equals("/")) {
            JsonResponse.badRequest(resp, "Rental ID is required in the URL, e.g. /api/user/profile-image/5");
            return;
        }

        int rentalId = Integer.parseInt(pathInfo.substring(1));
        int loginUserId= GetUserIdOfCurrentLogin.getUserIdFromRequest(req);

        String role=authRepository.getRoleByUserId(loginUserId);

        if (role!="ADMIN" && loginUserId!=rentalId){
            JsonResponse.forbidden(resp, "You don't have permission to do this task");
            return;
        }

            // Call service layer
         ReturnSummaryDTO summaryDTO= rentalService.returnCar(rentalId);

            JsonResponse.ok(resp, summaryDTO);

    }
}

