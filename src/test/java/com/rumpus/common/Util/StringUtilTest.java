package com.rumpus.common.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.rumpus.common.CommonTest;
import com.rumpus.common.util.StringUtil;

/**
 * This class is used to test the {@link StringUtil} class.
 */
public class StringUtilTest extends CommonTest {
    
    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    // setters getters
    @Test
    @Order(1)
    void testTrimStartAndEnd() {
        String expected = " ";
        String actual = StringUtil.trimStartAndEnd("hello hello", "hello");
        assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    void testTODO1() {
    }

    @Test
    @Order(3)
    void testTODO2() {
    }
}
