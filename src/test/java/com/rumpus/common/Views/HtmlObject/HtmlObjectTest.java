package com.rumpus.common.Views.HtmlObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.rumpus.common.AbstractCommonTest;
import com.rumpus.common.views.Html.AbstractHtmlObject;
import com.rumpus.common.views.Html.Attribute;
import com.rumpus.common.views.Html.HtmlTagAttributes;

public class HtmlObjectTest extends AbstractCommonTest {
    
    public HtmlObjectTest() {
        super(HtmlObjectTest.class);
    }

    // TODO: I STOPPED HERE. We need to test AbstractHtmlObject. Specifically, addHtmlTagAttribute and other attribute helpers. I'm getting wrong results.

    static final Attribute CLASS_BLOCK_ATTRIBUTE1 = Attribute.create("class", "block");
    static final Attribute CLASS_BLOCK_ATTRIBUTE2 = Attribute.create("class", "block2");
    static final Attribute ADMIN_ATTRIBUTE1 = Attribute.create("admin", "true");
    static final Attribute ADMIN_ATTRIBUTE2 = Attribute.create("admin", "false");
    HtmlTagAttributes actualHtmlTagAttributes;
    AbstractHtmlObject actualAbstractHtmlObject;

    @Override
    public void setUpClass() {
        this.actualHtmlTagAttributes = HtmlTagAttributes.createEmpty();
        this.actualHtmlTagAttributes.add(CLASS_BLOCK_ATTRIBUTE1);
        this.actualHtmlTagAttributes.add(ADMIN_ATTRIBUTE1);
        this.actualAbstractHtmlObject = AbstractHtmlObject.createEmptyAbstractHtmlObject();
    }

    @Override
    public void tearDownClass() {
    }

    @Override
    public void setUp() {
        // stopping here. looking at unique id manager. since the manager is static it is causing problems.
        LOG("HtmlObjectTest::setUp()");
        this.actualHtmlTagAttributes = HtmlTagAttributes.createEmpty();
        this.actualHtmlTagAttributes.add(CLASS_BLOCK_ATTRIBUTE1);
        this.actualHtmlTagAttributes.add(ADMIN_ATTRIBUTE1);
        this.actualAbstractHtmlObject = AbstractHtmlObject.createEmptyAbstractHtmlObject();
        // LOG("Attributes after setUp:\n", this.actualHtmlTagAttributes.toString());
    }

    @Override
    public void tearDown() {
    }

    //////////////////////////
    /////////// TESTS ////////
    //////////////////////////
    @Test
    @Order(1)
    void testSetHtmlAttributes() {
        LOG("Attributes 1:\n", this.actualHtmlTagAttributes.toString());

        // Set the html attributes and make sure they are set with get method
        this.actualAbstractHtmlObject.setHtmlAttributes(this.actualHtmlTagAttributes);
        assertEquals(this.actualHtmlTagAttributes, this.actualAbstractHtmlObject.getHtmlAttributes());

        // itterate through the html attributes and make sure they are all there
        for(Attribute attribute : this.actualAbstractHtmlObject.getHtmlAttributes()) {
            LOG(attribute.toString());
            assertEquals(this.actualHtmlTagAttributes.get(attribute.getUniqueId()), attribute);
        }
    }

    @Test
    @Order(2)
    void testAddHtmlTagAttribute() {
        LOG("Attributes 2:\n", this.actualHtmlTagAttributes.toString());

        // this should not be added to the HtmlTagAttributes, since it is a 'class' attribute, which already exists
        final Attribute UNPROPER_PROPERTY_ATTRIBUTE = Attribute.create("class", "this should not be added");
        this.actualAbstractHtmlObject.addHtmlTagAttribute(UNPROPER_PROPERTY_ATTRIBUTE);
        assertEquals(this.actualHtmlTagAttributes, this.actualAbstractHtmlObject.getHtmlAttributes());

        // this should be added to the HtmlTagAttributes, since it is a 'proper-property' attribute, which does not exist
        final Attribute PROPER_PROPERTY_ATTRIBUTE = Attribute.create("proper-property", "this should be added");
        this.actualAbstractHtmlObject.addHtmlTagAttribute(PROPER_PROPERTY_ATTRIBUTE);
        LOG(this.actualAbstractHtmlObject.getHtmlAttributes().toString());
        LOG(this.actualHtmlTagAttributes.toString());
        // this.actualHtmlTagAttributes.add(PROPER_PROPERTY_ATTRIBUTE);
        assertEquals(this.actualHtmlTagAttributes, this.actualAbstractHtmlObject.getHtmlAttributes());

        // this.actualAbstractHtmlObject.addHtmlTagAttribute(ADMIN, null);

        LOG("Attributes 3:\n", this.actualHtmlTagAttributes.toString());
    }

    @Test
    void testRemoveHtmlTagAttribute() {

    }

    @Test
    void testAddToAttribute() {

    }
}
