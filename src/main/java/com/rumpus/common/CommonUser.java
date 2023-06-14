package com.rumpus.common;

import java.util.function.Function;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.rumpus.common.Builder.CommonStringBuilder;
import com.rumpus.common.Builder.LogBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public abstract class CommonUser<USER extends Model<USER>> extends Model<USER> {

    private static final String NAME = "CommonUser";

    private CommonUserDetails userDetails; // holds username and password among others
    private String userPassword; // used for when user logs in initially to authenticate. Otherwise this should be empty. TODO: Maybe look into better solution for this.
    static private PasswordEncoder encoder;
    private String email;

    static {
        CommonUser.encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // CommonUser.encoder = new BCryptPasswordEncoder()
    }
    
    // Ctors
    public CommonUser() {
        super(NAME);
        super.statement = statement();
        init();
    }
    public CommonUser(String modelName) {
        super(modelName);
        super.statement = statement();
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
        String encodedPassword = CommonUser.encoder.encode(password);
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

    @Override 
    public String toString() {
        return CommonStringBuilder.buildString(
            "\n Name: ", this.name, "\n",
            " Id: ", this.id, "\n",
            " Username: ", this.getUsername(), "\n",
            " Password: ", this.getPassword(), "\n",
            " Email: ", this.email, "\n");
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
