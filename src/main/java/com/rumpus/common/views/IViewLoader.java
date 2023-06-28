package com.rumpus.common.views;

import com.rumpus.common.Builder.ITableBuilder;

public interface IViewLoader {
    public Footer getFooter();
    public int setFooter(Footer f);
    public ITableBuilder getUserTable();
    public int setUserTable(ITableBuilder table);
}
