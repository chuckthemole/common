package com.rumpus.common.Log.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Log.AbstractCommonLogger;

final public class SLF4JLogger extends AbstractCommonLogger implements Logger {

    private SLF4JLogger(Class<?> clazz) {
        super(clazz);
    }

    public static SLF4JLogger create(Class<?> clazz) {
        return new SLF4JLogger(clazz);
    }

    //////////////////////////////////////////////
    ///////// ICommonLogger Overrides ////////////
    //////////////////////////////////////////////

    @Override
    public void abstractInfo(String... message) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(this.clazz, message).toString();
        this.info(log);
    }


    @Override
    public void abstractDebug(String... message) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(this.clazz, message).toString();
        this.debug(log);
    }


    @Override
    public void abstractWarn(String... message) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(this.clazz, message).toString();
        this.warn(log);
    }


    @Override
    public void abstractError(String... message) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(this.clazz, message).toString();
        this.error(log);
    }


    @Override
    public void abstractError(Throwable throwable, String... message) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(this.clazz, message).toString();
        this.error(log, throwable);
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

    //////////////////////////////////////////////
    ///////// SLF4J Logger Overrides /////////////
    //////////////////////////////////////////////

    @Override
    public String getName() {
        return LoggerFactory.getLogger(this.clazz).getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return LoggerFactory.getLogger(this.clazz).isTraceEnabled();
    }

    @Override
    public void trace(String msg) {
        LoggerFactory.getLogger(this.clazz).trace(msg);
    }

    @Override
    public void trace(String format, Object arg) {
        LoggerFactory.getLogger(this.clazz).trace(format, arg);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        LoggerFactory.getLogger(this.clazz).trace(format, arg1, arg2);
    }

    @Override
    public void trace(String format, Object... arguments) {
        LoggerFactory.getLogger(this.clazz).trace(format, arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        LoggerFactory.getLogger(this.clazz).trace(msg, t);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return LoggerFactory.getLogger(this.clazz).isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String msg) {
        LoggerFactory.getLogger(this.clazz).trace(marker, msg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        LoggerFactory.getLogger(this.clazz).trace(marker, format, arg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        LoggerFactory.getLogger(this.clazz).trace(marker, format, arg1, arg2);
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        LoggerFactory.getLogger(this.clazz).trace(marker, format, argArray);
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        LoggerFactory.getLogger(this.clazz).trace(marker, msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return LoggerFactory.getLogger(this.clazz).isDebugEnabled();
    }

    @Override
    public void debug(String msg) {
        LoggerFactory.getLogger(this.clazz).debug(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        LoggerFactory.getLogger(this.clazz).debug(format, arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        LoggerFactory.getLogger(this.clazz).debug(format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object... arguments) {
        LoggerFactory.getLogger(this.clazz).debug(format, arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        LoggerFactory.getLogger(this.clazz).debug(msg, t);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return LoggerFactory.getLogger(this.clazz).isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String msg) {
        LoggerFactory.getLogger(this.clazz).debug(marker, msg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        LoggerFactory.getLogger(this.clazz).debug(marker, format, arg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        LoggerFactory.getLogger(this.clazz).debug(marker, format, arg1, arg2);
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        LoggerFactory.getLogger(this.clazz).debug(marker, format, arguments);
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        LoggerFactory.getLogger(this.clazz).debug(marker, msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return LoggerFactory.getLogger(this.clazz).isInfoEnabled();
    }

    @Override
    public void info(String msg) {
        LoggerFactory.getLogger(this.clazz).info(msg);
    }

    @Override
    public void info(String format, Object arg) {
        LoggerFactory.getLogger(this.clazz).info(format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        LoggerFactory.getLogger(this.clazz).info(format, arg1, arg2);
    }

    @Override
    public void info(String format, Object... arguments) {
        LoggerFactory.getLogger(this.clazz).info(format, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        LoggerFactory.getLogger(this.clazz).info(msg, t);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return LoggerFactory.getLogger(this.clazz).isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String msg) {
        LoggerFactory.getLogger(this.clazz).info(marker, msg);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        LoggerFactory.getLogger(this.clazz).info(marker, format, arg);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        LoggerFactory.getLogger(this.clazz).info(marker, format, arg1, arg2);
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        LoggerFactory.getLogger(this.clazz).info(marker, format, arguments);
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        LoggerFactory.getLogger(this.clazz).info(marker, msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return LoggerFactory.getLogger(this.clazz).isWarnEnabled();
    }

    @Override
    public void warn(String msg) {
        LoggerFactory.getLogger(this.clazz).warn(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        LoggerFactory.getLogger(this.clazz).warn(format, arg);
    }

    @Override
    public void warn(String format, Object... arguments) {
        LoggerFactory.getLogger(this.clazz).warn(format, arguments);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        LoggerFactory.getLogger(this.clazz).warn(format, arg1, arg2);
    }

    @Override
    public void warn(String msg, Throwable t) {
        LoggerFactory.getLogger(this.clazz).warn(msg, t);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return LoggerFactory.getLogger(this.clazz).isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String msg) {
        LoggerFactory.getLogger(this.clazz).warn(marker, msg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        LoggerFactory.getLogger(this.clazz).warn(marker, format, arg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        LoggerFactory.getLogger(this.clazz).warn(marker, format, arg1, arg2);
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        LoggerFactory.getLogger(this.clazz).warn(marker, format, arguments);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        LoggerFactory.getLogger(this.clazz).warn(marker, msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return LoggerFactory.getLogger(this.clazz).isErrorEnabled();
    }

    @Override
    public void error(String msg) {
        LoggerFactory.getLogger(this.clazz).error(msg);
    }

    @Override
    public void error(String format, Object arg) {
        LoggerFactory.getLogger(this.clazz).error(format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        LoggerFactory.getLogger(this.clazz).error(format, arg1, arg2);
    }

    @Override
    public void error(String format, Object... arguments) {
        LoggerFactory.getLogger(this.clazz).error(format, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        LoggerFactory.getLogger(this.clazz).error(msg, t);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return LoggerFactory.getLogger(this.clazz).isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String msg) {
        LoggerFactory.getLogger(this.clazz).error(marker, msg);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        LoggerFactory.getLogger(this.clazz).error(marker, format, arg);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        LoggerFactory.getLogger(this.clazz).error(marker, format, arg1, arg2);
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        LoggerFactory.getLogger(this.clazz).error(marker, format, arguments);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        LoggerFactory.getLogger(this.clazz).error(marker, msg, t);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
