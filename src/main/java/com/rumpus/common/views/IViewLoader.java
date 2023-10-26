package com.rumpus.common.views;

import com.rumpus.common.Builder.ITableBuilder;

public interface IViewLoader {
    public Footer getFooter();
    public int setFooter(Footer footer);
    public Header getHeader();
    public int setHeader(Header header);
    public ITableBuilder getUserTable();
    public int setUserTable(ITableBuilder table);
}
