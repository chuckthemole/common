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
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.TypeAdapter;

public class CommonUser<USER extends Model<USER>> extends Model<USER> {

    private static final String MODEL_NAME = "CommonUser";
    private static final String I_NEED_AUTHORITIES = "WHAT_AUTHORITY";

    private UserBuilder userDetailsBuilder;
    // private UserDetails userDetails;
    static private PasswordEncoder encoder;
    @Expose private String email;
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
        this.userDetailsBuilder = User.withUsername(NO_NAME).password(NO_NAME).authorities(I_NEED_AUTHORITIES, I_NEED_AUTHORITIES);
        if(this.attributes == null || this.attributes.isEmpty()) {
            LOG.info("WARNING: AttributeMap is empty.");
            // this.userDetails.setUserName(NO_NAME);
            this.id = NO_ID;
            this.authId = EMPTY;
            return EMPTY;
        }
        if(this.attributes.containsKey(USERNAME)) {
            // this.userDetails.setUserName(this.attributes.get(USERNAME));
            this.userDetailsBuilder.username(this.attributes.get(USERNAME));
        } else {
            // this.userDetails.setUserName(NO_NAME); // don't need this else
        }
        if(this.attributes.containsKey(ID)) {
            this.setId(this.attributes.get(ID));
        } else {
            this.setId(NO_ID);
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
            this.setPassword(this.attributes.get(PASSWORD));
        } else {
            this.setPassword(NO_NAME);
        }
        this.setStatement(statement());

        // this.userDetails = new CommonUserDetails(detailsBuilder.build());
        return SUCCESS;
    }

    // Can't instantiate if Abstract
    // static factory methods
    // public static CommonUser create(Map<String, String> attributes) {return new CommonUser(attributes);}
    // public static CommonUser createWithName(String name) {
    //     Map<String, String> map = new HashMap<>();
    //     map.put(USERNAME, name);
    //     return CommonUser.create(map);
    // }
    
    // Getters Setters
    public UserDetails getUserDetails() {
        return this.userDetailsBuilder.build();
    }
    public void setUserDetails(UserDetails userDetails) {
        // if(userDetails.getAuthorities().isEmpty()) {

        // }
        // if(this.userDetailsBuilder.build().getAuthorities() == null || this.userDetailsBuilder.build().getAuthorities().isEmpty()) {
        //     this.userDetailsBuilder.authorities(I_NEED_AUTHORITIES);
        // }
        this.userDetailsBuilder = User.withUsername(userDetails.getUsername()).password(userDetails.getPassword()).authorities(userDetails.getAuthorities());
    }
    public String getUsername() {
        return this.userDetailsBuilder.build().getUsername();
    }
    public void setUsername(String name) {
        this.attributes.put(USERNAME, name);
        this.userDetailsBuilder.username(name);
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
        this.attributes.put(EMAIL, email);
    }
    public String getPassword() {
        return this.userDetailsBuilder.build().getPassword();
    }
    public void setPassword(String password) {
        String encodedPassword = CommonUser.encoder.encode(password);
        // UserBuilder builder = User.withUserDetails(userDetails).password(CommonUser.encoder.encode(password));
        this.attributes.put(PASSWORD, encodedPassword);
        this.userDetailsBuilder.password(encodedPassword);
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
        if (o == this) {
            return true;
        } else if (!(o instanceof CommonUser)) {
            return false;
        }

        CommonUser<USER> user = (CommonUser<USER>) o;
        LOG.info("User Name: " + user.getUsername());
        LOG.info("This User Name: " + this.getUsername());
        if(user.getUsername().equals(this.getUsername())) {
            LOG.info("User pass: " + user.getUserDetails().getPassword());
            LOG.info("This User pass: " + this.getUserDetails().getPassword());
            if(user.getUserDetails().getPassword().equals(this.getUserDetails().getPassword())) {
                return true;
            }
        }
        LOG.info("User email: " + user.getEmail());
        LOG.info("This User email: " + this.getEmail());
        if(user.email.equals(this.email)) {
            if(user.getUserDetails().getPassword().equals(this.getUserDetails().getPassword())) {
                return true;
            }
        }
        return false;
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

    static private class UserGsonSerializer extends GsonSerializer<CommonUser<?>> {

        @Override
        public JsonElement serialize(CommonUser<?> user, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty(USERNAME, user.getUsername());
            jsonObj.addProperty(EMAIL, user.email);
            jsonObj.addProperty(PASSWORD, user.getPassword());
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
