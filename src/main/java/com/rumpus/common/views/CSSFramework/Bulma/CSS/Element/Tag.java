package com.rumpus.common.views.CSSFramework.Bulma.CSS.Element;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public class Tag extends AbstractBulmaElement {
    private static final String NAME = "BulmaTag";
    private static final String CLASS_NAME = "tag";
    private static final AbstractHtmlObject.HtmlTagType HTML_TAG_TYPE = AbstractHtmlObject.HtmlTagType.SPAN;

    private Tag(String body) {
        super(NAME, HTML_TAG_TYPE, body);
        this.init();
    }

    private void init() {
        this.addHtmlTagAttribute(AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute(), CLASS_NAME);
    }

    public static Tag createWithBody(String body) {
        return new Tag(body);
    }
    public static Tag createWithNoBody() {
        return new Tag("");
    }
}
