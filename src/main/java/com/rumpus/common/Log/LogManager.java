package com.rumpus.common.Log;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Manager.AbstractCommonManager;

public class LogManager extends AbstractCommonManager<LogCollection> {

    private final static String NAME = "LogManager";

    private LogManager() {
        super(NAME, false);
    }

    public static LogManager create() {
        return new LogManager();
    }

    public LogCollection addLogCollection(String name, LogCollection collection) {
        return this.put(name, collection);
    }
    
    public LogCollection removeLogCollection(String name) {
        return this.remove(name);
    }

    /**
     * Log a {@link LogItem} to the appropriate {@link LogCollection} hashing using LogItem's logName
     * 
     * @param logItem item to log
     */
    public void log(LogItem logItem) {
        if(logItem != null) {
            LogCollection logCollection = this.get(logItem.getLogName());
            if(logCollection != null) {
                LogBuilder.logBuilderFromStringArgs("Adding LogItem into LogCollection '", logItem.getLogName(), "'").info();
                logCollection.add(logItem);
            } else {
                LogBuilder.logBuilderFromStringArgs("No LogCollection exists with name '", logItem.getLogName(), "'").error();
            }
        } else {
            LogBuilder.logBuilderFromStringArgs("LogItem is null").error();
        }
    }

    @Override
    public LogCollection createEmptyManagee() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createEmptyManagee'");
    }

    @Override
    public LogCollection createEmptyManagee(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createEmptyManagee'");
    }
}
