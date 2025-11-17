package Controller.CustomerManagement;

import Annotation.RequiresPermission;
import Controller.Auth.BaseServlet;
import DTO.CustomerDTO.CustomerResponseDTO;
import Enums.Permissions;
import Repository.AuthRepository;
import Service.CustomerService;
import Utill.GetUserIdOfCurrentLogin;
import Utill.JsonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.AuthenticationException;
import java.io.IOException;

@WebServlet(name = "GetCustomerProfileById", urlPatterns = "/api/customers/*")
public class GetCustomerProfile extends BaseServlet {

    private final CustomerService customerService = new CustomerService();
    private final AuthRepository authRepository=new AuthRepository();

    @Override
    @RequiresPermission(Permissions.GET_CUSTOMER_PROFILE)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            JsonResponse.badRequest(resp, "Missing customer ID in URL");
            return;
        }

        int customerId = Integer.parseInt(pathInfo.substring(1));
        int loginUserId= GetUserIdOfCurrentLogin.getUserIdFromRequest(req);

        String role=authRepository.getRoleByUserId(loginUserId);

        if (role!="ADMIN" && loginUserId!=customerId){
            JsonResponse.forbidden(resp, "You don't have permission to do this task");
            return;
        }

        CustomerResponseDTO customer = customerService.getCustomerProfileById(customerId);

        if (customer == null) {
            JsonResponse.notFound(resp, "Customer not found with this ID");
            return;
        }

        JsonResponse.ok(resp, customer);
    }
}
