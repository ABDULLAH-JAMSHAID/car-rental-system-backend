package Controller.CarImage;

import Controller.Auth.BaseServlet;
import Service.CarImageService;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/car-images/update-main")
public class SetMainImage extends BaseServlet {
    private final CarImageService carImageService = new CarImageService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> body = mapper.readValue(req.getInputStream(), Map.class);
        int carId = (int) body.get("carId");
        int imageId = (int) body.get("imageId");

        carImageService.setMainImage(carId, imageId);

        JsonResponse.ok(resp, Map.of(
                "success", true,
                "message", "Main image updated successfully"
        ));
    }
}
