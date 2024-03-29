package com.rumpus.common.User;

import java.util.Map;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rumpus.common.Builder.CommonStringBuilder;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Model.CommonKeyHolder;

/**
 * TODO: let's create an interface for this, ICommonUser. Then we can have an AbstractCommonUser that implements ICommonUser. Then we can have a CommonUser that extends AbstractCommonUser. - chuck 1/15/2024
 */
public abstract class AbstractCommonUser<USER extends AbstractModel<USER>, USER_META extends AbstractCommonUserMetaData<USER_META>> extends AbstractModel<USER> { // TODO: i feeel like I tried this before. but USER extends AbstractCommonUser<USER, USER_META>

    private static final String NAME = "CommonUser";

    @JsonIgnore private String userPassword; // used for when user logs in initially to authenticate. Otherwise this should be empty. TODO: Maybe look into better solution for this.
    static private PasswordEncoder encoder;
    private String email;
    private CommonUserDetails userDetails; // holds username and password among others
    private AbstractCommonUserMetaData<USER_META> metaData;

    static {
        AbstractCommonUser.encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // CommonUser.encoder = new BCryptPasswordEncoder()
    }
    
    // Ctors
    public AbstractCommonUser() {
        super(NAME);
        init();
    }
    public AbstractCommonUser(String modelName) {
        super(modelName);
        init();
    }

    // init fields with empty values. caller should init using setter methods.
    private void init() {
        this.userDetails = CommonUserDetails.createFromUsernamePasswordAuthority(EMPTY_FIELD, EMPTY_FIELD, null, true);
        this.email = EMPTY_FIELD;
        this.id = EMPTY_FIELD;
        // this.id = NO_ID;
        this.key = new CommonKeyHolder(); // doing this for now. should take out later maybe. or set keys in setters
        // this.setStatement(statement()); // do i need to do this?
    }

    public CommonUserDetails getUserDetails() {
        return this.userDetails;
    }
    public void setUserDetails(CommonUserDetails userDetails) {
        this.userDetails = userDetails;
    }
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

    public AbstractCommonUserMetaData<USER_META> getMetaData() {
        return this.metaData;
    }

    public void setMetaData(AbstractCommonUserMetaData<USER_META> metaData) {
        this.metaData = metaData;
    }

    @Override 
    public String toString() {
        return CommonStringBuilder.buildString(
            "\n Name: ", this.name, "\n",
            " Id: ", this.id, "\n",
            " Username: ", this.getUsername(), "\n",
            " Password: ", this.getPassword(), "\n",
            " Email: ", this.email, "\n",
            this.metaData.toString());
    }

    @Override
    public boolean equals(Object o) {
        LOG("CommonUser::equals()");
        if (o == this) {
            return true;
        } else if (!(o instanceof AbstractCommonUser)) {
            return false;
        }

        @SuppressWarnings(UNCHECKED)
        AbstractCommonUser<USER, USER_META> user = (AbstractCommonUser<USER, USER_META>) o;

        boolean flag = true;
        if(!this.usernameIsEqual(user)) {
            LogBuilder log = new LogBuilder(true, "\nUsernames are not equal", "\nUser 1: ", this.getUsername(), "\nUser 2: ", user.getUsername());
            log.info();
            flag = false;
        }
        if(!this.idIsEqual(user)) {
            LogBuilder log = new LogBuilder(true, "\nIds are not equal", "\nUser 1: ", this.getId(), "\nUser 2: ", user.getId());
            log.info();
            flag = false;
        }
        // TODO need to do some work with passwords. come back to this later for equality - chuck 6/8/2023
        // if(!this.passwordIsEqual(user)) {
        //     LogBuilder log = new LogBuilder(true, "\nPasswords are not equal", "\nUser 1: ", this.getPassword(), "\nUser 2: ", user.getPassword());
        //     log.info();
        //     flag = false;
        // }
        if(!this.emailIsEqual(user)) {
            LogBuilder log = new LogBuilder(true, "\nEmails are not equal", "\nUser 1: ", this.getEmail(), "\nUser 2: ", user.getEmail());
            log.info();
            flag = false;
        }
        if(!this.userDetailsIsEqual(user)) {
            LogBuilder log = new LogBuilder(true, "\nUser Details are not equal", "\nUser 1: ", this.getUserDetails().toString(), "\nUser 2: ", user.getUserDetails().toString());
            log.info();
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getModelAttributesMap() {
        LOG("AbstractCommonUser::getModelAttributesMap()");
        Map<String, Object> modelAttributesMap = Map.of(ID, this.id, EMAIL, this.getEmail());
        return modelAttributesMap;
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
