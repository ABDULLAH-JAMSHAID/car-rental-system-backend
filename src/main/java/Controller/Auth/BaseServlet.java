package Controller.Auth;

import Annotation.RequiresPermission;
import Enums.Permissions;
import Handler.AppException;
import Repository.AuthRepository;
import Utill.JsonResponse;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class BaseServlet extends HttpServlet {

    private final AuthRepository authRepository = new AuthRepository();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 🔹 Method name build (doGet, doPost, etc.)
            String methodName = "do" + req.getMethod().substring(0, 1).toUpperCase()
                    + req.getMethod().substring(1).toLowerCase();

            Method method = this.getClass().getDeclaredMethod(
                    methodName,
                    HttpServletRequest.class,
                    HttpServletResponse.class
            );
            method.setAccessible(true);

            // 🔹 Agar @RequiresPermission lagi hai to check karo
            if (method.isAnnotationPresent(RequiresPermission.class)) {
                RequiresPermission annotation = method.getAnnotation(RequiresPermission.class);
                Permissions requiredPermission = annotation.value();

                Claims claims = (Claims) req.getAttribute("claims");
                if (claims == null) {
                    JsonResponse.unauthorized(resp, "Missing token claims.");
                    return;
                }

                Number user_id = (Number) claims.get("user_id");
                if (user_id == null) {
                    JsonResponse.unauthorized(resp, "User ID not found in claims.");
                    return;
                }

                int userId = user_id.intValue();
                int roleId=authRepository.findRoleIdByUserId(userId);

                if (authRepository.userHasPermission(roleId, requiredPermission)) {
                    method.invoke(this, req, resp);
                } else {
                    JsonResponse.forbidden(resp, "Access Denied: You don't have permission to access this resource.");
                }
            } else {
                // Agar permission required nahi hai
                method.invoke(this, req, resp);
            }

        } catch (NoSuchMethodException e) {
            super.service(req, resp);

        } catch (InvocationTargetException e) {
            // 🔹 Agar controller method ke andar koi exception throw hui
            Throwable targetEx = e.getTargetException();
            handleCustomException(targetEx, resp);

        } catch (Exception e) {
            // 🔹 Fallback for any unexpected error
            handleCustomException(e, resp);
        }
    }

    // ✅ Centralized JSON Exception Handling
    private void handleCustomException(Throwable ex, HttpServletResponse resp) throws IOException {
        if (ex instanceof AppException appEx) {
            // 🔹 Custom AppException → clean JSON format
            JsonResponse.error(resp, appEx.getStatusCode(), appEx.getMessage());

        } else if (ex instanceof IllegalArgumentException) {
            JsonResponse.error(resp, 400, ex.getMessage());

        } else {
            // 🔹 Unexpected exception (fallback)
            JsonResponse.serverError(resp, "Internal Server Error: " + ex.getMessage());
        }
    }
}
