package com.rumpus.common.Serializer;

/**
 * Exception class for handling errors during processing of Serializer objects.
 */
public class ProcessingException extends RuntimeException {

    /**
     * Constructs a new ProcessingException with the given message.
     * 
     * @param message The message to include in the exception.
     */
    public ProcessingException(String message) {
        super(message);
    }

    /**
     * Constructs a new ProcessingException with the given message and cause.
     * 
     * @param message The message to include in the exception.
     * @param cause The cause of the exception.
     */
    public ProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
