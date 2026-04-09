package org.example.db;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Test
    void testSaveCartReturnsGeneratedId() throws Exception {

        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try (MockedStatic<DatabaseConnection> dbMock =
                     mockStatic(DatabaseConnection.class)) {

            dbMock.when(DatabaseConnection::getConnection).thenReturn(conn);

            when(conn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                    .thenReturn(stmt);

            when(stmt.executeUpdate()).thenReturn(1);
            when(stmt.getGeneratedKeys()).thenReturn(rs);

            when(rs.next()).thenReturn(true);
            when(rs.getInt(1)).thenReturn(42);

            CartService service = new CartService();

            int result = service.saveCart(3, 100.0, "en");

            assertEquals(42, result);

            verify(stmt).setInt(1, 3);
            verify(stmt).setDouble(2, 100.0);
            verify(stmt).setString(3, "en");
        }
    }

    @Test
    void testSaveCartReturnsMinusOneWhenNoKey() throws Exception {

        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try (MockedStatic<DatabaseConnection> dbMock =
                     mockStatic(DatabaseConnection.class)) {

            dbMock.when(DatabaseConnection::getConnection).thenReturn(conn);

            when(conn.prepareStatement(anyString(), anyInt())).thenReturn(stmt);
            when(stmt.getGeneratedKeys()).thenReturn(rs);

            when(rs.next()).thenReturn(false);

            CartService service = new CartService();

            int result = service.saveCart(1, 10.0, "en");

            assertEquals(-1, result);
        }
    }

    @Test
    void testSaveItemExecutesInsert() throws Exception {

        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        try (MockedStatic<DatabaseConnection> dbMock =
                     mockStatic(DatabaseConnection.class)) {

            dbMock.when(DatabaseConnection::getConnection).thenReturn(conn);

            when(conn.prepareStatement(anyString())).thenReturn(stmt);

            CartService service = new CartService();

            service.saveItem(1, 2, 10.0, 3, 30.0);

            verify(stmt).setInt(1, 1);
            verify(stmt).setInt(2, 2);
            verify(stmt).setDouble(3, 10.0);
            verify(stmt).setInt(4, 3);
            verify(stmt).setDouble(5, 30.0);

            verify(stmt).executeUpdate();
        }
    }

    @Test
    void testSaveItemHandlesSQLException() throws Exception {

        Connection conn = mock(Connection.class);

        try (MockedStatic<DatabaseConnection> dbMock =
                     mockStatic(DatabaseConnection.class)) {

            dbMock.when(DatabaseConnection::getConnection).thenReturn(conn);

            when(conn.prepareStatement(anyString()))
                    .thenThrow(new SQLException("DB error"));

            CartService service = new CartService();

            RuntimeException ex = assertThrows(RuntimeException.class, () ->
                    service.saveItem(1, 1, 10.0, 1, 10.0)
            );

            assertTrue(ex.getMessage().contains("DB failure in saveItem"));
        }
    }
}