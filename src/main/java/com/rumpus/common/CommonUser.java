package com.rumpus.common;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.AttributedCharacterIterator.Attribute;
import java.io.IOException;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.Builder.LogBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.TypeAdapter;

// TODO: think about making this class abstract
public class CommonUser<USER extends Model<USER>> extends Model<USER> {

    private static final String MODEL_NAME = "CommonUser";
    private static final String I_NEED_AUTHORITIES = "WHAT_AUTHORITY";

    // private UserBuilder userDetailsBuilder;
    private CommonUserDetails userDetails; // holds username/password among others
    private String userPassword; // used for when user logs in initially to authenticate. Otherwise this should be empty. TODO: Maybe look into better solution for this.
    static private PasswordEncoder encoder;
    private String email;
    private int authId;

    static {
        CommonUser.encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // CommonUser.encoder = new BCryptPasswordEncoder()
    }
    
    // Ctors
    public CommonUser() {
        super(MODEL_NAME);
        super.statement = statement();
        init();
    }
    public CommonUser(String modelName) {
        super(modelName);
        super.statement = statement();
        init();
    }
    public CommonUser(Map<String, String> attributes) {
        super(MODEL_NAME, attributes);
        init();
    }
    public CommonUser(String modelName, Map<String, String> attributes) {
        super(modelName, attributes);
        init();
    }

    @Override
    public int init() {
        // this.userDetails = new CommonUserDetails();
        // CommonUser.encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // this.userDetailsBuilder = User.withUsername(NO_NAME).password(NO_NAME).authorities(ROLE_USER, ROLE_EMPLOYEE); // maybe just give USER role to begin with
        // this.userDetailsBuilder = User.builder().authorities(ROLE_USER, ROLE_EMPLOYEE);
        // UserBuilder builder = User.builder().authorities(null);

        this.userDetails = CommonUserDetails.createFromUsernamePasswordAuthority(NO_NAME, NO_PASS, null, true);
        
        if(this.attributes == null || this.attributes.isEmpty()) {
            StringBuilder sbLogInfo = new StringBuilder();
            sbLogInfo.append("\nAttributeMap is empty\n").append("This may not be needed depending on the use.");
            LOG.info(sbLogInfo.toString());
            this.id = NO_ID; // TODO: give Ids?
            this.authId = EMPTY; // TODO: do I need this?
            return EMPTY;
        }
        if(this.attributes.containsKey(USERNAME)) {
            // this.userDetails.setUserName(this.attributes.get(USERNAME));
            // this.userDetailsBuilder.username(this.attributes.get(USERNAME));
            this.userDetails.setUsername(this.attributes.get(USERNAME));
        } else {
            // this.attributes.put(USERNAME, this.getUsername());
            // this.userDetails.setUserName(NO_NAME); // don't need this else
        }
        if(this.attributes.containsKey(ID)) {
            this.setId(this.attributes.get(ID));
        } else {
            // todo maybe?
        }
        if(this.attributes.containsKey(AUTH_ID)) {
            this.setAuth(Integer.parseInt(this.attributes.get(AUTH_ID)));
        } else {
            this.setAuth(EMPTY);
        }
        if(this.attributes.containsKey(EMAIL)) {
            this.setEmail(this.attributes.get(EMAIL));
        } else {
            this.setEmail(NO_NAME);
        }
        if(this.attributes.containsKey(PASSWORD)) {
            this.userDetails.setPassword(this.attributes.get(PASSWORD));
            this.setUserPassword(userPassword);
            // this.setPassword(this.attributes.get(PASSWORD));
        } else {
            this.userDetails.setPassword(this.attributes.get(NO_PASS));
            this.setUserPassword(NO_PASS);
            // this.setPassword(NO_PASS);
        }
        this.setStatement(statement());

        // this.userDetails = new CommonUserDetails(detailsBuilder.build());
        return SUCCESS;
    }
    
    // Getters Setters

    // public UserBuilder getUserBuilder() {
    //     return this.userDetailsBuilder;
    // }

    // public void setUserBuilder(String username, String password) {
    //     this.userDetailsBuilder = User.withUsername(username).password(password).authorities(ROLE_USER, ROLE_EMPLOYEE);
    // }

    public CommonUserDetails getUserDetails() {
        // return this.userDetailsBuilder.build();
        return this.userDetails;
    }
    public void setUserDetails(CommonUserDetails userDetails) {
        // if(userDetails.getAuthorities().isEmpty()) {

        // }
        // if(this.userDetailsBuilder.build().getAuthorities() == null || this.userDetailsBuilder.build().getAuthorities().isEmpty()) {
        //     this.userDetailsBuilder.authorities(I_NEED_AUTHORITIES);
        // }
        // this.userDetailsBuilder = User.withUsername(userDetails.getUsername()).password(userDetails.getPassword()).authorities(userDetails.getAuthorities());
        this.userDetails = userDetails;
    }
    public String getUsername() {
        // return this.userDetailsBuilder.build().getUsername();
        return this.userDetails.getUsername();
    }
    public void setUsername(String name) {
        this.attributes.put(USERNAME, name);
        // this.userDetailsBuilder.username(name);
        this.userDetails.setUsername(name);
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
        this.attributes.put(EMAIL, email);
    }
    public String getPassword() {
        // return this.userDetailsBuilder.build().getPassword();
        return this.userDetails.getPassword();
    }
    public void setPassword(String password) {
        this.userPassword = password;
        String encodedPassword = CommonUser.encoder.encode(password);
        String strippedPass = encodedPassword.replaceFirst("\\{bcrypt\\}", "");
        // UserBuilder builder = User.withUserDetails(userDetails).password(CommonUser.encoder.encode(password));

        this.attributes.put(PASSWORD, strippedPass);
        // this.userDetailsBuilder.password(strippedPass);
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
    public int getAuth() {
        return this.authId;
    }
    public void setAuth(int a) {
        this.attributes.put(AUTH_ID, Integer.toString(a));
        this.authId = a;
    }

    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n Name: ").append(this.name).append("\n")
            .append(" Email: ").append(this.email).append("\n")
            .append(" UserName: ").append(this.getUsername()).append("\n")
            .append(" Password: ").append(this.getPassword()).append("\n")
            .append(" AuthId: ").append(this.authId).append("\n")
            .append("  Attributes:\n");
            if(this.attributes == null || this.attributes.isEmpty()) {
                sb.append("    No Attributes\n");
            }
            for(Map.Entry<String, String> entry : this.attributes.entrySet()) {
                sb.append("    ").append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
            }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        LOG.info("CommonUser::equals()");
        if (o == this) {
            return true;
        } else if (!(o instanceof CommonUser)) {
            return false;
        }

        @SuppressWarnings(UNCHECKED)
        CommonUser<USER> user = (CommonUser<USER>) o;

        boolean flag = true;
        if(!this.usernameIsEqual(user)) {
            LogBuilder log = new LogBuilder("\nUsernames are not equal", "\nUser 1: ", this.getUsername(), "\nUser 2: ", user.getUsername());
            log.info();
            flag = false;
        }
        if(!this.idIsEqual(user)) {
            LogBuilder log = new LogBuilder("\nIds are not equal", "\nUser 1: ", this.getId(), "\nUser 2: ", user.getId());
            log.info();
            flag = false;
        }
        // TODO need to do some work with passwords. come back to this later for equality - chuck 6/8/2023
        // if(!this.passwordIsEqual(user)) {
        //     LogBuilder log = new LogBuilder("\nPasswords are not equal", "\nUser 1: ", this.getPassword(), "\nUser 2: ", user.getPassword());
        //     log.info();
        //     flag = false;
        // }
        if(!this.emailIsEqual(user)) {
            LogBuilder log = new LogBuilder("\nEmails are not equal", "\nUser 1: ", this.getEmail(), "\nUser 2: ", user.getEmail());
            log.info();
            flag = false;
        }
        if(!this.userDetailsIsEqual(user)) {
            LogBuilder log = new LogBuilder("\nUser Details are not equal", "\nUser 1: ", this.getUserDetails().toString(), "\nUser 2: ", user.getUserDetails().toString());
            log.info();
            flag = false;
        }
        return flag;
    }

    // member values to check for equality
    private boolean usernameIsEqual(CommonUser<USER> user) {
        return this.getUsername().equals(user.getUsername()) ? true : false;
    }

    // private boolean idIsEqual(CommonUser<USER> user) {
    //     return this.getId().equals(user.getId()) ? true : false;
    // }

    // TODO check why I have 2 password getters/setters
    private boolean passwordIsEqual(CommonUser<USER> user) {
        return this.getPassword().equals(user.getPassword()) ? true : false;
    }

    private boolean emailIsEqual(CommonUser<USER> user) {
        return this.getEmail().equals(user.getEmail()) ? true : false;
    }

    private boolean userDetailsIsEqual(CommonUser<USER> user) {
        return this.getUserDetails().equals(user.getUserDetails()) ? true : false;
    }

    private Function<PreparedStatement, PreparedStatement> statement() {
        return(
            (PreparedStatement statement) -> {
                try {
                    // debugUser();
                    // statement.setString(1, this.getUserDetails().getPassword());
                    statement.setString(1, this.email);
                    statement.setString(2, this.getUsername());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return  statement;
            }
        );
    }

    private int debugUser() {
        LOG.info("User statement()");
        StringBuilder sb = new StringBuilder();
        sb.append("  User name: ").append(this.getUsername());
        LOG.info(sb.toString());
        sb.setLength(0); // clear sb
        sb.append("  User email: ").append(this.email);
        LOG.info(sb.toString());
        sb.setLength(0);
        sb.append("  User password: ").append(this.getUserDetails().getPassword());
        LOG.info(sb.toString());
        return SUCCESS;
    }

    // TODO: abstract this serializer class for models
    static private class UserGsonSerializer extends GsonSerializer<CommonUser<?>> {

        @Override
        public JsonElement serialize(CommonUser<?> user, Type typeOfSrc, JsonSerializationContext context) {
            LOG.info("UserGsonSerializer::serialize()");
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty(USERNAME, user.getUsername());
            jsonObj.addProperty(EMAIL, user.email);
            jsonObj.addProperty(PASSWORD, user.getPassword());
            jsonObj.addProperty(ID, user.getId());
            LOG.info(jsonObj.toString());
            return jsonObj;
        }
    }
    static public UserGsonSerializer getSerializer() {
        return new UserGsonSerializer();
    }

    // public class CommonUserAdapter extends TypeAdapter<CommonUser<USER>> {

    //     @Override
    //     public void write(JsonWriter out, CommonUser<USER> user) throws IOException {
    //         out.beginObject(); 
    //         out.name(USERNAME);
    //         out.value(user.getUsername());
    //         out.name(EMAIL);
    //         out.value(user.getEmail());
    //         out.name(PASSWORD);
    //         out.value(user.getPassword());
    //         out.endObject();
    //     }

    //     @Override
    //     public CommonUser<USER> read(JsonReader in) throws IOException {
    //         CommonUser<USER> user = new CommonUser<>();
    //         in.beginObject();
    //         String fieldname = null;

    //         while (in.hasNext()) {
    //             JsonToken token = in.peek();
                
    //             if (token.equals(JsonToken.NAME)) {
    //                 //get the current token 
    //                 fieldname = in.nextName(); 
    //             }
                
    //             if (USERNAME.equals(fieldname)) {
    //                 //move to next token
    //                 token = in.peek();
    //                 user.setUsername(in.nextString());
    //             }
                
    //             if(EMAIL.equals(fieldname)) {
    //                 //move to next token
    //                 token = in.peek();
    //                 user.setEmail(in.nextString());
    //             }
    //         }
    //         in.endObject();
    //         return user;
    //     }
    // }

    // private class Details extends CommonUserDetails {
    //     Details() {super();}
    // }
}
