package com.cershy.linyuserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "voice")
public class VoiceConfig {
    private String model;
    private String transitionApi;
}
