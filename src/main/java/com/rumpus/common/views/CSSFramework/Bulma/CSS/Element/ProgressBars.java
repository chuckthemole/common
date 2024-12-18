package com.rumpus.common.views.CSSFramework.Bulma.CSS.Element;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public class ProgressBars extends AbstractBulmaElement {

    private static final AbstractHtmlObject.HtmlTagType HTML_TAG_TYPE = AbstractHtmlObject.HtmlTagType.PROGRESS;

    private ProgressBars(String body) {
        super(HTML_TAG_TYPE, body);
        this.init();
    }

    private void init() {
        this.addHtmlTagAttribute(
            AbstractHtmlObject.CommonHtmlAttribute.CLASS.getCommonHtmlAttribute(),
            this.getClass().getSimpleName());
    }

    public static ProgressBars createWithBody(String body) {
        return new ProgressBars(body);
    }
    public static ProgressBars createWithNoBody() {
        return new ProgressBars("");
    }
}
