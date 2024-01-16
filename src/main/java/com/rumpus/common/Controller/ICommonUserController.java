package com.rumpus.common.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rumpus.common.Service.AbstractUserService;
import com.rumpus.common.Session.CommonSession;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.views.Template.IUserTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface ICommonUserController
    <
        /////////////////////////
        // Define generics here//
        /////////////////////////
        USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>,
        USER_SERVICE extends AbstractUserService<USER, USER_META>,
        USER_TEMPLATE extends IUserTemplate<USER, USER_META>
    > extends ICommonController {

        ///////////
        // Paths //
        ///////////
        public static final String PATH_USER = "/user";
        public static final String PATH_GET_USERS = "/users";
        public static final String PATH_GET_USERS_BY_SORT = "/users/{sort}";
        public static final String PATH_DELETE_USER = "/delete_user";
        public static final String PATH_UPDATE_USER = "/update_user";
        public static final String PATH_VALUE_GET_BY_USER_NAME = "/get_user_by_name/{username}";
        public static final String PATH_VALUE_GET_BY_USER_ID = "/user/{id}";
        public static final String PATH_VARIABLE_GET_BY_USER_NAME = "username";
        public static final String PATH_VARIABLE_GET_BY_USER_ID = "id";
        public static final String PATH_VARIABLE_SORT = "sort";

        /**
         * Get all users
         * 
         * @param sort the {@link com.rumpus.common.User.AbstractCommonUserCollection.Sort} (as a String) to sort by
         * @param session the {@link HttpSession} to use
         * @return a list of all users as a {@link ResponseEntity}
         */
        @GetMapping(value = ICommonUserController.PATH_GET_USERS_BY_SORT)
        public ResponseEntity<java.util.List<USER>> getAllUsers(@PathVariable(ICommonUserController.PATH_VARIABLE_SORT) String sort, HttpSession session);

        /**
         * Submit a new user to be created
         * 
         * @param newUser the {@link USER} to create
         * @param request the {@link HttpServletRequest} to use
         * @return the {@link CommonSession} as a {@link ResponseEntity}
         */
        @PostMapping(value = ICommonUserController.PATH_USER)
        public ResponseEntity<CommonSession> userSubmit(@RequestBody USER newUser, HttpServletRequest request);

        /**
         * Delete a user
         * 
         * @param user the {@link USER} to delete
         * @param request the {@link HttpServletRequest} to use
         * @return the {@link CommonSession} as a {@link ResponseEntity}
         */
        @PostMapping(value = ICommonUserController.PATH_DELETE_USER)
        public ResponseEntity<CommonSession> deleteUser(@RequestBody String user, HttpServletRequest request);

        /**
         * Update a user
         * 
         * @param user the {@link USER} to update
         * @param request the {@link HttpServletRequest} to use
         * @return the {@link CommonSession} as a {@link ResponseEntity}
         */
        @PostMapping(value = ICommonUserController.PATH_UPDATE_USER)
        public ResponseEntity<CommonSession> updateUser(@RequestBody USER user, HttpServletRequest request);

        /**
         * Get a user by username
         * 
         * @TODO this should be secured so user info is not visible
         * @param username the username of the {@link USER} to get
         * @param request the {@link HttpServletRequest} to use
         * @return the {@link USER} as a {@link ResponseEntity}
         */
        @GetMapping(value = ICommonUserController.PATH_VALUE_GET_BY_USER_NAME)
        public ResponseEntity<USER> getUserByUsername(@PathVariable(ICommonUserController.PATH_VARIABLE_GET_BY_USER_NAME) String username, HttpServletRequest request);

        /**
         * Get a user by id
         * 
         * @TODO this should be secured so user info is not visible
         * @param id the id of the {@link USER} to get
         * @param request the {@link HttpServletRequest} to use
         * @return the {@link USER} as a {@link ResponseEntity}
         */
        @GetMapping(value = ICommonUserController.PATH_VALUE_GET_BY_USER_ID)
        public ResponseEntity<USER> getUserById(@PathVariable(ICommonUserController.PATH_VARIABLE_GET_BY_USER_ID) String id, HttpServletRequest request);

        /**
         * Get the current user
         * 
         * @param authentication the {@link Authentication} to use
         * @return the {@link USER} as a {@link ResponseEntity}
         */
        @GetMapping(value = "current_user")
        public ResponseEntity<USER> getCurrentUser(Authentication authentication);
    
}
