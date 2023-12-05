package com.rumpus.common.views.CSSFramework.Bulma.Element;

import com.rumpus.common.views.Html.AbstractHtmlObject;

/**
 * Bulma Block
 * 
 * note: this will default to a div tag. you can change it to a different tag by using the setHtmlTagType method.
 * 
 * @link https://bulma.io/documentation/elements/block/
 */
public class Block extends AbstractBulmaElement {

    private static final String NAME = "BulmaBlock";
    private static final String CLASS_NAME = "block";
    private static final AbstractHtmlObject.HtmlTagType HTML_TAG_TYPE = AbstractHtmlObject.HtmlTagType.DIV;

    private Block(String body) {
        super(NAME, HTML_TAG_TYPE, body);
        this.init();
    }

    private void init() {
        this.addHtmlTagAttribute(AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute(), CLASS_NAME);
    }

    public static Block createWithBody(String body) {
        return new Block(body);
    }
    public static Block createWithNoBody() {
        return new Block("");
    }
}
