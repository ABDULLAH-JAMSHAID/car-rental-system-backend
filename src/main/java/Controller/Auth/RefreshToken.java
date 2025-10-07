package Controller.Auth;

import Model.User;
import Repository.AuthRepository;
import Utill.JsonResponse;
import Utill.JwtUtil;
import Utill.RedisUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/refresh")
public class RefreshToken extends BaseServlet {
    private final AuthRepository authRepository=new AuthRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String refreshToken = extractRefreshToken(req);

        // Check 1: Refresh token cookie mein hai ya nahi?
        if (refreshToken == null) {
            JsonResponse.unauthorized(resp, "Refresh token not found. Please log in again.");
            return;
        }

        // Check 2: Token blacklist to nahi ho chuka (logout ki wajah se)?
        if (RedisUtil.isTokenBlacklisted(refreshToken)) {
            JsonResponse.unauthorized(resp, "Session has been invalidated. Please log in again.");
            return;
        }

        // Check 3: Token expire to nahi ho gaya ya signature theek hai?
        if (!JwtUtil.isTokenValid(refreshToken)) {
            JsonResponse.unauthorized(resp, "Session has expired. Please log in again.");
            return;
        }

        // --- Agar sab checks pass ho gaye, to naya access token banayein ---
        try {
            // 1. Refresh token se username nikalein
            String email = JwtUtil.extractEmail(refreshToken);

            // 2. Database se user ki latest details (role, id) fetch karein
            User user = authRepository.find(email, null);

            if (user == null) {
                JsonResponse.unauthorized(resp, "User associated with this session not found. Please log in again.");
                return;
            }

            // 3. Naye access token ke liye claims tayyar karein
            Map<String, Object> claims = new HashMap<>();// Aapke User model ke mutabiq
            claims.put("user_id", user.getId());
            claims.put("full_name", user.getFull_name());
            claims.put("email", user.getEmail());// Aapke User model ke mutabiq

            // 4. Sirf naya ACCESS TOKEN generate karein
            String newAccessToken = JwtUtil.generateAccessToken(user.getEmail(), claims);

            // 5. Naya access token client ko wapas bhejein
            Map<String, String> responseData = new HashMap<>();
            responseData.put("accessToken", newAccessToken);

            JsonResponse.ok(resp, responseData);

        } catch (Exception e) {
            // Agar token se username nikalne mein ya DB access mein masla ho
            JsonResponse.serverError(resp, "An internal error occurred while refreshing the session.");
        }
    }

    private String extractRefreshToken(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}