package com.rumpus.common.views.CSSFramework.Bulma.CSS.Element;

import com.rumpus.common.views.Html.AbstractHtmlObject;

/**
 * Bulma Box
 * @link https://bulma.io/documentation/elements/box/
 * 
 * note: this will default to a div tag. you can change it to a different tag by using the setHtmlTagType method.
 */
public class Box extends AbstractBulmaElement {

    private static final AbstractHtmlObject.HtmlTagType HTML_TAG_TYPE = AbstractHtmlObject.HtmlTagType.DIV;

    private Box(String body) {
        super(HTML_TAG_TYPE, body);
        this.init();
    }

    private void init() {
        this.addHtmlTagAttribute(
            AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute(),
            this.getClass().getSimpleName());
    }

    public static Box createWithBody(String body) {
        return new Box(body);
    }
    public static Box createWithNoBody() {
        return new Box("");
    }
}
