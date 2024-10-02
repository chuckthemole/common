package com.rumpus.common.Blob;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.io.IOException;

/**
 * Test class for the JdbcBlob implementation of the IBlob interface.
 */
public class JdbcBlobTest {

    private static final byte[] TEST_DATA = "Test data for blob".getBytes();
    private JdbcBlob jdbcBlob;

    @BeforeEach
    public void setUp() {
        jdbcBlob = JdbcBlob.createFromByteArray(TEST_DATA);
    }

    @Test
    public void testLength() throws SQLException {
        long length = jdbcBlob.length();
        assertEquals(TEST_DATA.length, length, "Length of BLOB should match the length of the input byte array");
    }

    @Test
    public void testGetBytes() throws SQLException {
        byte[] bytes = jdbcBlob.getBytes(1, TEST_DATA.length);
        assertArrayEquals(TEST_DATA, bytes, "Retrieved bytes should match the input byte array");
    }

    @Test
    public void testGetBinaryStream() throws SQLException {
        InputStream binaryStream = jdbcBlob.getBinaryStream();
        assertNotNull(binaryStream, "Binary stream should not be null");

        // Convert InputStream to byte array and compare with TEST_DATA
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int data;
        try {
            while ((data = binaryStream.read()) != -1) {
                outputStream.write(data);
            }
        } catch (IOException e) {
            throw new ProcessingException("Error writing output stream", e);
        }
        byte[] result = outputStream.toByteArray();
        assertArrayEquals(TEST_DATA, result, "Binary stream content should match the input byte array");
    }

    @Test
    public void testSetBytes() throws SQLException {
        byte[] newData = "Updated blob data".getBytes();
        int bytesWritten = jdbcBlob.setBytes(1, newData);
        assertEquals(newData.length, bytesWritten, "Bytes written should match the length of the input byte array");

        // Verify the data has been updated
        byte[] result = jdbcBlob.getBytes(1, newData.length);
        assertArrayEquals(newData, result, "Blob content should match the updated byte array");
    }

    @Test
    public void testPositionWithByteArray() throws SQLException {
        byte[] pattern = "blob".getBytes();
        long position = jdbcBlob.position(pattern, 1);
        assertTrue(position > 0, "Pattern should be found in the BLOB");
    }

    @Test
    public void testTruncate() throws SQLException {
        jdbcBlob.truncate(4);
        assertEquals(4, jdbcBlob.length(), "BLOB length should be truncated to 4 bytes");
        byte[] truncatedData = jdbcBlob.getBytes(1, 4);
        assertArrayEquals("Test".getBytes(), truncatedData, "Truncated data should match the expected content");
    }

    @Test
    public void testFree() {
        assertDoesNotThrow(() -> jdbcBlob.free(), "Free should not throw any exception");
    }

    @Test
    public void testSetBinaryStream() throws SQLException {
        final byte[] newData = "New blob data".getBytes();
        OutputStream outputStream = null;

        try {
            outputStream = jdbcBlob.setBinaryStream(1);
            assertNotNull(outputStream, "Output stream should not be null");

            // Write new data to the blob via the output stream
            outputStream.write(newData);
            outputStream.flush(); // Flush the stream to ensure data is written

        } catch (IOException e) {
            throw new ProcessingException("Error writing to output stream", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close(); // Ensure the stream is closed
                } catch (IOException e) {
                    throw new ProcessingException("Error closing output stream", e);
                }
            }
        }

        // Verify the blob has been updated
        final byte[] result = jdbcBlob.getBytes(1, newData.length);

        // Print newData and result to see if they are the same
        System.out.println("newData: " + new String(newData));
        System.out.println("result: " + new String(result));
        assertArrayEquals(newData, result, "Blob content should match the new data written via the output stream");
    }
}
