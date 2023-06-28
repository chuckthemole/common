package com.rumpus.common;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public abstract class AbstractCommonTypeAdapter<MODEL extends AbstractModel<MODEL>> extends TypeAdapter<MODEL> {}
