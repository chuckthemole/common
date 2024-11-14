package com.rumpus.common.Log;

import com.rumpus.common.ICommon;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Config.AbstractCommonConfig;

/**
 * Abstract class for a common logger
 */
abstract public class AbstractCommonLogger implements ICommonLogger {

    /**
     * The class for this logger
     */
    protected Class<?> clazz;

    /**
     * Constructor
     * 
     * @param name The name of the logger
     */
    public AbstractCommonLogger() {
        this.clazz = ICommon.class; // TODO: why? - chuck
    }

    /**
     * Constructor
     * 
     * @param name The name of the logger
     * @param clazz The class for this logger
     */
    public AbstractCommonLogger(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void logArgs(String... args) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(this.clazz, args).toString();
        this.infoLevel(log);
    }

    @Override
    public void logAtLevel(LogLevel level, String... args) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(this.clazz, args).toString();
        switch (level) {
            case DEBUG:
                this.debugLevel(log);
                break;
            case INFO:
                this.infoLevel(log);
                break;
            case WARN:
                this.warnLevel(log);
                break;
            case ERROR:
                this.errorLevel(log);
                break;
            case DEBUG1:
                this.debug1Level(log);
                break;
            case DEBUG2:
                this.debug2Level(log);
                break;
            case DEBUG3:
                this.debug3Level(log);
                break;
            case DEBUG4:
                this.debug4Level(log);
                break;
            default:
                this.infoLevel(log); // TODO: think about default - chuck
                break;
        }
    }

    /**
     * Log a debug message
     * 
     * @param args The message to log
     */
    abstract protected void abstractDebug(String... args);

    /**
     * Log an info message
     * 
     * @param args The message to log
     */
    abstract protected void abstractInfo(String... args);

    /**
     * Log a warning message
     * 
     * @param args The message to log
     */
    abstract protected void abstractWarn(String... args);

    /**
     * Log an error message
     * 
     * @param args The message to log
     */
    abstract protected void abstractError(String... args);

    /**
     * Log an error message
     * 
     * @param throwable The throwable to log
     * @param args The message to log
     */
    abstract protected void abstractError(Throwable throwable, String... args);

    /**
     * Log a debug level 1 message
     * 
     * @param args The message to log
     */
    abstract protected void abstractDebug1(String... args);

    /**
     * Log a debug level 2 message
     * 
     * @param args The message to log
     */
    abstract protected void abstractDebug2(String... args);

    /**
     * Log a debug level 3 message
     * 
     * @param args The message to log
     */
    abstract protected void abstractDebug3(String... args);

    /**
     * Log a debug level 4 message
     * 
     * @param args The message to log
     */
    abstract protected void abstractDebug4(String... args);

    @Override
    public void debugLevel(String... args) {
        if(this.isLoggingEnabledForLevel(LogLevel.DEBUG)) {
            this.abstractDebug(args);
        }
    }

    @Override
    public void infoLevel(String... args) {
        if(this.isLoggingEnabledForLevel(LogLevel.INFO)) {
            this.abstractInfo(args);
        }
    }

    @Override
    public void warnLevel(String... args) {
        if(this.isLoggingEnabledForLevel(LogLevel.WARN)) {
            this.abstractWarn(args);
        }
    }

    @Override
    public void errorLevel(String... args) {
        if(this.isLoggingEnabledForLevel(LogLevel.ERROR)) {
            this.abstractError(args);
        }
    }

    @Override
    public void errorLevel(Throwable throwable, String... args) {
        if(this.isLoggingEnabledForLevel(LogLevel.ERROR)) {
            this.abstractError(throwable, args);
        }
    }

    @Override
    public void debug1Level(String... args) {
        if(this.isLoggingEnabledForLevel(LogLevel.DEBUG1)) {
            this.abstractDebug1(args);
        }
    }

    @Override
    public void debug2Level(String... args) {
        if(this.isLoggingEnabledForLevel(LogLevel.DEBUG2)) {
            this.abstractDebug2(args);
        }
    }

    @Override
    public void debug3Level(String... args) {
        if(this.isLoggingEnabledForLevel(LogLevel.DEBUG3)) {
            this.abstractDebug3(args);
        }
    }

    @Override
    public void debug4Level(String... args) {
        if(this.isLoggingEnabledForLevel(LogLevel.DEBUG4)) {
            this.abstractDebug4(args);
        }
    }

    @Override
    public void setClass(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Class<?> getClass(Class<?> clazz) {
        return this.clazz;
    }

    // Helper methods
    private boolean isLoggingEnabledForLevel(LogLevel level) {
        final LogLevel currentLevel = AbstractCommonConfig.getLogLevel();
        return level.getLevel() > currentLevel.getLevel() ? false : true;
    }
}
