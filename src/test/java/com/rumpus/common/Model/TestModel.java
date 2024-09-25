package com.rumpus.common.Model;

import java.io.IOException;

import com.google.gson.stream.JsonReader;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

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
    public TypeAdapter<TestModel> createTypeAdapter() {
        // Provide an appropriate TypeAdapter implementation for TestModel
        return new TypeAdapter<TestModel>() {
            @Override
            public void write(JsonWriter out, TestModel value) throws IOException {
                out.beginObject();
                out.name("id").value(value.getId().toString());
                out.endObject();
            }

            @Override
            public TestModel read(JsonReader in) throws IOException {
                in.beginObject();
                String id = null;
                while (in.hasNext()) {
                    String name = in.nextName();
                    if (name.equals("id")) {
                        id = in.nextString();
                    }
                }
                in.endObject();
                TestModel model = new TestModel("Test Model");
                model.setId(java.util.UUID.fromString(id));
                return model;
            }
        };
    }

    @Override
    public int compareTo(TestModel o) {
        return this.getId().toString().compareTo(o.getId().toString());
    }
}

