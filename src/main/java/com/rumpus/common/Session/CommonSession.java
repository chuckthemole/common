package com.rumpus.common.Session;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.session.Session;

import com.rumpus.common.util.Random;

public class CommonSession implements Session {

    private static final String NO_ID = "-1";
    private static final short ID_LENGTH = 10;
    private static final long DEFAULT_MAX_INACTIVE_INTERVAL = 30;

    private String id;
    private static Set<String> sessionIds; // make sure session ids are unique
    private Map<String, String> attributes;
    private final Instant creationTime;
    private Instant lastAccesedTime;
    private Duration maxInactiveInterval;
    private boolean isExpired;

    public CommonSession() {
        this.id = getUniqueId();
        CommonSession.sessionIds.add(this.id);
        this.creationTime = Instant.now();
        this.lastAccesedTime = Instant.now();
        this.attributes = new HashMap<>();
        this.maxInactiveInterval = Duration.ofMinutes(DEFAULT_MAX_INACTIVE_INTERVAL);
        this.isExpired = false;
    }
    public CommonSession(Session session) {
        this.id = session.getId();
        CommonSession.sessionIds.add(this.id);
        this.creationTime = session.getCreationTime();
        this.lastAccesedTime = session.getLastAccessedTime();
        Set<String> names = session.getAttributeNames();
        this.attributes = new HashMap<>();
        for(String name : names) {
            this.attributes.put(name, session.getAttribute(name));
        }
        this.maxInactiveInterval = session.getMaxInactiveInterval();
        this.isExpired = session.isExpired();
    }
    public CommonSession(String id, Map<String, String> attributes, Instant creationTime, Instant lastAccesedTime, Duration maxInactiveInterval, boolean isExpired) {
        this.id = id;
        CommonSession.sessionIds.add(id);
        this.attributes = attributes;
        this.creationTime = creationTime;
        this.lastAccesedTime = lastAccesedTime;
        this.maxInactiveInterval = maxInactiveInterval;
        this.isExpired = isExpired;
    }
    // public static CommonSession create() {
    //     return new CommonSession<>();
    // }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String changeSessionId() {
        String tempId = getUniqueId();
        sessionIds.add(tempId);
        this.id = tempId;
        return this.id;
    }

    public String setNoId() {
        this.id = NO_ID;
        return NO_ID;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getAttribute(String attributeName) {
        return this.attributes.get(attributeName);
    }

    public Map<String, String> getAttibutes() {
        return this.attributes;
    }

    @Override
    public Set<String> getAttributeNames() {
        return this.attributes.keySet();
    }

    @Override
    public void setAttribute(String attributeName, Object attributeValue) {
        this.attributes.put(attributeName, String.class.cast(attributeValue));
    }

    @Override
    public void removeAttribute(String attributeName) {
        this.attributes.remove(attributeName);
    }

    @Override
    public Instant getCreationTime() {
        return this.creationTime;
    }

    @Override
    public void setLastAccessedTime(Instant lastAccessedTime) {
        this.lastAccesedTime = lastAccessedTime;
    }

    @Override
    public Instant getLastAccessedTime() {
        return this.lastAccesedTime;
    }

    @Override
    public void setMaxInactiveInterval(Duration interval) {
        this.maxInactiveInterval = interval;
    }

    @Override
    public Duration getMaxInactiveInterval() {
        return this.maxInactiveInterval;
    }

    @Override
    public boolean isExpired() {
        return isExpired;
    }
    
    private String getUniqueId() {
        String tempId;
        while(true) {
            tempId = Random.alphaNumericUpper(ID_LENGTH);
            if(!sessionIds.contains(tempId)) {
                break;
            }
        }
        return tempId;
    }
}
