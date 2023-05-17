package com.rumpus.common;

import j2html.TagCreator;

abstract class TableBuilder extends HtmlBuilder implements ITableBuilder {

    private int columns;
    private int rows;
    private static String margin;


    HtmlBuilder builder;

    public TableBuilder(String name) {
        super(name);
        setCssFramework(CSS.NONE);
    }
    public TableBuilder(String name, String css) {
        super(name);
        setCssFramework(CSS.valueOf(css));
    }

    public String getTable() {
        String table = 
            TagCreator.div(
                TagCreator.attrs(".container"),
                TagCreator.div(
                    TagCreator.attrs("notification"),
                    TagCreator.table(
                        TagCreator.attrs(".table.is-hoverable.is-fullwidth"),
                        TagCreator.thead(
                            TagCreator.tr()
                        ),
                        TagCreator.tfoot(
                            TagCreator.tr()
                        ),
                        TagCreator.tbody()
                    ),
                    TagCreator.div(
                        TagCreator.attrs("container m-4"),
                        TagCreator.form(
                            TagCreator.button(
                                TagCreator.attrs(".m-4.addUser.button.is-primary")
                            )
                        )
                    )
                )
            ).withClass(margin).toString();
        return table;
    }
    
}
