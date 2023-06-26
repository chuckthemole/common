package com.rumpus.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.mysql.cj.exceptions.ExceptionInterceptor;
// import java.sql.Blob;
import com.mysql.cj.jdbc.Blob;
import com.mysql.cj.log.Log;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class BlobHandler extends Rumpus {

    private static Object deserialize(InputStream stream) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(stream);
        try {
            return ois.readObject();
        } finally {
            ois.close();
        }
    }

    private static byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.close();
        return baos.toByteArray();
    }

    @SuppressWarnings(UNCHECKED)
    private static List<Map<String, String>> getParams(Blob blob) throws Exception {
        return (List<Map<String, String>>) deserialize(blob.getBinaryStream());
    }

    public static Blob updatedParamField(Blob blob, String paramName, String fieldName, String value)
            throws Exception {

        List<Map<String, String>> params = getParams(blob);
        Map<String, String> param = getParam(params, paramName);
        param.put(fieldName, value);
        // oracle.sql.BLOB res = oracle.sql.BLOB.createTemporary(blob.getOracleConnection(), true, oracle.sql.BLOB.DURATION_CALL);
        ExceptionInterceptor interceptor = new ExceptionInterceptor() {

            @Override
            public ExceptionInterceptor init(Properties props, Log log) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'init'");
            }

            @Override
            public void destroy() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'destroy'");
            }

            @Override
            public Exception interceptException(Exception sqlEx) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'interceptException'");
            }
            
        };
        return new Blob(serialize(params), interceptor);
        // res.setBytes(1, serialize(params));
        // return res;
    }

    // public static void updateParamField(oracle.sql.BLOB[] blobs, String paramName, String fieldName, String value)
    //         throws Exception {

    //     oracle.sql.BLOB blob = blobs[0];
    //     List<Map<String, String>> params = getParams(blob);
    //     Map<String, String> param = getParam(params, paramName);
    //     param.put(fieldName, value);
    //     blob.truncate(0);
    //     blob.setBytes(1, serialize(params));
    // }

    private static Map<String, String> getParam(List<Map<String, String>> params, String name) {
        for (Map<String, String> param : params) {
            if (name.equals(param.get("name"))) {
                return param;
            }
        }
        return null;
    }

    // public static String getParamField(oracle.sql.BLOB blob, String paramName, String fieldName) throws Exception {
    //     Map<String, String> param = getParam(getParams(blob), paramName);
    //     return param == null ? null : param.get(fieldName);
    // }

}
