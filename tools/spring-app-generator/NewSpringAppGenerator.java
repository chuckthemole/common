package tools.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Generator for creating Spring Boot application skeletons.
 * 
 * Usage:
 * 1. Non-interactive (recommended for CI/CD or scripted runs):
 * ./gradlew run -PmainClass=tools.generator.NewSpringAppGenerator
 * --args="AppName com.example.package"
 * 
 * 2. Interactive (prompts for app and package name):
 * ./gradlew run -PmainClass=tools.generator.NewSpringAppGenerator
 * 
 * Features:
 * - Creates a basic Spring Boot main class
 * - Creates a dedicated resources directory with application properties
 * - Can easily be extended to scaffold controllers, services, repositories, and
 * entities
 */
public class NewSpringAppGenerator {

    public static void main(String[] args) {
        String appName;
        String packageName;

        // 1. Read arguments from the command line if available
        if (args.length >= 2) {
            appName = args[0];
            packageName = args[1];
        } else {
            // 2. Fallback to interactive prompt
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter new app name (e.g., RumpusApp): ");
            appName = scanner.nextLine().trim();

            System.out.print("Enter package name (e.g., com.rumpus): ");
            packageName = scanner.nextLine().trim();

            scanner.close();
        }

        try {
            createAppStructure(appName, packageName);
            System.out.println("App skeleton created successfully!");
        } catch (IOException e) {
            System.err.println("Error creating app: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Creates the folder structure and files for a new Spring Boot app.
     * 
     * @param appName     Name of the new application class
     * @param packageName Package where the app class should reside
     * @throws IOException If directories or files cannot be created
     */
    private static void createAppStructure(String appName, String packageName) throws IOException {
        // 1. Convert package name to folder structure
        String baseDir = "src/main/java/" + packageName.replace('.', '/');
        File appDir = new File(baseDir);

        if (!appDir.exists() && !appDir.mkdirs()) {
            throw new IOException("Failed to create directories: " + baseDir);
        }

        // 2. Create main Spring Boot application class
        File appFile = new File(appDir, appName + ".java");
        try (FileWriter writer = new FileWriter(appFile)) {
            writer.write("package " + packageName + ";\n\n");
            writer.write("import org.springframework.boot.SpringApplication;\n");
            writer.write("import org.springframework.boot.autoconfigure.SpringBootApplication;\n\n");
            writer.write("@SpringBootApplication\n");
            writer.write("public class " + appName + " {\n");
            writer.write("    public static void main(String[] args) {\n");
            writer.write("        SpringApplication.run(" + appName + ".class, args);\n");
            writer.write("    }\n");
            writer.write("}\n");
        }

        // 3. Create resources folder and default properties file
        File resourcesDir = new File("src/main/resources");
        if (!resourcesDir.exists() && !resourcesDir.mkdirs()) {
            throw new IOException("Failed to create resources directory");
        }

        File propsFile = new File(resourcesDir, "application-" + appName.toLowerCase() + ".properties");
        try (FileWriter writer = new FileWriter(propsFile)) {
            writer.write("# Properties for " + appName + "\n");
            writer.write("# e.g., server.port=8081\n");
        }

        // Optional: Scaffold standard subfolders (controllers, services, etc.)
        createSubfolder(appDir, "controllers");
        createSubfolder(appDir, "services");
        createSubfolder(appDir, "repositories");
        createSubfolder(appDir, "entities");
    }

    /**
     * Creates a subfolder inside the app directory if it doesn't exist.
     */
    private static void createSubfolder(File parentDir, String subfolderName) throws IOException {
        File subDir = new File(parentDir, subfolderName);
        if (!subDir.exists() && !subDir.mkdirs()) {
            throw new IOException("Failed to create subdirectory: " + subfolderName);
        }
    }
}
