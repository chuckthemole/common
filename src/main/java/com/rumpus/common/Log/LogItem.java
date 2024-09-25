package com.rumpus.common.Log;

import java.io.IOException;
import java.io.OutputStream;

import com.google.gson.TypeAdapter;
import com.rumpus.common.Builder.CommonStringBuilder;
import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Model.IModelIdManager;

public class LogItem extends AbstractModel<LogItem, java.util.UUID> {

    private String logName;
    private String time;
    private String username;
    private String userId;
    private String action;

    private LogItem(String logName, String time, String username, String userId, String action) {
        super("LogItem");
        this.logName = logName;
        this.time = time;
        this.username = username;
        this.userId = userId;
        this.action = action;
    }

    public static LogItem create(String logName, String time, String username, String userId, String action) {
        return new LogItem(logName, time, username, userId, action);
    }

    public String getLogName() {
        return this.logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public void serialize(LogItem object, OutputStream outputStream) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'serialize'");
    }
    
    // TODO: look at this createTypeAdapter method more. - chuck
    @Override
    public TypeAdapter<LogItem> createTypeAdapter() {
        return new TypeAdapter<LogItem>() {
            @Override
            public void write(com.google.gson.stream.JsonWriter out, LogItem value) throws IOException {
                out.beginObject();
                out.name("logName");
                out.value(value.getLogName());
                out.name("time");
                out.value(value.getTime());
                out.name("username");
                out.value(value.getUsername());
                out.name("userId");
                out.value(value.getUserId());
                out.name("action");
                out.value(value.getAction());
                out.endObject();
            }

            @Override
            public LogItem read(com.google.gson.stream.JsonReader in) throws IOException {
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
        };
    }

    @Override
    public String toString() {
        return CommonStringBuilder.buildString(
            "username: ", this.username,
            " user id: ", this.userId,
            " action: ", this.action,
            " time: ", this.time);
    }

    @Override
    public int compareTo(LogItem o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }

    @Override
    public IModelIdManager getIdManager() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdManager'");
    }
}
