package com.rumpus.common.Session;

import com.rumpus.common.Dao;

import org.springframework.session.SessionRepository;

public class CommonSessionRepository extends Dao<CommonSession> implements SessionRepository<CommonSession> {

    private static final String NAME = "CommonSessionRepository";
    private static final String TABLE = "CommonSession";

    private SessionEvent event;
    
    public CommonSessionRepository() {
        super(TABLE, NO_META_TABLE, NAME);
    }
    public CommonSessionRepository(String table, String name) {
        super(table, NO_META_TABLE, name);
        this.event = new SessionEvent();
    }

    @Override
    public CommonSession createSession() {
        LOG.info("CommonSessionRepository::createSession()");
        CommonSession session = new CommonSession(true);
        event.create(this, session);
        return session;
    }

    @Override
    public void save(CommonSession session) {
        LOG.info("CommonSessionRepository::save()");
        super.add(session);
    }

    @Override
    public CommonSession findById(String id) {
        LOG.info("CommonSessionRepository::findById()");
        CommonSession session = super.get(id);
        if(session == null) {
            session = new CommonSession(true);
            session.setId(id);
        }
        return session;
    }

    @Override
    public void deleteById(String id) {
        LOG.info("CommonSessionRepository::deleteById()");
        this.event.destroy(this, this.findById(id));
        super.remove(Integer.valueOf(id));
    }
}
