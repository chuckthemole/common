package com.rumpus.common.FileIO;

import java.util.Optional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.ICommon;
import com.rumpus.common.Log.ICommonLogger.LogLevel;

/**
 * Abstract class for reading models from files.
 */
abstract public class AbstractFileIO extends AbstractCommonObject implements IFileIO {

    /**
     * The last error message generated
     */
    private static String lastError = "";

    public AbstractFileIO() {}

    @Override
    public Optional<String> readRawFileContent(String filePath) {
        Path path = Path.of(filePath);

        try {
            // Reset lastError before operation
            lastError = "";
            // Read the file content as a String
            String content = Files.readString(path);
            return Optional.of(content);
        } catch (IOException e) {
            // Set the last error message when an exception occurs
            lastError = "Error reading file at " + filePath + ": " + e.getMessage();
            LOG_THIS("Error reading file: ", filePath, e.getClass().getSimpleName(), e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<FileMetadata> getFileMetadata(String filePath) {
        Path path = Path.of(filePath);

        try {
            // Reset lastError before operation
            lastError = "";

            // Retrieve basic file attributes
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);

            // Create a FileMetadata object
            FileMetadata metadata = FileMetadata.createFromAttributes(attributes);

            return Optional.of(metadata);
        } catch (IOException e) {
            // Set last error message if something goes wrong
            lastError = "Error retrieving metadata for file at " + filePath + ": " + e.getMessage();
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean isValidFile(String filePath) {
        Path path = Path.of(filePath);

        try {
            // Reset lastError before operation
            lastError = "";
            // Check if the file exists and is a regular file
            return Files.exists(path) && Files.isRegularFile(path);
        } catch (SecurityException e) {
            // Set the last error message when an exception occurs
            lastError = "Error validating file at " + filePath + ": " + e.getMessage();
            LOG_THIS("Error validating file: ", filePath, e.getClass().getSimpleName(), e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<String> getLastError() {
        if(lastError.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(lastError);
    }

    /**
     * Helper method for logging errors with class context and log level.
     *
     * @param args Arguments to log.
     */
    private static void LOG_THIS(String... args) {
        ICommon.LOG(AbstractFileIO.class, args);
    }

    /**
     * Overloaded helper method for logging errors with specified log level.
     *
     * @param level The log level (e.g., ERROR, WARN, INFO).
     * @param args  The log messages.
     */
    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(AbstractFileIO.class, level, args);
    }
}
