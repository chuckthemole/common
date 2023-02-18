package com.rumpus.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.session.SessionRepository;

public interface ISessionRepository extends SessionRepository<CommonSession>  {
}
