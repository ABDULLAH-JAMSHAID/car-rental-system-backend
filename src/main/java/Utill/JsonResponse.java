package Utill;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonResponse {
    private static final Gson gson = new Gson();

    /**
     * Private helper to write JSON response
     */
    private static void write(HttpServletResponse resp, int status, boolean success, Object body) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", status);
        responseBody.put("success", success);

        if (success) {
            responseBody.put("data", body);
        } else {
            responseBody.put("error", body);
        }

        resp.getWriter().write(gson.toJson(responseBody));
    }

    /**
     * Success (200 OK) response with data
     */
    public static void ok(HttpServletResponse resp, Object body) throws IOException {
        write(resp, HttpServletResponse.SC_OK, true, body);
    }

    /**
     * Success (201 Created) response with data
     */
    public static void created(HttpServletResponse resp, Object body) throws IOException {
        write(resp, HttpServletResponse.SC_CREATED, true, body);
    }

    /**
     * Error response with custom status code
     */
    public static void error(HttpServletResponse resp, int status, String message) throws IOException {
        write(resp, status, false, message);
    }

    /**
     * Shortcut for 400 Bad Request
     */
    public static void badRequest(HttpServletResponse resp, String message) throws IOException {
        error(resp, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    /**
     * Shortcut for 401 Unauthorized
     */
    public static void unauthorized(HttpServletResponse resp, String message) throws IOException {
        error(resp, HttpServletResponse.SC_UNAUTHORIZED, message);
    }

    /**
     * Shortcut for 403 Forbidden
     */
    public static void forbidden(HttpServletResponse resp, String message) throws IOException {
        error(resp, HttpServletResponse.SC_FORBIDDEN, message);
    }

    /**
     * Shortcut for 404 Not Found
     */
    public static void notFound(HttpServletResponse resp, String message) throws IOException {
        error(resp, HttpServletResponse.SC_NOT_FOUND, message);
    }

    /**
     * Shortcut for 500 Internal Server Error
     */
    public static void serverError(HttpServletResponse resp, String message) throws IOException {
        error(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
    }
}
