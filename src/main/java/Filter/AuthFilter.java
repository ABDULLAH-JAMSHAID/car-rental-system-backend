package Filter;

import Utill.JsonResponse;
import Utill.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/api/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        // Auth endpoints ko bypass karo
        if (path.endsWith("/api/login")
                || path.endsWith("/api/register")
                || path.endsWith("/api/refresh")
                || path.endsWith("/api/verify-otp")
                || path.endsWith("/api/logout")
                || path.endsWith("/api/resend-otp")|| path.endsWith("/api/addCar")
                || path.endsWith("/api/updateCar/*")) {
            chain.doFilter(request, response);
            return;
        }

        // Authorization header check
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            JsonResponse.badRequest(res, "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);

        // Token valid hai ya nahi
        if (!JwtUtil.isTokenValid(token)) {
            JsonResponse.unauthorized(res, "Invalid or expired token");
            return;
        }

        try {
            // Token parse karke claims set karo
            Jws<Claims> jws = JwtUtil.parseToken(token);
            Claims claims = jws.getBody();
            req.setAttribute("claims", claims);

          //   Bas token aur claims set karna hai, permission check BaseServlet karega
            chain.doFilter(request, response);

        } catch (JwtException e) {
            JsonResponse.badRequest(res, "Invalid or expired token");
        }
    }

    @Override public void init(FilterConfig filterConfig) {}
    @Override public void destroy() {}
}
