package Controller.CarImage;

import Controller.Auth.BaseServlet;
import Service.CarImageService;
import Utill.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/api/car-images/delete")
public class DeleteCarImage extends BaseServlet {

    private final CarImageService carImageService = new CarImageService();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Step 1: Parse JSON from request
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> requestData = mapper.readValue(req.getInputStream(), Map.class);

        // Step 2: Extract imageId
        int imageId = (int) requestData.get("imageId");

        // Step 3: Perform delete operation
        boolean deleted = carImageService.deleteImage(imageId);

        // Step 4: Return JSON response
        if (deleted) {
            JsonResponse.ok(resp, Map.of(
                    "success", true,
                    "message", "Image deleted successfully"
            ));
        } else {
            JsonResponse.badRequest(resp," Unable to delete image");
        }
    }
}
