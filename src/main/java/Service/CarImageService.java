package Service;

import Handler.AppException;
import Model.CarImage;
import Repository.CarImageRepository;
import Utill.FileUploadUtil;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class CarImageService {

    private final CarImageRepository carImageRepository = new CarImageRepository();

    // 🔹 Existing method — upload image
    public int uploadImage(int carId, InputStream fileStream, String fileName, boolean isMain) throws IOException {
        String imageUrl = FileUploadUtil.saveFile(fileStream, fileName);

        CarImage image = new CarImage();
        image.setCarId(carId);
        image.setImageUrl(imageUrl);
        image.setMain(isMain);
        image.setUploadedAt(Timestamp.from(Instant.now()));

        return carImageRepository.save(image);
    }

    // 🔹 Existing method — get images by car
    public List<CarImage> getImagesByCarId(int carId) {
        return carImageRepository.findByCarId(carId);
    }

    // 🆕 New method — update which image is main
    public void setMainImage(int carId, int imageId) {
        carImageRepository.updateMainImage(carId, imageId);
    }

    public boolean deleteImage(int imageId) {
        // Step 1: DB se image ka record fetch karo (taake file path mil jaye)
        CarImage image = carImageRepository.findById(imageId);
        if (image == null) {
            throw new AppException(HttpServletResponse.SC_NOT_FOUND, "Image not found");
        }

        // Step 2: Folder se image delete karo
        boolean deletedFromFolder = FileUploadUtil.deleteFile(image.getImageUrl());

        // Step 3: DB se record delete karo
        boolean deletedFromDb = carImageRepository.deleteById(imageId);

        return deletedFromFolder && deletedFromDb;
    }

}
