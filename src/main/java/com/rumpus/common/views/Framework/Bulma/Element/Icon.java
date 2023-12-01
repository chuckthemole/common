package com.rumpus.common.views.Framework.Bulma.Element;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public class Icon extends AbstractBulmaElement {
    private static final String NAME = "BulmaIcon";
    private static final String CLASS_NAME = "delete";
    private static final AbstractHtmlObject.HtmlTagType HTML_TAG_TYPE = AbstractHtmlObject.HtmlTagType.SPAN;

    private Icon(String body) {
        super(NAME, HTML_TAG_TYPE, body);
        this.init();
    }

    private void init() {
        this.addHtmlTagAttribute(AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute(), CLASS_NAME);
    }

    public static Icon createWithBody(String body) {
        return new Icon(body);
    }
    public static Icon createWithNoBody() {
        return new Icon("");
    }
}
