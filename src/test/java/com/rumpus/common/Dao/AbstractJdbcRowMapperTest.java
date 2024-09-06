package com.rumpus.common.Dao;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.rumpus.common.AbstractCommonTest;
import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.jdbc.AbstractJdbcRowMapper;
import com.rumpus.common.Model.User.TestUserModel;

/**
 * Test the {@link AbstractJdbcRowMapper} class
 * 
 * <ul>
 *   <li>Tests {@link AbstractJdbcRowMapper#mapRow(ResultSet, int)}
 *      <ul>
 *          <li>TestMapRow</li>
 *          <li>testMapRow_NullFunction</li>
 *      </ul>
 *   </li>
 * </ul>
 */
public class AbstractJdbcRowMapperTest extends AbstractCommonTest {

    public AbstractJdbcRowMapperTest() {
        super(AbstractJdbcRowMapperTest.class);
    }

    @Override
    protected void setUpClass() {
    }

    @Override
    protected void tearDownClass() {
    }

    @Override
    protected void tearDown() {
    }

    // Mock the ResultSet
    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ObjectUserRowMapper jdbcRowMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMapRow() throws SQLException {
        final String TEST_USERNAME = "this_is_a_cool_username";
        // Mock behavior of ResultSet
        when(resultSet.getString(ICommon.USERNAME)).thenReturn(TEST_USERNAME);

        // Call the mapRow method
        TestUserModel model = jdbcRowMapper.mapRow(resultSet, 1);

        // Verify the result
        assertNotNull(model);
        assertEquals(TEST_USERNAME, model.getUsername());

        // Verify interactions with the mocked ResultSet
        verify(resultSet).getString(ICommon.USERNAME);
    }

    @Test
    public void testMapRow_NullFunction() throws SQLException {
        // Set a null mapping function
        jdbcRowMapper.setMapFunc(null);

        // Call the mapRow method, should return null since map function is null
        TestUserModel model = jdbcRowMapper.mapRow(resultSet, 1);
        assertNull(model);
    }
}
