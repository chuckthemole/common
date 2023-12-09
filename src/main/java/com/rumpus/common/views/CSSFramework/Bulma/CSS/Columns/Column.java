package com.rumpus.common.views.CSSFramework.Bulma.CSS.Columns;

public class Column extends AbstractBulmaColumns {
    
    private static final String NAME = "BulmaColumn";

    private Column() {
        super(NAME, ColumnsType.COLUMN);
        this.init();
    }

    public static Column create() {
        return new Column();
    }

    private void init() {
        this.addHtmlTagAttribute("class", "column");
    }
}
