package com.rumpus.common.Log.LogItem;

import java.util.HashSet;
import java.util.Set;

import com.rumpus.common.Manager.IManageable;
import com.rumpus.common.Model.AbstractModelsCollection;

public class LogItemCollection extends AbstractModelsCollection<LogItem, Set<LogItem>> implements IManageable {
    public LogItemCollection() {
        super(new HashSet<>());
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
