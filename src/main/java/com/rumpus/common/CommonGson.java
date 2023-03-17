package com.rumpus.common;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rumpus.common.util.Pair;

abstract public class CommonGson extends Rumpus {

    protected static GsonBuilder gsonBuilder;

    static {
        CommonGson.gsonBuilder = new GsonBuilder();
    }

    // public int register(Type type, Object object) {
    //     CommonGson.gsonBuilder.registerTypeAdapter(type, object);
    //     return SUCCESS;
    // }

    // public int register(List<Pair<Type, Object>> items) {
    //     for(Pair<Type, Object> item : items) {
    //         this.register(item.getFirst(), item.getSecond());
    //     }
    //     return SUCCESS;
    // }

    public Gson getGson() {
        return CommonGson.gsonBuilder.create();
    }
    
}
