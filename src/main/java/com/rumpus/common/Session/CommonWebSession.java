package com.rumpus.common.Session;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.session.Session;
import org.springframework.web.server.WebSession;

import reactor.core.publisher.Mono;
import java.util.concurrent.atomic.AtomicReference;

public class CommonWebSession implements WebSession {

    protected Map<String, Object> attributes;
    private AtomicReference<State> state;
    private final CommonSession session;

    enum State {
		NEW, STARTED
	}
    
    public CommonWebSession(Session session) {
        this.attributes = new HashMap<>();
        this.state = new AtomicReference<>();
        this.session = new CommonSession(session);
    }
    public CommonWebSession(CommonSession session) {
        this.attributes = new HashMap<>();
        this.state = new AtomicReference<>();
        this.session = session;
    }

    @Override
    public void setMaxIdleTime(Duration maxIdleTime) {
        this.session.setMaxInactiveInterval(maxIdleTime);
    }

    @Override
    public Mono<Void> changeSessionId() {
        return Mono.defer(() -> {
			this.session.changeSessionId();
			return save();
		});
    }

    @Override
    public String getId() {
        return this.session.getId().toString();
    }

    @Override
    public Map<String, Object> getAttributes() {
        if(this.attributes != null && !this.attributes.isEmpty()) { // has been initialized, so return. else initialize the map.
            return this.attributes;
        }
        this.attributes = new HashMap<>();
        for(String name : this.session.getAttributeNames()) {
            this.attributes.put(name, this.session.getAttribute(name));
        }
        return this.attributes;
    }

    @Override
    public void start() {
        this.state.compareAndSet(State.NEW, State.STARTED);
    }

    @Override
    public boolean isStarted() {
        State value = this.state.get();
		return (State.STARTED.equals(value) || (State.NEW.equals(value) && !this.session.getAttributeNames().isEmpty()));
    }

    @Override
    public Mono<Void> invalidate() {
        return Mono.defer(() -> {
			this.session.setNoId();
			return save();
		});
    }

    @Override
    public Mono<Void> save() {
        // CommonWebSession.sessionStore.save();
        return null;
    }

    @Override
    public boolean isExpired() {
        return this.session.isExpired();
    }

    @Override
    public Instant getCreationTime() {
        return this.session.getCreationTime();
    }

    @Override
    public Instant getLastAccessTime() {
        return this.session.getLastAccessedTime();
    }

    @Override
    public Duration getMaxIdleTime() {
        return this.session.getMaxInactiveInterval();
    };
}
