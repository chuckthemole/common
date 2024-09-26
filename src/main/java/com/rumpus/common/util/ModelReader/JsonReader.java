package com.rumpus.common.util.ModelReader;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.rumpus.common.ICommon;
import com.rumpus.common.Logger.AbstractCommonLogger.LogLevel;
import com.rumpus.common.Model.AbstractModel;

/**
 * Implementation of FileReader for reading JSON files.
 */
final public class JsonReader extends AbstractFileReader {

    /**
     * Gson instance for parsing JSON content.
     */
    private static final Gson gson = new Gson();

    // Private constructor and factory method
    private JsonReader() {
        super("JsonReader");
    }

    public static JsonReader create() {
        return new JsonReader();
    }

    @Override
    public <MODEL extends AbstractModel<MODEL, UUID>> Optional<MODEL[]> readModelsFromFile(String filePath, Type type) {
        final String jsonContent = ReaderUtil.readFileAsString(filePath);

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

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(JsonReader.class, level, args);
    }
}
