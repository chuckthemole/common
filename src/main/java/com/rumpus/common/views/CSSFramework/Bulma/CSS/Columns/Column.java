package com.rumpus.common.views.CSSFramework.Bulma.CSS.Columns;

public class Column extends AbstractBulmaColumns {

    private Column() {
        super(ColumnsType.COLUMN);
        this.init();
    }

    public static Column create() {
        return new Column();
    }

    private void init() {
        this.addHtmlTagAttribute("class", "column");
    }
}
