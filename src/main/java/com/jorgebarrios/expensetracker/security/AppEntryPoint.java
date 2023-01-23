package com.jorgebarrios.expensetracker.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class AppEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException auth
                        ) throws IOException, ServletException {
        // 401
        System.out.println("Error 401" + request.getHeaderNames()
                                                .toString());
        auth.printStackTrace();
        setResponseError(
                response,
                HttpServletResponse.SC_UNAUTHORIZED,
                "Authentication Failed"
                        );
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
                        ) throws IOException {
        // 403
        setResponseError(
                response,
                HttpServletResponse.SC_FORBIDDEN,
                String.format(
                        "Access Denies: %s",
                        accessDeniedException.getMessage()
                             )
                        );
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            RuntimeException accessDeniedException
                        ) throws IOException {
        // 403
        System.out.println(accessDeniedException.getMessage());
        accessDeniedException.printStackTrace();
        setResponseError(
                response,
                HttpServletResponse.SC_BAD_REQUEST,
                String.format(
                        "Access Denies: %s",
                        accessDeniedException.getMessage()
                             )
                        );
    }


    private void setResponseError(
            HttpServletResponse response,
            int errorCode,
            String errorMessage
                                 ) throws IOException {
        response.setStatus(errorCode);
        response.getWriter()
                .write(errorMessage);
        response.getWriter()
                .flush();
        response.getWriter()
                .close();
    }

}
