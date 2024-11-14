package com.rumpus.common.views.CSSFramework.Bulma.CSS.Element;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public class Content extends AbstractBulmaElement {

    private static final AbstractHtmlObject.HtmlTagType HTML_TAG_TYPE = AbstractHtmlObject.HtmlTagType.DIV;

    private Content(String body) {
        super(HTML_TAG_TYPE, body);
        this.init();
    }

    private void init() {
        this.addHtmlTagAttribute(
            AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute(),
            this.getClass().getSimpleName());
    }

    public static Content createWithBody(String body) {
        return new Content(body);
    }
    public static Content createWithNoBody() {
        return new Content("");
    }
}
