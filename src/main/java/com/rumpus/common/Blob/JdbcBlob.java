package com.rumpus.common.Blob;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.jdbc.Blob;
import com.rumpus.common.CommonExceptionInterceptor;

public class JdbcBlob extends AbstractBlob {

    private static final String NAME = "JdbcBlob";
    private ExceptionInterceptor exceptionInterceptor = new CommonExceptionInterceptor();

    public JdbcBlob(byte[] data) {
        super(NAME, new Blob(data, new CommonExceptionInterceptor().init(new Properties(), null)));
    }

    public JdbcBlob(byte[] data, ExceptionInterceptor exceptionInterceptor) {
        super(NAME, new Blob(data, exceptionInterceptor));
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
        
        List<Map<String, String>> params = getParams(blob);
        Map<String, String> param = getParam(params, paramName);
        param.put(fieldName, value);
        
        // oracle.sql.BLOB res = oracle.sql.BLOB.createTemporary(blob.getOracleConnection(), true, oracle.sql.BLOB.DURATION_CALL);
        // ExceptionInterceptor interceptor = new ExceptionInterceptor() {

        //     @Override
        //     public ExceptionInterceptor init(Properties props, Log log) {
        //         // TODO Auto-generated method stub
        //         throw new UnsupportedOperationException("Unimplemented method 'init'");
        //     }

        //     @Override
        //     public void destroy() {
        //         // TODO Auto-generated method stub
        //         throw new UnsupportedOperationException("Unimplemented method 'destroy'");
        //     }

        //     @Override
        //     public Exception interceptException(Exception sqlEx) {
        //         // TODO Auto-generated method stub
        //         throw new UnsupportedOperationException("Unimplemented method 'interceptException'");
        //     }
            
        // };
        ExceptionInterceptor exceptionInterceptor = new CommonExceptionInterceptor().init(new Properties(), null);
        return new Blob(serialize(params), exceptionInterceptor);
        // return null;
        // res.setBytes(1, serialize(params));
        // return res;
    }
    
}
