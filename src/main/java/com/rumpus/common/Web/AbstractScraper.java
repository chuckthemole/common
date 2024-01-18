package com.rumpus.common.Web;

import java.util.List;

abstract public class AbstractScraper extends AbstractWeb implements Runnable {

    protected String page;
    protected String body;
    protected String head;

    public AbstractScraper(String name, String browserVersion, String uri, List<String> params) {
        super(name, browserVersion, uri, params);
    }
    
    /**
     * Scrape the page runner
     */
    abstract public void scrape();

    public String getPage() {
        return this.page;
    }
    
    public String getBody() {
        return this.body;
    }

    public String getHead() {
        return this.head;
    }
}
