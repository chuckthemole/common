package com.rumpus.common.util;

import java.io.IOException;
import java.lang.OutOfMemoryError;
import java.lang.SecurityException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.JsonParseException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rumpus.common.ICommon;
import com.rumpus.common.Logger.AbstractCommonLogger.LogLevel;
import com.rumpus.common.Model.AbstractModel;


/**
 * Utility class for reading json files.
 */
public class ReadJson<MODEL extends AbstractModel<MODEL, ?>> implements ICommon {

    /**
     * Read a file as a string.
     * 
     * @param file the file to read
     * @return the file as a string
     * @throws IOException if an I/O error occurs reading from the stream
     * @throws OutOfMemoryError if an array of the required size cannot be allocated, for example the file is larger that 2GB
     * @throws SecurityException if the file cannot be read
     */
    public static String readFileAsString(final String file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            LOG_THIS(LogLevel.ERROR, "ReadJson::readFileAsString()::IOException", e.getMessage());
        } catch (OutOfMemoryError e) {
            LOG_THIS(LogLevel.ERROR, "ReadJson::readFileAsString()::OutOfMemoryError", e.getMessage());
        } catch (SecurityException e) {
            LOG_THIS(LogLevel.ERROR, "ReadJson::readFileAsString()::SecurityException", e.getMessage());
        }
        return "";
    }

    /**
     * Read a model from a file.
     * 
     * @param <MODEL> the model type
     * @param file the file to read
     * @param type the type of the model
     * @return the array of models
     * @throws JsonSyntaxException if the json is not valid
     * @throws Exception if the file cannot be read TODO: should be more specific
     */
    public static <MODEL extends AbstractModel<MODEL, ?>> MODEL[] readModelsFromFile(final String file, final Type type) throws JsonSyntaxException, JsonParseException {
        Gson gson = new Gson();
        final String filePath = ReadJson.readFileAsString(file);
        try {
            return gson.fromJson(filePath, type);
        } catch (JsonSyntaxException e) {
            LOG_THIS(LogLevel.ERROR, "ReadJson::readModelsFromFile()::JsonSyntaxException", e.getMessage());
        } catch (JsonParseException e) {
            LOG_THIS(LogLevel.ERROR, "ReadJson::readModelsFromFile()::JsonParseException", e.getMessage());
        }
        return null;
    }

    private static void LOG_THIS(String... args) {
        ICommon.LOG(ReadJson.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(ReadJson.class, level, args);
    }
}
