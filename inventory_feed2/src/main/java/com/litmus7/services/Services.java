package com.litmus7.services;

import java.sql.Connection;
import java.util.List;
import java.io.File;
import java.sql.SQLException;


import com.litmus7.Dao.Dao;
import com.litmus7.Dto.Dto;
import com.litmus7.Util.FileUtil;
import com.litmus7.Util.ValidationUtil;

public class Services {
    private final Dao dao = new Dao();
    public boolean processFile(Connection conn, File file) {
        try {
            conn.setAutoCommit(false); // Start transaction

            List<Dto> records = FileUtil.parseCsv(file);

            for (Dto dto : records) {
                if (!ValidationUtil.isValid(dto)) {
                    throw new RuntimeException("Invalid record: " + dto);
                }
                dao.insertInventoryRecord(conn, dto);
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            try {
                conn.rollback();
                System.err.println("Transaction rolled back for file: " + file.getName());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;

        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
