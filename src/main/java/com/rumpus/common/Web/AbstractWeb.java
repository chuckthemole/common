package com.rumpus.common.Web;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Builder.LogBuilder;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

abstract public class AbstractWeb extends AbstractCommonObject {

    protected WebClient client;
    protected BrowserVersion browserVersion;
    private static final BrowserVersion DEFAULT_BROWSER_VERSION = BrowserVersion.CHROME;
    protected String uri;
    protected List<String> params;

    static {
        BrowserVersion.setDefault(DEFAULT_BROWSER_VERSION);
    }

    public AbstractWeb(String browserVersion, String uri, List<String> params) {
        this.init(browserVersion, uri, params);
    }

    private void init(String browserVersion, String uri, List<String> params) {
        this.uri = uri;
        this.params = params == null ? new ArrayList<>(): params;
        if(BrowserVersion.CHROME.toString().equals(browserVersion)) {
            this.browserVersion = BrowserVersion.CHROME;
        } else if(BrowserVersion.EDGE.toString().equals(browserVersion)) {
            this.browserVersion = BrowserVersion.EDGE;
        } else if(BrowserVersion.FIREFOX.toString().equals(browserVersion)) {
            this.browserVersion = BrowserVersion.FIREFOX;
        } else if(BrowserVersion.FIREFOX_ESR.toString().equals(browserVersion)) {
            this.browserVersion = BrowserVersion.FIREFOX_ESR;
        } else if(BrowserVersion.INTERNET_EXPLORER.toString().equals(browserVersion)) {
            this.browserVersion = BrowserVersion.INTERNET_EXPLORER;
        } else if(BrowserVersion.BEST_SUPPORTED.toString().equals(browserVersion)) {
            this.browserVersion = BrowserVersion.BEST_SUPPORTED;
        } else {
            this.browserVersion = BrowserVersion.getDefault();
        }
        this.browserVersion = BrowserVersion.CHROME;
        this.client = new WebClient(this.browserVersion);
        this.setDefaultOptions();
    }

    /**
     * Get this html page object using a base url with paramaters and this client
     * 
     * @return the retrieved page if found or null if exception
     */
    public HtmlPage getHtmlPage() {
        return this.getHtmlPageWithParams(this.buildUri(this.uri, this.params));
    }

    private void setDefaultOptions() {
        this.client.getOptions().setCssEnabled(false);
        this.client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        this.client.getOptions().setThrowExceptionOnScriptError(false);
        this.client.getOptions().setPrintContentOnFailingStatusCode(false);
    }

    private String buildUri(String base, List<String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(base).append("?");
        for(String param : params) {
            sb.append(param);
            sb.append("&");
        }
        return sb.toString();
    }

    private HtmlPage getHtmlPageWithParams(String uriWithParams) {
        HtmlPage page = null;
        try {
            page = this.client.getPage(uriWithParams);
        } catch (Exception e) {
            LogBuilder.logBuilderFromStringArgs(this.getClass().getName(), "getHtmlPage", e.getMessage());
        }
        this.client.close();
        return page;
    }

    public WebClient getClient() {
        return client;
    }

    public void setClient(WebClient client) {
        this.client = client;
    }

    public BrowserVersion getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(BrowserVersion browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
}
