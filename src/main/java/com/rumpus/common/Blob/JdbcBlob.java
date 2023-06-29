package com.rumpus.common.Blob;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.jdbc.Blob;
import com.rumpus.common.Log.CommonExceptionInterceptor;
import com.rumpus.common.Log.CommonLog;
import com.rumpus.common.Model.AbstractMetaData;

public class JdbcBlob<META extends AbstractMetaData<?>> extends AbstractBlob<META> {

    private static final String NAME = "JdbcBlob";

    // ctors
    private JdbcBlob(byte[] data) {
        super(NAME, new Blob(data, new CommonExceptionInterceptor().init(new Properties(), new CommonLog())));
    }
    private JdbcBlob(byte[] data, ExceptionInterceptor exceptionInterceptor) {
        super(NAME, new Blob(data, exceptionInterceptor));
    }

    // public static factory
    public static <META extends AbstractMetaData<?>> JdbcBlob<META> createFromByteArray(byte[] data) {
        return new JdbcBlob<>(data);
    }
    public static <META extends AbstractMetaData<?>> JdbcBlob<META> createFromByteArrayAndInterceptor(byte[] data, ExceptionInterceptor exceptionInterceptor) {
        return new JdbcBlob<>(data, exceptionInterceptor);
    }

    @Override
    public long length() throws SQLException {
        return this.blob.length();
    }

    @Override
    public byte[] getBytes(long pos, int length) throws SQLException {
        return this.blob.getBytes(pos, length);
    }

    @Override
    public InputStream getBinaryStream() throws SQLException {
        return this.blob.getBinaryStream();
    }

    @Override
    public long position(byte[] pattern, long start) throws SQLException {
        return this.blob.position(pattern, start);
    }

    @Override
    public long position(java.sql.Blob pattern, long start) throws SQLException {
        return this.blob.position(pattern, start);
    }

    @Override
    public int setBytes(long pos, byte[] bytes) throws SQLException {
        return this.blob.setBytes(pos, bytes);
    }

    @Override
    public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {
        return this.blob.setBytes(pos, bytes, offset, len);
    }

    @Override
    public OutputStream setBinaryStream(long pos) throws SQLException {
        return this.blob.setBinaryStream(pos);
    }

    @Override
    public void truncate(long len) throws SQLException {
        this.blob.truncate(len);
    }

    @Override
    public void free() throws SQLException {
        this.blob.free();
    }

    @Override
    public InputStream getBinaryStream(long pos, long length) throws SQLException {
        return this.blob.getBinaryStream(pos, length);
    }

    @Override
    public java.sql.Blob updatedParamField(java.sql.Blob blob, String paramName, String fieldName, String value) {
        @SuppressWarnings(UNCHECKED) META meta = (META) AbstractBlob.getObjectFromBlob(blob);
        // TODO: need to update the param. have not implemented a solution for this yet. this is just returning the same blob right now.
        ExceptionInterceptor exceptionInterceptor = new CommonExceptionInterceptor().init(new Properties(), new CommonLog());
        return new Blob(AbstractBlob.serialize(meta), exceptionInterceptor);
    }
    
}
