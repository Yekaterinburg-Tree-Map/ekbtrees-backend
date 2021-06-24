package ru.ekbtreeshelp.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "crypto")
public class CryptoConfig {
    String salt;
}
