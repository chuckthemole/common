package com.rumpus.common.Log.application;

import java.util.logging.Logger;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Log.AbstractCommonLogger;
import com.rumpus.common.Log.ICommonLogger;

/**
 * Implementation of the ICommonLogger interface.
 */
public class JavaLogger extends AbstractCommonLogger {

    private final Logger logger;

    private JavaLogger(Class<?> clazz) {
        super(clazz);
        this.logger = Logger.getLogger(clazz.getName());
    }

    public static ICommonLogger createEmptyLogger() {
        return new JavaLogger(com.rumpus.common.ICommon.DEFAULT_LOGGER_CLASS);
    }

    public static ICommonLogger createLogger(Class<?> clazz) {
        return new JavaLogger(clazz);
    }

    @Override
    public void abstractInfo(String... message) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(this.clazz, message).toString();
        logger.info(log);
    }

    @Override
    public void abstractDebug(String... message) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(this.clazz, message).toString();
        logger.fine(log); // TODO: Look into this
    }

    @Override
    public void abstractWarn(String... message) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(this.clazz, message).toString();
        logger.warning(log);
    }

    @Override
    public void abstractError(String... message) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(this.clazz, message).toString();
        logger.severe(log);
    }

    @Override
    public void abstractError(Throwable throwable, String... message) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(this.clazz, message).toString();
        logger.severe(log);
        logger.severe(throwable.toString());
    }

    @Override
    protected void abstractDebug1(String... args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'abstractDebug1'");
    }

    @Override
    protected void abstractDebug2(String... args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'abstractDebug2'");
    }

    @Override
    protected void abstractDebug3(String... args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'abstractDebug3'");
    }

    @Override
    protected void abstractDebug4(String... args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'abstractDebug4'");
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
