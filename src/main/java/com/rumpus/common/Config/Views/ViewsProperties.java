package com.rumpus.common.Config.Views;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "properties.views")
public class ViewsProperties {

    private String brand;

    public ViewsProperties() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
