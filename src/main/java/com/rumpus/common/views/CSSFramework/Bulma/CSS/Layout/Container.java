package com.rumpus.common.views.CSSFramework.Bulma.CSS.Layout;

public class Container extends AbstractBulmaLayout {
    
    private static final String NAME = "BulmaContainer";

    private Container() {
        super(NAME, LayoutType.CONTAINER);
        this.init();
    }

    public static Container create() {
        return new Container();
    }

    private void init() {
        this.addHtmlTagAttribute("class", "container");
    }
}
