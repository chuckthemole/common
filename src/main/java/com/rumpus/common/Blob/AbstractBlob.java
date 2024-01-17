package com.rumpus.common.Blob;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Model.AbstractMetaData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.SQLException;

// started here for inspo
// https://stackoverflow.com/questions/14768439/deserialize-java-object-from-a-blob/17840811#17840811

/**
 * Abstract helper class for blob handling (serialization).
 * Keeping the member variable blob here for now to be able to subclass if needed
 */
public abstract class AbstractBlob<META extends AbstractMetaData<?>> extends AbstractCommonObject implements Blob {

    protected Blob blob;
    private static final com.rumpus.common.Logger.ICommonLogger LOG = com.rumpus.common.Logger.CommonLogger.createLogger(AbstractBlob.class);

    public AbstractBlob(final String name, Blob blob) {
        super(name);
        this.blob = blob;
    }

    /**
     * 
     * @param blob
     * @param paramName
     * @param fieldName
     * @param value
     * @return
     */
    abstract public Blob updatedParamField(Blob blob, String paramName, String fieldName, String value);

    /**
     * 
     * @param blob
     */
    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    /**
     * 
     * @return
     */
    public Blob getBlob() {
        return this.blob;
    }

    /**
     * public static method for serilizing AbstractMetaData object
     * 
     * @param <META> AbstractMetaData<?>
     * @param object object to serialize
     * @return byte array
     */
    public static <META extends AbstractMetaData<?>> byte[] serialize(META object) {
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

    /**
     * public static method for obtaining AbstractMetaData object from Blob.
     * 
     * @param <META> AbstractMetaData<?>
     * @param blob to deserialize and cast to META object
     * @return META object
     */
    public static <META extends AbstractMetaData<?>> META getObjectFromBlob(Blob blob) {
        if(blob == null) {
            LOG.info("Error: the given blob has a value of null. Returning null.");
            return null;
        }

        LOG.info("Provided blob is not null, continuing...");
        try {
            META object = deserialize(blob.getBinaryStream());
            if(object == null) {
                LOG.info("Error: deserializing params from blob.");
                return null;
            }
            LogBuilder.logBuilderFromStringArgs("Blob deserialized, returning object.").info();
            return object;
        } catch (SQLException e) {
            LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace());
        } catch (Exception e) {
            LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace());
        }
        LogBuilder.logBuilderFromStringArgs("Returning empty object...");
        return null;
    }

    @SuppressWarnings(UNCHECKED)
    private static <META extends AbstractMetaData<?>> META deserialize(InputStream stream) {
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
                    return (META) ois.readObject();
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
}
