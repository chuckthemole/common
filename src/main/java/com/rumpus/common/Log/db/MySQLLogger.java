package com.rumpus.common.Log.db;

import com.mysql.cj.log.Log;
import com.rumpus.common.Log.AbstractCommonLogger;

/**
 * MySQLLogger implementation with infoEnabled set to true on construction
 */
public class MySQLLogger extends AbstractCommonLogger implements Log {

    private boolean debugEnabled;
    private boolean errorEnabled;
    private boolean fatalEnabled;
    private boolean infoEnabled;
    private boolean traceEnabled;
    private boolean warnEnabled;

    public MySQLLogger() {
        this.debugEnabled = false;
        this.errorEnabled = false;
        this.fatalEnabled = false;
        this.infoEnabled = true;
        this.traceEnabled = false;
        this.warnEnabled = false;
    }

    public MySQLLogger(Log log) {
        this.debugEnabled = log.isDebugEnabled();
        this.errorEnabled = log.isErrorEnabled();
        this.fatalEnabled = log.isFatalEnabled();
        this.infoEnabled = log.isInfoEnabled();
        this.traceEnabled = log.isTraceEnabled();
        this.warnEnabled = log.isWarnEnabled();
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public void setErrorEnabled(boolean errorEnabled) {
        this.errorEnabled = errorEnabled;
    }

    public void setFatalEnabled(boolean fatalEnabled) {
        this.fatalEnabled = fatalEnabled;
    }

    public void setInfoEnabled(boolean infoEnabled) {
        this.infoEnabled = infoEnabled;
    }

    public void setTraceEnabled(boolean traceEnabled) {
        this.traceEnabled = traceEnabled;
    }

    public void setWarnEnabled(boolean warnEnabled) {
        this.warnEnabled = warnEnabled;
    }

    /**
     * Sets enabled flags back to false, except infoEnabled set to true.
     */
    public void clear() {
        this.debugEnabled = false;
        this.errorEnabled = false;
        this.fatalEnabled = false;
        this.infoEnabled = true;
        this.traceEnabled = false;
        this.warnEnabled = false;
    }

    //////////////////////////////////////////////
    ///////// Mysql logger Overrides /////////////
    //////////////////////////////////////////////

    @Override
    public boolean isDebugEnabled() {
        return this.debugEnabled;
    }

    @Override
    public boolean isErrorEnabled() {
        return this.errorEnabled;
    }

    @Override
    public boolean isFatalEnabled() {
        return this.fatalEnabled;
    }

    @Override
    public boolean isInfoEnabled() {
        return this.infoEnabled;
    }

    @Override
    public boolean isTraceEnabled() {
        return this.traceEnabled;
    }

    @Override
    public boolean isWarnEnabled() {
        return this.warnEnabled;
    }

    @Override
    public void logDebug(Object msg) {
        // LOG(LogLevel.DEBUG, (String) msg);
    }

    @Override
    public void logDebug(Object msg, Throwable thrown) {
        // LOG(LogLevel.DEBUG, (String) msg, thrown.getMessage());
    }

    @Override
    public void logError(Object msg) {
        // LOG(LogLevel.ERROR, (String) msg);
    }

    @Override
    public void logError(Object msg, Throwable thrown) {
        // LOG(LogLevel.ERROR, (String) msg, thrown.getMessage());
    }

    @Override
    public void logFatal(Object msg) {
        // LOG(LogLevel.ERROR, (String) msg);
    }

    @Override
    public void logFatal(Object msg, Throwable thrown) {
        // LOG(LogLevel.ERROR, (String) msg, thrown.getMessage());
    }

    @Override
    public void logInfo(Object msg) {
        // LOG((String) msg);
    }

    @Override
    public void logInfo(Object msg, Throwable thrown) { // TODO: should I make a LOG method in AbstractCommonObject to take Throwable?
        // LOG((String) msg, thrown.getMessage());
    }

    @Override
    public void logTrace(Object msg) {
        // TODO: implement
        // throw error
    }

    @Override
    public void logTrace(Object msg, Throwable thrown) {
        // TODO: implement
    }

    @Override
    public void logWarn(Object msg) {
        // TODO: implement
    }

    @Override
    public void logWarn(Object msg, Throwable thrown) {
        // TODO: implement
    }

    //////////////////////////////////////////////
    ///////// ICommonLogger logger Overrides /////
    //////////////////////////////////////////////

    @Override
    protected void abstractDebug(String... args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'abstractDebug'");
    }

    @Override
    protected void abstractInfo(String... args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'abstractInfo'");
    }

    @Override
    protected void abstractWarn(String... args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'abstractWarn'");
    }

    @Override
    protected void abstractError(String... args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'abstractError'");
    }

    @Override
    protected void abstractError(Throwable throwable, String... args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'abstractError'");
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
