package Service;

import Handler.AppException;

import Model.ProfileImage;
import Repository.ProfileImageRepository;
import Utill.FileUploadUtil;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.Instant;

public class ProfileImageService {

    private final ProfileImageRepository profileImageRepository=new ProfileImageRepository();

    // 🔹 Upload or replace profile image
    public int uploadProfileImage(int userId, InputStream fileStream, String fileName) throws IOException {

        // Step 1: check if old image exists in DB
        ProfileImage existing = profileImageRepository.findByUserId(userId);
        if (existing != null && existing.getImageUrl() != null) {
            // delete old image file from folder
            FileUploadUtil.deleteFile(existing.getImageUrl());
            // remove old DB record (optional, since we'll upsert below)
            profileImageRepository.deleteByUserId(userId);
        }

        // Step 2: save new file and generate URL/path
        String imageUrl = FileUploadUtil.saveFile(fileStream, fileName);

        // Step 3: create new image model
        ProfileImage image = new ProfileImage();
        image.setUserId(userId);
        image.setImageUrl(imageUrl);
        image.setUploadedAt(Timestamp.from(Instant.now()));

        // Step 4: save new record to DB
        return profileImageRepository.save(image);
    }

    // 🔹 Delete profile image (both file + db)
    public boolean deleteProfileImage(int userId) {
        ProfileImage image = profileImageRepository.findByUserId(userId);
        if (image == null) {
            throw new AppException(HttpServletResponse.SC_NOT_FOUND, "Profile image not found");
        }

        boolean deletedFromFolder = FileUploadUtil.deleteFile(image.getImageUrl());
        boolean deletedFromDb = profileImageRepository.deleteByUserId(userId);

        return deletedFromFolder && deletedFromDb;
    }

}
