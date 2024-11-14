package com.rumpus.common.Log;

import com.rumpus.common.Manager.IManageable;

/**
 * Interface for the common logger.
 * This interface defines the common logging methods that are implemented by various
 * logging classes (e.g., SLF4JLogger). It supports different logging levels to 
 * control the verbosity of log output.
 */
public interface ICommonLogger extends IManageable {

   /**
     * The log levels represent different degrees of logging verbosity. 
     * Each level corresponds to a progressively more detailed amount of log output:
     * 
     * - `DEBUG1`, `DEBUG2`, `DEBUG3`, `DEBUG4`: These levels allow fine-grained 
     *   control of debug logs. They offer increasing verbosity, where `DEBUG1` is the 
     *   least detailed, and `DEBUG4` is the most detailed. These can be used to customize 
     *   how much debug information is logged.
     * 
     * - `TRACE`: This is typically the most detailed level for tracing program execution 
     *   and is generally used for pinpointing exactly what is happening in your code.
     * 
     * - `DEBUG`: This level is used to output debugging information that is useful during 
     *   development but typically not needed in production.
     * 
     * - `INFO`: General informational messages about application progress or state. These 
     *   logs typically include high-level actions (e.g., "user logged in").
     * 
     * - `WARN`: Warning messages indicate potential issues or unexpected situations 
     *   that do not stop the program but might need attention.
     * 
     * - `ERROR`: Error messages are used when something goes wrong in the application, 
     *   requiring immediate attention. Typically, these correspond to failures or 
     *   exceptions.
     */
    public enum LogLevel {
        DEBUG1(1),  // Least detailed debug level
        DEBUG2(2),  // More detailed debug level
        DEBUG3(3),  // Further detailed debug level
        DEBUG4(4),  // Most detailed debug level
        
        TRACE(5),   // Trace level, highly detailed
        DEBUG(1),   // Standard debug level, similar to DEBUG1
        INFO(2),    // Informational log level, similar to DEBUG2
        WARN(2),    // Warning log level, similar to DEBUG2
        ERROR(1);   // Error log level, similar to DEBUG1

        private final int level;

        LogLevel(int level) {
            this.level = level;
        }

        /**
         * Get the integer value associated with the log level.
         * 
         * @return the integer value of the log level
         */
        public int getLevel() {
            return this.level;
        }
    }


    /**
     * Logs a message using the default logging level.
     * 
     * @param args The message to log
     */
    void logArgs(String... args);

    /**
     * Logs a message at a specific log level.
     * The appropriate level should be passed in (e.g., `INFO`, `ERROR`, `DEBUG1`).
     * This method ensures that the message is only logged if the logger's current
     * configuration permits logging at that level.
     * 
     * @param level the level to log the message at (e.g., `DEBUG1`, `INFO`, `ERROR`)
     * @param args The message to log
     */
    void logAtLevel(LogLevel level, String... args);

    /**
     * Logs a message at the `INFO` level.
     * This is generally used for high-level operational messages, to indicate
     * the normal progress of the application (e.g., startup, user events).
     * 
     * @param message The message to log
     */
    void infoLevel(String... message);

    /**
     * Logs a message at the `DEBUG` level.
     * This level is used for debugging output to help diagnose issues during 
     * development. These messages are usually not included in production logs.
     * 
     * @param message The message to log
     */
    void debugLevel(String... message);

    /**
     * Logs a message at the `DEBUG1` level.
     * 
     * @param message The message to log
     */
    void debug1Level(String... message);

    /**
     * Logs a message at the `DEBUG2` level.
     * 
     * @param message The message to log
     */
    void debug2Level(String... message);

    /**
     * Logs a message at the `DEBUG3` level.
     * 
     * @param message The message to log
     */
    void debug3Level(String... message);

    /**
     * Logs a message at the `DEBUG4` level.
     * 
     * @param message The message to log
     */
    void debug4Level(String... message);

    /**
     * Logs a message at the `WARN` level.
     * This is for logging potential issues that may not immediately cause a 
     * failure but could lead to problems (e.g., deprecated method usage).
     * 
     * @param message The message to log
     */
    void warnLevel(String... message);

    /**
     * Logs a message at the `ERROR` level.
     * This is used for logging critical issues or errors that may cause the 
     * application to fail or behave incorrectly. These messages usually 
     * represent serious issues that require immediate attention.
     * 
     * @param message The message to log
     */
    void errorLevel(String... message);

    /**
     * Logs a message along with a throwable (e.g., an exception) at the `ERROR` level.
     * This is typically used to capture stack traces along with an error message, 
     * providing more context for the error.
     * 
     * @param throwable The exception or throwable to log
     * @param message The message to log
     */
    void errorLevel(Throwable throwable, String... message);

    /**
     * Sets the class for this logger.
     * This can be used to associate the logger with a specific class,
     * providing context for logging (e.g., for prefixing log messages with the 
     * class name).
     * 
     * @param clazz The class to associate with this logger
     */
    public void setClass(Class<?> clazz);

    /**
     * Retrieves the class associated with this logger.
     * 
     * @param clazz The class to retrieve
     * @return The class associated with the logger
     */
    public Class<?> getClass(Class<?> clazz);
}
