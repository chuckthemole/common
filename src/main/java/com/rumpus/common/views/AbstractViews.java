package com.rumpus.common.views;

import com.rumpus.common.Builder.ITableBuilder;
import com.rumpus.common.Manager.AbstractCommonManager;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.views.Template.AbstractTemplate;
import com.rumpus.common.views.Template.AbstractUserTemplate;

import java.util.List;

/**
 * @author Charles Thomas
 * 
 * This class is the base class for all views. It acts as a manager of {@link AbstractTemplate}s.
 * TODO: define generics for this class, ie USER, USER_META, etc.
 */
public abstract class AbstractViews extends AbstractCommonManager<AbstractTemplate>  {

    public static final String CURRENT_USER_TEMPLATE_KEY = "currentUserTemplate";
    protected final static String DEFAULT_NAVBAR_BRAND = "/images/default_brand.PNG";
    
    protected Footer footer;
    protected Header header;
    protected ITableBuilder userTable;
    protected ResourceManager resourceManager;

	public AbstractViews(String name) {
        super(name, false);
        this.init();
	}

    protected int init() { // TODO: make private?
        this.resourceManager = ResourceManager.createEmptyManager();
        initTemplates();
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
     * Init templates for views
     * @return SUCCESS if successful, otherwise FAILURE
     * @see {@link AbstractTemplate}
     */
    abstract protected int initTemplates();

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

    public List<Resource> getResources() {
        return this.resourceManager.getResources();
    }

    // TODO: this should have defined generics. Do this for this class.
    @SuppressWarnings("unchecked")
    public AbstractUserTemplate<? extends AbstractCommonUser<?, ?>, ? extends AbstractCommonUserMetaData<?>> getCurrentUserTemplate() {
        return (AbstractUserTemplate<? extends AbstractCommonUser<?, ?>, ? extends AbstractCommonUserMetaData<?>>) this.get(AbstractViews.CURRENT_USER_TEMPLATE_KEY);
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

    @Override
    public AbstractTemplate createEmptyManagee() {
        return AbstractTemplate.createEmptyTemplate();
    }

    @Override
    public AbstractTemplate createEmptyManagee(String name) {
        AbstractTemplate template = AbstractTemplate.createEmptyTemplate();
        return this.put(name, template);
    }
}
