package com.litmus7.services;

import com.litmus7.controller.EmployeeManagerController;
import com.litmus7.util.csvUtil;
import com.litmus7.dto.Employee;
import com.litmus7.util.ValidationUtil;
import com.litmus7.util.dbUtil;
import com.litmus7.dao.EmployeeDao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagerService {

    public List<Employee> readCSV(String filePath) {
        List<Employee> employees = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                // System.out.println(line);
                String[] data = line.split(",");
                if (data.length != 8) {
                    System.out.println(data.length);
                    System.out.println("Invalid number of fields");
                    continue;
                }

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
            System.out.println("Error reading CSV: ivalid field type " + e.getMessage());
        }

        return employees;
    }

    public boolean employeeExists(Connection conn, int empId) {
        EmployeeDao daofunction = new EmployeeDao();
        boolean exists = false;
        try {
            exists = daofunction.employeeExists(conn, empId);
        } catch (Exception e) {
            System.out.println("Error checking duplicate: " + e.getMessage());
        }
        return exists;
    }

    private boolean isValidEmployee(Employee emp) {
        System.out.println("Validating employee: " + emp.getEmpId());
        return ValidationUtil.isValidEmail(emp.getEmail())
                && ValidationUtil.isValidPhone(emp.getPhone())
                && ValidationUtil.isValidJoinDate(emp.getJoinDate());
    }

    public void writeDataToDB(String csvPath) {
        EmployeeDao daofunc = new EmployeeDao();
        List<Employee> employeeList = readCSV(csvPath);
        Connection conn = dbUtil.getConnection();
        if (conn != null) {
            daofunc.writeToDB(conn, employeeList);
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    public Employee getEmployeeDetails(Integer id) {
        boolean status = false;
        EmployeeDao daofunc = new EmployeeDao();
        Connection conn = dbUtil.getConnection();
        Employee emp = null;
        if (conn != null) {
            try {
                emp = daofunc.getEmployeeDetails(conn, id);
                status = true;
            } catch (Exception e) {
                System.out.println("there is a problem with dao function" + e.getMessage());
            }
        }
        return emp;

    }

    public Employee updateEmployeeDetails(Integer empId, String field) {
        Connection conn = dbUtil.getConnection();
        EmployeeDao daofunc = new EmployeeDao();
        Employee emp = daofunc.updateEmployeeDetails(conn, empId, field);
        return emp;

    }

    public boolean deleteEmployeeDetails(Integer empId) {
        EmployeeDao daofunc = new EmployeeDao();
        boolean effect = daofunc.deleteEmployeeDetails(empId);
        return effect;

    }

    public boolean addEmployee() {

        EmployeeDao daofunc = new EmployeeDao();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter emmployee details in followiing order\n");
        System.out.println("Employee id");
        Integer empId = sc.nextInt();
        sc.nextLine();
        boolean result = false;
        if (!employeeExists(dbUtil.getConnection(), empId)) {
            System.out.print("first_name: ");
            String fname = sc.nextLine();

            System.out.print("\nlast_name: ");
            String lname = sc.nextLine();

            System.out.print("\nemail : ");
            String email = sc.nextLine();
            result = ValidationUtil.isValidEmail(email);
            if (!result){
                System.out.println("INVALID EMAIL !");
                return result;
            }
            System.out.print("\nphone no: ");
            String pnumber = sc.nextLine();
            result = ValidationUtil.isValidPhone(pnumber);
            if (!result){
                System.out.println("INVALID PHONE NUMBER !");
                return result;
            }

            System.out.print("\ndepartment: ");
            String department = sc.nextLine();

            System.out.print("\nSalary: ");
            double salary = sc.nextDouble();
            result = ValidationUtil.isValidSalary(salary);
            if (!result){
                System.out.println("INVALID SALARY !");
                return result;
            }
            sc.nextLine();

            System.out.print("\njoin_date(yyyy-mm-dd): ");
            String dateStr = sc.nextLine();
            result = ValidationUtil.isValidJoinDate(dateStr);
            if (!result){
                System.out.println("INVALID JOINING DATE !");
                return result;
            }
            Employee emp = new Employee(empId, fname, lname, email, pnumber, department, salary, dateStr);

            result = daofunc.addEmployee(emp);
        } 
        else {
            System.out.println("Error : employee id already present");
        }
        return result;

    }

    public void addEmployeesInBatch(List<Employee> employeeList) {
    EmployeeDao daofunc = new EmployeeDao();
    int[] results = daofunc.addEmployeesInBatch(employeeList);
    int successCount = 0;
    int failCount = 0;

    for (int i = 0; i < results.length; i++) {
        if (results[i] >= 0) {
            successCount++;
        } else 
        { 
            failCount++;
            System.err.println("Failed to insert Employee with ID: " + employeeList.get(i).getEmpId());
        }
    }

    System.out.println("Batch insert complete: \nTotal added = " + successCount + ", Total failed = " + failCount);
}



    public void transferEmployeesToDepartment(List<Integer> empIds, String newDepartment) {
        EmployeeDao dao = new EmployeeDao();
        boolean success = dao.transferEmployeesToDepartment(empIds, newDepartment);

        if (success) {
            System.out.println("All employees successfully transferred to department: " + newDepartment);
        } else {
            System.out.println("Transfer failed. No changes were made.");
        }
    }
}


