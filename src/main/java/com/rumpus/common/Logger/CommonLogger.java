package com.rumpus.common.Logger;

/**
 * Implementation of the ICommonLogger interface.
 */
public class CommonLogger extends AbstractCommonLogger {

    private static final String NAME = "CommonLogger";

    private CommonLogger(Class<?> clazz) {
        super(NAME, clazz);
    }

    public static ICommonLogger createEmptyLogger() {
        return new CommonLogger(com.rumpus.common.AbstractCommon.DEFAULT_LOGGER_CLASS);
    }

    public static ICommonLogger createLogger(Class<?> clazz) {
        return new CommonLogger(clazz);
    }
}
