package com.rumpus.common.views.CSSFramework.Bulma.Element;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public class Delete extends AbstractBulmaElement {
    private static final String NAME = "BulmaDelete";
    private static final String CLASS_NAME = "delete";
    private static final AbstractHtmlObject.HtmlTagType HTML_TAG_TYPE = AbstractHtmlObject.HtmlTagType.BUTTON;

    private Delete(String body) {
        super(NAME, HTML_TAG_TYPE, body);
        this.init();
    }

    private void init() {
        this.addHtmlTagAttribute(AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute(), CLASS_NAME);
    }

    public static Delete createWithBody(String body) {
        return new Delete(body);
    }
    public static Delete createWithNoBody() {
        return new Delete("");
    }
}
