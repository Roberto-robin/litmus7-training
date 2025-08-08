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

public class EmployeeManagerService {

    public List<Employee> readCSV(String filePath) {
        List<Employee> employees = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
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
        try{
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

    public Employee getEmployeeDetails(Integer id)
    {
        boolean status = false;
        EmployeeDao daofunc = new EmployeeDao();
        Connection conn = dbUtil.getConnection();
        Employee emp = null;
        if(conn!=null)
        {
            try{
                emp = daofunc.getEmployeeDetails(conn,id);
                status = true;
            }
            catch(Exception e)
            {
                System.out.println("there is a problem with dao function"+e.getMessage());
            }
        }
        return emp;

    }

    public Employee updateEmployeeDetails(Integer empId, String field)
    {
        Connection conn = dbUtil.getConnection();
        EmployeeDao daofunc = new EmployeeDao();
        Employee emp = daofunc.updateEmployeeDetails(conn,empId,field);
        return emp;


    }
}

