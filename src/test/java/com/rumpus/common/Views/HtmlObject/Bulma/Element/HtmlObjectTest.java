package com.rumpus.common.Views.HtmlObject.Bulma.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.rumpus.common.AbstractCommonTest;
import com.rumpus.common.views.Html.AbstractHtmlObject;

public class HtmlObjectTest extends AbstractCommonTest {
    
    public HtmlObjectTest() {
        super(HtmlObjectTest.class);
    }

    // TODO: I STOPPED HERE. We need to test AbstractHtmlObject. Specifically, addHtmlTagAttribute and other attribute helpers. I'm getting wrong results.

    AbstractHtmlObject actualAbstractHtmlObject;

    @Override
    public void setUpClass() {
    }

    @Override
    public void tearDownClass() {
    }

    @Override
    public void setUp() {
        this.actualAbstractHtmlObject = AbstractHtmlObject.createEmptyAbstractHtmlObject();
    }

    @Override
    public void tearDown() {
    }

    @Test
    void testSetHtmlAttributes() {
    }

    @Test
    void testAddHtmlTagAttribute() {
    }

    @Test
    void testRemoveHtmlTagAttribute() {

    }

    @Test
    void testAddToAttribute() {

    }
}
