package com.rumpus.common;

import java.util.HashSet;
import java.util.Set;

public class ActiveUserStore {
    
    private Set<String> users;

    public ActiveUserStore() {
        this.users = new HashSet<>();
    }

    public Set<String> getUsers() {
        return this.users;
    }

    public boolean isUserActive(String username) {
        return this.users.contains(username) ? true : false;
    }
}
