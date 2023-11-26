package com.rumpus.common.views;

import com.rumpus.common.Builder.ITableBuilder;
import com.rumpus.common.AbstractCommonObject;

/**
 * @author Charles Thomas
 * 
 * Views used for webpage. As of now (2023/3/23) only contains footer. You can add other views here.
 * You must implement the views when using.
 */
public abstract class AbstractViewLoader extends AbstractCommonObject  {
    
    protected Footer footer;
    protected Header header;
    protected ITableBuilder userTable;

	public AbstractViewLoader(String name) {
        super(name);
        // init();
	}

    protected int init() {
        initFooter();
        initHeader();
        initUserTable();
        return SUCCESS;
    }

    /**
     * Init footer
     * @return SUCCESS if successful, otherwise FAILURE
     */
    abstract protected int initFooter();
    /**
     * Init header
     * @return SUCCESS if successful, otherwise FAILURE
     */
    abstract protected int initHeader();
    /**
     * Init user table
     * @return SUCCESS if successful, otherwise FAILURE
     */
    abstract protected int initUserTable();

    public Footer getFooter() {
        return this.footer;
    }

    public int setFooter(Footer footer) {
        this.footer = footer;
        return SUCCESS;
    }

    public Header getHeader() {
        return this.header;
    }

    public int setHeader(Header header) {
        this.header = header;
        return SUCCESS;
    }

    public ITableBuilder getUserTable() {
        return this.userTable;
    }

    public int setUserTable(ITableBuilder table) {
        this.userTable = table;
        return SUCCESS;
    }
}
