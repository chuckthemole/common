package com.rumpus.common.FileIO;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.rumpus.common.ICommon;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.Model.AbstractModel;

/**
 * Implementation of FileReader for reading JSON files.
 */
final public class JsonIO extends AbstractFileIO {

    /**
     * Gson instance for parsing JSON content.
     */
    private static final Gson gson = new Gson();

    // Private constructor and factory method
    private JsonIO() {}

    public static JsonIO create() {
        return new JsonIO();
    }

    @Override
    public <MODEL extends AbstractModel<MODEL, UUID>> Optional<MODEL> readModelFromFile(String filePath, Type type) {
        final String jsonContent = FileIOUtil.readFileAsString(filePath);

        if (jsonContent.isEmpty()) {
            LOG_THIS(LogLevel.ERROR, "File content is empty or could not be read: " + filePath);
            return Optional.empty();
        }

        try {
            MODEL model = gson.fromJson(jsonContent, type);
            return Optional.ofNullable(model);
        } catch (JsonParseException e) {
            LOG_THIS(LogLevel.ERROR, "Error parsing JSON from file: " + filePath, e.getClass().getSimpleName(), e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public <MODEL extends AbstractModel<MODEL, UUID>> Optional<MODEL[]> readModelsFromFile(String filePath, Type type) {
        final String jsonContent = FileIOUtil.readFileAsString(filePath);

        if (jsonContent.isEmpty()) {
            LOG_THIS(LogLevel.ERROR, "File content is empty or could not be read: " + filePath);
            return Optional.empty();
        }

        try {

            MODEL[] models = gson.fromJson(jsonContent, type);
            return Optional.ofNullable(models);
        } catch (JsonParseException e) {
            LOG_THIS(LogLevel.ERROR, "Error parsing JSON from file: " + filePath, e.getClass().getSimpleName(), e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public <MODEL extends AbstractModel<MODEL, UUID>> boolean writeModelsToFile(String filePath, MODEL[] models) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeModelsToFile'");
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(JsonIO.class, level, args);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
