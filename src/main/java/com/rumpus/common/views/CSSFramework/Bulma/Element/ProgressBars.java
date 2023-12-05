package com.rumpus.common.views.CSSFramework.Bulma.Element;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public class ProgressBars extends AbstractBulmaElement {
    private static final String NAME = "BulmaProgressBars";
    private static final String CLASS_NAME = "progress";
    private static final AbstractHtmlObject.HtmlTagType HTML_TAG_TYPE = AbstractHtmlObject.HtmlTagType.PROGRESS;

    private ProgressBars(String body) {
        super(NAME, HTML_TAG_TYPE, body);
        this.init();
    }

    private void init() {
        this.addHtmlTagAttribute(AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute(), CLASS_NAME);
    }

    public static ProgressBars createWithBody(String body) {
        return new ProgressBars(body);
    }
    public static ProgressBars createWithNoBody() {
        return new ProgressBars("");
    }
}
