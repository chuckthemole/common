package com.rumpus.common;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Log.ICommonLogger;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.Log.application.SLF4JLogger;

/**
 * AbstractCommonObject class.
 * <p>
 * AbstractCommonObject is the top level abstract class for all common objects.
 * It provides a logger and logging methods for all common objects.
 */
abstract public class AbstractCommonObject implements ICommon {

    /**
     * The status of this object.
     */
    public enum ObjectStatus { NEW, INITIALIZED, PROCESSING, COMPLETED, ERROR }

    /**
     * The status of this object.
     */
    private ObjectStatus status = ObjectStatus.NEW;

    /**
     * The logger for this object.
     */
    private ICommonLogger LOGGER = null;

    /**
     * Top level AbstractCommonObject constructor.
     * <p>
     * Uses SLF4JLogger for logging.
     */
    public AbstractCommonObject() {
        if(this.LOGGER == null) {
            // TODO: this is causing an infinite looop rn - chuck
            this.LOGGER = SLF4JLogger.create(this.getClass()); // TODO: should we inject the logger? Or maybe an enum - chuck
        }
    }

    /**
     * Top level AbstractCommonObject constructor.
     * 
     * @param logger the logger to use for this object
     */
    public AbstractCommonObject(ICommonLogger logger) {
        this.LOGGER = logger != null ? logger : SLF4JLogger.create(this.getClass());
    }

    /**
     * Top level AbstractCommonObject LOG method. Default is info level (Debug2).
     * 
     * @param args The message to log
     */
    protected void LOG(String... args) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(this.getClass(), args).toString();
        this.LOGGER.infoLevel(log);
    }

    /**
     * Top level AbstractCommonObject LOG method. Uses the specified level.
     * 
     * @param level the level to log the message at
     * @param args The message to log
     */
    protected void LOG(LogLevel level, String... args) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(this.getClass(), args).toString();
        this.LOGGER.logAtLevel(level, log);
    }

    protected static void LOG(Class<?> clazz, String... args) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(clazz, args).toString();
        ICommon.LOG_COMMON.setClass(clazz);
        ICommon.LOG_COMMON.infoLevel(log);
        ICommon.LOG_COMMON.setClass(ICommon.DEFAULT_LOGGER_CLASS);
    }

    protected static void LOG(Class<?> clazz, LogLevel level, String... args) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(clazz, args).toString();
        ICommon.LOG_COMMON.setClass(clazz);
        ICommon.LOG_COMMON.infoLevel(log);
        ICommon.LOG_COMMON.setClass(ICommon.DEFAULT_LOGGER_CLASS);
    }

    /**
     * Set the status of this object.
     * 
     * @param status the status to set
     */
    public void setStatus(ObjectStatus status) {
        this.status = status;
    }

    /**
     * Get the status of this object.
     * 
     * @return the status of this object
     */
    public ObjectStatus getStatus() {
        return this.status;
    }

    /**
     * Set the logger for this object.
     * 
     * @param logger
     */
    public void setLogger(ICommonLogger logger) {
        this.LOGGER = logger;
    }

    /**
     * Get the logger for this object.
     * 
     * @return the logger for this object
     */
    public ICommonLogger getLogger() {
        return this.LOGGER;
    }
    
    /**
     * Top level AbstractCommonObject toString() method.
     * <p>
     * Returns the name of this object.
     * 
     * @return the name of this object
     */
    @Override
    public abstract String toString();
}
