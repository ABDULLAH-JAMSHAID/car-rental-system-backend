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
    private static void write(HttpServletResponse resp, int status, boolean success, Map<String, Object> body) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        body.put("status", status);
        body.put("success", success);
        resp.getWriter().write(gson.toJson(body));
    }

    /**
     * 200 OK
     */
    public static void ok(HttpServletResponse resp, Object data) throws IOException {
        Map<String, Object> body = new HashMap<>();
        body.put("data", data);
        write(resp, HttpServletResponse.SC_OK, true, body);
    }

    /**
     * 201 Created (with message)
     */
    public static void created(HttpServletResponse resp,Object data) throws IOException {
        Map<String, Object> body = new HashMap<>();
        body.put("data", data);
        write(resp, HttpServletResponse.SC_CREATED, true, body);
    }

    /**
     * Error (custom status)
     */
    public static void error(HttpServletResponse resp, int status, String message) throws IOException {
        Map<String, Object> body = new HashMap<>();
        body.put("message", message);
        write(resp, status, false, body);
    }

    public static void badRequest(HttpServletResponse resp, String message) throws IOException {
        error(resp, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    public static void unauthorized(HttpServletResponse resp, String message) throws IOException {
        error(resp, HttpServletResponse.SC_UNAUTHORIZED, message);
    }

    public static void forbidden(HttpServletResponse resp, String message) throws IOException {
        error(resp, HttpServletResponse.SC_FORBIDDEN, message);
    }

    public static void notFound(HttpServletResponse resp, String message) throws IOException {
        error(resp, HttpServletResponse.SC_NOT_FOUND, message);
    }

    public static void serverError(HttpServletResponse resp, String message) throws IOException {
        error(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
    }
}
