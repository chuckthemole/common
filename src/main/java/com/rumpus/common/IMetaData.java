package com.rumpus.common;

import java.time.Instant;
import java.util.Map;

import org.springframework.jdbc.core.support.SqlLobValue;

import com.google.gson.TypeAdapter;

public interface IMetaData<MODEL extends Model<MODEL>> extends IRumpusObject {
    Instant getCreationTime();
    void setCreationTime(Instant time);
    String getStandardFormattedCreationTime();
}