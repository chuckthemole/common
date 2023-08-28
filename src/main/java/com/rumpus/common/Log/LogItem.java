package com.rumpus.common.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.google.gson.TypeAdapter;
import com.rumpus.common.Builder.CommonStringBuilder;
import com.rumpus.common.Model.AbstractModel;

public class LogItem extends AbstractModel<LogItem> {

    private String logName;
    private String time;
    private String username;
    private String userId;
    private String action;

    private LogItem(String logName, String time, String username, String userId, String action) {
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
    @Override
    public TypeAdapter<LogItem> createTypeAdapter() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTypeAdapter'");
    }
    @Override
    public Map<String, Object> getModelAttributesMap() {
        return Map.of(
            "logName", this.logName,
            "time", this.time,
            "username", this.username,
            "userId", this.userId,
            "action", this.action);
    }

    @Override
    public String toString() {
        return CommonStringBuilder.buildString(
            "username: ", this.username,
            " user id: ", this.userId,
            " action: ", this.action,
            " time: ", this.time);
    }
}
