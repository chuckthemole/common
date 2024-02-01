package com.rumpus.common.Log;

import com.mysql.cj.log.Log;
import com.rumpus.common.AbstractCommonObject;

/**
 * CommonLog implementation with infoEnabled set to true on construction
 */
public class CommonLog extends AbstractCommonObject implements Log {

    private static final String NAME = "CommonLog";

    private boolean debugEnabled;
    private boolean errorEnabled;
    private boolean fatalEnabled;
    private boolean infoEnabled;
    private boolean traceEnabled;
    private boolean warnEnabled;

    public CommonLog() {
        super(NAME);
        this.debugEnabled = false;
        this.errorEnabled = false;
        this.fatalEnabled = false;
        this.infoEnabled = true;
        this.traceEnabled = false;
        this.warnEnabled = false;
    }

    public CommonLog(Log log) {
        super(NAME);
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
        LOG.debug((String) msg);
    }

    @Override
    public void logDebug(Object msg, Throwable thrown) {
        LOG.debug((String) msg, thrown);
    }

    @Override
    public void logError(Object msg) {
        LOG.error((String) msg);
    }

    @Override
    public void logError(Object msg, Throwable thrown) {
        LOG.error((String) msg, thrown);
    }

    @Override
    public void logFatal(Object msg) {
        LOG.error((String) msg);
    }

    @Override
    public void logFatal(Object msg, Throwable thrown) {
        LOG.error((String) msg, thrown);
    }

    @Override
    public void logInfo(Object msg) {
        LOG((String) msg);
    }

    @Override
    public void logInfo(Object msg, Throwable thrown) { // TODO: should I make a LOG method in AbstractCommonObject to take Throwable?
        LOG((String) msg, thrown.getMessage());
    }

    @Override
    public void logTrace(Object msg) {
        LOG.trace((String) msg);
    }

    @Override
    public void logTrace(Object msg, Throwable thrown) {
        LOG.trace((String) msg, thrown);
    }

    @Override
    public void logWarn(Object msg) {
        LOG.warn((String) msg);
    }

    @Override
    public void logWarn(Object msg, Throwable thrown) {
        LOG.warn((String) msg, thrown);
    }
    
}
