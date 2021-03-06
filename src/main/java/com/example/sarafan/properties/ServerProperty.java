package com.example.sarafan.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sarafan")
@Data
public class ServerProperty {
        private String hostname;
        private int port;
}
