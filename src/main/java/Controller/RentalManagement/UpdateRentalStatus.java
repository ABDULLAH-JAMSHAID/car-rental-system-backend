package Controller.RentalManagement;

import Annotation.RequiresPermission;
import Controller.Auth.BaseServlet;
import DTO.RentalDTO.RentalRequestDTO;
import Enums.Permissions;
import Service.RentalService;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;


@WebServlet("/api/admin/approve/*")
public class UpdateRentalStatus extends BaseServlet {

    private final RentalService rentalService = new RentalService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @RequiresPermission(Permissions.UPDATE_RENTAL_STATUS)
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            JsonResponse.badRequest(resp, "Missing rental ID in URL");
            return;
        }

        int rentalId = Integer.parseInt(pathInfo.substring(1));

        RentalRequestDTO body=objectMapper.readValue(req.getInputStream(), RentalRequestDTO.class);

        boolean isUpdated = rentalService.updateRentalStatus(rentalId, body.getStatus());
        if (!isUpdated) {
            JsonResponse.notFound(resp, "No Rental Found With This ID");
            return;
        }
        JsonResponse.ok(resp, "Rental status updated successfully");


    }
}
