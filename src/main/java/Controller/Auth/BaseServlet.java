package Controller.Auth;


import Handler.AppException;
import Utill.JsonResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public abstract class BaseServlet extends HttpServlet {

    private final UserRepository userRepository = new UserRepository();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Method name banani (doGet, doPost, etc.)
            String methodName = "do" + req.getMethod().substring(0, 1).toUpperCase()
                    + req.getMethod().substring(1).toLowerCase();

            Method method = this.getClass().getDeclaredMethod(
                    methodName,
                    HttpServletRequest.class,
                    HttpServletResponse.class
            );
            method.setAccessible(true);

            // 🔹 Agar @RequiresPermission annotation lagi hai
            if (method.isAnnotationPresent(RequiresPermission.class)) {
                RequiresPermission annotation = method.getAnnotation(RequiresPermission.class);
                Permission requiredPermission = annotation.value();

                Claims claims = (Claims) req.getAttribute("claims");
                if (claims == null) {
                    JsonResponse.unauthorized(resp, "Missing token claims.");
                    return;
                }

                Number uid = (Number) claims.get("uid");
                if (uid == null) {
                    JsonResponse.unauthorized(resp, "User ID not found in claims.");
                    return;
                }

                int userId = uid.intValue();

                if (userRepository.userHasPermission(userId, requiredPermission)) {
                    method.invoke(this, req, resp);
                } else {
                    JsonResponse.forbidden(resp, "Access Denied: You don't have permission to access this resource.");
                }
            } else {
                // Agar permission check required nahi hai
                method.invoke(this, req, resp);
            }

        } catch (NoSuchMethodException e) {
            // Agar method support hi nahi hota (default handling)
            super.service(req, resp);

        } catch (InvocationTargetException e) {
            // Agar method ke andar exception aya
            Throwable targetEx = e.getTargetException();
            handleCustomException(targetEx, resp);

        } catch (Exception e) {
            // Fallback for any other errors
            handleCustomException(e, resp);
        }
    }

    // 🔹 Centralized Exception Handling
    private void handleCustomException(Throwable ex, HttpServletResponse resp) throws IOException {
        if (ex instanceof AppException appEx) {
            // Agar tumne AppException throw kiya tha (custom status code ke sath)
            JsonResponse.error(resp, appEx.getStatusCode(), appEx.getMessage());
        } else {
            // Agar koi unexpected exception aya
            JsonResponse.serverError(resp, "Unexpected error: " + ex.getMessage());
        }
    }
}
