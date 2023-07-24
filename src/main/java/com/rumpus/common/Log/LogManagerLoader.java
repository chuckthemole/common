package com.rumpus.common.Log;

/**
 * Used to create a {@link LogManager} using public static methods. 
 */
public class LogManagerLoader {

    private static final String MAIN_LOG = "MAIN_LOG";
    private static final String ADMIN_LOG = "ADMIN_LOG";

    public static LogManager getDefaultLogManager() {
        LogManager manager = LogManager.create();
        manager.addLogCollection(MAIN_LOG, new LogCollection());
        manager.addLogCollection(ADMIN_LOG, new LogCollection());
        return manager;
    }
}
