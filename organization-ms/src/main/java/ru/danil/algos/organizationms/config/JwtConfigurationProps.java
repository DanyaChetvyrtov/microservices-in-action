package ru.danil.algos.organizationms.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver.jwt")
public class JwtConfigurationProps {
    private String issuerUri;
    private String jwkSetUri;
}
