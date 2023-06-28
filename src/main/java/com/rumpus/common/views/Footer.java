package com.rumpus.common.views;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Charles Thomas
 * 
 * Footer view
 */
public class Footer extends AbstractView {

    private static final String NAME = "footer";
    private List<Column> columns;
    private static short size;

    public Footer() {
        super(NAME);
        size = 0;
        columns = new ArrayList<>();
    }

    public int add(String title, List<String> items) {
        Column column = new Column(title, items);
        columns.add(column);
        size++;
        return SUCCESS;
    }

    public Column column(short index) {
        Column column = columns.get(index);
        return column;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public int setColumns(List<Column> columns) {
        this.columns = columns;
        return SUCCESS;
    }

    public short getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Column column : this.columns) {
            sb.append(column.toString());
        }
        return sb.toString();
    }

    public class Column {
        private final String title;
        private final List<String> items;

        public Column(String title, List<String> items) {
            this.title = title;
            this.items = items;
        }

        public String getTitle() {
            return this.title;
        }

        public List<String> getItems() {
            return this.items;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Title: ").append(this.title).append("\n");
            for(String item : this.items) {
                sb.append("  ").append(item).append("\n");
            }
            return sb.toString();
        }
    }
}
