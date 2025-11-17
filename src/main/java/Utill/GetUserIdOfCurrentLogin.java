package Utill;

import Handler.AppException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetUserIdOfCurrentLogin {

    public static int getUserIdFromRequest(HttpServletRequest req) throws AppException {
        Object obj = req.getAttribute("claims");

        if (obj == null) {
            throw new AppException(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Missing claims");
        }

        Claims claims = (Claims) obj;
        Object userIdObj = claims.get("user_id");

        if (userIdObj == null) {
            throw new AppException(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Missing user_id in token");
        }

        try {
            return Integer.parseInt(userIdObj.toString());
        } catch (NumberFormatException e) {
            throw new AppException(HttpServletResponse.SC_UNAUTHORIZED, "Invalid user_id format in token");
        }
    }
    public static int extractIdFromUrl(HttpServletRequest req) throws AppException {
        String pathInfo = req.getPathInfo(); // e.g., /5
        if (pathInfo == null || pathInfo.equals("/")) {
            throw new AppException(HttpServletResponse.SC_BAD_REQUEST, "Car ID is required in the URL");
        }

        try {
            return Integer.parseInt(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            throw new AppException(HttpServletResponse.SC_BAD_REQUEST, "Invalid Car ID format in URL");
        }
    }
}
