package com.rumpus.common.Config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@SpringBootTest
@Import(CommonConfigTest.TestConfig.class) // Import our test config
class CommonConfigTest {

    @Autowired
    private Environment environment;

    @Autowired
    private AbstractCommonConfig config;

    @Test
    void testPropertiesLoaded() {
        System.out.println("=== Debug Properties ===");
        config.debugProperties();

        // Optionally assert some known properties
        assert environment.getProperty(AbstractCommonConfig.URL) != null : "URL not loaded";
        assert environment.getProperty(AbstractCommonConfig.USER) != null : "USER not loaded";
        assert environment.getProperty(AbstractCommonConfig.DRIVER) != null : "DRIVER not loaded";
    }

    // Minimal concrete subclass of the abstract config
    @Configuration
    static class TestConfig extends AbstractCommonConfig {

        @Autowired
        public TestConfig(Environment environment) {
            super(environment);
        }

        @Override
        public String sqlDialect() {
            // Return a dummy value just for testing
            return "DEFAULT";
        }

        @Override 
        public String toString() {
            return "TODO: implement";
        }
    }
}
