package com.rumpus.common.FileIO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Type;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rumpus.common.Model.User.TestUserModel;

/**
 * Test class for the FileProcessor class.
 */
public class FileProcessorTest {

    private static final String JSON_USERS_FILE = "src/test/java/com/rumpus/common/test_data/users/test_users.json";
    private static final String JSON_USERS_FILE_EMPTY = "src/test/java/com/rumpus/common/test_data/users/empty_users_file.json";
    private static final String JSON_USERS_FILE_INVALID = "src/test/java/com/rumpus/common/test_data/users/invalid_users_file.json";

    @Mock
    private IFileReader mockFileReader;

    @InjectMocks
    private FileProcessor fileProcessor;

    private final Type type = TestUserModel[].class;
    private final int userModelsLength = 3;
    private final TestUserModel[] userModels = new TestUserModel[] {
        TestUserModel.create("username1", "password1", "email1@email.com"),
        TestUserModel.create("username2", "password2", "email2@email.com"),
        TestUserModel.create("username3", "password3", "email3@email.com")
    };

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessFile_Success() {
        
        // Mock the file reader response
        when(this.mockFileReader.<TestUserModel>readModelsFromFile(FileProcessorTest.JSON_USERS_FILE, this.type)).thenReturn(Optional.of(this.userModels));

        // Act
        Optional<TestUserModel[]> result = this.fileProcessor.processFile(FileProcessorTest.JSON_USERS_FILE, this.type);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(this.userModelsLength, result.get().length);
        assertEquals("username1", result.get()[0].getUsername());
        assertEquals("email1@email.com", result.get()[0].getEmail());
        assertEquals("username2", result.get()[1].getUsername());
        assertEquals("email2@email.com", result.get()[1].getEmail());
        assertEquals("username3", result.get()[2].getUsername());
        assertEquals("email3@email.com", result.get()[2].getEmail());
    }

    @Test
    void testProcessFile_ParsingError() {
        // Mock the file reader to return an empty Optional
        when(this.mockFileReader.readModelsFromFile(FileProcessorTest.JSON_USERS_FILE_INVALID, this.type)).thenReturn(Optional.empty());

        // Act
        Optional<TestUserModel[]> result = this.fileProcessor.processFile(FileProcessorTest.JSON_USERS_FILE_INVALID, this.type);

        // Assert
        assertFalse(result.isPresent());
    }
}
