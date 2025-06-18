package com.rumpus.common.Diagnostic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Forum.ForumThreadManager;
import com.rumpus.common.Log.ICommonLogger.LogLevel;

import jakarta.annotation.PostConstruct;

// Comprehensive Common Library Loading Diagnostics
public class AbstractLibraryLoadingDiagnostics extends AbstractCommonObject { // TODO: should we just do implements
                                                                              // ICommon?

    private final String LIBRARY_PACKAGE = "com.rumpus.common"; // Replace with your actual package
    private final String LIBRARY_ARTIFACT_NAME = "your-common-library"; // Replace with your artifact name
    private final String LIBRARY_GROUP_ID = "your.group.id"; // Replace with your group ID

    @EventListener
    public void onApplicationStarting(ApplicationStartingEvent event) {
        LOG(AbstractLibraryLoadingDiagnostics.class, "=== APPLICATION STARTING - Full Library Check ===");
        performCompleteLibraryCheck("ApplicationStarting");
    }

    @EventListener
    public void onApplicationEnvironmentPrepared(ApplicationEnvironmentPreparedEvent event) {
        LOG(AbstractLibraryLoadingDiagnostics.class, "=== ENVIRONMENT PREPARED - Full Library Check ===");
        performCompleteLibraryCheck("EnvironmentPrepared");
    }

    @EventListener
    public void onApplicationStarted(ApplicationStartedEvent event) {
        LOG(AbstractLibraryLoadingDiagnostics.class, "=== APPLICATION STARTED - Full Library Check ===");
        performCompleteLibraryCheck("ApplicationStarted");
    }

    @PostConstruct
    public void onPostConstruct() {
        LOG(AbstractLibraryLoadingDiagnostics.class, "=== POST CONSTRUCT - Full Library Check ===");
        performCompleteLibraryCheck("PostConstruct");
    }

    private void performCompleteLibraryCheck(String phase) {
        LOG(AbstractLibraryLoadingDiagnostics.class,
                "=================== COMPLETE LIBRARY CHECK - {} ===================", phase);

        // 1. Check JAR presence and version
        checkJarPresenceAndVersion(phase);

        // 2. Check all library classes
        checkAllLibraryClasses(phase);

        // 3. Check library resources
        checkLibraryResources(phase);

        // 4. Check Maven/Gradle metadata
        checkDependencyMetadata(phase);

        // 5. Check classloader hierarchy
        checkClassLoaderHierarchy(phase);

        // 6. Validate library initialization
        validateLibraryInitialization(phase);

        LOG(AbstractLibraryLoadingDiagnostics.class, "=================== END LIBRARY CHECK - {} ===================",
                phase);
    }

    private void checkJarPresenceAndVersion(String phase) {
        LOG(AbstractLibraryLoadingDiagnostics.class, "--- JAR Presence & Version Check (Phase: {}) ---", phase);

        String classpath = System.getProperty("java.class.path");
        String[] classpathEntries = classpath.split(File.pathSeparator);

        List<String> libraryJars = Arrays.stream(classpathEntries)
                .filter(entry -> entry.contains(LIBRARY_ARTIFACT_NAME))
                .collect(Collectors.toList());

        if (libraryJars.isEmpty()) {
            LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.ERROR,
                    "✗ NO library JARs found in classpath (Phase: {})", phase);
            LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.ERROR, "  Full classpath: {}", classpath);
        } else {
            LOG(AbstractLibraryLoadingDiagnostics.class, "✓ Found {} library JAR(s) (Phase: {}):",
                    String.valueOf(libraryJars.size()), phase);
            libraryJars.forEach(jar -> {
                LOG(AbstractLibraryLoadingDiagnostics.class, "  - {}", jar);
                checkJarContents(jar, phase);
            });
        }
    }

    private void checkJarContents(String jarPath, String phase) {
        try {
            if (jarPath.endsWith(".jar")) {
                try (JarFile jar = new JarFile(jarPath)) {
                    long classCount = jar.stream()
                            .filter(entry -> entry.getName().endsWith(".class"))
                            .filter(entry -> entry.getName().startsWith(LIBRARY_PACKAGE.replace(".", "/")))
                            .count();

                    LOG(AbstractLibraryLoadingDiagnostics.class, "    JAR contains {} library classes",
                            String.valueOf(classCount));

                    // Check for manifest info
                    Manifest manifest = jar.getManifest();
                    if (manifest != null) {
                        Attributes attrs = manifest.getMainAttributes();
                        String version = attrs.getValue("Implementation-Version");
                        String title = attrs.getValue("Implementation-Title");
                        LOG(AbstractLibraryLoadingDiagnostics.class, "    JAR Version: {}, Title: {}", version, title);
                    }
                }
            }
        } catch (IOException e) {
            LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.WARN, "    Could not inspect JAR contents: {}",
                    e.getMessage());
        }
    }

    private void checkAllLibraryClasses(String phase) {
        LOG(AbstractLibraryLoadingDiagnostics.class, "--- All Library Classes Check (Phase: {}) ---", phase);

        try {
            // Get all classes from the library package
            Set<String> libraryClasses = scanForLibraryClasses();

            if (libraryClasses.isEmpty()) {
                LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.ERROR,
                        "✗ No library classes found in package: {} (Phase: {})", LIBRARY_PACKAGE, phase);
            } else {
                LOG(AbstractLibraryLoadingDiagnostics.class, "✓ Found {} library classes (Phase: {})",
                        String.valueOf(libraryClasses.size()), phase);

                int loadedCount = 0;
                int failedCount = 0;

                for (String className : libraryClasses) {
                    try {
                        Class<?> clazz = Class.forName(className);
                        LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.DEBUG, "  ✓ Loaded: {} (ClassLoader: {})",
                                className, clazz.getClassLoader().toString());
                        loadedCount++;
                    } catch (ClassNotFoundException | NoClassDefFoundError e) {
                        LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.ERROR, "  ✗ Failed to load: {} - {}",
                                className, e.getMessage());
                        failedCount++;
                    }
                }

                LOG(AbstractLibraryLoadingDiagnostics.class, "Class loading summary (Phase: {}): {} loaded, {} failed",
                        phase, String.valueOf(loadedCount), String.valueOf(failedCount));

                if (failedCount > 0) {
                    LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.ERROR,
                            "✗ Some library classes failed to load! This indicates a serious issue.");
                }
            }

        } catch (Exception e) {
            LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.ERROR,
                    "✗ Error scanning for library classes (Phase: {}): {}", phase, e.getMessage());
        }
    }

    private Set<String> scanForLibraryClasses() throws IOException {
        Set<String> classNames = new HashSet<>();
        String packagePath = LIBRARY_PACKAGE.replace(".", "/");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(packagePath);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.DEBUG, "Scanning resource: {}", resource.toString());

            if (resource.getProtocol().equals("jar")) {
                scanJarForClasses(resource, packagePath, classNames);
            } else if (resource.getProtocol().equals("file")) {
                scanDirectoryForClasses(new File(resource.getFile()), LIBRARY_PACKAGE, classNames);
            }
        }

        return classNames;
    }

    private void scanJarForClasses(URL resource, String packagePath, Set<String> classNames) throws IOException {
        String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!"));
        try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"))) {
            jar.stream()
                    .filter(entry -> entry.getName().startsWith(packagePath))
                    .filter(entry -> entry.getName().endsWith(".class"))
                    .forEach(entry -> {
                        String className = entry.getName()
                                .replace("/", ".")
                                .replace(".class", "");
                        classNames.add(className);
                    });
        }
    }

    private void scanDirectoryForClasses(File directory, String packageName, Set<String> classNames) {
        if (!directory.exists())
            return;

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    scanDirectoryForClasses(file, packageName + "." + file.getName(), classNames);
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().replace(".class", "");
                    classNames.add(className);
                }
            }
        }
    }

    private void checkLibraryResources(String phase) {
        LOG(AbstractLibraryLoadingDiagnostics.class, "--- Library Resources Check (Phase: {}) ---", phase);

        String[] commonResourcePaths = {
                "META-INF/spring.factories",
                "META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports",
                "application.properties",
                "application.yml",
                "META-INF/MANIFEST.MF"
        };

        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        for (String resourcePath : commonResourcePaths) {
            try {
                Enumeration<URL> resources = cl.getResources(resourcePath);
                List<URL> resourceList = Collections.list(resources);

                if (!resourceList.isEmpty()) {
                    LOG(AbstractLibraryLoadingDiagnostics.class, "  ✓ Found {} instance(s) of {}",
                            String.valueOf(resourceList.size()),
                            resourcePath);
                    resourceList.forEach(
                            url -> LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.DEBUG, "    - {}",
                                    url.toString()));
                } else {
                    LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.DEBUG, "  - No {} found", resourcePath);
                }
            } catch (IOException e) {
                LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.WARN, "  ! Error checking {}: {}", resourcePath,
                        e.getMessage());
            }
        }
    }

    private void checkDependencyMetadata(String phase) {
        LOG(AbstractLibraryLoadingDiagnostics.class, "--- Dependency Metadata Check (Phase: {}) ---", phase);

        // Check Maven metadata
        String pomPath = String.format("META-INF/maven/%s/%s/pom.properties", LIBRARY_GROUP_ID, LIBRARY_ARTIFACT_NAME);
        checkResourceContent(pomPath, "Maven POM properties", phase);

        // Check Gradle metadata
        String gradlePath = String.format("META-INF/gradle/%s/%s/gradle.properties", LIBRARY_GROUP_ID,
                LIBRARY_ARTIFACT_NAME);
        checkResourceContent(gradlePath, "Gradle properties", phase);

        // Check general library info
        checkResourceContent("library.properties", "Library properties", phase);
        checkResourceContent("build.properties", "Build properties", phase);
    }

    private void checkResourceContent(String resourcePath, String description, String phase) {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
            if (is != null) {
                Properties props = new Properties();
                props.load(is);
                LOG(AbstractLibraryLoadingDiagnostics.class, "  ✓ Found {}: {} properties", description,
                        String.valueOf(props.size()));

                // Log important properties
                props.entrySet().stream()
                        .filter(entry -> entry.getKey().toString().contains("version") ||
                                entry.getKey().toString().contains("name") ||
                                entry.getKey().toString().contains("artifact"))
                        .forEach(entry -> LOG(AbstractLibraryLoadingDiagnostics.class,
                                "    - {}: {}",
                                entry.getKey().toString(),
                                entry.getValue().toString()));

                is.close();
            } else {
                LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.DEBUG, "  - No {} found", description);
            }
        } catch (IOException e) {
            LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.WARN, "  ! Error reading {}: {}", description,
                    e.getMessage());
        }
    }

    private void checkClassLoaderHierarchy(String phase) {
        LOG(AbstractLibraryLoadingDiagnostics.class, "--- ClassLoader Hierarchy Check (Phase: {}) ---", phase);

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        int level = 0;

        while (cl != null) {
            LOG(AbstractLibraryLoadingDiagnostics.class, "  Level {}: {} ({})", String.valueOf(level),
                    cl.getClass().getSimpleName(),
                    cl.toString());

            // Check what this classloader can see from our library
            try {
                URL resource = cl.getResource(LIBRARY_PACKAGE.replace(".", "/"));
                if (resource != null) {
                    LOG(AbstractLibraryLoadingDiagnostics.class, "    ✓ Can see library package at: {}",
                            resource.toString());
                }
            } catch (Exception e) {
                LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.DEBUG, "    - Cannot access library package: {}",
                        e.getMessage());
            }

            cl = cl.getParent();
            level++;

            if (level > 10) { // Prevent infinite loops
                LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.WARN,
                        "    ... (stopping at level 10 to prevent infinite loop)");
                break;
            }
        }
    }

    private void validateLibraryInitialization(String phase) {
        LOG(AbstractLibraryLoadingDiagnostics.class, "--- Library Initialization Validation (Phase: {}) ---", phase);

        try {
            // Check if any static initializers have run
            checkStaticFields(phase);

            // Check if Spring components are available
            checkSpringComponents(phase);

            // Try to create instances of key classes
            testInstanceCreation(phase);

        } catch (Exception e) {
            LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.ERROR,
                    "✗ Library initialization validation failed (Phase: {}): {}", phase, e.getMessage());
        }
    }

    private void checkStaticFields(String phase) {
        // Add your specific static field checks here
        // Example:
        try {
            Class<?> rumpusClass = Class.forName("your.package.IRumpus");
            Field field = rumpusClass.getDeclaredField("rumpusForumThreads");
            field.setAccessible(true);
            Object value = field.get(null);

            if (value != null) {
                LOG(AbstractLibraryLoadingDiagnostics.class,
                        "  ✓ IRumpus.rumpusForumThreads is initialized (Phase: {})", phase);
            } else {
                LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.WARN,
                        "  ! IRumpus.rumpusForumThreads is null (Phase: {})", phase);
            }
        } catch (Exception e) {
            LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.ERROR,
                    "  ✗ Cannot check IRumpus static fields (Phase: {}): {}", phase, e.getMessage());
        }
    }

    private void checkSpringComponents(String phase) {
        // This will be called later when ApplicationContext is available
        // For now, just check if component classes exist
        try {
            Set<String> libraryClasses = scanForLibraryClasses();
            long componentCount = libraryClasses.stream()
                    .mapToLong(className -> {
                        try {
                            Class<?> clazz = Class.forName(className);
                            return (clazz.isAnnotationPresent(Component.class) ||
                                    clazz.isAnnotationPresent(Service.class) ||
                                    clazz.isAnnotationPresent(Repository.class) ||
                                    clazz.isAnnotationPresent(Controller.class) ||
                                    clazz.isAnnotationPresent(Configuration.class)) ? 1 : 0;
                        } catch (Exception e) {
                            return 0;
                        }
                    })
                    .sum();

            LOG(AbstractLibraryLoadingDiagnostics.class, "  Found {} Spring component classes in library (Phase: {})",
                    String.valueOf(componentCount), phase);

        } catch (Exception e) {
            LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.WARN,
                    "  Could not scan for Spring components (Phase: {}): {}", phase, e.getMessage());
        }
    }

    private void testInstanceCreation(String phase) {
        // Add tests for creating instances of your key classes
        try {
            // Example - replace with your actual classes
            ForumThreadManager manager = ForumThreadManager.create();
            LOG(AbstractLibraryLoadingDiagnostics.class, "  ✓ Can create ForumThreadManager instances (Phase: {})",
                    phase);
        } catch (Exception e) {
            LOG(AbstractLibraryLoadingDiagnostics.class, LogLevel.ERROR,
                    "  ✗ Cannot create ForumThreadManager instances (Phase: {}): {}", phase, e.getMessage());
        }
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}