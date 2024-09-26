package com.rumpus.common.util.ModelReader;

import java.util.Optional;
import java.util.UUID;

import com.rumpus.common.Model.AbstractModel;

import java.lang.reflect.Type;

final public class FileProcessor {

    private final IFileReader fileReader;

    // Constructor accepting a FileReader (e.g., JsonFileReader, XmlFileReader)
    public FileProcessor(IFileReader fileReader) {
        this.fileReader = fileReader;
    }

    /**
     * Process a file and return an array of models.
     * 
     * @param filePath
     * @param type
     * @return
     */
    public <MODEL extends AbstractModel<MODEL, UUID>> Optional<MODEL[]> processFile(String filePath, Type type) {
        return fileReader.readModelsFromFile(filePath, type);
    }
}
