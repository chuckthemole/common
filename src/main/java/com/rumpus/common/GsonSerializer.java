package com.rumpus.common;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public abstract class GsonSerializer<T extends Model<?>> implements JsonSerializer<T> {

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObj = new JsonObject();
        for(Map.Entry<String, Object> entry : src.getModelAttributesMap().entrySet()) {
            jsonObj.addProperty(entry.getKey(), entry.getValue().toString());
        }
        return jsonObj;
    }
}