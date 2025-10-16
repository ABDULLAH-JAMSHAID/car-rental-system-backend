package Utill;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;

public class FileUploadUtil {

    private static final String UPLOAD_DIR = "C:/uploads"; // adjust if needed

    public static String saveFile(InputStream inputStream, String fileName) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);

        try (OutputStream os = Files.newOutputStream(filePath)) {
            inputStream.transferTo(os);
        }

        // Return relative path (for database)
        return "/uploads/" + fileName;
    }
    public static boolean deleteFile(String filePath) {
        try {
            java.io.File file = new java.io.File(filePath);
            if (file.exists()) {
                return file.delete();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
