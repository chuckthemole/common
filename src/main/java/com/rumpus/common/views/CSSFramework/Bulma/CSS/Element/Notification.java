package com.rumpus.common.views.CSSFramework.Bulma.CSS.Element;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public class Notification extends AbstractBulmaElement {

    private static final AbstractHtmlObject.HtmlTagType HTML_TAG_TYPE = AbstractHtmlObject.HtmlTagType.DIV;

    private Notification(String body) {
        super(HTML_TAG_TYPE, body);
        this.init();
    }

    private void init() {
        this.addHtmlTagAttribute(
            AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute(),
            this.getClass().getSimpleName());
    }

    public static Notification createWithBody(String body) {
        return new Notification(body);
    }
    public static Notification createWithNoBody() {
        return new Notification("");
    }
}
