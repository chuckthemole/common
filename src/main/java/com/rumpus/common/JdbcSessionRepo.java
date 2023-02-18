package com.rumpus.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;

public class JdbcSessionRepo extends JdbcIndexedSessionRepository {

    public JdbcSessionRepo(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        super(jdbcTemplate, transactionTemplate);
    }
    public JdbcSessionRepo(JdbcOperations jdbcOperations, TransactionOperations transactionOperations) {
        super(jdbcOperations, transactionOperations);
    }

    // private static final String NAME = "JdbcSession";
    // private Map<String, CommonSession> sessions;
    // private JdbcTemplate jdbcTemplate;
    // private TransactionTemplate transactionTemplate;
    // // private JdbcIndexedSessionRepository sessionRepository;
    
    // // JdbcSessionRepo(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
    // //     this.sessions = new HashMap<>();
    // //     // sessionRepository = new JdbcIndexedSessionRepository(jdbcTemplate, transactionTemplate);
    // // }

    // @Override
    // public CommonSession createSession() {
    //     CommonSession session = new CommonSession(this.sessionRepository.createSession());
    //     save(session);
    //     return session;
    // }

    // @Override
    // public void save(CommonSession session) {
    //     if(session != null && this.sessions.containsKey(session.getId())) { // is not null and is current session
    //         // TODO save this session to db
    //     } else if(session != null && !this.sessions.containsKey(session.getId())) {
    //         // may want this else if.
    //     }
    //     if(session != null) {
    //         this.sessions.put(session.getId(), session);
    //     }
    //     sessionRepository.save((Session) session);
    // }

    // @Override
    // public CommonSession findById(String id) {
    //     return sessions.get(id);
    // }

    // @Override
    // public void deleteById(String id) {
    //     this.sessions.remove(id);
    //     removeFromDB(id);
    // }

    // private int removeFromDB(String id) {
    //     // TODO implementation
    //     return SUCCESS;
    // }
}
