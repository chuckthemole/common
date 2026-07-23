package com.rumpus.common.Config.Logging;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.Log.LoggerContext;

@ConfigurationProperties(prefix = "properties.logging")
public class LoggingProperties {

    private LogLevel level = LogLevel.DEBUG2;

    LoggingProperties() {
        LoggerContext.setLogLevel(level);
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }
}
