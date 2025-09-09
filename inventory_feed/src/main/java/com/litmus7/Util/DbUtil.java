package com.litmus7.Util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.litmus7.Dao.Dbconfig;

public class DbUtil {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Dbconfig.URL, Dbconfig.USER, Dbconfig.PASSWORD);
    
}
}
