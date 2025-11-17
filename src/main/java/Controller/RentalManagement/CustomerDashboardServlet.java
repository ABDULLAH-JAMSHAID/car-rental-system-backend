package Controller.RentalManagement;

import Annotation.RequiresPermission;
import Controller.Auth.BaseServlet;
import DTO.RentalDTO.CustomerDashboardDTO;
import Enums.Permissions;
import Handler.AppException;
import Service.RentalService;
import Utill.JsonResponse;
import io.jsonwebtoken.Claims;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/customer/dashboard")
public class CustomerDashboardServlet extends BaseServlet {
    private final RentalService rentalService = new RentalService();

    @Override
    @RequiresPermission(Permissions.CUSTOMER_DASHBOARD_ACCESS)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Claims claims = (Claims) req.getAttribute("claims");
        if (claims == null) {
            throw new AppException(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }

        // ✅ Safe parsing
        int id = Integer.parseInt(claims.get("user_id").toString());

        CustomerDashboardDTO dashboard = rentalService.getCustomerDashboard(id);

        if (dashboard == null) {
            JsonResponse.notFound(resp, "Dashboard data not available");
            return;
        }

        JsonResponse.ok(resp, dashboard);
    }
}
