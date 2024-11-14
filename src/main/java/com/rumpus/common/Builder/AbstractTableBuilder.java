package com.rumpus.common.Builder;

import j2html.TagCreator;
import j2html.Config;

public abstract class AbstractTableBuilder extends AbstractHtmlBuilder implements ITableBuilder {

    private int columns;
    private int rows;
    private static String margin;


    AbstractHtmlBuilder builder;

    public AbstractTableBuilder() {
        
    }
    public AbstractTableBuilder(String name, String css) {
        
        setCssFramework(css);
    }

    private int init() {
        this.columns = 0;
        this.rows = 0;
        AbstractTableBuilder.margin = "";
        return SUCCESS;
    }

    public String getTable() {
        String table = "Table";
            // TagCreator.div(
            //     TagCreator.attrs(".container"),
            //     TagCreator.div(
            //         TagCreator.attrs(".notification"),
            //         TagCreator.table(
            //             TagCreator.attrs(".table.is-hoverable.is-fullwidth"),
            //             TagCreator.thead(
            //                 TagCreator.tr()
            //             ),
            //             TagCreator.tfoot(
            //                 TagCreator.tr()
            //             ),
            //             TagCreator.tbody()
            //         ),
            //         TagCreator.div(
            //             TagCreator.attrs(".container m-4"),
            //             TagCreator.form(
            //                 TagCreator.button(
            //                     TagCreator.attrs(".m-4.addUser.button.is-primary")
            //                 )
            //             )
            //         )
            //     )
            // ).render();
            // .withClass(margin).toString();
        return table;
    }
    
}
