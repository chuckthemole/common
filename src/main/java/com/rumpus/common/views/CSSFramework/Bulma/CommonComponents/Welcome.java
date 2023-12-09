package com.rumpus.common.views.CSSFramework.Bulma.CommonComponents;

import com.rumpus.common.views.Component.AbstractWelcome;
import com.rumpus.common.views.Html.AbstractHtmlObject;

public class Welcome extends AbstractWelcome {

    private static final String NAME = "BulmaWelcome";

    private Welcome() {
        super(NAME);
        this.init();
    }

    public static Welcome create() {
        return new Welcome();
    }

    private void init() {
        this.addHtmlTagAttribute("class", "hero is-info welcome is-small");
        this.div.addHtmlTagAttribute("class", "hero-body");
    }

    @Override
    public void setChildrenFromHeaders() {
        AbstractHtmlObject divContainer = AbstractHtmlObject.createEmptyAbstractHtmlObject();
        divContainer.setHtmlTagType(AbstractHtmlObject.HtmlTagType.DIV);
        divContainer.addHtmlTagAttribute("class", "container");

        if(!this.h1.isEmpty()) {
            AbstractHtmlObject h1Object = AbstractHtmlObject.createEmptyAbstractHtmlObject();
            h1Object.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H1);
            h1Object.addHtmlTagAttribute("class", "title");
            h1Object.setBody(this.h1);
            divContainer.addChild(h1Object);
        }
        if(!this.h2.isEmpty()) {
            AbstractHtmlObject h2Object = AbstractHtmlObject.createEmptyAbstractHtmlObject();
            h2Object.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H2);
            h2Object.addHtmlTagAttribute("class", "subtitle");
            h2Object.setBody(this.h2);
            divContainer.addChild(h2Object);
        }
        if(!this.h3.isEmpty()) {
            AbstractHtmlObject h3Object = AbstractHtmlObject.createEmptyAbstractHtmlObject();
            h3Object.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H3);
            h3Object.addHtmlTagAttribute("class", "subtitle");
            h3Object.setBody(this.h3);
            divContainer.addChild(h3Object);
        }
        if(!this.h4.isEmpty()) {
            AbstractHtmlObject h4Object = AbstractHtmlObject.createEmptyAbstractHtmlObject();
            h4Object.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H4);
            h4Object.addHtmlTagAttribute("class", "subtitle");
            h4Object.setBody(this.h4);
            divContainer.addChild(h4Object);
        }
        if(!this.h5.isEmpty()) {
            AbstractHtmlObject h5Object = AbstractHtmlObject.createEmptyAbstractHtmlObject();
            h5Object.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H5);
            h5Object.addHtmlTagAttribute("class", "subtitle");
            h5Object.setBody(this.h5);
            divContainer.addChild(h5Object);
        }
        if(!this.h6.isEmpty()) {
            AbstractHtmlObject h6Object = AbstractHtmlObject.createEmptyAbstractHtmlObject();
            h6Object.setHtmlTagType(AbstractHtmlObject.HtmlTagType.H6);
            h6Object.addHtmlTagAttribute("class", "subtitle");
            h6Object.setBody(this.h6);
            divContainer.addChild(h6Object);
        }

        this.div.addChild(divContainer);
        this.addChild(this.div);
    }
}
