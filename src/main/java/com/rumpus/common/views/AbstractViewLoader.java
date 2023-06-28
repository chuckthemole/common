package com.rumpus.common.views;

import com.rumpus.common.ITableBuilder;
import com.rumpus.common.RumpusObject;
import com.rumpus.common.AbstractTableBuilder;

/**
 * @author Charles Thomas
 * 
 * Views used for webpage. As of now (2023/3/23) only contains footer. You can add other views here.
 * You must implement the views when using.
 */
public abstract class AbstractViewLoader extends RumpusObject implements IViewLoader {
    
    protected Footer footer;
    protected ITableBuilder userTable;

	public AbstractViewLoader(String name) {
        super(name);
        // init();
	}

    // private int init() {
    //     initFooter();
    //     return SUCCESS;
    // }

    // private int initFooter() {

    //     this.footer = new Footer();
    //     List<Pair<String, List<String>>> columns = new ArrayList<>( // Add footers here, Pair<title,items>
    //         List.of(
    //             new Pair<>("Useful", new ArrayList<>(List.of("Shop", "Rules", "News"))),
    //             new Pair<>("Support", new ArrayList<>(List.of("Shop", "Rules", "News"))),
    //             new Pair<>("Extras", new ArrayList<>(List.of("Shop", "Rules", "News")))
    //         )
    //     );

    //     for(Pair<String, List<String>> column : columns) {
    //         this.footer.add(column.getFirst(), column.getSecond());
    //     }

    //     return SUCCESS;
    // }

    @Override
    public Footer getFooter() {
        return this.footer;
    }

    @Override
    public int setFooter(Footer footer) {
        this.footer = footer;
        return SUCCESS;
    }

    @Override
    public ITableBuilder getUserTable() {
        return this.userTable;
    }

    @Override
    public int setUserTable(ITableBuilder table) {
        this.userTable = table;
        return SUCCESS;
    }
}
