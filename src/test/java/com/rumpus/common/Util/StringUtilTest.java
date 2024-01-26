package com.rumpus.common.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.rumpus.common.AbstractCommonTest;
import com.rumpus.common.util.StringUtil;

/**
 * This class is used to test the {@link StringUtil} class.
 */
public class StringUtilTest extends AbstractCommonTest {

    public StringUtilTest() {
        super(StringUtilTest.class);
    }
    
    @Override
    public void setUpClass() {
    }

    @Override
    public void tearDownClass() {
    }

    @Override
    public void setUp() {
    }

    @Override
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
