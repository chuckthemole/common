package com.rumpus.common.views.CSSFramework.Bulma.CSS.Columns;

public class Columns extends AbstractBulmaColumns {

    private Columns() {
        super(ColumnsType.COLUMNS);
        this.init();
    }

    public static Columns create() {
        return new Columns();
    }

    private void init() {
        this.addHtmlTagAttribute("class", "columns");
    }
}
