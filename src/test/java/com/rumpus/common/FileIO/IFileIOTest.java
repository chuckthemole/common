package com.rumpus.common.FileIO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.nio.file.Files;
import java.nio.file.Path;

import com.rumpus.common.Model.User.TestUserModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

/**
 * Test class for the IFileReader interface.
 * Add tests for the readModelsFromFile method.
 * TODO: this will need to be done for xml when it is implemented.
 */
class IFileIOTest {

    private static final String JSON_USERS_FILE = "src/test/java/com/rumpus/common/test_data/users/test_users.json";
    private static final String JSON_USER_FILE = "src/test/java/com/rumpus/common/test_data/users/test_user.json";
    private static final String JSON_USERS_FILE_EMPTY = "src/test/java/com/rumpus/common/test_data/users/empty_users_file.json";
    private static final String JSON_USERS_FILE_INVALID = "src/test/java/com/rumpus/common/test_data/users/invalid_users_file.json";
    private static final String JSON_META_DATA = "src/test/java/com/rumpus/common/test_data/file_meta_data_test.json";
    private static final int JSON_META_DATA_FILE_SIZE = 659;
    private IFileIO jsonFileReader;

    private static final String emptyJsonContent = "";
    private static final String invalidJsonContent = "{invalid json}";
    private static final int jsonContentLength = 10;
    private static final String jsonContent = "[\n" +
        "    {\n" +
        "        \"userDetails\": {\n" +
        "            \"username\": \"chuckthemole\",\n" +
        "            \"password\": \"coolpassbro\"\n" +
        "        },\n" +
        "        \"email\": \"chuckthemole@gmail.com\",\n" +
        "        \"id\": \"11111111-1111-1111-1111-111111111111\"\n" +
        "    },\n" +
        "    {\n" +
        "        \"userDetails\": {\n" +
        "            \"username\": \"chuck\",\n" +
        "            \"password\": \"coolpassbro\"\n" +
        "        },\n" +
        "        \"email\": \"chuck@gmail.com\",\n" +
        "        \"id\": \"22222222-2222-2222-2222-222222222222\"\n" +
        "    },\n" +
        "    {   \n" +
        "        \"userDetails\": {\n" +
        "            \"username\": \"bill\",\n" +
        "            \"password\": \"coolpassbro\"\n" +
        "        },\n" +
        "        \"email\": \"bill@hotmail.com\",\n" +
        "        \"id\": \"123e4567-e89b-12d3-a456-426614174000\"\n" +
        "    },\n" +
        "    {\n" +
        "        \"userDetails\": {\n" +
        "            \"username\": \"jacob\",\n" +
        "            \"password\": \"coolpassbro\"\n" +
        "        },\n" +
        "        \"email\": \"jacob@gmail.com\",\n" +
        "        \"id\": \"9b52e58b-4597-4fd1-b2e2-d682afffc0db\"\n" +
        "    },\n" +
        "    {\n" +
        "        \"userDetails\": {\n" +
        "            \"username\": \"cooluser123\",\n" +
        "            \"password\": \"coolpassbro\"\n" +
        "        },\n" +
        "        \"email\": \"cooluser123@gmail.com\",\n" +
        "        \"id\": \"110e8400-e29b-41d4-a716-446655440000\"\n" +
        "    },\n" +
        "    {\n" +
        "        \"userDetails\": {\n" +
        "            \"username\": \"goduser1\",\n" +
        "            \"password\": \"coolpassbro\"\n" +
        "        },\n" +
        "        \"email\": \"goduser1@yahoo.com\",\n" +
        "        \"id\": \"48e744f1-504f-4dc1-bebc-b7a774b5db73\"\n" +
        "    },\n" +
        "    {\n" +
        "        \"userDetails\": {\n" +
        "            \"username\": \"76whatuser\",\n" +
        "            \"password\": \"coolpassbro\"\n" +
        "        },\n" +
        "        \"email\": \"76whatuser@gmail.com\",\n" +
        "        \"id\": \"a8098c1a-f86e-11da-bd1a-00112444be1e\"\n" +
        "    },\n" +
        "    {\n" +
        "        \"userDetails\": {\n" +
        "            \"username\": \"bobuser\",\n" +
        "            \"password\": \"coolpassbro\"\n" +
        "        },\n" +
        "        \"email\": \"bobuser@hotmail.com\",\n" +
        "        \"id\": \"c9bf9e57-1685-4c89-bafb-ff5af830be8a\"\n" +
        "    },\n" +
        "    {\n" +
        "        \"userDetails\": {\n" +
        "            \"username\": \"jackuser\",\n" +
        "            \"password\": \"coolpassbro\"\n" +
        "        },\n" +
        "        \"email\": \"jackuser@yahoo.com\",\n" +
        "        \"id\": \"e1c29b13-ffca-4c58-b49f-6f939e769d52\"\n" +
        "    },\n" +
        "    {\n" +
        "        \"userDetails\": {\n" +
        "            \"username\": \"killuser\",\n" +
        "            \"password\": \"coolpassbro\"\n" +
        "        },\n" +
        "        \"email\": \"killuser@gmail.com\",\n" +
        "        \"id\": \"e8d9b9f2-2225-4c4f-b5a7-3ec337db1b27\"\n" +
        "    }\n" +
        "]";
    
    private static final String[] userNames = {
        "chuckthemole",
        "chuck",
        "bill",
        "jacob",
        "cooluser123",
        "goduser1",
        "76whatuser",
        "bobuser",
        "jackuser",
        "killuser"
    };

    @BeforeEach
    void setUp() {
        this.jsonFileReader = JsonIO.create(); // Using the JsonReader implementation
    }

    @Test
    void testReadModelsFromFile_Success() {

        // Mocking the Reader.readFileAsString method
        try (MockedStatic<FileIOUtil> readerMock = mockStatic(FileIOUtil.class)) {
            readerMock.when(() -> FileIOUtil.readFileAsString(IFileIOTest.JSON_USERS_FILE)).thenReturn(IFileIOTest.jsonContent);

            // Act
            Optional<TestUserModel[]> result = this.jsonFileReader.readModelsFromFile(
                IFileIOTest.JSON_USERS_FILE,
                TestUserModel[].class);

            // Assert
            assertTrue(result.isPresent());
            assertEquals(IFileIOTest.jsonContentLength, result.get().length);

            TestUserModel[] user = result.get();
            for (int i = 0; i < user.length; i++) {
                assertEquals(IFileIOTest.userNames[i], user[i].getUsername());
            }
        }
    }

    @Test
    void testReadModelsFromFile_EmptyFile() {

        // Mocking the Reader.readFileAsString method
        try (MockedStatic<FileIOUtil> readerMock = mockStatic(FileIOUtil.class)) {
            readerMock.when(() -> FileIOUtil.readFileAsString(IFileIOTest.JSON_USERS_FILE_EMPTY)).thenReturn(IFileIOTest.emptyJsonContent);

            // Act
            Optional<TestUserModel[]> result = this.jsonFileReader.readModelsFromFile(
                IFileIOTest.JSON_USERS_FILE_EMPTY,
                TestUserModel[].class);

            // Assert
            assertFalse(result.isPresent());
        }
    }

    @Test
    void testReadModelsFromFile_ParsingError() {

        // Mocking the Reader.readFileAsString method
        try (MockedStatic<FileIOUtil> readerMock = mockStatic(FileIOUtil.class)) {
            readerMock.when(() -> FileIOUtil.readFileAsString(IFileIOTest.JSON_USERS_FILE_INVALID)).thenReturn(IFileIOTest.invalidJsonContent);

            // Act
            Optional<TestUserModel[]> result = this.jsonFileReader.readModelsFromFile(
                IFileIOTest.JSON_USERS_FILE_INVALID,
                TestUserModel[].class);

            // Assert
            assertFalse(result.isPresent());
        }
    }

    @Test
    void testReadModelFromFile_Success() {
        try (MockedStatic<FileIOUtil> readerMock = mockStatic(FileIOUtil.class)) {
            readerMock.when(() -> FileIOUtil.readFileAsString(JSON_USER_FILE)).thenReturn(
                "        {\n" +
                "        \"userDetails\": {\n" +
                "            \"username\": \"chuckthemole\",\n" +
                "            \"password\": \"coolpassbro\"\n" +
                "        },\n" +
                "        \"email\": \"chuckthemole@gmail.com\",\n" +
                "        \"id\": \"11111111-1111-1111-1111-111111111111\"\n" +
                "       }"
            );

            Optional<TestUserModel> result = this.jsonFileReader.readModelFromFile(
                IFileIOTest.JSON_USER_FILE,
                TestUserModel.class);
            assertTrue(result.isPresent());
            assertEquals("chuckthemole", result.get().getUsername());
            assertEquals("chuckthemole@gmail.com", result.get().getEmail());
            assertEquals("11111111-1111-1111-1111-111111111111", result.get().getId().toString());
        }
    }

    @Test
    void testWriteModelsToFile_Success() {
        // TestUserModel[] models = new TestUserModel[1]; // Sample test model array
        // models[0] = new TestUserModel("username", "email", UUID.randomUUID());

        // boolean result = this.jsonFileReader.writeModelsToFile("output.json", models);
        // assertTrue(result);
        assertTrue(true); // TODO: implement this test. Still need to implement the writeModelsToFile method.
    }

    @Test
    void testIsValidFile_ValidFile() {
        try (MockedStatic<Files> readerMock = mockStatic(Files.class)) {
            final Path path = Path.of(JSON_USERS_FILE);
            readerMock.when(() -> Files.exists(path)).thenReturn(true);
            readerMock.when(() -> Files.isRegularFile(path)).thenReturn(true);

            boolean result = this.jsonFileReader.isValidFile(JSON_USERS_FILE);
            assertTrue(result);
        }
    }

    @Test
    void testIsValidFile_InvalidFile() {
        try (MockedStatic<Files> readerMock = mockStatic(Files.class)) {
            final Path path = Path.of(JSON_USERS_FILE);
            readerMock.when(() -> Files.exists(path)).thenReturn(false);
            readerMock.when(() -> Files.isRegularFile(path)).thenReturn(false);

            boolean result = this.jsonFileReader.isValidFile("invalid_file.json");
            assertFalse(result);
        }
    }

    @Test
    void testReadRawFileContent_Success() {
        try (MockedStatic<Files> readerMock = mockStatic(Files.class)) {
            final Path path = Path.of(IFileIOTest.JSON_USERS_FILE);
            readerMock.when(() -> Files.readString(path)).thenReturn(IFileIOTest.jsonContent);

            Optional<String> result = this.jsonFileReader.readRawFileContent(IFileIOTest.JSON_USERS_FILE);
            assertTrue(result.isPresent());
            assertEquals(IFileIOTest.jsonContent, result.get());
        }
    }

    @Test
    void testGetLastError_NoError() {
        Optional<String> error = this.jsonFileReader.getLastError();
        assertFalse(error.isPresent());
    }

    @Test
    void testGetFileMetadata_Success() {
        Optional<FileMetadata> metadata = this.jsonFileReader.getFileMetadata(IFileIOTest.JSON_META_DATA);
        assertTrue(metadata.isPresent());
        assertEquals(IFileIOTest.JSON_META_DATA_FILE_SIZE, metadata.get().getFileSize());
    }
}

