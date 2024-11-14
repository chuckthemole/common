package com.rumpus.common.Builder;

import com.rumpus.common.AbstractCommonObject;

// import static j2html.TagCreator.*;

import j2html.TagCreator;

abstract class AbstractHtmlBuilder extends AbstractCommonObject implements IHtmlBuilder {

    protected static TagCreator tagCreator;
    private CSS cssFramework;

    public AbstractHtmlBuilder() {
        
        this.cssFramework = CSS.NONE;
    }

    @Override
    public void setCssFramework(String css) {
        if(css.equals("bulma")) {
            this.cssFramework = CSS.BULMA;
        } else if(css.equals("bootstrap")) {
            this.cssFramework = CSS.BOOTSTRAP;
        } else {
            this.cssFramework = CSS.NONE;
        }
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
