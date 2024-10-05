package com.rumpus.common.Model;

import java.io.IOException;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class TestModelSerializer extends AbstractModelSerializer<TestModel> {

    private static final String NAME = "TestModelSerializer";

    public TestModelSerializer() {
        super(NAME, SerializationType.JSON);
    }

    @Override
    protected void writeJson(JsonWriter out, TestModel object) throws IOException {
        out.beginObject();
        out.name("id").value(object.getId().toString());
        out.endObject();
    }

    @Override
    protected TestModel readJson(JsonReader in) throws IOException {
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
    
}
