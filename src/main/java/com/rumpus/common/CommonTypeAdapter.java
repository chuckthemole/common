package com.rumpus.common;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public abstract class CommonTypeAdapter<MODEL extends Model<MODEL>> extends TypeAdapter<MODEL> {}