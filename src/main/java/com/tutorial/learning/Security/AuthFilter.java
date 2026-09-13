package com.tutorial.learning.Security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import jakarta.servlet.ServletException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthFilter extends OncePerRequestFilter {
    private static final String ACCESS_COOKIE = "access_cookie";
    private static final String CREATE_USER_PATH = "/user/create_user";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isPreflightRequest(request) || isPublicRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = findAccessToken(request);
        if (token == null) {
            writeError(response, HttpServletResponse.SC_UNAUTHORIZED, "Missing required cookie");
            return;
        }

        if (!jwtUtils.validateToken(token)) {
            writeError(response, HttpServletResponse.SC_FORBIDDEN, "Invalid or expired token");
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(jwtUtils.getAuthentication(token));
        filterChain.doFilter(request, response);
    }

    private boolean isPreflightRequest(HttpServletRequest request) {
        return "OPTIONS".equalsIgnoreCase(request.getMethod());
    }

    private boolean isPublicRequest(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/auth/") || CREATE_USER_PATH.equals(path);
    }

    private String findAccessToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if (ACCESS_COOKIE.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private void writeError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("text/plain");
        response.getWriter().write(message);
    }
}
