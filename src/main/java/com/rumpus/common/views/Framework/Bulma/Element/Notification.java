package com.rumpus.common.views.Framework.Bulma.Element;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public class Notification extends AbstractBulmaElement {
    private static final String NAME = "BulmaNotification";
    private static final String CLASS_NAME = "notification";
    private static final AbstractHtmlObject.HtmlTagType HTML_TAG_TYPE = AbstractHtmlObject.HtmlTagType.DIV;

    private Notification(String body) {
        super(NAME, HTML_TAG_TYPE, body);
        this.init();
    }

    private void init() {
        this.addHtmlTagAttribute(AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute(), CLASS_NAME);
    }

    public static Notification createWithBody(String body) {
        return new Notification(body);
    }
    public static Notification createWithNoBody() {
        return new Notification("");
    }
}
