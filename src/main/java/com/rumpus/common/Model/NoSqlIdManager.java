package com.rumpus.common.Model;

public class NoSqlIdManager implements IModelIdManager<String> {

    @Override
    public String generateId() {
        return java.util.UUID.randomUUID().toString(); // Generate a UUID for NoSQL-based DBs
        // return org.bson.types.ObjectId.get().toString(); // Example for MongoDB ObjectId
    }

    @Override
    public boolean validateId(Object id) {
        return id instanceof String; // Assuming String IDs for NoSQL
    }
}

