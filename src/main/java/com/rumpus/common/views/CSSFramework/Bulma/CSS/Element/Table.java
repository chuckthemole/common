package com.rumpus.common.views.CSSFramework.Bulma.CSS.Element;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public class Table extends AbstractBulmaElement {
    private static final String NAME = "BulmaTable";
    private static final String CLASS_NAME = "table";
    private static final AbstractHtmlObject.HtmlTagType HTML_TAG_TYPE = AbstractHtmlObject.HtmlTagType.TABLE;

    private Table(String body) {
        super(NAME, HTML_TAG_TYPE, body);
        this.init();
    }

    private void init() {
        this.addHtmlTagAttribute(AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute(), CLASS_NAME);
    }

    public static Table createWithBody(String body) {
        return new Table(body);
    }
    public static Table createWithNoBody() {
        return new Table("");
    }
}
