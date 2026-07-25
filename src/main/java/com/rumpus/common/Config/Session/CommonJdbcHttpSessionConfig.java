package com.rumpus.common.Config.Session;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

/**
 * Enables Spring Session with JDBC-backed HTTP session storage.
 *
 * <p>
 * By default, Spring Boot stores {@code HttpSession} data in memory within the
 * embedded servlet container (Tomcat, Jetty, etc.). As a result:
 * <ul>
 * <li>All active sessions are lost when the application restarts.</li>
 * <li>Sessions cannot be shared across multiple application instances.</li>
 * </ul>
 *
 * <p>
 * Importing this configuration enables Spring Session JDBC, causing session
 * data to be persisted in the application's relational database instead of
 * server memory. This allows sessions to survive application restarts and
 * enables multiple application instances to share authenticated user sessions.
 *
 * <h2>Typical use cases</h2>
 * <ul>
 * <li>Applications using Spring Security with form-based authentication.</li>
 * <li>Applications using OAuth2 Login where authenticated users are stored in
 * an {@code HttpSession}.</li>
 * <li>Traditional MVC web applications that maintain server-side session
 * state.</li>
 * <li>Applications deployed behind a load balancer where session sharing
 * between instances is required.</li>
 * </ul>
 *
 * <h2>When this configuration is usually unnecessary</h2>
 * <ul>
 * <li>Stateless REST APIs.</li>
 * <li>Applications using JWT or other token-based authentication.</li>
 * <li>Applications that do not use {@code HttpSession}.</li>
 * </ul>
 *
 * <h2>Requirements</h2>
 * <ul>
 * <li>A configured {@link javax.sql.DataSource}.</li>
 * <li>Spring Session JDBC on the classpath.</li>
 * <li>The Spring Session database schema. Spring Boot can initialize the schema
 * automatically using:
 *
 * <pre>
 * spring:
 *   session:
 *     store-type: jdbc
 *     jdbc:
 *       initialize-schema: always
 * </pre>
 *
 * </li>
 * </ul>
 *
 * <p>
 * Spring Boot automatically configures the required {@code DataSource},
 * transaction manager, and session filter in the typical single-database
 * application. This configuration simply enables JDBC-backed session storage.
 */
@Configuration
@EnableJdbcHttpSession
public class CommonJdbcHttpSessionConfig {
}
