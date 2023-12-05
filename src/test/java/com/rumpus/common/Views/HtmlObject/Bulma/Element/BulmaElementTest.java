package com.rumpus.common.Views.HtmlObject.Bulma.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.rumpus.common.views.CSSFramework.Bulma.Element.Block;
import com.rumpus.common.views.Html.AbstractHtmlObject;

/**
 * Tests {@link AbstractBulmaElement} and in turn {@link AbstractHtmlObject} since the previous inherrits from the latter
 * 
 *  TODO: add tests for the other methods
 */
public class BulmaElementTest {

    AbstractHtmlObject abstractHtmlObject;

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
    void testBlock() {
        String body = "this is a cool body";
        this.abstractHtmlObject = Block.createWithBody(body);
        assertEquals(body, this.abstractHtmlObject.getBody());
        assertEquals(AbstractHtmlObject.HtmlTagType.DIV, this.abstractHtmlObject.getHtmlTagType());
        Map<String, String> attributes = this.abstractHtmlObject.getHtmlTagAttributes();
        assertEquals("block", attributes.get(AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute()));
    }

    @Test
    @Order(2)
    void testBlockWithNoBody() {
        this.abstractHtmlObject = Block.createWithNoBody();
        assertEquals("", this.abstractHtmlObject.getBody());
        assertEquals(AbstractHtmlObject.HtmlTagType.DIV, this.abstractHtmlObject.getHtmlTagType());
        Map<String, String> attributes = this.abstractHtmlObject.getHtmlTagAttributes();
        assertEquals("block", attributes.get(AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute()));
    }

    @Test
    @Order(3)
    void testBlockWithBodyAndAttributes() {
        String body = "this is a cool body";
        this.abstractHtmlObject = Block.createWithBody(body);
        assertEquals(body, this.abstractHtmlObject.getBody());
        assertEquals(AbstractHtmlObject.HtmlTagType.DIV, this.abstractHtmlObject.getHtmlTagType());
        Map<String, String> attributes = this.abstractHtmlObject.getHtmlTagAttributes();
        assertEquals("block", attributes.get(AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute()));
    }
}
