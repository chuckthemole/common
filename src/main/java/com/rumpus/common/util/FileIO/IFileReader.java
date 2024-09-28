package com.rumpus.common.util.FileIO;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.UUID;
import com.rumpus.common.ICommon;
import com.rumpus.common.Model.AbstractModel;

/**
 * Interface defining the contract for reading, writing, and handling file operations.
 *
 * @param <MODEL> The type of model to be returned after parsing.
 */
public interface IFileReader extends ICommon {

    /**
     * Reads and parses multiple models from the file at the given path.
     *
     * @param filePath The path of the file to read.
     * @param type The type of model to parse the file content into.
     * @return An Optional containing the parsed models, or an empty Optional if parsing fails.
     */
    public <MODEL extends AbstractModel<MODEL, UUID>> Optional<MODEL[]> readModelsFromFile(String filePath, Type type);

    /**
     * Reads and parses a single model from the file at the given path.
     *
     * @param filePath The path of the file to read.
     * @param type The type of model to parse the file content into.
     * @return An Optional containing the parsed model, or an empty Optional if parsing fails.
     */
    public <MODEL extends AbstractModel<MODEL, UUID>> Optional<MODEL> readModelFromFile(String filePath, Type type);

    /**
     * Writes the given models to a file at the specified path.
     *
     * @param filePath The path of the file to write.
     * @param models The array of models to write to the file.
     * @return true if the write operation succeeds, false otherwise.
     */
    public <MODEL extends AbstractModel<MODEL, UUID>> boolean writeModelsToFile(String filePath, MODEL[] models);

    /**
     * Validates whether the file at the given path exists and is in a valid format.
     *
     * @param filePath The path of the file to validate.
     * @return true if the file is valid, false otherwise.
     */
    public boolean isValidFile(String filePath);

    /**
     * Reads the raw content of the file at the specified path.
     *
     * @param filePath The path of the file to read.
     * @return An Optional containing the raw file content as a String, or an empty Optional if reading fails.
     */
    public Optional<String> readRawFileContent(String filePath);

    /**
     * Returns the last error encountered during file operations.
     *
     * @return An Optional containing the error message, or an empty Optional if no error occurred.
     */
    public Optional<String> getLastError();

    /**
     * Retrieves metadata information about the file, such as size and modification date.
     *
     * @param filePath The path of the file to retrieve metadata for.
     * @return An Optional containing file metadata, or an empty Optional if the file does not exist.
     */
    public Optional<FileMetadata> getFileMetadata(String filePath);

    // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
    // TODO: Thinking about this strategy. Leaving here for now.
    // Below gives an example how to implement IModelParser interface and json parser
    // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
    
    /**
     * Reads and parses models from a file using a custom parser.
     *
     * @param filePath The path of the file to read.
     * @param parser A custom parser to use for parsing the file content into models.
     * @return An Optional containing the parsed models, or an empty Optional if parsing fails.
     */
    // public <MODEL extends AbstractModel<MODEL, UUID>> Optional<MODEL[]> readWithCustomParser(String filePath, IModelParser<MODEL> parser);

    // /**
    //  * Interface defining the contract for parsing models from raw file content.
    //  *
    //  * @param <MODEL> The type of model to be parsed.
    //  */
    // public interface IModelParser<MODEL> {

    //     /**
    //      * Parses a single model from raw content.
    //      *
    //      * @param rawContent The raw content of the file to be parsed.
    //      * @return An Optional containing the parsed model, or an empty Optional if parsing fails.
    //      */
    //     Optional<MODEL> parseModel(String rawContent);

    //     /**
    //      * Parses multiple models from raw content.
    //      *
    //      * @param rawContent The raw content of the file to be parsed.
    //      * @return An Optional containing an array of parsed models, or an empty Optional if parsing fails.
    //      */
    //     Optional<MODEL[]> parseModels(String rawContent);
    // }

    // public class JsonModelParser<MODEL> implements IModelParser<MODEL> {

    //     @Override
    //     public Optional<MODEL> parseModel(String rawContent) {
    //         // Custom JSON parsing logic for a single model
    //     }
    
    //     @Override
    //     public Optional<MODEL[]> parseModels(String rawContent) {
    //         // Custom JSON parsing logic for multiple models
    //     }
    // }
    

}
