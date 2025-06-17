package ru.danil.algos.ostock.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class OAuth2FeignConfig {
    @Bean
    public RequestInterceptor bearerTokenRequestInterceptor() {
        return requestTemplate -> {
            // Получаем токен из контекста безопасности
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Current authentication: " + authentication);
            if (authentication instanceof JwtAuthenticationToken jwtToken) {
                System.out.println("Current token: " + jwtToken.getToken());
                requestTemplate.header(
                        "Authorization",
                        "Bearer " + jwtToken.getToken().getTokenValue()
                );
            }else System.out.println("Authorization header not present");
        };
    }
}
