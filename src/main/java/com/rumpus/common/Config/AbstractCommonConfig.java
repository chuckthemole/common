package com.rumpus.common.Config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

/**
 * Common config for web app. Using jdbc template right now. Should abstract this to allow other impls.
 * <p>
 * Create a properties.yml file in the resources directory. This file will contain the properties for the application.
 * <p>
 * The properties file should contain the following properties:
 * <p>
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
@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "properties")
@org.springframework.context.annotation.PropertySource(value = "classpath:properties.yml", factory = com.rumpus.common.Config.Properties.YamlPropertySourceFactory.class)
public abstract class AbstractCommonConfig extends com.rumpus.common.AbstractCommonObject {

    protected static final String BEAN_PORT_MANAGER = "portManager";
    protected static final String BEAN_JDBC_USER_DETAILS_MANAGER = "jdbcUserDetailsManager";
    protected static final String BEAN_AUTHENTICATION_PROVIDER = "authenticationProvider";
    protected static final String BEAN_PASSWORD_ENCODER = "passwordEncoder";
    protected static final String BEAN_DATA_SOURCE = "dataSource";

    protected Environment environment;
    @Autowired protected ApplicationContext applicationContext;

    // Scopes: https://www.baeldung.com/spring-bean-scopes
    /**
     * The container will create a single instance of this bean.
     */
    protected final String SCOPE_SINGLETON = "singleton";
    /**
     * The container will create a new instance of the bean each time a request for that specific bean is made.
     */
    protected final String SCOPE_PROTOTYPE = "prototype";

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

    public AbstractCommonConfig(String name, Environment environment) {
        super(name);
        this.environment = environment;
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    // @org.springframework.boot.context.properties.ConfigurationProperties(prefix = "datasource")
	protected DataSource dataSource() {

        // A few checks before we proceed. This checks the environment for the properties we need.
        if(this.environment == null) {
            LOG_THIS("Environment is null.");
            return null;
        }
        if(this.environment.getProperty(AbstractCommonConfig.USER) == null) {
            LOG_THIS("USER is null.");
            return null;
        }
        if(this.environment.getProperty(AbstractCommonConfig.PASSWORD) == null) {
            LOG_THIS("PASSWORD is null.");
            return null;
        }
        if(this.environment.getProperty(AbstractCommonConfig.DRIVER) == null) {
            LOG_THIS("DRIVER is null.");
            return null;
        }
        if(this.environment.getProperty(AbstractCommonConfig.URL) == null) {
            LOG_THIS("URL is null.");
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

        // CommonJdbcUserManager manager = new CommonJdbcUserManager(dataSource);
        // manager.manager().setUsersByUsernameQuery(SET_USERS_QUERY);
		// return manager.manager();
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
    public com.rumpus.common.Server.Port.IPort applicationPort() {
        return com.rumpus.common.Server.Port.Port.create(this.environment.getProperty(AbstractCommonConfig.PORT));
    }

    // TODO: Should I create a cloud config class? and put this bean in there?
    @Bean
    @Scope(SCOPE_SINGLETON)
    public com.rumpus.common.Cloud.Aws.IAwsS3BucketProperties awsS3Bucket() {
        LOG_THIS("Creating AwsS3Bucket bean...");
        final String bucketName = this.environment.getProperty(AbstractCommonConfig.S3_BUCKET_NAME_PROPERTY);
        final String accessKey = this.environment.getProperty(AbstractCommonConfig.S3_ACCESS_KEY_PROPERTY);
        final String secretAccessKey = this.environment.getProperty(AbstractCommonConfig.S3_SECRET_ACCESS_KEY_PROPERTY);
        final String region = this.environment.getProperty(AbstractCommonConfig.S3_REGION_PROPERTY);
        if(bucketName == null) {
            LOG_THIS("S3_BUCKET_NAME_PROPERTY is null. Returning null for AwsS3Bucket bean.");
            return null;
        }
        if(accessKey == null) {
            LOG_THIS("S3_ACCESS_KEY_PROPERTY is null. Returning null for AwsS3Bucket bean.");
            return null;
        }
        if(secretAccessKey == null) {
            LOG_THIS("S3_SECRET_ACCESS_KEY_PROPERTY is null. Returning null for AwsS3Bucket bean.");
            return null;
        }
        if(region == null) {
            LOG_THIS("S3_REGION_PROPERTY is null. Returning null for AwsS3Bucket bean.");
            return null;
        }
        com.rumpus.common.Cloud.Aws.IAwsS3BucketProperties awsS3Bucket = com.rumpus.common.Cloud.Aws.AwsS3BucketProperties.create(bucketName, accessKey, secretAccessKey, region);
        LOG_THIS("Created AwsS3Bucket bean with name: ", awsS3Bucket.getBucketName());
        return awsS3Bucket;
    }

    // TODO: come back to this. Allow for different config types, i.e. yaml, properties, etc.
    // not using right now...
    // @Bean
    // @Profile("yaml_config")
    // public Environment yamlEnvironment() {
    //     return this.applicationContext.getEnvironment();
    // }

    private void LOG_THIS(String... args) {
        com.rumpus.common.Builder.LogBuilder.logBuilderFromStringArgsNoSpaces(AbstractCommonConfig.class, args).info();
    }
}
