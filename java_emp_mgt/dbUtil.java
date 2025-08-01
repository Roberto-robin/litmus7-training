import java.sql.Connection;
import java.sql.DriverManager;

public class dbUtil {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
        } catch (Exception e) {
            System.out.println("DB Connection Failed: " + e.getMessage());

            return null;
        }
    }

}
