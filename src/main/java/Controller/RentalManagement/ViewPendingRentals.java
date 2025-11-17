package Controller.RentalManagement.Admin;

import Annotation.RequiresPermission;
import Controller.Auth.BaseServlet;
import DTO.RentalDTO.GetRentalsByStatus;
import DTO.RentalDTO.PendingRentalDTO;
import DTO.RentalDTO.RentalRequestDTO;
import Enums.CarStatus;
import Enums.Permissions;
import Handler.AppException;
import Service.RentalService;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/rentals/pending")
public class ViewPendingRentals extends BaseServlet {

    private final RentalService rentalService = new RentalService();
    private final ObjectMapper mapper=new ObjectMapper();

    @Override
    @RequiresPermission(Permissions.VIEW_PENDING_RENTALS)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        GetRentalsByStatus status=mapper.readValue(req.getInputStream(), GetRentalsByStatus.class);
        CarStatus carStatus= CarStatus.valueOf(status.getStatus());

            List<PendingRentalDTO> rentals = rentalService.getPendingRentals(carStatus);
            if (rentals==null){
                JsonResponse.notFound(resp,"No Pending Rentals Found");
            }
            JsonResponse.ok(resp,rentals);

    }
}
