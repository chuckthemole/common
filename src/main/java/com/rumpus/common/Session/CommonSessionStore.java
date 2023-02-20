package com.rumpus.common.Session;

import org.springframework.web.server.WebSession;
import org.springframework.web.server.session.WebSessionStore;

import reactor.core.publisher.Mono;

// import org.springframework.boot.autoconfigure.web.ServerProperties.Reactive.Session;

public class CommonSessionStore implements WebSessionStore {

	// private final ReactiveSessionRepository<CommonSession> sessions;
    private final CommonSessionRepo sessions;

	public CommonSessionStore(CommonSessionRepo reactiveSessionRepository) {
		this.sessions = reactiveSessionRepository;
	}

    @Override
    public Mono<WebSession> createWebSession() {
        return Mono.just(new CommonWebSession(sessions.createSession().block()));
    }

    @Override
    public Mono<WebSession> retrieveSession(String sessionId) {
        return Mono.just(new CommonWebSession(this.sessions.findById(sessionId).block()));
    }

    @Override
    public Mono<Void> removeSession(String sessionId) {
        return this.sessions.deleteById(sessionId);
    }

    @Override
    public Mono<WebSession> updateLastAccessTime(WebSession webSession) {
        CommonSession session = sessions.findById(webSession.getId()).block();
        session.setLastAccessedTime(webSession.getLastAccessTime());
        sessions.save(session);
        return Mono.just(new CommonWebSession(session));
    }

}
