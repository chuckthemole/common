package com.rumpus.common.Model;

import java.util.UUID;

public class TestModel extends AbstractModel<TestModel, java.util.UUID> {

    private static IModelIdManager<UUID> idManager;

    static {
        idManager = new SqlIdManager();
    }

    public TestModel() {
        this.generateId();
    }

    @Override
    public IModelIdManager<java.util.UUID> getIdManager() {
        return TestModel.idManager;
    }

    @Override
    public int compareTo(TestModel o) {
        return this.getId().toString().compareTo(o.getId().toString());
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}

