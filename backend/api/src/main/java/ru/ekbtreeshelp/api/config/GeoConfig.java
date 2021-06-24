package ru.ekbtreeshelp.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "geo")
public class GeoConfig {
    private Double clusterDistance;
    private Integer srid;
}
