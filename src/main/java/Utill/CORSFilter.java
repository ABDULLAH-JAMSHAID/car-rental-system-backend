package Utill;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Allow your frontend origin
        res.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");

        // Allow credentials (if you use cookies for refresh token)
        res.setHeader("Access-Control-Allow-Credentials", "true");

        // Allow headers that browser may send
        res.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");

        // Allow HTTP methods
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        // Preflight request ko yahin se return kar do
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(request, response);
    }
}
