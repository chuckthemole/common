package com.rumpus.common;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.http.CacheControl;

import org.slf4j.Logger;

// TODO: @SpringBootApplication is equivalent to using @Configuration, @EnableAutoConfiguration, and @ComponentScan with their default attributes.
// think about customizing the attributes of these annotations. Something to look into
// @Configuration
// @EnableAutoConfiguration
// @Import({ RumpusConfig.class, ChuckConfig.class })

// TODO: I'm having trouble moving these annotations to CommonApplication. See if we can.
@SpringBootApplication
// @EnableJpaRepositories(basePackages = "com.rumpus.rumpus.data")
/**
 * TODO: Started this but was not working. Look into this more.
 * 
 * Common application class for all applications to extend.
 */
abstract public class CommonApplication implements WebMvcConfigurer {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CommonApplication.class);
    
    private static ApplicationContext applicationContext;

    public static void displayAllBeans() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        System.out.println("* * * Beans List * * * ");
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
        System.out.println("* * * Beans List End * * * ");
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        LOGGER.info("App::handleContextRefresh()");
        final org.springframework.core.env.Environment environment = event.getApplicationContext().getEnvironment();
        LOGGER.info("Active profiles: {}", java.util.Arrays.toString(environment.getActiveProfiles()));

        final org.springframework.core.env.MutablePropertySources sources = ((org.springframework.core.env.AbstractEnvironment) environment).getPropertySources();

        java.util.stream.StreamSupport.stream(sources.spliterator(), false)
            .filter(propertySource -> propertySource instanceof org.springframework.core.env.EnumerablePropertySource)
            .map(propertySource -> ((org.springframework.core.env.EnumerablePropertySource) propertySource).getPropertyNames())
            .flatMap(java.util.Arrays::stream)
            .distinct()
            .filter(prop -> !(prop.contains("credentials") || prop.contains("password")))
            .forEach(prop -> LOGGER.info("{}: {}", prop, environment.getProperty(prop)));
    }

    // Added to serve static images from /WEB-INF/images
    // you can access them from /images
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/images/**")
            .addResourceLocations("/WEB-INF/images/") // should I add static in resources here, too?
            .setCacheControl(CacheControl.maxAge(2, java.util.concurrent.TimeUnit.HOURS).cachePublic());
    }
}

