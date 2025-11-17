package Controller.RentalManagement;


import Annotation.RequiresPermission;
import Controller.Auth.BaseServlet;
import DTO.RentalDTO.AdminDashboardDTO;
import Enums.Permissions;
import Service.RentalService;
import Utill.JsonResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/admin/dashboard")
public class AdminDashboardServlet extends BaseServlet {
    private final RentalService rentalService = new RentalService();

    @Override
    @RequiresPermission(Permissions.ADMIN_DASHBOARD_ACCESS)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AdminDashboardDTO dashboard = rentalService.getDashboardStats();

        if (dashboard == null) {
            JsonResponse.notFound(resp, "Dashboard data not available");
            return;
        }

        JsonResponse.ok(resp, dashboard);
    }
}
