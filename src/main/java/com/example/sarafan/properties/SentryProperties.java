package com.example.sarafan.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ivan Kurilov on 15.06.2021
 */

@Configuration
@ConfigurationProperties(prefix = "sentry")
@Data
public class SentryProperties {
    private String dsn;
}
