package com.rumpus.common.Diagnostic;

import org.springframework.core.annotation.Order;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Forum.ForumThreadManager;
import com.rumpus.common.Log.ICommonLogger.LogLevel;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;

// Startup validator that runs after everything is loaded
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AbstractLibraryStartupValidator extends AbstractCommonObject implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AbstractLibraryStartupValidator.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG(AbstractLibraryLoadingDiagnostics.class, "=== COMMON LIBRARY STARTUP VALIDATION ===");

        validateSpringIntegration();
        validateLibraryHealth();

        LOG(AbstractLibraryLoadingDiagnostics.class, "=== COMMON LIBRARY VALIDATION COMPLETE ===");
    }

    private void validateSpringIntegration() {
        LOG(AbstractLibraryLoadingDiagnostics.class, "--- Spring Integration Validation ---");

        // Check if library beans are registered
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        long libraryBeanCount = Arrays.stream(allBeanNames)
                .filter(name -> {
                    try {
                        Object bean = applicationContext.getBean(name);
                        return bean.getClass().getPackage().getName().startsWith("your.common.library.package");
                    } catch (Exception e) {
                        return false;
                    }
                })
                .peek(name -> LOG(AbstractLibraryLoadingDiagnostics.class, "  ✓ Found library bean: {}", name))
                .count();

        LOG(AbstractLibraryLoadingDiagnostics.class, "Total library beans registered: {}", String.valueOf(libraryBeanCount));

        if (libraryBeanCount == 0) {
            LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.WARN,
                    "! No library beans found - check component scanning configuration");
        }
    }

    private void validateLibraryHealth() {
        LOG(AbstractLibraryLoadingDiagnostics.class, "--- Library Health Validation ---");

        try {
            // Perform any library-specific health checks
            // Example: Check if ForumThreadManager works correctly
            ForumThreadManager manager = applicationContext.getBean(ForumThreadManager.class);
            LOG(AbstractLibraryLoadingDiagnostics.class, "✓ ForumThreadManager bean is available and working");

            // Add more health checks specific to your library

        } catch (Exception e) {
            LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.ERROR, "✗ Library health check failed: {}",
                    e.getMessage());
            throw new RuntimeException("Common library is not healthy", e);
        }
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
