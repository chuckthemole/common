package com.rumpus.common.Config.Logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@ConditionalOnProperty(value = "properties.logging.print-properties", havingValue = "true")
@Component
public class PropertyLogger {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyLogger.class);

    private final Environment environment;

    public PropertyLogger(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void printProperties() {

        LOG.info("========== Application Properties ==========");

        logProperty("properties.port");

        LOG.info("----- Database -----");
        logProperty("properties.datasource.url");
        logProperty("properties.datasource.username");
        logMaskedProperty("properties.datasource.password");
        logProperty("properties.datasource.driver");
        logProperty("properties.datasource.dialect");

        LOG.info("----- AWS -----");
        logProperty("properties.aws.region");
        logProperty("properties.aws.s3.bucket");
        logMaskedProperty("properties.aws.credentials.access-key");
        logMaskedProperty("properties.aws.credentials.secret-key");

        LOG.info("----- Redis -----");
        logProperty("properties.redis.host");
        logProperty("properties.redis.port");

        LOG.info("----- Security -----");
        logMaskedProperty("properties.jwt.secret");
        logProperty("properties.jwt.expiration");

        logProperty("properties.oauth2.client.registration.google.client-id");
        logMaskedProperty("properties.oauth2.client.registration.google.client-secret");

        LOG.info("----- CORS -----");
        logProperty("properties.frontend.origins");
        logProperty("properties.frontend.methods");
        logProperty("properties.frontend.headers");
        logProperty("properties.frontend.credentials");

        LOG.info("----- Notion -----");
        logProperty("properties.notion.database");

        LOG.info("============================================");
    }

    private void logProperty(String key) {

        String value = environment.getProperty(key);

        if (value == null) {
            LOG.info("{} = <not set>", key);
        } else {
            LOG.info("{} = {}", key, value);
        }
    }

    private void logMaskedProperty(String key) {

        String value = environment.getProperty(key);

        if (value == null) {
            LOG.info("{} = <not set>", key);
        } else {
            LOG.info("{} = ********", key);
        }
    }

}
