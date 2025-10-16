package Controller.CustomerManagement;

import Controller.Auth.BaseServlet;
import DTO.CustomerDTO.CustomerResponseDTO;
import Service.CustomerService;
import Utill.JsonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetCustomerProfileById", urlPatterns = "/api/customers/*")
public class GetCustomerProfile extends BaseServlet {

    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            JsonResponse.badRequest(resp, "Missing customer ID in URL");
            return;
        }

        int customerId = Integer.parseInt(pathInfo.substring(1));

        CustomerResponseDTO customer = customerService.getCustomerProfileById(customerId);

        if (customer == null) {
            JsonResponse.notFound(resp, "Customer not found with this ID");
            return;
        }

        JsonResponse.ok(resp, customer);
    }
}
