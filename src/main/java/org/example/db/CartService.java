package org.example.db;

import java.sql.*;

public class CartService {

    public int saveCart(int totalItems, double totalCost, String language) {

        String sql = "INSERT INTO cart_records (total_items, total_cost, language) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, totalItems);
            stmt.setDouble(2, totalCost);
            stmt.setString(3, language);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB failure in saveCart", e);
        }

        return -1;
    }

    public void saveItem(int cartId, int itemNumber, double price, int quantity, double subtotal) {

        String sql = "INSERT INTO cart_items (cart_record_id, item_number, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartId);
            stmt.setInt(2, itemNumber);
            stmt.setDouble(3, price);
            stmt.setInt(4, quantity);
            stmt.setDouble(5, subtotal);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("DB failure in saveItem", e);
        }
    }
}