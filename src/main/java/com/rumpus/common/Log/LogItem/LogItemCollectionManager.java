package com.rumpus.common.Log.LogItem;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.Manager.AbstractCommonManager;

public class LogItemCollectionManager extends AbstractCommonManager<String, LogItemCollection> {

    private static final String MAIN_LOG = "MAIN_LOG";
    private static final String ADMIN_LOG = "ADMIN_LOG";

    private LogItemCollectionManager() {
        super(false);
    }

    public static LogItemCollectionManager create() {
        return new LogItemCollectionManager();
    }

    public static LogItemCollectionManager createWithMainAndAdmin() {
        LogItemCollectionManager manager = LogItemCollectionManager.create();
        manager.addLogCollection(MAIN_LOG, new LogItemCollection());
        manager.addLogCollection(ADMIN_LOG, new LogItemCollection());
        return manager;
    }

    public LogItemCollection addLogCollection(String name, LogItemCollection collection) {
        return this.put(name, collection);
    }
    
    public LogItemCollection removeLogCollection(String name) {
        return this.remove(name);
    }

    /**
     * Log a {@link LogItem} to the appropriate {@link LogItemCollection} hashing using LogItem's logName
     * 
     * @param logItem item to log
     */
    public void log(LogItem logItem) {
        if(logItem != null) {
            LogItemCollection logCollection = this.get(logItem.getLogName());
            if(logCollection != null) {
                final String log = LogBuilder.logBuilderFromStringArgs(
                    "Adding LogItem into LogCollection '",
                    logItem.getLogName(),
                    "'").toString();
                LOG(log);
                logCollection.add(logItem);
            } else {
                final String log = LogBuilder.logBuilderFromStringArgs(
                    "No LogCollection exists with name '",
                    logItem.getLogName(),
                    "'").toString();
                LOG(LogLevel.ERROR, log);
            }
        } else {
            final String log = LogBuilder.logBuilderFromStringArgs("LogItem is null").toString();
            LOG(LogLevel.ERROR, log);
        }
    }

    @Override
    public LogItemCollection createEmptyManagee() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createEmptyManagee'");
    }

    @Override
    public LogItemCollection createEmptyManagee(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createEmptyManagee'");
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
