package com.rumpus.common.Config;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.Log.LogItem.LogItemCollectionManager;
import com.rumpus.common.Server.Port.IPort;
import com.rumpus.common.Service.JwtService;
import com.rumpus.common.Cloud.Aws.AwsS3BucketProperties;

import com.rumpus.common.Cloud.Aws.IAwsS3BucketProperties;
import com.rumpus.common.Config.Properties.yaml.YamlPropertySourceFactory;


import jakarta.annotation.PostConstruct;

/**
 * Common config for web app. Using jdbc template right now. Should abstract
 * this to allow other impls.
 * <p>
 * Create a properties.yml file in the resources directory. This file will
 * contain the properties for the application.
 * <p>
 * The properties file should contain the following properties:
 * <p>
 * 
 * <pre>
 * properties:
 *  port: 8080
 *  datasource:
 *      url: jdbc:coolsql://localhost:6666/
 *      username: rumpus
 *      password: rumpus
 *      driver: org.coolsql.Driver
 * </pre>
 * 
 * TODO: think about making this into an annotation.
 * 
 * @author: Chuck Thomas
 */
@ConfigurationProperties(prefix = "properties")
@PropertySource(value = "classpath:properties.yml", factory = YamlPropertySourceFactory.class)
public abstract class AbstractCommonConfig extends AbstractCommonObject { // TODO: Can this just implement ICommon?

    protected static final String BEAN_PORT_MANAGER = "portManager";
    protected static final String BEAN_JDBC_USER_DETAILS_MANAGER = "jdbcUserDetailsManager";
    protected static final String BEAN_AUTHENTICATION_PROVIDER = "authenticationProvider";
    protected static final String BEAN_PASSWORD_ENCODER = "passwordEncoder";
    protected static final String BEAN_DATA_SOURCE = "dataSource";
    protected static final String BEAN_SQL_DIALECT = "sqlDialect";

    protected Environment environment;

    @Autowired
    protected ApplicationContext applicationContext;

    // Scopes: https://www.baeldung.com/spring-bean-scopes
    /**
     * The container will create a single instance of this bean.
     */
    protected final String SCOPE_SINGLETON = "singleton";
    /**
     * The container will create a new instance of the bean each time a request for
     * that specific bean is made.
     */
    protected final String SCOPE_PROTOTYPE = "prototype";

    /**
     * The log level for this application.
     */
    private static volatile LogLevel g_logLevel = LogLevel.DEBUG2;

    ////////////////////////////////////////////////////////////////
    // Properties: list of properties in the properties.yml file. //
    ////////////////////////////////////////////////////////////////
    // DataSource properties
    protected static final String URL = "properties.datasource.url";
    protected static final String USER = "properties.datasource.username";
    protected static final String DRIVER = "properties.datasource.driver";
    protected static final String PASSWORD = "properties.datasource.password";
    protected static final String PORT = "port";
    // Cloud properties
    public static final String S3_BUCKET_NAME_PROPERTY = "properties.cloud.aws.s3.bucket";
    public static final String S3_ACCESS_KEY_PROPERTY = "properties.cloud.aws.credentials.access-key";
    public static final String S3_SECRET_ACCESS_KEY_PROPERTY = "properties.cloud.aws.credentials.secret-key";
    public static final String S3_REGION_PROPERTY = "properties.cloud.aws.s3.region";
    // Redis properties
    protected static final String REDIS_HOST = "properties.redis.host";
    protected static final String REDIS_PORT = "properties.redis.port";
    // JWT
    public static final String JWT_SECRET = "properties.jwt.secret";
    public static final String JWT_EXPIRATION = "properties.jwt.expiration";
    public static final String JWT_SECRET_VALUE_ANNOTATION = "${properties.jwt.secret}";
    public static final String JWT_SECRET_EXPIRATION_ANNOTATION = "${properties.jwt.expiration}";
    // OAUTH2
    public static final String OAUTH2_GOOGLE_CLIENT_ID = "properties.oauth2.client.registration.google.client-id";
    public static final String OAUTH2_GOOGLE_CLIENT_SECRET = "properties.oauth2.client.registration.google.client-secret";
    // CORS Allowed Origins
    public static final String CORS_ALLOWED_FRONTEND_ORIGINS = "properties.frontend.origins"; // TODO:
                                                                                              // environment.getProperty()
                                                                                              // has a tough time
                                                                                              // parsing to List
    public static final String CORS_ALLOWED_FRONTEND_ALLOWED_METHODS = "properties.frontend.methods";

    public AbstractCommonConfig(Environment environment) {

        this.environment = environment;
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    // @org.springframework.boot.context.properties.ConfigurationProperties(prefix =
    // "datasource")
    protected DataSource dataSource() {

        // A few checks before we proceed. This checks the environment for the
        // properties we need.
        if (this.environment == null) {
            LOG("Environment is null.");
            return null;
        }
        if (this.environment.getProperty(AbstractCommonConfig.USER) == null) {
            LOG("USER is null.");
            return null;
        }
        if (this.environment.getProperty(AbstractCommonConfig.PASSWORD) == null) {
            LOG("PASSWORD is null.");
            return null;
        }
        if (this.environment.getProperty(AbstractCommonConfig.DRIVER) == null) {
            LOG("DRIVER is null.");
            return null;
        }
        if (this.environment.getProperty(AbstractCommonConfig.URL) == null) {
            LOG("URL is null.");
            return null;
        }

        // If we get here, we have the properties we need. Let's create the data source.
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(this.environment.getProperty(AbstractCommonConfig.URL));
        driverManagerDataSource.setUsername(this.environment.getProperty(AbstractCommonConfig.USER));
        driverManagerDataSource.setPassword(this.environment.getProperty(AbstractCommonConfig.PASSWORD));
        driverManagerDataSource.setDriverClassName(this.environment.getProperty(AbstractCommonConfig.DRIVER));
        return driverManagerDataSource;
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();

        jdbcUserDetailsManager.setDataSource(dataSource());
        return jdbcUserDetailsManager;
    }

    @Bean
    abstract public String sqlDialect(); // TODO: Can we have this return an enum?

    @Bean
    @DependsOn({ AbstractCommonConfig.BEAN_DATA_SOURCE, AbstractCommonConfig.BEAN_SQL_DIALECT })
    public DSLContext dslContext() {
        SQLDialect sqlDialect;
        try {
            sqlDialect = SQLDialect.valueOf(this.sqlDialect());
        } catch (IllegalArgumentException e) {
            sqlDialect = SQLDialect.DEFAULT;
        }
        return DSL.using(this.dataSource(), sqlDialect);
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        RedisStandaloneConfiguration redisStandaloneConfiguration = jedisConFactory.getStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(
                this.environment.getProperty(AbstractCommonConfig.REDIS_HOST));
        redisStandaloneConfiguration.setPort(Integer.parseInt(
                this.environment.getProperty(AbstractCommonConfig.REDIS_PORT)));
        return jedisConFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    @DependsOn(AbstractCommonConfig.BEAN_JDBC_USER_DETAILS_MANAGER)
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(jdbcUserDetailsManager());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public IPort applicationPort() {
        return com.rumpus.common.Server.Port.Port.create(this.environment.getProperty(AbstractCommonConfig.PORT));
    }

    // TODO: Should I create a cloud config class? and put this bean in there?
    @Bean
    @Scope(SCOPE_SINGLETON)
    public IAwsS3BucketProperties awsS3Bucket() {
        if (!this.environment.containsProperty(AbstractCommonConfig.S3_BUCKET_NAME_PROPERTY) ||
                !this.environment.containsProperty(AbstractCommonConfig.S3_ACCESS_KEY_PROPERTY) ||
                !this.environment.containsProperty(AbstractCommonConfig.S3_SECRET_ACCESS_KEY_PROPERTY) ||
                !this.environment.containsProperty(AbstractCommonConfig.S3_REGION_PROPERTY)) {
            LOG("One or more of the S3 properties are missing. Returning null for AwsS3Bucket bean.");
            return AwsS3BucketProperties.createEmpty();
        }
        final String bucketName = this.environment.getProperty(AbstractCommonConfig.S3_BUCKET_NAME_PROPERTY);
        final String accessKey = this.environment.getProperty(AbstractCommonConfig.S3_ACCESS_KEY_PROPERTY);
        final String secretAccessKey = this.environment.getProperty(AbstractCommonConfig.S3_SECRET_ACCESS_KEY_PROPERTY);
        final String region = this.environment.getProperty(AbstractCommonConfig.S3_REGION_PROPERTY);

        IAwsS3BucketProperties awsS3Bucket = AwsS3BucketProperties.create(bucketName, accessKey, secretAccessKey,
                region);
        LOG("Created AwsS3Bucket bean with name: ", awsS3Bucket.getBucketName());
        return awsS3Bucket;
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public LogItemCollectionManager logManager() {
        LogItemCollectionManager manager = LogItemCollectionManager.createWithMainAndAdmin();
        return manager;
    }

    /**
     * Get the log level for this application.
     */
    public static LogLevel getLogLevel() {
        return g_logLevel;
    }

    /**
     * Set the log level for this application.
     */
    public static void setLogLevel(LogLevel logLevel) {
        g_logLevel = logLevel;
    }

    // TODO: come back to this. Allow for different config types, i.e. yaml,
    // properties, etc.
    // not using right now...
    // @Bean
    // @Profile("yaml_config")
    // public Environment yamlEnvironment() {
    // return this.applicationContext.getEnvironment();
    // }

    @PostConstruct
    protected void debugProperties() {
        printIfExists(URL);
        printIfExists(USER);
        printIfExists(DRIVER);
        printIfExists(PASSWORD);
        printIfExists(PORT);

        printIfExists(S3_BUCKET_NAME_PROPERTY);
        printIfExists(S3_ACCESS_KEY_PROPERTY);
        printIfExists(S3_SECRET_ACCESS_KEY_PROPERTY);
        printIfExists(S3_REGION_PROPERTY);

        printIfExists(REDIS_HOST);
        printIfExists(REDIS_PORT);

        printIfExists(JWT_SECRET);
        printIfExists(JWT_EXPIRATION);

        printIfExists(OAUTH2_GOOGLE_CLIENT_ID);
        printIfExists(OAUTH2_GOOGLE_CLIENT_SECRET);

        printIfExists(CORS_ALLOWED_FRONTEND_ORIGINS);
        printIfExists(CORS_ALLOWED_FRONTEND_ALLOWED_METHODS);
    }

    private void printIfExists(String propertyKey) {
        String value = environment.getProperty(propertyKey);
        if (value != null) {
            System.out.println(propertyKey + " = " + value);
        } else {
            System.out.println(propertyKey + " is not defined");
        }
    }
}
