package com.rumpus.common.Config.Properties.yaml;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import com.rumpus.common.Config.Properties.AbstractApplicationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "properties")
@PropertySource(value = "classpath:properties.yml", factory = YamlPropertySourceFactory.class)
abstract public class AbstractYamlApplicationProperties {

    private String name;
    private List<String> aliases;

    public AbstractYamlApplicationProperties() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }
}
