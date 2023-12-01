package com.rumpus.common.views.Html;

public class EmptyHtmlObject extends AbstractHtmlObject {

    private static final String NAME = "EmptyHtmlObject";

    private EmptyHtmlObject() {
        super(NAME, AbstractHtmlObject.HtmlTagType.EMPTY, "");
    }

    protected static EmptyHtmlObject create() {
        return new EmptyHtmlObject();
    }
}
