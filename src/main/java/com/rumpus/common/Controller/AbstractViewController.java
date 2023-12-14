package com.rumpus.common.Controller;

import java.util.List;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.views.AbstractViews;
import com.rumpus.common.views.Footer;
import com.rumpus.common.views.Header;
import com.rumpus.common.views.Resource;
import com.rumpus.common.views.ResourceManager;
import com.rumpus.common.views.Component.AbstractComponent;
import com.rumpus.common.views.Html.AbstractHtmlObject;
import com.rumpus.common.views.Template.AbstractTemplate;

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

    ////////////////////////////
    // Template Controller //////
    ////////////////////////////
    /**
     * Get the Templates for a particular view at /template/{template_name}. Example: /template/RumpusAdmin
     * 
     * @param templateName The name of the template
     * @param request The HttpServletRequest
     * @return The Template as a ResponseEntity
     */
    @GetMapping(AbstractCommonController.PATH_TEMPLATE_BY_NAME)
    public ResponseEntity<AbstractTemplate> getTemplate(@PathVariable(AbstractCommonController.PATH_VARIABLE_TEMPLATE_BY_NAME) String templateName, HttpServletRequest request) {

        // check if the templateName is null or empty
        if(templateName == null || templateName.isEmpty()) {
            LogBuilder.logBuilderFromStringArgs("templateName", " is null or empty").info();
            return new ResponseEntity<AbstractTemplate>(AbstractTemplate.createEmptyTemplate(), HttpStatusCode.valueOf(404));
        }

        AbstractTemplate retrievedTemplate = this.viewLoader.get(templateName); // get the template from the viewLoader
        HttpStatusCode httpCode = HttpStatusCode.valueOf(200);

        // check if the template is null, empty, or has a null head, update status code, and debug in the logs
        if(retrievedTemplate == null) {
            LogBuilder.logBuilderFromStringArgs(templateName, " not found in viewLoader: null", "  Available templates: ").info();
            LogBuilder.logBuilderFromSet(this.viewLoader.keySet()).info();
            httpCode = HttpStatusCode.valueOf(404);
        } else if(retrievedTemplate.isEmpty()) {
            LogBuilder.logBuilderFromStringArgs(templateName, " not found in viewLoader: empty", "  Available templates: ").info();
            LogBuilder.logBuilderFromSet(this.viewLoader.keySet()).info();
            httpCode = HttpStatusCode.valueOf(404);
        } else if(retrievedTemplate.getHead() == null) {
            LogBuilder.logBuilderFromStringArgs(templateName, " not found in viewLoader: head is null").info();
            httpCode = HttpStatusCode.valueOf(404);
        } else {
            LOG.info(templateName, " found in viewLoader");
        }

        return new ResponseEntity<AbstractTemplate>(retrievedTemplate, httpCode);
    }
    /** */
    @GetMapping(AbstractCommonController.PATH_COMPONENT_BY_NAME)
    public ResponseEntity<AbstractComponent> getTemplateComponent(
        @PathVariable(AbstractCommonController.PATH_VARIABLE_TEMPLATE_BY_NAME) String templateName,
        @PathVariable(AbstractCommonController.PATH_VARIABLE_COMPONENT_BY_NAME) String componentName,
        HttpServletRequest request) {

            // check if the templateName or componentName is null or empty
            if(templateName == null || templateName.isEmpty()) {
                LogBuilder.logBuilderFromStringArgs("templateName", " is null or empty").info();
                return new ResponseEntity<AbstractComponent>(AbstractComponent.createEmptyComponent(), HttpStatusCode.valueOf(404));
            } else if(componentName == null || componentName.isEmpty()) {
                LogBuilder.logBuilderFromStringArgs("componentName", " is null or empty").info();
                return new ResponseEntity<AbstractComponent>(AbstractComponent.createEmptyComponent(), HttpStatusCode.valueOf(404));
            }

            AbstractTemplate retrievedTemplate = this.viewLoader.get(templateName); // get the template from the viewLoader
            HttpStatusCode httpCode = HttpStatusCode.valueOf(200);
            AbstractComponent retrievedComponent = null;

            // check if the template is null, empty, or has a null head, update status code, and debug in the logs
            if(retrievedTemplate == null) {
                LogBuilder.logBuilderFromStringArgs(templateName, " not found in viewLoader: null", "  Available templates: ").info();
                LogBuilder.logBuilderFromSet(this.viewLoader.keySet()).info();
                httpCode = HttpStatusCode.valueOf(404);
            } else if(retrievedTemplate.isEmpty()) {
                LogBuilder.logBuilderFromStringArgs(templateName, " not found in viewLoader: empty", "  Available templates: ").info();
                LogBuilder.logBuilderFromSet(this.viewLoader.keySet()).info();
                httpCode = HttpStatusCode.valueOf(404);
            } else if(retrievedTemplate.getHead() == null) {
                LogBuilder.logBuilderFromStringArgs(templateName, " not found in viewLoader: head is null").info();
                httpCode = HttpStatusCode.valueOf(404);
            } else {
                // Found template now check for component
                LOG.info(templateName, " found in viewLoader");
                retrievedComponent = retrievedTemplate.get(componentName);
                if(retrievedComponent == null) {
                    LogBuilder.logBuilderFromStringArgs(componentName, " not found in template: null", "  Available components: ").info();
                    LogBuilder.logBuilderFromSet(retrievedTemplate.keySet()).info();
                    httpCode = HttpStatusCode.valueOf(404);
                } else {
                    LOG.info(componentName, " found in template");
                }
            }

            return new ResponseEntity<AbstractComponent>(retrievedComponent != null ? retrievedComponent : AbstractComponent.createEmptyComponent(), httpCode);
    }
    // /**
    //  * Update a particular Template, using @PostMapping, for a particular view. This should take a PathVariable, a Template object to update, and a HttpServletRequest.
    //  */
    // @PostMapping(AbstractCommonController.PATH_TEMPLATE_BY_NAME)
    // public ResponseEntity<AbstractTemplate> updateTemplate(@PathVariable(AbstractCommonController.PATH_VARIABLE_TEMPLATE_BY_NAME) String templateName, AbstractTemplate template, HttpServletRequest request) {
    //     return new ResponseEntity<Template>(viewLoader.setTemplate(templateName, template), HttpStatusCode.valueOf(200));
    // }
}
