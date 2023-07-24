package com.rumpus.common.Session;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.session.Session;

import com.google.gson.TypeAdapter;
import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.util.Random;

import jakarta.servlet.http.HttpSession;

public class CommonSession extends AbstractModel<CommonSession> implements Session {

    private static final String NAME = "CommonSession";

    // common attributes
    public static final String UTC_TIME_DIFFERENCE = "UTC_TIME_DIFFERENCE";

    private static final String NO_ID = "-1";
    private static final short ID_LENGTH = 10;
    private static final long DEFAULT_MAX_INACTIVE_INTERVAL = 30;

    // private String id;
    private static Set<String> sessionIds; // make sure session ids are unique
    private final Instant creationTime;
    private Instant lastAccessedTime;
    private Duration maxInactiveInterval;
    private boolean isExpired;

    private Map<String, String> attributes; // TODO: added this member since deleting it in Model. idk how this affects this class. look into it - chuck 6/13/2023

    static {
        CommonSession.sessionIds = new HashSet<>();
    }

    public CommonSession(boolean initialize) {
        super(NAME);
        this.creationTime = Instant.now();
        if(initialize) {
            this.id = getUniqueId();
            CommonSession.sessionIds.add(this.id);
            this.lastAccessedTime = Instant.now();
            this.attributes = new HashMap<>();
            this.maxInactiveInterval = Duration.ofMinutes(DEFAULT_MAX_INACTIVE_INTERVAL);
            this.isExpired = false;
        }
    }
    public CommonSession(Session session) {
        super(NAME);
        this.id = session.getId();
        CommonSession.sessionIds.add(this.id);
        this.creationTime = session.getCreationTime();
        this.lastAccessedTime = session.getLastAccessedTime();
        Set<String> names = session.getAttributeNames();
        this.attributes = new HashMap<>();
        for(String name : names) {
            this.attributes.put(name, session.getAttribute(name));
        }
        this.maxInactiveInterval = session.getMaxInactiveInterval();
        this.isExpired = session.isExpired();
    }
    public CommonSession(HttpSession session) {
        super(NAME);
        this.id = session.getId();
        CommonSession.sessionIds.add(this.id);

        // try to parse creation time, if not, set to now()
        Instant tempCreationTime = Instant.MAX;
        try {
            tempCreationTime = Instant.parse(Long.toString(session.getCreationTime()));
        } catch(DateTimeParseException exception) {
            LOG.info("Unable to parse creation time for HttpSession.");
        }
        if(tempCreationTime == Instant.MAX) {
            tempCreationTime = Instant.now();
        }
        this.creationTime = tempCreationTime;

        // try to parse last access time, if not, set to now()
        this.lastAccessedTime = Instant.MAX;
        try {
            this.lastAccessedTime = Instant.parse(Long.toString(session.getLastAccessedTime()));
        } catch(DateTimeParseException exception) {
            LOG.info("Unable to parse last accessed time for HttpSession.");
        }
        if(this.lastAccessedTime == Instant.MAX) {
            this.lastAccessedTime = Instant.now();
        }

        Set<String> names = new HashSet<>();
        Iterator<String> itr = session.getAttributeNames().asIterator();
        while(itr.hasNext()) {
            names.add(itr.next());
        }

        this.attributes = new HashMap<>();
        for(String name : names) {
            this.attributes.put(name, session.getAttribute(name).toString());
        }

        // try to parse max inactive interval, if not, set to default
        this.maxInactiveInterval = Duration.ZERO;
        try {
            this.maxInactiveInterval = Duration.parse(Integer.toString(session.getMaxInactiveInterval()));
        } catch(DateTimeParseException exception) {
            LOG.info("Unable to parse max inactive interval for HttpSession.");
        }
        if(Duration.ZERO.equals(this.maxInactiveInterval)) {
            this.maxInactiveInterval = Duration.ofMinutes(DEFAULT_MAX_INACTIVE_INTERVAL);
        }

        this.isExpired = Boolean.parseBoolean(session.getAttribute("expired") != null ? session.getAttribute("expired").toString() : "false");
    }
    public CommonSession(
            String id,
            Map<String, String> attributes,
            Instant creationTime,
            Instant lastAccessedTime,
            Duration maxInactiveInterval,
            boolean isExpired) {
        super(NAME);
        this.id = id;
        CommonSession.sessionIds.add(id);
        this.attributes = attributes;
        this.creationTime = creationTime;
        this.lastAccessedTime = lastAccessedTime;
        this.maxInactiveInterval = maxInactiveInterval;
        this.isExpired = isExpired;
    }
    private CommonSession(Instant creationTime) {
        super(NAME);
        this.creationTime = creationTime;
    }
    public static CommonSession create() {
        return new CommonSession(true);
    }
    public static CommonSession create(String id, Map<String, String> attributes, Instant creationTime, Instant lastAccessedTime, Duration maxInactiveInterval, boolean isExpired) {
        CommonSession session = new CommonSession(creationTime);
        session.setId(id);
        for(Map.Entry<String, String> attribute : attributes.entrySet()) {
            session.setAttribute(attribute.getKey(), attribute.getValue());
        }
        session.setLastAccessedTime(lastAccessedTime);
        session.setMaxInactiveInterval(maxInactiveInterval);
        session.setExpired(isExpired);
        return session;
    }
    public static CommonSession createFromMap(Map<String, String> entries) {
        CommonSession session;
        if(entries.containsKey("creation_time")) {
            session = new CommonSession(Instant.parse(entries.get("creation_time")));
        } else {
            session = new CommonSession(Instant.now());
        }
        for(Map.Entry<String, String> entry : entries.entrySet()) {
            if(entry.getKey().equals("id")) {
                session.setId(entry.getValue());
            } else if(entry.getKey().equals("last_accesed_time")) {
                session.setLastAccessedTime(Instant.parse(entry.getValue()));
            } else if(entry.getKey().equals("max_inactive_interval")) {
                session.setMaxInactiveInterval(Duration.parse(entry.getValue()));
            } else if(entry.getKey().equals("expired")) {
                session.setExpired(Boolean.valueOf(entry.getValue()));
            }
        }
        return session;
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
    @SuppressWarnings(UNCHECKED)
    public String getAttribute(String attributeName) {
        final String attribute = this.attributes.get(attributeName);
        return attribute != null ? attribute : "";
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
        this.lastAccessedTime = lastAccessedTime;
    }

    @Override
    public Instant getLastAccessedTime() {
        return this.lastAccessedTime;
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

    private void setExpired(boolean isExpired) {
        this.isExpired = isExpired;
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
    @Override
    public void serialize(CommonSession object, OutputStream outputStream) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'serialize'");
    }
    @Override
    public Map<String, Object> getModelAttributesMap() {
        LOG.info("CommonSession::getModelAtrributesMap()");
        // TODO Auto-generated method stub
        LOG.info("Unimplemented method 'getModelAttributesMap'");
        return Map.of();
    }
    @Override
    public TypeAdapter<CommonSession> createTypeAdapter() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTypeAdapter'");
    }
}
