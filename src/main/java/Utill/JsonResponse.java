package Utill;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonResponse {

    // Use a single preconfigured ObjectMapper
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule()) // support for LocalDate, LocalDateTime, etc.
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ISO format instead of timestamps

    private static void write(HttpServletResponse resp, int status, boolean success, Map<String, Object> body) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        body.put("status", status);
        body.put("success", success);

        // ✅ serialize with Jackson (no reflection issues)
        mapper.writeValue(resp.getWriter(), body);
    }

    public static void ok(HttpServletResponse resp, Object data) throws IOException {
        Map<String, Object> body = new HashMap<>();
        body.put("data", data);
        write(resp, HttpServletResponse.SC_OK, true, body);
    }

    public static void created(HttpServletResponse resp, Object data) throws IOException {
        Map<String, Object> body = new HashMap<>();
        body.put("data", data);
        write(resp, HttpServletResponse.SC_CREATED, true, body);
    }

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
