package ru.ekbtreeshelp.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JWTConfigAuth {
    Integer accessTokenLifespan;
    Integer refreshTokenLifespan;
    String accessTokenSecret;
    String refreshTokenSecret;
}
