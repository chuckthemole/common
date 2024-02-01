package com.rumpus.common.Session;

import org.springframework.session.Session;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDestroyedEvent;

import com.rumpus.common.AbstractCommonObject;

public class SessionEvent extends AbstractCommonObject {

    private static final String NAME = "SessionEvent";

    protected Created createdEvent;
    protected Destroyed destroyedEvent;
    protected boolean isCreated;
    protected boolean isDestroyed;

    public SessionEvent() {
        super(NAME);
        this.isCreated = false;
        this.isDestroyed = false;
        this.createdEvent = null;
        this.destroyedEvent = null;
    }
    public static SessionEvent create() {
        return new SessionEvent();
    }

    public void create(Object source, Session session) {
        this.createdEvent = new Created(source, session);
        this.isCreated = true;
    }
    public void destroy(Object source, Session session) {
        if(isCreated) {
            this.destroyedEvent = new Destroyed(source, session);
            this.isDestroyed = true;
        } else {
            LOG("ERROR: can't destory what was not created.");
        }
    }

    public class Created extends SessionCreatedEvent {

        public Created(Object source, Session session) {
            super(source, session);
        }
        // public static Created create(Object source, Session session) {
        //     return new Created(source, session);
        // }
    }

    public class Destroyed extends SessionDestroyedEvent{

        public Destroyed(Object source, Session session) {
            super(source, session);
        }

    }
}
