package com.litmus7.util;

import com.litmus7.dao.DBConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class dbUtil {
    private static final Map<String, String> fieldTypeMap = new HashMap<>();

    static {
        fieldTypeMap.put("first_name", "string");
        fieldTypeMap.put("last_name", "string");
        fieldTypeMap.put("email", "string");
        fieldTypeMap.put("phone", "string");
        fieldTypeMap.put("department", "string");
        fieldTypeMap.put("salary", "double");
        fieldTypeMap.put("join_date", "date");  
    }

    public static String getFieldType(String fieldName) {
        return fieldTypeMap.getOrDefault(fieldName, "string");
    }

    public static boolean isValidField(String fieldName) {
        return fieldTypeMap.containsKey(fieldName);
    }




    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
        } catch (Exception e) {
            System.out.println("DB Connection Failed: " + e.getMessage());

            return null;
        }
    }

    public static String getdBName()
    {
        Connection conn = dbUtil.getConnection();
        String dbName = "No db found";
        String query = "SELECT DATABASE()";

        try (PreparedStatement pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) {

        if (rs.next()) {
            dbName = rs.getString(1);
        }

    } catch (SQLException e) {
        System.out.println("Error fetching database name: " + e.getMessage());
    }
       return dbName;
    }
 

}
