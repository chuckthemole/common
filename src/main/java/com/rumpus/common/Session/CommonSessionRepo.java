package com.rumpus.common.Session;

import java.util.HashMap;
import java.util.Map;

import org.springframework.session.ReactiveSessionRepository;

import reactor.core.publisher.Mono;

public class CommonSessionRepo implements ReactiveSessionRepository<CommonSession> {

    private Map<String, CommonSession> sessions;

    CommonSessionRepo() {
        this.sessions = new HashMap<>();
    }

    @Override
    public Mono<CommonSession> createSession() {
        CommonSession session = new CommonSession(true);
        save(session);
        return Mono.just(session);
    }

    @Override
    public Mono<Void> save(CommonSession session) {
        return Mono.create((mono) -> {
            if(session != null && this.sessions.containsKey(session.getId())) { // is not null and is current session
                // TODO save this session to db
            } else if(session != null && !this.sessions.containsKey(session.getId())) {
                // may want this else if. 
                
            }
            if(session != null) {
                this.sessions.put(session.getId().toString(), session);
            }
        });
    }

    @Override
    public Mono<CommonSession> findById(String id) {
        return Mono.just(this.sessions.get(id));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return Mono.defer(() -> {
            return save(this.sessions.remove(id));
        });
    }
    
}
