package ru.ekbtreeshelp.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JWTConfig {
    private String accessTokenSecret;
}
