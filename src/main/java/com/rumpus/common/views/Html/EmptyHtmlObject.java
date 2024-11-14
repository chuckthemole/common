package com.rumpus.common.views.Html;

public class EmptyHtmlObject extends AbstractHtmlObject {

    private EmptyHtmlObject() {
        super(AbstractHtmlObject.HtmlTagType.EMPTY, "");
    }

    protected static EmptyHtmlObject create() {
        return new EmptyHtmlObject();
    }
}
