package org.example.db;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocalizationServiceTest {

    @Test
    void testGetStringsReturnsData() throws Exception {

        // Mock JDBC components
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        // Mock static DatabaseConnection
        try (MockedStatic<DatabaseConnection> dbMock =
                     mockStatic(DatabaseConnection.class)) {

            dbMock.when(DatabaseConnection::getConnection).thenReturn(conn);

            when(conn.prepareStatement(anyString())).thenReturn(stmt);
            when(stmt.executeQuery()).thenReturn(rs);

            // Simulate ResultSet
            when(rs.next()).thenReturn(true, true, false);

            when(rs.getString("key"))
                    .thenReturn("greet", "exit");

            when(rs.getString("value"))
                    .thenReturn("Hello", "Goodbye");

            LocalizationService service = new LocalizationService();

            Map<String, String> result = service.getStrings("en");

            assertEquals(2, result.size());
            assertEquals("Hello", result.get("greet"));
            assertEquals("Goodbye", result.get("exit"));

            verify(stmt).setString(1, "en");
        }
    }

    @Test
    void testGetStringsHandlesSQLException() throws Exception {

        Connection conn = mock(Connection.class);

        try (MockedStatic<DatabaseConnection> dbMock =
                     mockStatic(DatabaseConnection.class)) {

            dbMock.when(DatabaseConnection::getConnection).thenReturn(conn);

            when(conn.prepareStatement(anyString()))
                    .thenThrow(new SQLException("DB error"));

            LocalizationService service = new LocalizationService();

            Map<String, String> result = service.getStrings("en");

            // Should return empty map on failure
            assertTrue(result.isEmpty());
        }
    }
}