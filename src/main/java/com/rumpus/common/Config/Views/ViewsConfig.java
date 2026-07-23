package com.rumpus.common.Config.Views;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ViewsProperties.class)
public class ViewsConfig {
    @Bean
    public String navbarBrand(ViewsProperties properties) {
        return properties.getBrand();
    }
}
