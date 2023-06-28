package com.rumpus.common.Blob;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Builder.LogBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

// started here for inspo
// https://stackoverflow.com/questions/14768439/deserialize-java-object-from-a-blob/17840811#17840811

/**
 * Abstract helper class for blob handling (serialization). 
 * Keeping the member variable blob here for now to be able to subclass if needed
 */
public abstract class AbstractBlob extends AbstractCommonObject implements Blob {

    protected Blob blob;

    public AbstractBlob(final String name, Blob blob) {
        super(name);
        this.blob = blob;
    }

    abstract public Blob updatedParamField(Blob blob, String paramName, String fieldName, String value);

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public Blob getBlob() {
        return this.blob;
    }

    // TODO: think about access for deserialize and serialize. 
    // maybe serialize is used to return this blob
    // deserialize is used in getParams right now, maybe just have getParams public?
    
    protected static Object deserialize(InputStream stream) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(stream);
        } catch (IOException e) {
            LOG.error("-- AbstractBlob::deserialize() IOException 1 --");
            LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).error();
            return null;
        }
        if(ois != null) {
            try {
                try {
                    return ois.readObject();
                } catch (ClassNotFoundException e) {
                    LOG.error("-- AbstractBlob::deserialize() ClassNotFoundException --");
                    LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).error();
                    return null;
                } catch (IOException e) {
                    LOG.error("-- AbstractBlob::deserialize() IOException 2 --");
                    LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).error();
                    return null;
                }
            } finally { // TODO: clean this up with my returns
                try {
                    ois.close();
                } catch (IOException e) {
                    LOG.error("-- AbstractBlob::deserialize() IOException 3 --");
                    LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).error();
                    return null;
                }
            }
        }
        return null;
    }

    public static byte[] serialize(Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
        } catch (IOException e) {
            LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace());
        }
        if(oos != null) {
            try {
                oos.writeObject(object);
            } catch (IOException e) {
                LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace());
            }
            try {
                oos.close();
            } catch (IOException e) {
                LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace());
            }
        }
        return baos.toByteArray();
    }

    @SuppressWarnings(UNCHECKED)
    public static List<Map<String, String>> getParams(Blob blob) {
        if(blob == null) {
            LOG.info("Error: the given blob has a value of null. Returning empty list.");
            return List.of();
        }

        LOG.info("Provided blob is not null, continuing...");
        try {
            List<Map<String, String>> list = (List<Map<String, String>>) deserialize(blob.getBinaryStream());
            if(list == null || list.isEmpty()) {
                LOG.info("Error: deserializing params from blob.");
                return List.of();
            }
            LogBuilder.logBuilderFromStringArgs("Deserialized blob, list size: ", String.valueOf(list.size())).info();
            return list;
        } catch (SQLException e) {
            LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace());
        } catch (Exception e) {
            LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace());
        }
        LogBuilder.logBuilderFromStringArgs("Returning empty list...");
        return List.of();
    }

    /**
     * TODO: look at this more. have not touched yet. 
     * @param params
     * @param name
     * @return
     */
    protected static Map<String, String> getParam(List<Map<String, String>> params, String name) {
        for (Map<String, String> param : params) {
            if (name.equals(param.get("name"))) {
                return param;
            }
        }
        return Map.of();
    }
}
