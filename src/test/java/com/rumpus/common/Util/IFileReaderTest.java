package com.rumpus.common.Util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Type;
import java.util.Optional;

import com.rumpus.common.ICommon;
import com.rumpus.common.Logger.AbstractCommonLogger.LogLevel;
import com.rumpus.common.Model.User.TestUserModel;
import com.rumpus.common.util.ModelReader.IFileReader;
import com.rumpus.common.util.ModelReader.JsonReader;
import com.rumpus.common.util.ModelReader.ReaderUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

/**
 * Test class for the IFileReader interface.
 * Add tests for the readModelsFromFile method.
 * TODO: this will need to be done for xml when it is implemented.
 */
class IFileReaderTest {

    private static final String JSON_USERS_FILE = "src/test/java/com/rumpus/common/test_data/users/test_users.json";
    private static final String JSON_USERS_FILE_EMPTY = "src/test/java/com/rumpus/common/test_data/users/empty_users_file.json";
    private static final String JSON_USERS_FILE_INVALID = "src/test/java/com/rumpus/common/test_data/users/invalid_users_file.json";
    private IFileReader jsonFileReader;
    private final Type type = TestUserModel[].class;

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
        this.jsonFileReader = JsonReader.create(); // Using the JsonReader implementation
    }

    @Test
    void testReadModelsFromFile_Success() {

        // Mocking the Reader.readFileAsString method
        try (MockedStatic<ReaderUtil> readerMock = mockStatic(ReaderUtil.class)) {
            readerMock.when(() -> ReaderUtil.readFileAsString(IFileReaderTest.JSON_USERS_FILE)).thenReturn(IFileReaderTest.jsonContent);

            // Act
            Optional<TestUserModel[]> result = this.jsonFileReader.readModelsFromFile(IFileReaderTest.JSON_USERS_FILE, this.type);

            // Assert
            assertTrue(result.isPresent());
            assertEquals(IFileReaderTest.jsonContentLength, result.get().length);

            TestUserModel[] user = result.get();
            for (int i = 0; i < user.length; i++) {
                assertEquals(IFileReaderTest.userNames[i], user[i].getUsername());
            }
        }
    }

    @Test
    void testReadModelsFromFile_EmptyFile() {

        // Mocking the Reader.readFileAsString method
        try (MockedStatic<ReaderUtil> readerMock = mockStatic(ReaderUtil.class)) {
            readerMock.when(() -> ReaderUtil.readFileAsString(IFileReaderTest.JSON_USERS_FILE_EMPTY)).thenReturn(IFileReaderTest.emptyJsonContent);

            // Act
            Optional<TestUserModel[]> result = this.jsonFileReader.readModelsFromFile(IFileReaderTest.JSON_USERS_FILE_EMPTY, this.type);

            // Assert
            assertFalse(result.isPresent());
        }
    }

    @Test
    void testReadModelsFromFile_ParsingError() {

        // Mocking the Reader.readFileAsString method
        try (MockedStatic<ReaderUtil> readerMock = mockStatic(ReaderUtil.class)) {
            readerMock.when(() -> ReaderUtil.readFileAsString(IFileReaderTest.JSON_USERS_FILE_INVALID)).thenReturn(IFileReaderTest.invalidJsonContent);

            // Act
            Optional<TestUserModel[]> result = this.jsonFileReader.readModelsFromFile(IFileReaderTest.JSON_USERS_FILE_INVALID, this.type);

            // Assert
            assertFalse(result.isPresent());
        }
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(IFileReaderTest.class, level, args);
    }

    private static void LOG_THIS(String... args) {
        ICommon.LOG(IFileReaderTest.class, args);
    }
}
