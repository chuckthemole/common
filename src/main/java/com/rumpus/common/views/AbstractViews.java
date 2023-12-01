package com.rumpus.common.views;

import com.rumpus.common.Builder.ITableBuilder;

import java.util.List;

import com.rumpus.common.AbstractCommonObject;

/**
 * @author Charles Thomas
 * 
 * Views used for webpage. As of now (2023/3/23) only contains footer. You can add other views here.
 * You must implement the views when using.
 */
public abstract class AbstractViews extends AbstractCommonObject  {
    
    protected Footer footer;
    protected Header header;
    protected ITableBuilder userTable;
    protected ResourceManager resourceManager;
    protected SectionManager sectionManager;

	public AbstractViews(String name) {
        super(name);
        // init(); // TODO - should I uncomment and call init() here? instead of calling init() in constructor of child class?
	}

    protected int init() {
        this.sectionManager = SectionManager.createEmptyManager();
        initSections();
        initFooter();
        initHeader();
        initUserTable();
        initResourceManager();
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
    /**
     * Init resource manager
     * @return SUCCESS if successful, otherwise FAILURE
     */
    abstract protected int initResourceManager();
    /**
     * Init sections
     * @return SUCCESS if successful, otherwise FAILURE
     * @see {@link SectionManager} and {@link Section}
     */
    abstract protected int initSections();

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

    public Section getSection(String name) {
        return this.sectionManager.get(name);
    }

    public List<Resource> getResources() {
        return this.resourceManager.getResources();
    }

    /**
     * Get a particular resource by name
     * 
     * @param name the name of the resource
     * @return the resource or null if not found
     * @see Map.get()
     */
    public Resource getResourceByName(String name) {
        return this.resourceManager.get(name);
    }
}
