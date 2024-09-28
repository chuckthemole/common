package com.rumpus.common.FileIO;

import java.util.Optional;
import java.util.UUID;

import com.rumpus.common.Model.AbstractModel;

import java.lang.reflect.Type;

/**
 * Class for processing files.
 */
final public class FileProcessor {

    /**
     * The file reader to use for reading and parsing files.
     * <p>
     * Examples:
     * <p>
     * - {@link JsonIO}
     * <p>
     * - {@link XmlIO}
     */
    private final IFileIO fileReader;

    // Constructor accepting a FileReader (e.g., JsonFileReader, XmlFileReader)
    public FileProcessor(IFileIO fileReader) {
        this.fileReader = fileReader;
    }

    /**
     * Process a file and return an array of models.
     * 
     * @param filePath The path of the file to process.
     * @param type The type of model to parse the file content into.
     * @return An Optional containing the parsed models, or an empty Optional if parsing fails.
     */
    public <MODEL extends AbstractModel<MODEL, UUID>> Optional<MODEL[]> processFile(String filePath, Type type) {
        return fileReader.readModelsFromFile(filePath, type);
    }
}
