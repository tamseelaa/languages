package org.example.db;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @Test
    void testGetConnectionNotNull() throws Exception {
        Connection conn = DatabaseConnection.getConnection();

        assertNotNull(conn);
        assertFalse(conn.isClosed());

        conn.close();
    }
}