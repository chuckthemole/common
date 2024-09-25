package com.rumpus.common.Model;

import java.util.UUID;

public class SqlIdManager implements IModelIdManager<java.util.UUID> {

    @Override
    public UUID generateId() {
        return UUID.randomUUID(); // Generate a UUID for SQL-based DBs
    }

    @Override
    public boolean validateId(Object id) {
        return id instanceof UUID;
    }
}

