package Controller.RentalManagement;

import Annotation.RequiresPermission;
import Controller.Auth.BaseServlet;
import DTO.RentalDTO.OverdueRentalDTO;
import Enums.Permissions;
import Service.RentalService;
import Utill.JsonResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/admin/rentals/overdue")
public class AdminOverdueRentalsServlet extends BaseServlet {
    private final RentalService rentalService = new RentalService();

    @Override
    @RequiresPermission(Permissions.OVERDUE_RENTALS_ACCESS)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

            List<OverdueRentalDTO> overdueList = rentalService.getOverdueRentals();
        if (overdueList == null || overdueList.isEmpty()) {
            JsonResponse.notFound(resp, "No Overdue Rentals Found");
            return;
        }

        JsonResponse.ok(resp, overdueList);
    }
}
