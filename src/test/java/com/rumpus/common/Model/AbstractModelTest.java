package com.rumpus.common.Model;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Test class for AbstractModel
 * 
 * @see AbstractModel
 */
public class AbstractModelTest {

    private TestModel testModel;

    @BeforeEach
    public void setUp() {
        testModel = new TestModel("Test Model"); // Initialize the TestModel object before each test
    }

    @Test
    public void testGenerateId() {
        assertNotNull(testModel.getId(), "ID should be generated and not null");
    }

    @Test
    public void testValidateId() {
        assertTrue(testModel.validateId(), "ID should be valid after generation");
    }

    @Test
    public void testSetAndGetId() {
        // Object newId = "newId123";
        java.util.UUID newUUID = java.util.UUID.randomUUID();
        testModel.setId(newUUID);
        assertEquals(newUUID, testModel.getId(), "The ID should match the set ID");
    }

    @Test
    public void testCompareTo() {
        TestModel anotherModel = new TestModel("Another Model");
        anotherModel.setId(testModel.getId());
        assertEquals(0, testModel.compareTo(anotherModel), "Models with the same ID should be equal");
    }

    @Test
    public void testTypeAdapterSerialization() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(outputStream));
        testModel.getTypeAdapter().write(jsonWriter, testModel);
        jsonWriter.close();

        String json = outputStream.toString();
        assertTrue(json.contains("\"id\":\"" + testModel.getId().toString() + "\""), "Serialized JSON should contain the model's ID");
    }

    @Test
    public void testTypeAdapterDeserialization() throws IOException {
        final java.util.UUID expected_id = java.util.UUID.randomUUID();
        String json = "{\"id\":\"" + expected_id.toString() + "\"}";
        JsonReader jsonReader = new JsonReader(new java.io.StringReader(json));
        TestModel deserializedModel = testModel.getTypeAdapter().read(jsonReader);

        assertEquals(expected_id, deserializedModel.getId(), "Deserialized model should have the correct ID");
    }

    @Test
    public void testEquals() {
        TestModel anotherModel = new TestModel("Another Model");
        anotherModel.setId(testModel.getId());

        assertTrue(testModel.equals(anotherModel), "Models with the same ID should be equal");
        assertFalse(testModel.equals(null), "Model should not be equal to null");
        assertFalse(testModel.equals(new Object()), "Model should not be equal to an object of a different type");
    }
}
