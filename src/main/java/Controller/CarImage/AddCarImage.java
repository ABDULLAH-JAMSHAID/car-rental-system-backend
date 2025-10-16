package Controller.CarImage;

import Controller.Auth.BaseServlet;
import Service.CarImageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import Utill.JsonResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/car-images/upload")
@MultipartConfig
public class AddCarImage extends BaseServlet {

    private final CarImageService carImageService=new CarImageService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


            int carId = Integer.parseInt(req.getParameter("carId"));
            boolean isMain = Boolean.parseBoolean(req.getParameter("isMain"));
            Part filePart = req.getPart("image");

            String fileName = filePart.getSubmittedFileName();
            int imageId = carImageService.uploadImage(carId, filePart.getInputStream(), fileName, isMain);

            JsonResponse.created(resp, Map.of(
                    "success", true,
                    "message", "Image uploaded successfully",
                    "imageId", imageId
            ));


    }
}
