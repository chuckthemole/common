package com.rumpus.common;

import java.lang.reflect.Type;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.rumpus.common.Builder.LogBuilder;

// TODO: I don't think this should be a model. Remove its extention
public class CommonUserDetails extends Model<CommonUserDetails> implements UserDetails {

    private static final String NAME = "CommonUserDetails";
    private static final GrantedAuthority USER = new CommonAuthority(ROLE_USER);

    private String username;
    private String password;
    private boolean isEnabled;
    // private CommonAuthentication authority;
    private Set<GrantedAuthority> authorities;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;

    //  ctors
    private CommonUserDetails() {
        super(NAME);
        this.username = "";
        this.password = "";
        this.isEnabled = true;
        // CommonAuthority auth = new CommonAuthority("USER");
        // this.authority = new CommonAuthentication("", Set.of(auth), "", "", Map.of(), true);
        this.authorities = Set.of(USER);
        this.isAccountNonExpired = false;
        this.isAccountNonLocked = false;
        this.isCredentialsNonExpired = false;
    }
    private CommonUserDetails(UserDetails details) {
        super(NAME);
        this.username = details.getUsername();
        this.password = details.getPassword();
        this.isEnabled = details.isEnabled();
        this.isAccountNonExpired = details.isAccountNonExpired();
        this.isAccountNonLocked = details.isAccountNonLocked();
        this.isCredentialsNonExpired = details.isCredentialsNonExpired();
        // this.authority = new CommonAuthentication(details.getUsername(), getAuthorities(), details.getPassword(), details.getUsername(), Map.of(), details.isAccountNonExpired());
        this.authorities = details.getAuthorities() == null || details.getAuthorities().isEmpty() ? Set.of(USER) : Set.copyOf(details.getAuthorities());
    }
    private CommonUserDetails(
        String username,
        String password,
        boolean isEnabled,
        // CommonAuthentication authority,
        Set<GrantedAuthority> authorities,
        boolean isAccountNonExpired,
        boolean isAccountNonLocked,
        boolean isCredentialsNonExpired) {
            super(NAME);
            this.username = username;
            this.password = password;
            this.isEnabled = isEnabled;
            // this.authority = authority;
            this.authorities = authorities == null || authorities.isEmpty() ? Set.of(USER) : authorities;
            this.isAccountNonExpired = isAccountNonExpired;
            this.isAccountNonLocked = isAccountNonLocked;
            this.isCredentialsNonExpired = isCredentialsNonExpired;
    }

    // factory static methods
    public static CommonUserDetails createEmptyUserDetails() {
        return new CommonUserDetails();
    }
    public static CommonUserDetails createFromUserDetails(UserDetails details) {
        return new CommonUserDetails(details);
    }
    public static CommonUserDetails createFromUsernamePassword(
        String username,
        String password,
        boolean isEnabled) {
            return new CommonUserDetails(username, password, isEnabled, null, false, false, false);
    }
    public static CommonUserDetails createFromUsernamePasswordAuthority(
        String username,
        String password,
        Set<GrantedAuthority> authorities,
        boolean isEnabled) {
            return new CommonUserDetails(username, password, isEnabled, authorities, false, false, false);
    }
    public static CommonUserDetails createWithAll(
        String username,
        String password,
        boolean isEnabled,
        // CommonAuthentication authority,
        Set<GrantedAuthority> authorities,
        boolean isAccountNonExpired,
        boolean isAccountNonLocked,
        boolean isCredentialsNonExpired) {
            return new CommonUserDetails(username, password, isEnabled, authorities, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired);
    }

    // public CommonAuthentication getAuthority() {
    //     return this.authority;
    // }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        // if(authority != null) { // TODO: spring is not liking this. figure out why?
        //     return this.authority.getAuthorities();
        // }
        // return Set.of(); // return empty set if none can be found
        return this.authorities == null || this.authorities.isEmpty() ? Set.of() : this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIsAccountNonExpired(boolean isAccountNonExpired) {
        this.isAccountNonExpired = isAccountNonExpired;
    }

    public void setIsAccountNonLocked(boolean isAccountNonLocked) {
        this.isAccountNonLocked = isAccountNonLocked;
    }

    public void setIsCredentialsNonExpired(boolean isCredentialsNonExpired) {
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    private void checkAndSetAuthoritiesIfEmpty() {
        if(this.authorities == null || this.authorities.isEmpty()) {
            this.authorities.add(USER);
        }
    }

    static private class UserDetailsGsonSerializer extends GsonSerializer<CommonUser<?>> {

        @Override
        public JsonElement serialize(CommonUser<?> user, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty(USERNAME, user.getUsername());
            jsonObj.addProperty(PASSWORD, user.getPassword());
            return jsonObj;
        }
    }
    static public UserDetailsGsonSerializer getSerializer() {
        return new UserDetailsGsonSerializer();
    }

    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n Name: ").append(this.name).append("\n")
            .append(" Username: ").append(this.username).append("\n")
            .append(" Password: ").append(this.password).append("\n")
            .append(" isEnabled: ").append(this.isEnabled);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        LOG.info("CommonUserDetails::equals()");
        if (o == this) {
            return true;
        } else if (!(o instanceof CommonUserDetails)) {
            return false;
        }

        @SuppressWarnings(UNCHECKED)
        CommonUserDetails user = (CommonUserDetails) o;

        boolean flag = true;
        if(!this.usernameIsEqual(user)) {
            LogBuilder log = new LogBuilder("\nUsernames are not equal", "\nUser 1: ", this.username, "\nUser 2: ", user.username);
            log.info();
            flag = false;
        }
        // TODO need to do some work with passwords. come back to this later for equality - chuck 6/8/2023
        // if(!this.passwordIsEqual(user)) {
        //     LogBuilder log = new LogBuilder("\nPasswords are not equal", "\nUser 1: ", this.password, "\nUser 2: ", user.password);
        //     log.info();
        //     flag = false;
        // }
        return flag;
    }

    private boolean usernameIsEqual(CommonUserDetails user) {
        return this.getUsername().equals(user.username) ? true : false;
    }

    private boolean passwordIsEqual(CommonUserDetails user) {
        return this.getPassword().equals(user.password) ? true : false;
    }
}
