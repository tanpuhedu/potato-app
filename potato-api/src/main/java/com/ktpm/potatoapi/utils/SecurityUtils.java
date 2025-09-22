package com.ktpm.potatoapi.utils;

import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtils {
    public static String getCurrentUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        // return email
        return JwtUtils.extractUsername(token);
    }
}
