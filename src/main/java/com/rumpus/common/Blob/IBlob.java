package com.rumpus.common.Blob;

import java.sql.Blob;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * IBlob provides an abstraction over the standard {@link java.sql.Blob} interface for working
 * with SQL Binary Large Objects (BLOBs). It offers methods to retrieve and manipulate binary data 
 * stored in a database, while managing potential SQLExceptions.
 *
 * This interface can be used to encapsulate BLOB-related operations, offering flexibility
 * for different implementations, such as handling different database vendors.
 * 
 * TODO: Consider using more modern alternatives such as `java.nio` for better 
 * performance and flexibility when working with byte streams.
 */
public interface IBlob {

    /**
     * Returns the total number of bytes in this BLOB.
     *
     * @return The length of the BLOB in bytes.
     * @throws SQLException If there is an error accessing the BLOB.
     */
    public long length() throws SQLException;

    /**
     * Retrieves a portion of the BLOB's binary data as an array of bytes.
     *
     * @param pos    The position in the BLOB where the byte array starts (1-based).
     * @param length The number of bytes to retrieve.
     * @return A byte array containing the requested portion of the BLOB.
     * @throws SQLException If there is an error retrieving the bytes from the BLOB.
     */
    public byte[] getBytes(long pos, int length) throws SQLException;

    /**
     * Retrieves the BLOB's binary data as an InputStream.
     * 
     * This can be useful for streaming large BLOBs to avoid loading the entire 
     * content into memory at once.
     *
     * @return An InputStream for reading the BLOB's data.
     * @throws SQLException If there is an error accessing the BLOB data.
     */
    public InputStream getBinaryStream() throws SQLException;

    /**
     * Finds the position in this BLOB where the given byte array pattern begins, starting at the specified position.
     *
     * @param pattern The byte array to search for.
     * @param start   The position where the search should begin (1-based).
     * @return The position of the first occurrence of the pattern in the BLOB, or -1 if not found.
     * @throws SQLException If there is an error performing the search.
     */
    public long position(byte[] pattern, long start) throws SQLException;

    /**
     * Finds the position in this BLOB where the given BLOB pattern begins, starting at the specified position.
     *
     * @param pattern The BLOB pattern to search for.
     * @param start   The position where the search should begin (1-based).
     * @return The position of the first occurrence of the pattern in the BLOB, or -1 if not found.
     * @throws SQLException If there is an error performing the search.
     */
    public long position(Blob pattern, long start) throws SQLException;

    /**
     * Writes the given byte array to this BLOB starting at the specified position.
     *
     * @param pos   The position in the BLOB where the write should begin (1-based).
     * @param bytes The byte array to write to the BLOB.
     * @return The number of bytes written to the BLOB.
     * @throws SQLException If there is an error writing to the BLOB.
     */
    public int setBytes(long pos, byte[] bytes) throws SQLException;

    /**
     * Writes a portion of the given byte array to this BLOB starting at the specified position.
     *
     * @param pos    The position in the BLOB where the write should begin (1-based).
     * @param bytes  The byte array containing the data to write to the BLOB.
     * @param offset The offset in the byte array where the data begins.
     * @param len    The number of bytes to write.
     * @return The number of bytes written to the BLOB.
     * @throws SQLException If there is an error writing to the BLOB.
     */
    public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException;

    /**
     * Retrieves an OutputStream for writing binary data to this BLOB starting at the specified position.
     *
     * @param pos The position in the BLOB where the write should begin (1-based).
     * @return An OutputStream for writing data to the BLOB.
     * @throws SQLException If there is an error accessing the BLOB or the stream.
     */
    public OutputStream setBinaryStream(long pos) throws SQLException;

    /**
     * Truncates this BLOB to the specified length.
     * 
     * This can be used to reduce the size of a BLOB by discarding excess data.
     *
     * @param len The length to which the BLOB should be truncated (in bytes).
     * @throws SQLException If there is an error truncating the BLOB.
     */
    public void truncate(long len) throws SQLException;

    /**
     * Releases any resources held by this BLOB object. 
     * 
     * This is useful in situations where the Blob object is associated with a large amount of data,
     * and you want to free memory after processing. The Blob is invalidated after this method is called.
     *
     * @throws SQLException If there is an error freeing the BLOB's resources.
     */
    public void free() throws SQLException;
}