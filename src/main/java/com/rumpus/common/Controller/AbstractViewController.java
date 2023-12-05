package com.rumpus.common.Controller;

import java.util.List;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rumpus.common.views.AbstractViews;
import com.rumpus.common.views.Footer;
import com.rumpus.common.views.Header;
import com.rumpus.common.views.Resource;
import com.rumpus.common.views.ResourceManager;
import com.rumpus.common.views.Section;

import jakarta.servlet.http.HttpServletRequest;

public abstract class AbstractViewController extends AbstractCommonController {

    private static final String NAME = "ViewController";
    protected final AbstractViews viewLoader;

    public AbstractViewController(AbstractViews viewLoader) {
        super(NAME);
        this.viewLoader = viewLoader;
    }
    public AbstractViewController(String name, AbstractViews viewLoader) {
        super(name);
        this.viewLoader = viewLoader;
    }

    /**
     * Get the Footer
     * 
     * @return The Footer
     */
    @GetMapping(AbstractCommonController.PATH_FOOTER)
    public ResponseEntity<Footer> getFooter(HttpServletRequest request) {
        return new ResponseEntity<Footer>(viewLoader.getFooter(), HttpStatusCode.valueOf(200));
    }

    /**
     * Get the Header
     * 
     * @return The Header
     */
    @GetMapping(AbstractCommonController.PATH_HEADER)
    public ResponseEntity<Header> getHeader(HttpServletRequest request) {
        return new ResponseEntity<Header>(viewLoader.getHeader(), HttpStatusCode.valueOf(200));
    }

    /**
     * Get the User Table
     * 
     * @return The User Table
     */
    @GetMapping(AbstractCommonController.PATH_USER_TABLE)
    public ResponseEntity<String> getUserTable(HttpServletRequest request) {
        return new ResponseEntity<String>(viewLoader.getUserTable().getTable(), HttpStatusCode.valueOf(200));
    }

    /**
     * Get the Resources for a particular view
     * 
     * @return The Resource List
     */
    @GetMapping(AbstractCommonController.PATH_RESOURCES)
    public ResponseEntity<List<Resource>> getResources(HttpServletRequest request) {
        return new ResponseEntity<List<Resource>>(viewLoader.getResources(), HttpStatusCode.valueOf(200));
    }

    /**
     * Get a particular Resource for a particular view
     * 
     * @return The Resource
     */
    @GetMapping(AbstractCommonController.PATH_RESOURCE_BY_NAME)
    public ResponseEntity<Resource> getResourceByName(@PathVariable(AbstractCommonController.PATH_VARIABLE_RESOURCE_BY_NAME) String name, HttpServletRequest request) {
        Resource resource = viewLoader.getResourceByName(name);
        return new ResponseEntity<Resource>(resource != null ? resource : this.resourceManager.createEmptyManagee(), HttpStatusCode.valueOf(200));
    }

    @GetMapping(AbstractCommonController.PATH_SECTION_BY_NAME)
    public ResponseEntity<Section> getSection(@PathVariable(AbstractCommonController.PATH_VARIABLE_SECTION_BY_NAME) String sectionName, HttpServletRequest request) {
        return new ResponseEntity<Section>(viewLoader.getSection(sectionName), HttpStatusCode.valueOf(200));
    }
}
