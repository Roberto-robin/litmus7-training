import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagerController {

    public List<Employee> readCSV(String filePath) {
        List<Employee> employees = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length != 8) continue;

                int empId = Integer.parseInt(data[0]);
                String firstName = data[1];
                String lastName = data[2];
                String email = data[3];
                String phone = data[4];
                String department = data[5];
                double salary = Double.parseDouble(data[6]);
                String joinDate = data[7];

                employees.add(new Employee(empId, firstName, lastName, email, phone, department, salary, joinDate));
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
        return employees;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
        } catch (Exception e) {
            System.out.println("DB Connection Failed: " + e.getMessage());
            
            return null;
        }
    }

    public void writeToDB(Connection conn, List<Employee> employees) {
        try {
            Statement stmt = conn.createStatement();
            for (Employee emp : employees) {
                String query = String.format(
                    "INSERT INTO employee VALUES (%d, '%s', '%s', '%s', '%s', '%s', %.2f, '%s')",
                    emp.getEmpId(), emp.getFirstName(), emp.getLastName(), emp.getEmail(),
                    emp.getPhone(), emp.getDepartment(), emp.getSalary(), emp.getJoinDate()
                );
                stmt.executeUpdate(query);
                System.out.println("Inserted: " + emp.getEmpId());
            }
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error writing to DB: " + e.getMessage());
        }
    }

    public void writeDataToDB(String csvPath) {
        List<Employee> employeeList = readCSV(csvPath);
        Connection conn = getConnection();
        if (conn != null) {
            writeToDB(conn, employeeList);
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
