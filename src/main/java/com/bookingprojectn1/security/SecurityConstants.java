package com.bookingprojectn1.security;

public class SecurityConstants {
    public static final String[] WHITE_LIST = {
            "/api/auth/**",
            "/api/file/**",
            "/category/list",
            "api/notification/fcm",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/swagger-config",
            "/swagger-resources/**",
            "/webjars/**",
    };
}
