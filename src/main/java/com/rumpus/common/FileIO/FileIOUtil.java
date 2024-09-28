package com.rumpus.common.FileIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.rumpus.common.ICommon;
import com.rumpus.common.Logger.AbstractCommonLogger.LogLevel;

/**
 * Utility class for reading file contents.
 */
final public class FileIOUtil {

    /**
     * Reads the contents of a file as a string.
     *
     * @param filePath The path to the file.
     * @return The file contents as a string, or an empty string if reading fails.
     */
    public static String readFileAsString(final String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException | OutOfMemoryError | SecurityException e) {
            // Log error with the appropriate level and exception message.
            LOG_THIS("Error reading file: ", filePath, e.getClass().getSimpleName(), e.getMessage());
        }
        return "";
    }

    /**
     * Helper method for logging errors with class context and log level.
     *
     * @param args Arguments to log.
     */
    private static void LOG_THIS(String... args) {
        ICommon.LOG(FileIOUtil.class, args);
    }

    /**
     * Overloaded helper method for logging errors with specified log level.
     *
     * @param level The log level (e.g., ERROR, WARN, INFO).
     * @param args  The log messages.
     */
    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(FileIOUtil.class, level, args);
    }
}
