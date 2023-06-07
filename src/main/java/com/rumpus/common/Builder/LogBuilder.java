package com.rumpus.common.Builder;

public class LogBuilder extends Builder {

    public static final String NAME = "LogBuilder";

    public LogBuilder(String... args) {
        super(NAME, args);
    }

    // TODO error, warn, etc
    public void info() {
        LOG.info(this.toString());
    }
}
