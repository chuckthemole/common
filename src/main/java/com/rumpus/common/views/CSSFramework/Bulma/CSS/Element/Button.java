package com.rumpus.common.views.CSSFramework.Bulma.CSS.Element;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public class Button extends AbstractBulmaElement {

    private static final AbstractHtmlObject.HtmlTagType HTML_TAG_TYPE = AbstractHtmlObject.HtmlTagType.BUTTON;

    private Button(String body) {
        super(HTML_TAG_TYPE, body);
        this.init();
    }

    private void init() {
        this.addHtmlTagAttribute(
            AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute(),
            this.getClass().getSimpleName());
    }

    public static Button createWithBody(String body) {
        return new Button(body);
    }
    public static Button createWithNoBody() {
        return new Button("");
    }
}
