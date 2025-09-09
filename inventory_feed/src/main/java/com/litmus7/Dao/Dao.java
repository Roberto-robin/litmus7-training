package com.litmus7.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.litmus7.Dto.Dto;

public class Dao {
  public void insertInventoryRecord(Connection conn, Dto dto) throws SQLException {
        String sql = "INSERT INTO inventory (sku, productName, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getSku());
            ps.setString(2, dto.getProductName());
            ps.setInt(3, dto.getQuantity());
            ps.setDouble(4, dto.getPrice());
            ps.executeUpdate();
        }
    }
}

    

