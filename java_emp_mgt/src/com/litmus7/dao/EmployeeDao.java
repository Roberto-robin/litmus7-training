package com.litmus7.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;
import java.sql.Date;

import com.litmus7.dto.Employee;
import com.litmus7.util.dbUtil;
import com.litmus7.services.EmployeeManagerService;

public class EmployeeDao {

    public void writeToDB(Connection conn, List<Employee> employees) {
        try {
            EmployeeManagerService service = new EmployeeManagerService();
            Statement stmt = conn.createStatement();
            for (Employee emp : employees) {

                if (service.employeeExists(conn, emp.getEmpId())) {
                    System.out.println("Skipping duplicate empId: " + emp.getEmpId());
                    continue;
                }
                String query = String.format(
                        "INSERT INTO employee VALUES (%d, '%s', '%s', '%s', '%s', '%s', %.2f, '%s')",
                        emp.getEmpId(), emp.getFirstName(), emp.getLastName(), emp.getEmail(),
                        emp.getPhone(), emp.getDepartment(), emp.getSalary(), emp.getJoinDate());
                stmt.executeUpdate(query);
                System.out.println("Inserted: " + emp.getEmpId());

            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error writing to DB: " + e.getMessage());
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

    public Employee getEmployeeDetails(Connection conn, int empId)
    {
          Employee emp = null;
          try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM employee WHERE emp_id = ?")) {
            pstmt.setInt(1, empId);
            try{
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
            }
            catch(Exception e){
                System.out.println("Trouble executiing the querry "+ e.getMessage());
            }
           
        }
        catch(Exception e){
            System.out.println("error connecting to DB"+e.getMessage());

        }
        return emp;
    }

    public Employee updateEmployeeDetails(Connection conn, Integer empId, String field)
    {
        Employee emp = null;
        Scanner sc = new Scanner(System.in);
        String dbName = dbUtil.getdBName();
        String query = "UPDATE employee set "+ field +" = ? where emp_Id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            String fieldType = dbUtil.getFieldType(field);
            switch (fieldType) {
            case "string":
                System.out.println("enter updated value: ");
                String newStr = sc.nextLine();
                pstmt.setString(1,newStr);
                break;
            case "double":
                System.out.println("enter updated value: ");
                Double newDbl = sc.nextDouble();
                pstmt.setDouble(1,newDbl);
                break;
    
            case "date":
                System.out.println("enter updated value: ");
                String newVal = sc.nextLine();
                java.sql.Date date = java.sql.Date.valueOf(newVal); 
                pstmt.setDate(1,date);
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
}
