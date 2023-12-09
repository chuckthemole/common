package com.rumpus.common.views.Component;

import com.rumpus.common.Manager.IManageable;
import com.rumpus.common.views.Html.AbstractHtmlObject;

public abstract class AbstractWelcome extends AbstractHtmlObject implements IManageable {

    protected String h1;
    protected String h2;
    protected String h3;
    protected String h4;
    protected String h5;
    protected String h6;
    protected AbstractHtmlObject div; // first container child of the Welcome

    public AbstractWelcome(String name) {
        super(name, HtmlTagType.SECTION, "");
        this.init();
    }

    private void init() {
        this.h1 = "";
        this.h2 = "";
        this.h3 = "";
        this.h4 = "";
        this.h5 = "";
        this.h6 = "";
    }

    /**
     * Sets the children of AbstractHtmlObject from headers. Use this method to set the children of the Welcome, after adding headers.
     */
    abstract public void setChildrenFromHeaders();

    public void setH1(String h1) {
        this.h1 = h1;
    }

    public void setH2(String h2) {
        this.h2 = h2;
    }

    public void setH3(String h3) {
        this.h3 = h3;
    }

    public void setH4(String h4) {
        this.h4 = h4;
    }

    public void setH5(String h5) {
        this.h5 = h5;
    }

    public void setH6(String h6) {
        this.h6 = h6;
    }

    public String getH1() {
        return h1;
    }

    public String getH2() {
        return h2;
    }

    public String getH3() {
        return h3;
    }

    public String getH4() {
        return h4;
    }

    public String getH5() {
        return h5;
    }

    public String getH6() {
        return h6;
    }
}
