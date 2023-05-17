package com.rumpus.common;

// import static j2html.TagCreator.*;

import j2html.TagCreator;

abstract class HtmlBuilder extends RumpusObject implements IHtmlBuilder {

    protected static TagCreator tagCreator;
    private CSS cssFramework;

    public HtmlBuilder(String name) {
        super(name);
    }

    @Override
    public void setCssFramework(CSS css) {
        this.cssFramework = css;
    }
    
    public enum CSS {
        BULMA("bulma"),
        BOOTSTRAP("bootstrap"),
        NONE("");
        // add more as needed
    
        public final String css;
    
        private CSS(String css) {
            this.css = css;
        }

        public String getCss() {
            return this.css;
        }
    }
}
