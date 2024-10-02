package com.rumpus.common.Blob;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.ICommon;
import com.rumpus.common.Logger.AbstractCommonLogger.LogLevel;
import com.rumpus.common.Logger.CommonLogger;
import com.rumpus.common.Logger.ICommonLogger;

import java.sql.Blob;
import java.io.InputStream;
import java.sql.SQLException;
import java.io.OutputStream;
import java.sql.SQLFeatureNotSupportedException;

/**
 * Abstract helper class for handling BLOB serialization and deserialization in database contexts.
 * Provides methods for working with Java objects and SQL Blob types.
 * <p>
 * This class is designed to facilitate the conversion of Java objects to Blob types (serialization)
 * and the reverse operation (deserialization).
 */
public abstract class AbstractBlob extends AbstractCommonObject implements IBlob {

    protected Blob blob;
    private static final ICommonLogger LOG = CommonLogger.createLogger(AbstractBlob.class);

    /**
     * Constructs an AbstractBlob with a given name.
     *
     * @param name The name of the blob.
     */
    public AbstractBlob(final String name) {
        super(name);
        this.blob = null;
    }

    /**
     * Constructs an AbstractBlob with a given name and Blob.
     *
     * @param name The name of the blob.
     * @param blob The SQL Blob object.
     */
    public AbstractBlob(final String name, Blob blob) {
        super(name);
        this.blob = blob;
    }

    /**
     * Initialze the Blob object for this AbstractBlob.
     * <p>
     * This is needed if the Blob object is not set during construction.
     * 
     * @param blob The Blob object to set.
     */
    abstract public void initBlob();

    /**
     * Check if the Blob object is initialized.
     * 
     * @return true if the Blob object is initialized, false otherwise.
     */
    public boolean isInitialized() {
        return this.blob == null;
    }

    @Override
    public long length() throws SQLException {
        if(this.blob == null) {
            throw new ProcessingException("Blob object is null.");
        }
        return this.blob.length();
    }

    @Override
    public byte[] getBytes(long pos, int length) throws SQLException {
        if(this.blob == null) {
            throw new ProcessingException("Blob object is null.");
        }
        return this.blob.getBytes(pos, length);
    }

    @Override
    public InputStream getBinaryStream() throws SQLException {
        if(this.blob == null) {
            throw new ProcessingException("Blob object is null.");
        }
        return this.blob.getBinaryStream();
    }

    @Override
    public long position(byte[] pattern, long start) throws SQLException {
        if(this.blob == null) {
            throw new ProcessingException("Blob object is null.");
        }
        try {
            // Attempt to use the driver's implementation
            return this.blob.position(pattern, start);
        } catch (SQLFeatureNotSupportedException e) {
            LOG_THIS(LogLevel.ERROR, "Driver does not support position() for byte array. Using custom implementation.");

            // Fallback to custom implementation
            return positionFallback(pattern, start);
        }
    }

    @Override
    public long position(Blob pattern, long start) throws SQLException {
        if(this.blob == null) {
            throw new ProcessingException("Blob object is null.");
        }
        try {
            // Attempt to use the driver's implementation
            return this.blob.position(pattern, start);
        } catch (SQLFeatureNotSupportedException e) {
            // Log the exception if needed
            LOG_THIS(LogLevel.ERROR, "Driver does not support position() for Blob. Using custom implementation.");

            // Fallback to custom implementation
            return position(pattern.getBytes(1, (int) pattern.length()), start);
        }
    }

    // Custom position logic
    private long positionFallback(byte[] pattern, long start) throws SQLException {
        if (start < 1 || start > length()) {
            throw new SQLException("Start position out of bounds.");
        }

        // Retrieve the BLOB data as a byte array
        byte[] blobData = getBytes(1, (int) length());

        // Start searching for the pattern from the specified position
        int position = indexOf(blobData, pattern, (int) start - 1);
        return (position >= 0) ? position + 1 : -1; // Convert to 1-based index
    }

    // Helper method to find the index of a byte pattern in a byte array
    private int indexOf(byte[] array, byte[] pattern, int startIndex) {
        for (int i = startIndex; i <= array.length - pattern.length; i++) {
            boolean found = true;
            for (int j = 0; j < pattern.length; j++) {
                if (array[i + j] != pattern[j]) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return i; // Return 0-based index
            }
        }
        return -1; // Pattern not found
    }

    @Override
    public int setBytes(long pos, byte[] bytes) throws SQLException {
        if(this.blob == null) {
            throw new ProcessingException("Blob object is null.");
        }
        return this.blob.setBytes(pos, bytes);
    }

    @Override
    public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {
        if(this.blob == null) {
            throw new ProcessingException("Blob object is null.");
        }
        return this.blob.setBytes(pos, bytes, offset, len);
    }

    @Override
    public OutputStream setBinaryStream(long pos) throws SQLException {
        if(this.blob == null) {
            throw new ProcessingException("Blob object is null.");
        }
        return this.blob.setBinaryStream(pos);
    }

    @Override
    public void truncate(long len) throws SQLException {
        if(this.blob == null) {
            throw new ProcessingException("Blob object is null.");
        }
        this.blob.truncate(len);
    }

    @Override
    public void free() throws SQLException {
        if(this.blob == null) {
            throw new ProcessingException("Blob object is null.");
        }
        this.blob.free();
    }

    /**
     * Logs messages at default log level (INFO) for this class.
     *
     * @param args The message to log.
     */
    private static void LOG_THIS(String... args) {
        ICommon.LOG(AbstractBlob.class, args);
    }

    /**
     * Logs messages at the specified log level for this class.
     *
     * @param level The log level to use.
     * @param args  The message to log.
     */
    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(AbstractBlob.class, level, args);
    }
}
