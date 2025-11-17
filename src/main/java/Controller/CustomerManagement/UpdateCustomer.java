package Controller.CustomerManagement;

import Annotation.RequiresPermission;
import Controller.Auth.BaseServlet;
import DTO.CustomerDTO.CustomerRequestDTO;
import Enums.Permissions;
import Repository.AuthRepository;
import Service.CustomerService;
import Utill.GetUserIdOfCurrentLogin;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateCustomer", urlPatterns = "/api/updateCustomer/*")
public class UpdateCustomer extends BaseServlet {

    private final CustomerService customerService=new CustomerService();
    private final ObjectMapper objectMapper=new ObjectMapper();
    private final AuthRepository authRepository=new AuthRepository();

    @Override
    @RequiresPermission(Permissions.UPDATE_CUSTOMER_PROFILE)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo=req.getPathInfo();
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

        CustomerRequestDTO body= objectMapper.readValue(req.getInputStream(), CustomerRequestDTO.class);

        CustomerRequestDTO updated=customerService.updateCustomer(body,customerId);
        if (updated==null){
            JsonResponse.notFound(resp,"No Customer Found With This ID");
            return;
        }
        JsonResponse.ok(resp,updated);

    }
}
