package com.rumpus.common;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import com.google.gson.TypeAdapter;

/**
 * Abstract class for Model meta data. This holds some of the common member variables, like creationTime, and interface that each Model shares.
 * 
 * TODO: comment more on getLobValue and its use for serialization.
 */
public abstract class MetaData<MODEL extends Model<MODEL>> extends Model<MODEL> implements IMetaData<MODEL> {

    // private byte[] data = null;
    private Instant creationTime;
    public static final String USER_CREATION_DATE_TIME = "user_creation_datetime";
    // private TypeAdapter<?> typeAdapter = null;

    public MetaData(String name) {
        super(name);
        this.creationTime = Instant.now();
    }

    // @Override
    // public void setData(byte[] data) {
    //     this.data = data;
    // }

    // @Override
    // public byte[] getData() {
    //     if(this.data == null) {
    //         LOG.info("Error: Cannot get data because it has not been set.");
    //         return new byte[0]; // TODO: returning empty for now. Maybe return null
    //     }
    //     return this.data;
    // }

    @Override
    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public Instant getCreationTime() {
        return this.creationTime;
    }

    // @Override
    // public void setTypeAdapter(TypeAdapter<?> typeAdapter) {
    //     this.typeAdapter = typeAdapter;
    // }

    // @Override
    // public TypeAdapter<?> getTypeAdapter() {
    //     if(this.typeAdapter != null) {
    //         return this.typeAdapter;
    //     }
    //     LOG.info("Error: TypeAdapter is not set. Returning nulll...");
    //     return null;
    // }

    // @Override
    // public SqlLobValue getLobValue() {
    //     ByteArrayInputStream inputStrm = new ByteArrayInputStream(data);
    //     LobHandler handler = new DefaultLobHandler();
    //     SqlLobValue sqlLobValue = new SqlLobValue(inputStrm, data.length, handler);
    //     return sqlLobValue;
    // }

    /**
     * 
     * @return LocalDateTime object to string using ZoneOffset.UTC <- TODO look into this more. may have to come up with standards for time.
     */
    @Override
    public final String getStandardFormattedCreationTime() {
        return LocalDateTime.ofInstant(this.creationTime, ZoneOffset.UTC).toString();
    }

    // public void test() {
    //     SqlLobValue v = new SqlLobValue(new byte[2]);
    //     MapSqlParameterSource in = new MapSqlParameterSource();
    //     in.addValue("image",  new SqlLobValue(new ByteArrayInputStream(data), data.length, new DefaultLobHandler()), Types.BLOB);
    // }
}
