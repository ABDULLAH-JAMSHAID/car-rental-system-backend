package Controller.RentalManagement;

import Annotation.RequiresPermission;
import Controller.Auth.BaseServlet;
import DTO.RentalDTO.CancelRequestDTO;
import DTO.RentalDTO.CancelResponseDTO;
import Enums.Permissions;
import Handler.AppException;
import Service.RentalService;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/api/customer/rentals/cancel")
public class CancelBookingServlet extends BaseServlet {
    private final RentalService service = new RentalService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @RequiresPermission(Permissions.CANCEL_RENTAL)
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

            CancelRequestDTO requestDTO = mapper.readValue(req.getReader(), CancelRequestDTO.class);
            CancelResponseDTO responseDTO = service.cancelBooking(requestDTO);
            JsonResponse.ok(resp, responseDTO);

    }
}
