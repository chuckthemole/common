package com.rumpus.common.Config.Properties;

@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "properties")
@org.springframework.context.annotation.PropertySource(value = "classpath:properties.yml", factory = com.rumpus.common.Config.Properties.YamlPropertySourceFactory.class)
public class AbstractYamlApplicationProperties extends AbstractApplicationProperties {

    private String name;
    private java.util.List<String> aliases;

    public AbstractYamlApplicationProperties(String name) {
        super(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.List<String> getAliases() {
        return aliases;
    }

    public void setAliases(java.util.List<String> aliases) {
        this.aliases = aliases;
    }
}
