package com.rumpus.common.Manager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link com.rumpus.common.Manager.AbstractCommonManager} and {@link com.rumpus.common.Manager.AbstractCommonManagerIdKey}
 */
public class CommonManagerTest extends com.rumpus.common.AbstractCommonTest {

    public CommonManagerTest() {
        super(CommonManagerTest.class);
    }

    @Override
    protected void setUpClass() {
    }

    @Override
    protected void tearDownClass() {
    }

    @Override
    protected void setUp() {
    }

    @Override
    protected void tearDown() {
    }

    /**
     * Tests {@link com.rumpus.common.Manager.AbstractCommonManagerIdKey}
     * Using {@link com.rumpus.common.views.Html.HtmlTagAttributes} and {@link com.rumpus.common.views.Html.Attribute} to test rn. Maybe create a test class.
     */
    @Test
    @Order(1)
    void testAbstractCommonManagerIdKey() {
        AbstractCommonManagerIdKey<com.rumpus.common.views.Html.Attribute> manager1 = com.rumpus.common.views.Html.HtmlTagAttributes.createEmpty();
        AbstractCommonManagerIdKey<com.rumpus.common.views.Html.Attribute> manager2 = com.rumpus.common.views.Html.HtmlTagAttributes.createEmpty();
        manager1.addThenReturnId(com.rumpus.common.views.Html.Attribute.create("class", "block"));
        manager1.addThenReturnId(com.rumpus.common.views.Html.Attribute.create("admin", "true"));
        manager2.addThenReturnId(com.rumpus.common.views.Html.Attribute.create("class", "block"));
        manager2.addThenReturnId(com.rumpus.common.views.Html.Attribute.create("admin", "true"));
        assertEquals(manager1, manager2);
    }

    @Test
    @Order(2)
    void testTODO1() {
        com.rumpus.common.views.Html.HtmlTagAttributes htmlTagAttributes = com.rumpus.common.views.Html.HtmlTagAttributes.createEmpty();
        htmlTagAttributes.add(com.rumpus.common.views.Html.Attribute.create("class", "block"));
        htmlTagAttributes.add(com.rumpus.common.views.Html.Attribute.create("admin", "true"));
        assertTrue(htmlTagAttributes.containsAttributeProperty("class"));
        assertTrue(htmlTagAttributes.containsAttributeProperty(com.rumpus.common.views.Html.Attribute.AttributeProperty.CLASS));
        assertTrue(htmlTagAttributes.containsAttributeProperty("admin"));
        assertFalse(htmlTagAttributes.containsAttributeProperty("admin2"));
        assertFalse(htmlTagAttributes.containsAttributeProperty(com.rumpus.common.views.Html.Attribute.AttributeProperty.FOR));
    }
}
