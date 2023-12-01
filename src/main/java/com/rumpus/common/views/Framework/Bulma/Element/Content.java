package com.rumpus.common.views.Framework.Bulma.Element;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public class Content extends AbstractBulmaElement {
    private static final String NAME = "BulmaContent";
    private static final String CLASS_NAME = "content";
    private static final AbstractHtmlObject.HtmlTagType HTML_TAG_TYPE = AbstractHtmlObject.HtmlTagType.DIV;

    private Content(String body) {
        super(NAME, HTML_TAG_TYPE, body);
        this.init();
    }

    private void init() {
        this.addHtmlTagAttribute(AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute(), CLASS_NAME);
    }

    public static Content createWithBody(String body) {
        return new Content(body);
    }
    public static Content createWithNoBody() {
        return new Content("");
    }
}
