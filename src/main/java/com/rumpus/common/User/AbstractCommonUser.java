package com.rumpus.common.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rumpus.common.ICommon;
import com.rumpus.common.Builder.CommonStringBuilder;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Model.AbstractModel;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/**
 * Abstract Common User
 * 
 * @param <USER> the user type
 * @param <USER_META> the user meta data type
 */
@MappedSuperclass
public abstract class AbstractCommonUser<
        USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>
    > extends AbstractModel<USER, java.util.UUID> {

        /******************************************************************************
         *                       Member fields                                        *
         *****************************************************************************/
        
        @JsonIgnore private String userPassword; // used for when user logs in initially to authenticate. Otherwise this should be empty. TODO: Maybe look into better solution for this.
    
        /**
         * The static field for password encoder
         */
        static private PasswordEncoder encoder;

        /**
         * The user email
         */
        @Column(name = "email") private String email;

        /**
         * The {@link CommonUserDetails} object. 
         * This holds the username, password, and other user details.
         */
        private CommonUserDetails userDetails;

        /**
         * The {@link AbstractCommonUserMetaData} object.
         * TODO: should I move this to AbstractModel?
         */
        private USER_META metaData;

        static {
            AbstractCommonUser.encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder(); // TODO: can I inject this?
            // CommonUser.encoder = new BCryptPasswordEncoder()
        }
    
        /******************************************************************************
         *                       Constructors                                         *
         *****************************************************************************/

        public AbstractCommonUser() {
            init();
        }

        // init fields with empty values. caller should init using setter methods.
        private void init() {
            this.userDetails = CommonUserDetails.createFromUsernamePasswordAuthority(EMPTY_FIELD, EMPTY_FIELD, null, true);
            this.email = EMPTY_FIELD;
            this.setId(ICommon.EMPTY_UUID);
        }

        /******************************************************************************
         *                      access/getters/setter                                 *
         * ----------------------------------------------------------------------------
         *  Purpose: Isn't it obvious                                                 *
         * ----------------------------------------------------------------------------
         *****************************************************************************/

        public CommonUserDetails getUserDetails() {
            if(this.userDetails == null) {
                this.userDetails = CommonUserDetails.createEmptyUserDetails();
            }
            return this.userDetails;
        }

        public void setUserDetails(CommonUserDetails userDetails) {
            if(userDetails == null) {
                this.userDetails = CommonUserDetails.createEmptyUserDetails();
            } else {
                this.userDetails = userDetails;
            }
        }

        // public void setUserDetails(UserDetails userDetails) {
        //     this.userDetails = CommonUserDetails.createFromUserDetails(userDetails);
        // }

        public String getUsername() {
            return this.userDetails.getUsername();
        }

        public void setUsername(String name) {
            this.userDetails.setUsername(name);
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return this.userDetails.getPassword();
        }

        public void setPassword(String password) {
            this.userPassword = password;
            String encodedPassword = AbstractCommonUser.encoder.encode(password);
            String strippedPass = encodedPassword.replaceFirst("\\{bcrypt\\}", ""); // TODO: think about how to do this. maybe just compare with the bcrypt in front.
            this.userDetails.setPassword(strippedPass);
        }

        // @JsonIgnore
        public String getUserPassword() {
            if(this.userPassword.equals(null) || this.userPassword.isEmpty()) {
                return String.valueOf("");
            }
            return this.userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public USER_META getMetaData() {
            return this.metaData;
        }

        public void setMetaData(USER_META metaData) {
            this.metaData = metaData;
        }

        @Override 
        public String toString() {
            return CommonStringBuilder.buildString(
                "\n Class: ", this.getClass().getSimpleName(), "\n",
                " Id: ", this.getId().toString(), "\n",
                " Username: ", this.getUsername(), "\n",
                " Password: ", this.getPassword(), "\n",
                " Email: ", this.email, "\n",
                this.metaData.toString());
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof AbstractCommonUser)) {
                return false;
            }

            @SuppressWarnings(UNCHECKED)
            AbstractCommonUser<USER, USER_META> user = (AbstractCommonUser<USER, USER_META>) o;

            boolean flag = true;
            if(!this.usernameIsEqual(user)) {
                LogBuilder log = LogBuilder.logBuilderFromStringArgs(
                    "\nUsernames are not equal", "\nUser 1: ",
                    this.getUsername(),
                    "\nUser 2: ",
                    user.getUsername());
                LOG(log.toString());
                flag = false;
            }
            if(!this.getId().equals(user.getId())) {
                LogBuilder log = LogBuilder.logBuilderFromStringArgs(
                    "\nIds are not equal", "\nUser 1: ",
                    this.getId().toString(),
                    "\nUser 2: ",
                    user.getId().toString());
                LOG(log.toString());
                flag = false;
            }
            // TODO need to do some work with passwords. come back to this later for equality - chuck 6/8/2023
            // if(!this.passwordIsEqual(user)) {
            //     LogBuilder log = LogBuilder.logBuilderFromStringArgs("\nPasswords are not equal", "\nUser 1: ", this.getPassword(), "\nUser 2: ", user.getPassword());
            //     log.info();
            //     flag = false;
            // }
            if(!this.emailIsEqual(user)) {
                LogBuilder log = LogBuilder.logBuilderFromStringArgs(
                    "\nEmails are not equal",
                    "\nUser 1: ",
                    this.getEmail(),
                    "\nUser 2: ",
                    user.getEmail());
                LOG(log.toString());
                flag = false;
            }
            if(!this.userDetailsIsEqual(user)) {
                LogBuilder log = LogBuilder.logBuilderFromStringArgs(
                    "\nUser Details are not equal",
                    "\nUser 1: ",
                    this.getUserDetails().toString(),
                    "\nUser 2: ",
                    user.getUserDetails().toString());
                LOG(log.toString());
                flag = false;
            }
            return flag;
        }

        @Override
        public int compareTo(USER o) {
            return this.getUsername().compareTo(o.getUsername());
        }

        // member values to check for equality
        private boolean usernameIsEqual(AbstractCommonUser<USER, USER_META> user) {
            return this.getUsername().equals(user.getUsername()) ? true : false;
        }

        // TODO check why I have 2 password getters/setters
        private boolean passwordIsEqual(AbstractCommonUser<USER, USER_META> user) {
            return this.getPassword().equals(user.getPassword()) ? true : false;
        }

        private boolean emailIsEqual(AbstractCommonUser<USER, USER_META> user) {
            return this.getEmail().equals(user.getEmail()) ? true : false;
        }

        private boolean userDetailsIsEqual(AbstractCommonUser<USER, USER_META> user) {
            return this.getUserDetails().equals(user.getUserDetails()) ? true : false;
        }
}
