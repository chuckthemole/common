package com.rumpus.common.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.rumpus.common.views.AbstractView;
import com.rumpus.common.views.AbstractViewLoader;
import com.rumpus.common.views.Footer;
import com.rumpus.common.views.Header;

public abstract class AbstractViewController extends AbstractCommonController {

    private static final String NAME = "ViewController";
    protected final AbstractViewLoader viewLoader;

    public AbstractViewController(AbstractViewLoader viewLoader) {
        super(NAME);
        this.viewLoader = viewLoader;
    }
    public AbstractViewController(String name, AbstractViewLoader viewLoader) {
        super(name);
        this.viewLoader = viewLoader;
    }

    /**
     * Get the name of the controller.
     * 
     * @return The name of the controller.
     */
    abstract public String getName();
    /**
     * Get the Footer
     * 
     * @return The Footer
     */
    @GetMapping(AbstractCommonController.PATH_FOOTER)
    abstract public ResponseEntity<Footer> getFooter();
    /**
     * Get the Header
     * 
     * @return The Header
     */
    @GetMapping(AbstractCommonController.PATH_HEADER)
    abstract public ResponseEntity<Header> getHeader();
    /**
     * Get the User Table
     * 
     * @return The User Table
     */
    @GetMapping(AbstractCommonController.PATH_USER_TABLE)
    abstract public ResponseEntity<String> getUserTable();
}
