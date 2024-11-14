package com.rumpus.common.Test;

import com.rumpus.common.ICommon;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Log.ICommonLogger.LogLevel;

// import org.junit.jupiter.api.TestInstance;

// @TestInstance(TestInstance.Lifecycle.PER_CLASS) // TODO: look into this annotation more. It lets you use @BeforeAll and @AfterAll on non-static methods. Is this what I want? think more about how I want beforeAll and afterAll to work.

/**
 * Abstracted common class for all tests.
 * This includes the setUp and tearDown methods for each test, as well as the setUpClass and tearDownClass methods for the entire class.
 * This class also includes a LOG method for logging messages.
 */
abstract public class AbstractCommonTest implements ICommon {

    /**
     * The class that is being tested
     */
    private Class<?> clazz;

    /**
     * Constructor
     * @param clazz The class that is being tested
     */
    public AbstractCommonTest(Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * This method is called before all tests in this class
     */
    abstract protected void setUpClass();

    /**
     * This method is called after all tests in this class
     */
    abstract protected void tearDownClass();

    /**
     * This method is called before each test in this class
     */
    abstract protected void setUp();

    /**
     * This method is called after each test in this class
     */
    abstract protected void tearDown();

    @org.junit.jupiter.api.BeforeAll
    public static void beforeAll() {
        // this.setUpClass();
    }

    @org.junit.jupiter.api.AfterAll
    public static void afterAll() {
        // this.tearDownClass();
    }

    @org.junit.jupiter.api.BeforeEach
    public void beforeEach() {
        this.setUp();
    }

    @org.junit.jupiter.api.AfterEach
    public void afterEach() {
        this.tearDown();
    }

    /**
     * Logs a message using info level
     * @param args The message to log
     */
    protected void LOG(String... args) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(this.clazz, args).toString();
        LOG(LogLevel.INFO, this.clazz, log);
    }

    /**
     * Logs a message using the specified level
     * 
     * @param level the level to log the message at
     * @param args The message to log
     */
    protected void LOG(LogLevel level, String... args) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(this.clazz, args).toString();
        LOG(level, this.clazz, log);
    }

    /**
     * Logs a message using the specified level
     * 
     * @param level the level to log the message at
     * @param clazz The class that is being tested
     * @param message The message to log
     */
    protected void LOG(LogLevel level, Class<?> clazz, String... message) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(clazz, message).toString();
        ICommon.LOG_COMMON.setClass(clazz);
        ICommon.LOG_COMMON.logAtLevel(level, log);
        ICommon.LOG_COMMON.setClass(ICommon.DEFAULT_LOGGER_CLASS);
    }
}
