package com.rumpus.common;

import java.util.Map;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import com.google.gson.annotations.Expose;

public class CommonUserDetails extends Model<CommonUserDetails> implements UserDetails {

    private static final String NAME = "CommonUserDetails";

    @Expose private String username;
    private String password;
    private boolean isEnabled;
    private CommonAuthentication authority;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;

    protected CommonUserDetails() {
        super(NAME);
        this.username = "";
        this.password = "";
        this.isEnabled = true;
        CommonAuthority auth = new CommonAuthority("USER");
        this.authority = new CommonAuthentication("", Set.of(auth), "", "", Map.of(), true);
        this.isAccountNonExpired = false;
        this.isAccountNonLocked = false;
        this.isCredentialsNonExpired = false;
    }
    protected CommonUserDetails(UserDetails details) {
        super(NAME);
        this.username = details.getUsername();
        this.password = details.getPassword();
        this.isEnabled = details.isEnabled();
        this.isAccountNonExpired = details.isAccountNonExpired();
        this.isAccountNonLocked = details.isAccountNonLocked();
        this.isCredentialsNonExpired = details.isCredentialsNonExpired();
        this.authority = new CommonAuthentication(details.getUsername(), getAuthorities(), details.getPassword(), details.getUsername(), Map.of(), details.isAccountNonExpired());
    }
    protected CommonUserDetails(
            CommonAuthentication authority,
            String password,
            String username,
            boolean isAccountNonExpired,
            boolean isAccountNonLocked,
            boolean isCredentialsNonExpired,
            boolean isEnabled) {
        super(NAME);
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.authority = authority;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }

    public CommonAuthentication getAuthority() {
        return this.authority;
    }

    @Override
    public Set<CommonAuthority> getAuthorities() {
        if(authority != null) { // TODO: spring is not liking this. figure out why?
            return this.authority.getAuthorities();
        }
        return Set.of(); // return empty set if none can be found
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

    public void setAuthorities(CommonAuthentication authority) {
        this.authority = authority;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String username) {
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
}
