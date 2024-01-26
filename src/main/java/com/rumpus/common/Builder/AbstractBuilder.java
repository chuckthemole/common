package com.rumpus.common.Builder;

import com.rumpus.common.Logger.AbstractCommonLogger;

public abstract class AbstractBuilder extends AbstractCommonLogger {
    
    protected StringBuilder builder;

    public AbstractBuilder(String name) {
        super(name);
        this.builder = new StringBuilder();
    }

    /**
     * This method is used to create a Builder from a String array of arguments
     * <p>
     * By default this will add spaces between the arguments
     * 
     * @param name the name of the Builder
     * @param args the arguments to add to the Builder
     */
    public AbstractBuilder(String name, String... args) {
        super(name);
        this.builder = new StringBuilder();
        for(String arg : args) {
            this.builder.append(arg).append(" ");
        }
    }

    /**
     * This method is used to create a Builder from a String array of arguments
     * 
     * @param name the name of the Builder
     * @param addSpaces whether or not to add spaces between the arguments
     * @param args the arguments to add to the Builder
     */
    public AbstractBuilder(String name, boolean addSpaces, String... args) {
        super(name);
        this.builder = new StringBuilder();
        for(String arg : args) {
            if(addSpaces) {
                this.builder.append(arg).append(" ");
            } else {
                this.builder.append(arg);
            }
        }
    }

    public void clear() {
        this.builder = new StringBuilder();
    }

    // TODO: this is just sending the string to the logger, think about how to make this better. - chuck
    /**
     * This method is used to log the Builder's string using the logger's info level
     */
    public void info() {
        this.info(this.toString());
    }

    /**
     * This method is used to log the Builder's string using the logger's error level
     */
    public void error() {
        this.error(this.toString());
    }

    /**
     * This method is used to log the Builder's string using the logger's warn level
     */
    public void warn() {
        this.warn(this.toString());
    }

    /**
     * This method is used to log the Builder's string using the logger's debug level
     */
    public void debug() {
        this.debug(this.toString());
    }

    /**
     * This method is used to log the Builder's string using the logger's trace level
     */
    public void trace() {
        this.trace(this.toString());
    }

    /**
     * This method is used to log the Builder's string using the logger's level
     * 
     * @param level the level to log the Builder's string at
     */
    public void log(AbstractCommonLogger.LogLevel level) {
        if(level == AbstractCommonLogger.LogLevel.TRACE) {
            this.trace(this.toString());
        } else if(level == AbstractCommonLogger.LogLevel.DEBUG) {
            this.debug(this.toString());
        } else if(level == AbstractCommonLogger.LogLevel.INFO) {
            this.info(this.toString());
        } else if(level == AbstractCommonLogger.LogLevel.WARN) {
            this.warn(this.toString());
        } else if(level == AbstractCommonLogger.LogLevel.ERROR) {
            this.error(this.toString());
        }
    }

    public void append(String... appendees) {
        for(String appendee : appendees) {
            this.builder.append(appendee);
        }
    }

    public StringBuilder getStringBuilder() {
        return this.builder;
    }

    @Override
    public String toString() {
        return this.builder.toString();
    }
}
