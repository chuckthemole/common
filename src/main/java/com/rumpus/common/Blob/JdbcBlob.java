package com.rumpus.common.Blob;

import java.util.Properties;

import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.jdbc.Blob;
import com.rumpus.common.Log.CommonExceptionInterceptor;
import com.rumpus.common.Log.CommonLog;

/**
 * JdbcBlob class extends AbstractBlob to provide functionality for handling
 * SQL BLOB types using the MySQL JDBC Blob implementation.
 */
final public class JdbcBlob extends AbstractBlob {

    private static final String NAME = "JdbcBlob";

    // Constructors
    private JdbcBlob(byte[] data, ExceptionInterceptor exceptionInterceptor) {
        super(NAME, new Blob(data, exceptionInterceptor));
    }

    private JdbcBlob(byte[] data) {
        this(data, new CommonExceptionInterceptor().init(new Properties(), new CommonLog()));
    }

    // Public factory methods
    public static JdbcBlob createFromByteArray(byte[] data) {
        return new JdbcBlob(data);
    }

    public static JdbcBlob createFromByteArrayAndInterceptor(byte[] data, ExceptionInterceptor exceptionInterceptor) {
        return new JdbcBlob(data, exceptionInterceptor);
    }

    public static JdbcBlob createEmptyBlob() {
        return new JdbcBlob(new byte[0]);
    }

    public static JdbcBlob createEmptyBlobWithInterceptor(ExceptionInterceptor exceptionInterceptor) {
        return new JdbcBlob(new byte[0], exceptionInterceptor);
    }

    @Override
    public void initBlob() {
        this.blob = new Blob(new byte[0], new CommonExceptionInterceptor().init(new Properties(), new CommonLog()));
    }
}
