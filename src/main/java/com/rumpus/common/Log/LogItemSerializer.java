package com.rumpus.common.Log;

import java.io.IOException;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.Model.AbstractModelSerializer;

public class LogItemSerializer extends AbstractModelSerializer<LogItem> {

    private static final String NAME = "LogItemSerializer";

    public LogItemSerializer() {
        super(NAME, SerializationType.JSON);
    }

    @Override
    public void writeJson(JsonWriter out, LogItem object) throws IOException {
        out.beginObject();
        out.name("logName");
        out.value(object.getLogName());
        out.name("time");
        out.value(object.getTime());
        out.name("username");
        out.value(object.getUsername());
        out.name("userId");
        out.value(object.getUserId());
        out.name("action");
        out.value(object.getAction());
        out.endObject();
    }

    @Override
    public LogItem readJson(JsonReader in) throws IOException {
        in.beginObject();
        String logName = null;
        String time = null;
        String username = null;
        String userId = null;
        String action = null;
        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("logName")) {
                logName = in.nextString();
            }
            if (name.equals("time")) {
                time = in.nextString();
            }
            if (name.equals("username")) {
                username = in.nextString();
            }
            if (name.equals("userId")) {
                userId = in.nextString();
            }
            if (name.equals("action")) {
                action = in.nextString();
            }
        }
        in.endObject();
        return LogItem.create(logName, time, username, userId, action);
    }
}
