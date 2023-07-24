package com.rumpus.common.Log;

import java.util.HashMap;
import java.util.Map;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Builder.LogBuilder;

public class LogManager extends AbstractCommonObject {

    private final static String NAME = "LogManager";
    private Map<String, LogCollection> logManager;

    private LogManager() {
        super(NAME);
        this.logManager = new HashMap<>();
    }

    public static LogManager create() {
        return new LogManager();
    }

    public LogCollection addLogCollection(String name, LogCollection collection) {
        return this.logManager.put(name, collection);
    }
    
    public LogCollection removeLogCollection(String name) {
        return this.logManager.remove(name);
    }

    /**
     * Log a {@link LogItem} to the appropriate {@link LogCollection} hashing using LogItem's logName
     * 
     * @param logItem item to log
     */
    public void log(LogItem logItem) {
        if(logItem != null) {
            LogCollection logCollection = this.logManager.get(logItem.getLogName());
            if(logCollection != null) {
                logCollection.add(logItem);
            } else {
                LogBuilder.logBuilderFromStringArgs("No LogCollection exists with name '", logItem.getLogName(), "'").error();
            }
        } else {
            LogBuilder.logBuilderFromStringArgs("LogItem is null").error();
        }
    }
}
