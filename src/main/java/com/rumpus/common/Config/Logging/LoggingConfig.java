package com.rumpus.common.Config.Logging;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.Log.LogItem.LogItemCollectionManager;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
public class LoggingConfig {

    @Bean
    public LogItemCollectionManager logManager() {
        return LogItemCollectionManager.createWithMainAndAdmin();
    }

    @Bean
    public LogLevel logLevel(LoggingProperties properties) {
        return properties.getLevel();
    }
}
