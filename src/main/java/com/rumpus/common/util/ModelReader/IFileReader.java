package com.rumpus.common.util.ModelReader;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.UUID;

import com.rumpus.common.ICommon;
import com.rumpus.common.Model.AbstractModel;

/**
 * Interface defining the contract for reading and parsing files.
 *
 * @param <MODEL> The type of model to be returned after parsing.
 */
public interface IFileReader extends ICommon {
    /**
     * Reads and parses the file at the given path.
     *
     * @param filePath The path of the file to read.
     * @param type The type of model to parse the file content into.
     * @return An Optional containing the parsed models, or an empty Optional if parsing fails.
     */
    public <MODEL extends AbstractModel<MODEL, UUID>> Optional<MODEL[]> readModelsFromFile(String filePath, Type type);
}
