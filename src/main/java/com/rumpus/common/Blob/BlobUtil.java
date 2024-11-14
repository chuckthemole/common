package com.rumpus.common.Blob;

import com.rumpus.common.ICommon;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.Model.AbstractMetaData;

import java.sql.Blob;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

final public class BlobUtil implements ICommon {
    /**
     * Serializes an AbstractMetaData object into a byte array.
     *
     * @param object The object to serialize.
     * @param <META> The type of the object extending AbstractMetaData.
     * @return A byte array representing the serialized object.
     */
    public static <META extends AbstractMetaData<META>> byte[] serialize(META object) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new ProcessingException("Failed to serialize object", e);
        }
    }

    /**
     * Deserializes an AbstractMetaData object from a Blob.
     *
     * @param blob The Blob object to deserialize from.
     * @param <META> The type of the object extending AbstractMetaData.
     * @return An Optional containing the deserialized object, or an empty Optional if an error occurs.
     */
    public static <META extends AbstractMetaData<META>> Optional<META> getObjectFromBlob(Blob blob) {
        LOG_THIS("Attempting to deserialize Blob into META object.");

        if (blob == null) {
            LOG_THIS(LogLevel.ERROR, "Blob is null. Returning empty Optional.");
            // return an Optional with an empty Blob object
            return Optional.empty();
        }

        try {
            if (blob.length() == 0) {
                LOG_THIS(LogLevel.ERROR, "Blob length is 0. Returning empty Optional.");
                return Optional.empty();
            }

            try (InputStream binaryStream = blob.getBinaryStream()) {
                java.io.ObjectInputStream is = new ObjectInputStream(binaryStream);
                return Optional.ofNullable(BlobUtil.<META>deserialize(binaryStream));
            }
        } catch (SQLException | IOException e) {
            throw new ProcessingException("Error while reading Blob", e);
        } finally {
            // Call blob.free() if applicable to avoid memory issues.
            if (blob != null) {
                try {
                    blob.free();
                } catch (SQLException e) {
                    throw new ProcessingException("Failed to free Blob resource", e);
                }
            }
        }
    }

    public static Optional<ObjectInputStream> getObjectInputStream(Blob blob) {
        try {
            if (blob == null || blob.length() == 0) {
                LOG_THIS(LogLevel.ERROR, "Blob length is 0. Returning empty Optional.");
                return Optional.empty();
            }

            try (InputStream binaryStream = blob.getBinaryStream()) {
                return Optional.of(new ObjectInputStream(binaryStream));
            }
        } catch (SQLException | IOException e) {
            throw new ProcessingException("Error while reading Blob for getObjectInputStream", e);
        } finally {
            // Call blob.free() if applicable to avoid memory issues.
            if (blob != null) {
                try {
                    blob.free();
                } catch (SQLException e) {
                    throw new ProcessingException("Failed to free Blob resource", e);
                }
            }
        }
    } 

    /**
     * Deserializes an AbstractMetaData object from an InputStream.
     *
     * @param stream The InputStream to deserialize from.
     * @param <META> The type of the object extending AbstractMetaData.
     * @return An Optional containing the deserialized object, or an empty Optional if an error occurs.
     */
    private static <META extends AbstractMetaData<META>> META deserialize(InputStream stream) {
        try (ObjectInputStream ois = new ObjectInputStream(stream)) {
            return (META) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new ProcessingException("Failed to deserialize object", e);
        }
    }

    /**
     * Logs messages at default log level (INFO) for this class.
     *
     * @param args The message to log.
     */
    private static void LOG_THIS(String... args) {
        ICommon.LOG(BlobUtil.class, args);
    }

    /**
     * Logs messages at the specified log level for this class.
     *
     * @param level The log level to use.
     * @param args  The message to log.
     */
    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(BlobUtil.class, level, args);
    }
}
