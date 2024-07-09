package com.rumpus.common.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Service.IUserService;
import com.rumpus.common.Session.CommonSession;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.views.AbstractViews;
import com.rumpus.common.views.Footer;
import com.rumpus.common.views.Header;
import com.rumpus.common.views.Resource;
import com.rumpus.common.views.Component.AbstractComponent;
import com.rumpus.common.views.Html.AbstractHtmlObject;
import com.rumpus.common.views.Template.AbstractTemplate;
import com.rumpus.common.views.Template.AbstractUserTemplate;
import com.rumpus.common.views.Template.IUserTemplate;

import jakarta.servlet.http.HttpServletRequest;

public abstract class AbstractViewController
    <
        /////////////////////////
        // Define generics here//
        /////////////////////////
        USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>,
        USER_SERVICE extends IUserService<USER, USER_META>,
        USER_TEMPLATE extends IUserTemplate<USER, USER_META>
    >
    extends AbstractCommonController
    <
        /////////////////////////
        // Define generics here//
        /////////////////////////
        USER,
        USER_META,
        USER_SERVICE,
        USER_TEMPLATE
    > implements ICommonViewController {

        protected final AbstractViews viewLoader;

        public AbstractViewController(
            String name,
            AbstractViews viewLoader) {
                super(name);
                this.viewLoader = viewLoader;
        }

        // TODO: commited this but need to work on some more... - chuck
        // I should probably delete and think about having the header take care of this?
        @GetMapping("/resources/default_brand")
        public ResponseEntity<byte[]> getDefaultNavBarBrand(HttpServletRequest request) {
            org.springframework.core.io.Resource resource = this.resourceLoader.getResource("classpath:images/default_brand.PNG");
            InputStream in;
            try {
                in = resource.getInputStream();
                return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), HttpStatusCode.valueOf(200));
            } catch (IOException e) {
                LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace());
                return new ResponseEntity<byte[]>(new byte[0], HttpStatusCode.valueOf(404));
            }
        }

        /**
         * Get the Footer
         * 
         * @return The Footer
         */
        @GetMapping(ICommonViewController.PATH_FOOTER)
        public ResponseEntity<Footer> getFooter(HttpServletRequest request) {
            return new ResponseEntity<Footer>(viewLoader.getFooter(), HttpStatusCode.valueOf(200));
        }

        /**
         * Get the Header
         * 
         * @return The Header
         */
        @GetMapping(ICommonViewController.PATH_HEADER)
        public ResponseEntity<Header> getHeader(HttpServletRequest request) {
            return new ResponseEntity<Header>(viewLoader.getHeader(), HttpStatusCode.valueOf(200));
        }

        /**
         * Get the User Table
         * 
         * @return The User Table
         */
        @GetMapping(ICommonViewController.PATH_USER_TABLE)
        public ResponseEntity<String> getUserTable(HttpServletRequest request) {
            return new ResponseEntity<String>(viewLoader.getUserTable().getTable(), HttpStatusCode.valueOf(200));
        }

        /**
         * Get the Resources for a particular view
         * 
         * @return The Resource List
         */
        @GetMapping(ICommonViewController.PATH_RESOURCES)
        public ResponseEntity<List<Resource>> getResources(HttpServletRequest request) {
            return new ResponseEntity<List<Resource>>(viewLoader.getResources(), HttpStatusCode.valueOf(200));
        }

        /**
         * Get a particular Resource for a particular view
         * 
         * @return The Resource
         */
        @GetMapping(ICommonViewController.PATH_RESOURCE_BY_NAME)
        public ResponseEntity<Resource> getResourceByName(@PathVariable(ICommonViewController.PATH_VARIABLE_RESOURCE_BY_NAME) String name, HttpServletRequest request) {
            Resource resource = viewLoader.getResourceByName(name);
            return new ResponseEntity<Resource>(resource != null ? resource : this.resourceManager.createEmptyManagee(), HttpStatusCode.valueOf(200));
        }

        ////////////////////////////
        // Template Controller //////
        ////////////////////////////

        /**
         * 
         * TODO!!
         */
        @PostMapping(value = ICommonViewController.PATH_USER_TEMPLATE)
        public ResponseEntity<CommonSession> updateCurrentUserTemplate(
            @RequestBody USER user,
            // @RequestBody AbstractUserTemplate<? extends AbstractCommonUser<?, ?>, ? extends AbstractCommonUserMetaData<?>> userTemplate,
            HttpServletRequest request) {
                LOG("AbstractViewController::updateCurrentUserTemplate()");
                AbstractUserTemplate<? extends AbstractCommonUser<?, ?>, ? extends AbstractCommonUserMetaData<?>> userTemplate = viewLoader.getCurrentUserTemplate();
                // check if the templateName is null or empty
                if(userTemplate == null) {
                    LogBuilder.logBuilderFromStringArgs("Can not update template, userTemplate is null").info();
                    return new ResponseEntity<CommonSession>(CommonSession.createFromHttpSession(request.getSession()), HttpStatusCode.valueOf(404));
                }
                // viewLoader.put(AbstractViews.CURRENT_USER_TEMPLATE_KEY, userTemplate);
                userTemplate.setUser(user);
                return new ResponseEntity<CommonSession>(CommonSession.createFromHttpSession(request.getSession()), HttpStatusCode.valueOf(200));
        }

        /**
         * Get the Templates for a particular view at /template/{template_name}. Example: /template/RumpusAdmin
         * 
         * @param templateName The name of the template
         * @param request The HttpServletRequest
         * @return The Template as a ResponseEntity
         */
        @GetMapping(ICommonViewController.PATH_TEMPLATE_BY_NAME)
        public ResponseEntity<AbstractHtmlObject> getTemplate(
            @PathVariable(ICommonViewController.PATH_VARIABLE_TEMPLATE_BY_NAME) String templateName,
            HttpServletRequest request) {

                LOG("AbstractViewController::getTemplate()");

                // check if the templateName is null or empty
                if(templateName == null || templateName.isEmpty()) {
                    LogBuilder.logBuilderFromStringArgs("templateName", " is null or empty").info();
                    return new ResponseEntity<AbstractHtmlObject>(AbstractHtmlObject.createEmptyAbstractHtmlObject(), HttpStatusCode.valueOf(404));
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
                    LOG(templateName, " found in viewLoader");
                }

                AbstractUserTemplate<? extends AbstractCommonUser<?, ?>, ? extends AbstractCommonUserMetaData<?>> currentUserTemplate = viewLoader.getCurrentUserTemplate();
                LogBuilder.logBuilderFromStringArgsNoSpaces("DEBUG currentUserTemplate: username - ", currentUserTemplate.get(AbstractUserTemplate.USERNAME_TILE_KEY).toString(), " email - ", currentUserTemplate.get(AbstractUserTemplate.EMAIL_TILE_KEY).toString()).info();

                return new ResponseEntity<AbstractHtmlObject>(retrievedTemplate != null ? retrievedTemplate.getHead(): AbstractHtmlObject.createEmptyAbstractHtmlObject(), httpCode);
        }

        @GetMapping(ICommonViewController.PATH_USER_TEMPLATE)
        public ResponseEntity<AbstractHtmlObject> getUserTemplate(
            @PathVariable(ICommonViewController.PATH_VARIABLE_USER_ID) String userId,
            @PathVariable(ICommonViewController.PATH_VARIABLE_TEMPLATE_BY_NAME) String templateName,
            HttpServletRequest request) {

                LOG("AbstractViewController::getUserTemplate()");
                // check if the userId is null or empty
                if(userId == null || userId.isEmpty()) {
                    LogBuilder.logBuilderFromStringArgs(AbstractViewController.class, "userId", " is null or empty").info();
                    return new ResponseEntity<AbstractHtmlObject>(AbstractHtmlObject.createEmptyAbstractHtmlObject(), HttpStatusCode.valueOf(404));
                }
                // check if the templateName/userId is null or empty
                if(templateName == null || templateName.isEmpty()) {
                    LogBuilder.logBuilderFromStringArgs(AbstractViewController.class, "templateName", " is null or empty").info();
                    return new ResponseEntity<AbstractHtmlObject>(AbstractHtmlObject.createEmptyAbstractHtmlObject(), HttpStatusCode.valueOf(404));
                }

                AbstractCommonUser<USER, USER_META> user = this.userService.getById(userId);

                LOG("DEBUG USER");
                LOG(user.toString());

                /*
                * TODO: get the user from the database
                * I stopped here to work on controllers
                * I'm going to abstract rumpus controllers so I  an retreive users here.
                */
                @SuppressWarnings("unchecked")
                AbstractUserTemplate<USER, USER_META> currentUserTemplate = (AbstractUserTemplate<USER, USER_META>) viewLoader.get(AbstractViews.CURRENT_USER_TEMPLATE_KEY);
                currentUserTemplate.setUser(user);
                LOG("DEBUG currentUserTemplate");
                currentUserTemplate.reload();
                LogBuilder.logBuilderFromStringArgs(AbstractHtmlObject.class, currentUserTemplate.toString()).info();

                return new ResponseEntity<AbstractHtmlObject>(currentUserTemplate.getHead(), HttpStatusCode.valueOf(200));
        }

        /** */
        @GetMapping(ICommonViewController.PATH_COMPONENT_BY_NAME)
        public ResponseEntity<AbstractComponent> getTemplateComponent(
            @PathVariable(ICommonViewController.PATH_VARIABLE_TEMPLATE_BY_NAME) String templateName,
            @PathVariable(ICommonViewController.PATH_VARIABLE_COMPONENT_BY_NAME) String componentName,
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
                    LOG(templateName, " found in viewLoader");
                    retrievedComponent = retrievedTemplate.get(componentName);
                    if(retrievedComponent == null) {
                        LogBuilder.logBuilderFromStringArgs(componentName, " not found in template: null", "  Available components: ").info();
                        LogBuilder.logBuilderFromSet(retrievedTemplate.keySet()).info();
                        httpCode = HttpStatusCode.valueOf(404);
                    } else {
                        LOG(componentName, " found in template");
                    }
                }

                return new ResponseEntity<AbstractComponent>(retrievedComponent != null ? retrievedComponent : AbstractComponent.createEmptyComponent(), httpCode);
        }
        /**
         * Update a particular Template, using @PostMapping, for a particular view. This should take a PathVariable, a Template object to update, and a HttpServletRequest.
         * 
         * TODO: both this and the getter return AbstractHtmlObject. Should I be returning an AbstractTemplate instead? AbstractTemplate is a manager so it would be a map.
         */
        @PostMapping(ICommonViewController.PATH_TEMPLATE_BY_NAME)
        public ResponseEntity<AbstractHtmlObject> updateTemplate(
            @PathVariable(ICommonViewController.PATH_VARIABLE_TEMPLATE_BY_NAME) String templateName,
            AbstractTemplate updatedTemplate,
            HttpServletRequest request) {

                // check if the templateName is null or empty
                if(templateName == null || templateName.isEmpty()) {
                    LogBuilder.logBuilderFromStringArgs("templateName", " is null or empty").info();
                    return new ResponseEntity<AbstractHtmlObject>(AbstractHtmlObject.createEmptyAbstractHtmlObject(), HttpStatusCode.valueOf(404));
                }
                if(updatedTemplate == null) {
                    LogBuilder.logBuilderFromStringArgs("template", " is null").info();
                    return new ResponseEntity<AbstractHtmlObject>(AbstractHtmlObject.createEmptyAbstractHtmlObject(), HttpStatusCode.valueOf(404));
                }

                AbstractTemplate previousTemplate = this.viewLoader.put(templateName, updatedTemplate);
                HttpStatusCode httpCode = previousTemplate != null ? HttpStatusCode.valueOf(200) : HttpStatusCode.valueOf(404);
                return new ResponseEntity<AbstractHtmlObject>(previousTemplate != null ? previousTemplate.getHead() : AbstractHtmlObject.createEmptyAbstractHtmlObject(), httpCode);
        }
}
