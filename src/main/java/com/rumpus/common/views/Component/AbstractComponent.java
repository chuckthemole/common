package com.rumpus.common.views.Component;

import com.rumpus.common.views.Html.AbstractHtmlObject;

public abstract class AbstractComponent extends AbstractHtmlObject {

    public AbstractComponent(String name, HtmlTagType htmlTagType, String body) {
        super(name, htmlTagType, body);
    }
}
