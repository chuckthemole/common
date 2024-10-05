package com.rumpus.common.Model;

public class TestModel extends AbstractModel<TestModel, java.util.UUID> {

    private static IModelIdManager<java.util.UUID> idManager;

    static {
        idManager = new SqlIdManager();
    }

    public TestModel(String name) {
        super(name);
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
}

