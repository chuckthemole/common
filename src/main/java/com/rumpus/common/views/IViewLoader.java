package com.rumpus.common.views;

import com.rumpus.common.IRumpusObject;
import com.rumpus.common.ITableBuilder;

public interface IViewLoader extends IRumpusObject {
    public Footer getFooter();
    public int setFooter(Footer f);
    public ITableBuilder getUserTable();
    public int setUserTable(ITableBuilder table);
}
