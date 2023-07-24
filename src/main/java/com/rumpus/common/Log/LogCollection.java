package com.rumpus.common.Log;

import java.util.HashSet;
import java.util.Set;

import com.rumpus.common.Model.AbstractModelsCollection;

public class LogCollection extends AbstractModelsCollection<LogItem, Set<LogItem>> {

    public LogCollection() {
        super(new HashSet<>());
    }
}
