package com.rumpus.common.util;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rumpus.common.ICommon;
import com.rumpus.common.Model.AbstractModel;

public class ReadJson<MODEL extends AbstractModel<MODEL>> implements ICommon {

    private String file; // location of the file
    private Gson gson;
    private Type type; // consider passing in as TypeToken, eg new com.google.gson.reflect.TypeToken<RumpusUser[]>(){}.getType() using import com.google.gson.reflect.TypeToken;

    public ReadJson() {}
    public ReadJson(final String file, final Type type) {
        init(file, type);
    }

    private void init(final String file, final Type type) {
        this.gson = new Gson();
        this.file = file;
        this.type = type;
    }

    public static String readFileAsString(final String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public MODEL[] readModelsFromFile() throws JsonSyntaxException, Exception {
        return gson.fromJson(readFileAsString(this.file), this.type);
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return this.file;
    }
}
