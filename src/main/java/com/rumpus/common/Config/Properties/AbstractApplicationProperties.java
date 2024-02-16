package com.rumpus.common.Config.Properties;

import org.springframework.core.env.Profiles;

public class AbstractApplicationProperties extends com.rumpus.common.AbstractCommonObject implements org.springframework.core.env.Environment {

    java.util.Map<String, String> properties;

    public AbstractApplicationProperties(String name) {
        super(name);
        this.properties = new java.util.HashMap<String, String>();
    }

    @Override
    public boolean containsProperty(String key) {
        return this.properties.containsKey(key);
    }

    @Override
    public String getProperty(String key) {
        return this.properties.get(key);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return this.properties.getOrDefault(key, defaultValue);
    }

    @Override
    public <T> T getProperty(String key, Class<T> targetType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProperty'");
    }

    @Override
    public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProperty'");
    }

    @Override
    public String getRequiredProperty(String key) throws IllegalStateException {
        if(this.properties.get(key) == null) {
            LOG_THIS("Property '" + key + "' not found");
            throw new IllegalStateException("Property '" + key + "' not found");
        }
        return this.properties.get(key);
    }

    @Override
    public <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRequiredProperty'");
    }

    @Override
    public String resolvePlaceholders(String text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resolvePlaceholders'");
    }

    @Override
    public String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resolveRequiredPlaceholders'");
    }

    @Override
    public String[] getActiveProfiles() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getActiveProfiles'");
    }

    @Override
    public String[] getDefaultProfiles() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDefaultProfiles'");
    }

    @Override
    public boolean acceptsProfiles(String... profiles) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'acceptsProfiles'");
    }

    @Override
    public boolean acceptsProfiles(Profiles profiles) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'acceptsProfiles'");
    }

    private static void LOG_THIS(String... args) {
        com.rumpus.common.Builder.LogBuilder.logBuilderFromStringArgsNoSpaces(AbstractApplicationProperties.class, args).info();
    }
}
