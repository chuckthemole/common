package com.rumpus.common.Log;

import com.rumpus.common.Log.ICommonLogger.LogLevel;

public final class LoggerContext {

    private static volatile LogLevel logLevel = LogLevel.INFO;

    public static LogLevel getLogLevel() {
        return logLevel;
    }

    public static void setLogLevel(LogLevel level) {
        logLevel = level;
    }
}
