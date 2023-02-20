package com.rumpus.common.Session;

import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.config.annotation.SpringSessionDataSource;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.session.SessionRepository;

public class CommonSessionRepository<T extends Session> implements FindByIndexNameSessionRepository<T> {

    private FindByIndexNameSessionRepository<T> sessionRepository;

    public CommonSessionRepository(FindByIndexNameSessionRepository<T> sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    // private JdbcIndexedSessionRepository sessionRepository;
    // public CommonSessionRepository(DataSource ds, TransactionTemplate tt) {
    //     this.sessionRepository = new JdbcIndexedSessionRepository(null, tt)
    // }

    @Override
    public T createSession() {
        return this.sessionRepository.createSession();
    }

    @Override
    public void save(T session) {
        this.sessionRepository.save(session);
    }

    @Override
    public T findById(String id) {
        return this.sessionRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        this.sessionRepository.deleteById(id);
    }

    // TESTING
    void consume() {
        T session = this.sessionRepository.createSession();
        session.setAttribute("test", UUID.randomUUID().toString());
        this.sessionRepository.save(session);
    }

    @Override
    public Map<String, T> findByIndexNameAndIndexValue(String indexName, String indexValue) {
        return this.sessionRepository.findByIndexNameAndIndexValue(indexName, indexValue);
    }
    
}
