package com.litmus7.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;
import java.sql.Date;

import com.litmus7.dto.Employee;
import com.litmus7.util.dbUtil;
import com.litmus7.services.EmployeeManagerService;

public class EmployeeDao {

    public void writeToDB(Connection conn, List<Employee> employees) {
        String insertQuery = "INSERT INTO employee (emp_id, first_name, last_name, email, phone, department, salary, join_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            EmployeeManagerService service = new EmployeeManagerService();

            for (Employee emp : employees) {

                // Skip if duplicate
                if (service.employeeExists(conn, emp.getEmpId())) {
                    System.out.println("Skipping duplicate empId: " + emp.getEmpId());
                    continue;
                }

                // Set parameters
                pstmt.setInt(1, emp.getEmpId());
                pstmt.setString(2, emp.getFirstName());
                pstmt.setString(3, emp.getLastName());
                pstmt.setString(4, emp.getEmail());
                pstmt.setString(5, emp.getPhone());
                pstmt.setString(6, emp.getDepartment());
                pstmt.setDouble(7, emp.getSalary());
                pstmt.setDate(8, java.sql.Date.valueOf(emp.getJoinDate())); // must be "YYYY-MM-DD"

                pstmt.executeUpdate();
                System.out.println("Inserted: " + emp.getEmpId());
            }

        } catch (Exception e) {
            System.out.println("Error writing to DB: " + e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    public boolean employeeExists(Connection conn, int empId) {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM employee WHERE emp_id = ?")) {
            pstmt.setInt(1, empId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("Error checking duplicate: " + e.getMessage());
        }

        return false;
    }

    public Employee getEmployeeDetails(Connection conn, int empId) {
        Employee emp = null;
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM employee WHERE emp_id = ?")) {
            pstmt.setInt(1, empId);
            try {
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    emp = new Employee(
                            rs.getInt("emp_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("department"),
                            rs.getDouble("salary"),
                            rs.getDate("join_date").toString());

                }
            } catch (Exception e) {
                System.out.println("Trouble executiing the querry " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("error connecting to DB" + e.getMessage());

        }
        return emp;
    }

    public Employee updateEmployeeDetails(Connection conn, Integer empId, String field) {
        Employee emp = null;
        Scanner sc = new Scanner(System.in);
        String dbName = dbUtil.getdBName();
        String query = "UPDATE employee set " + field + " = ? where emp_Id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            String fieldType = dbUtil.getFieldType(field);
            switch (fieldType) {
                case "string":
                    System.out.println("enter updated value: ");
                    String newStr = sc.nextLine();
                    pstmt.setString(1, newStr);
                    break;
                case "double":
                    System.out.println("enter updated value: ");
                    Double newDbl = sc.nextDouble();
                    pstmt.setDouble(1, newDbl);
                    break;

                case "date":
                    System.out.println("enter updated value: ");
                    String newVal = sc.nextLine();
                    java.sql.Date date = java.sql.Date.valueOf(newVal);
                    pstmt.setDate(1, date);
                    break;
                default:
                    break;
            }
            pstmt.setInt(2, empId);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee updated successfully.");
                emp = getEmployeeDetails(conn, empId);
            } else {
                System.out.println("Update failed. Employee not found.");
            }

        } catch (Exception e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }

        return emp;
    }

    public boolean deleteEmployeeDetails(Integer empId) {

        Connection conn = dbUtil.getConnection();
        String query = "DELETE FROM employee WHERE emp_id = ?";
        boolean result = false;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, empId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }
        } catch (Exception e) {
            System.out.println("error connecting to db" + e.getMessage());
        }
        return result;

    }

    public boolean addEmployee(Employee emp) {
        Connection conn = dbUtil.getConnection();
        boolean result = false;
        String insertQuery = "INSERT INTO employee (emp_id, first_name, last_name, email, phone, department, salary, join_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setInt(1, emp.getEmpId());
            pstmt.setString(2, emp.getFirstName());
            pstmt.setString(3, emp.getLastName());
            pstmt.setString(4, emp.getEmail());
            pstmt.setString(5, emp.getPhone());
            pstmt.setString(6, emp.getDepartment());
            pstmt.setDouble(7, emp.getSalary());
            pstmt.setDate(8, java.sql.Date.valueOf(emp.getJoinDate()));

            pstmt.executeUpdate();
            System.out.println("Inserted: " + emp.getEmpId());
            conn.close();
            result = true;

        } catch (Exception e) {
            System.out.println("Error :" + e.getMessage());
        }
        return result;

    }

    public int[] addEmployeesInBatch(List<Employee> employeeList) {
        String sql = "INSERT INTO employee (emp_id, first_name, last_name, email, phone, department, salary, join_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int[] result = new int[0];

        try (Connection conn = dbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Employee emp : employeeList) {
                ps.setInt(1, emp.getEmpId());
                ps.setString(2, emp.getFirstName());
                ps.setString(3, emp.getLastName());
                ps.setString(4, emp.getEmail());
                ps.setString(5, emp.getPhone());
                ps.setString(6, emp.getDepartment());
                ps.setDouble(7, emp.getSalary());
                ps.setDate(8, java.sql.Date.valueOf(emp.getJoinDate()));
                ps.addBatch();
            }

            result = ps.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    
    }

    public boolean transferEmployeesToDepartment(List<Integer> employeeIds, String newDepartment) {
    String sql = "UPDATE employees SET department = ? WHERE emp_id = ?";
    Connection conn = null;

    try {
        conn = dbUtil.getConnection();
        conn.setAutoCommit(false); 

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Integer empId : employeeIds) {
                pstmt.setString(1, newDepartment);
                pstmt.setInt(2, empId);
                pstmt.addBatch();
            }

            int[] results = pstmt.executeBatch();

    
            for (int i = 0; i < results.length; i++) {
                if (results[i] == 0 || results[i] == Statement.EXECUTE_FAILED) {
                    throw new SQLException("Employee ID not found: " + employeeIds.get(i));
                }
            }

            conn.commit();
            return true;
        }

    } catch (SQLException e) {
        System.err.println("Transaction failed: " + e.getMessage());
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Rollback failed: " + rollbackEx.getMessage());
            }
        }
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error resetting auto-commit: " + e.getMessage());
            }
        }
    }
    return false;
}

}
