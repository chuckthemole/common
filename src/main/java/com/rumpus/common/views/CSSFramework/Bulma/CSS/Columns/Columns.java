package com.rumpus.common.views.CSSFramework.Bulma.CSS.Columns;

public class Columns extends AbstractBulmaColumns {

    private static final String NAME = "BulmaColumns";

    private Columns() {
        super(NAME, ColumnsType.COLUMNS);
        this.init();
    }

    public static Columns create() {
        return new Columns();
    }

    private void init() {
        this.addHtmlTagAttribute("class", "columns");
    }
}
