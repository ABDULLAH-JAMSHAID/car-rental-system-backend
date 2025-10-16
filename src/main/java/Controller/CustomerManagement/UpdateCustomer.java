package Controller.CustomerManagement;

import Controller.Auth.BaseServlet;
import DTO.CustomerDTO.CustomerRequestDTO;
import Service.CustomerService;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo=req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            JsonResponse.badRequest(resp, "Missing customer ID in URL");
            return;
        }
        String customerId = pathInfo.substring(1);

        CustomerRequestDTO body= objectMapper.readValue(req.getInputStream(), CustomerRequestDTO.class);

        CustomerRequestDTO updated=customerService.updateCustomer(body,customerId);
        if (updated==null){
            JsonResponse.notFound(resp,"No Customer Found With This ID");
            return;
        }
        JsonResponse.ok(resp,updated);

    }
}
