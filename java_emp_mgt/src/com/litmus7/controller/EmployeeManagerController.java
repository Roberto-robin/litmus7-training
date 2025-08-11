package com.litmus7.controller;

import com.litmus7.services.EmployeeManagerService;
import com.litmus7.response.StatusCode;
import com.litmus7.util.csvUtil;
import com.litmus7.util.dbUtil;
import com.litmus7.dto.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.sql.Connection;
import java.time.LocalDate;

public class EmployeeManagerController {

    EmployeeManagerService emps = new EmployeeManagerService();

    public StatusCode<Integer, String> readCSVCheck(String filePath) {
        if (filePath != null) {
            if (csvUtil.checkCSV(filePath)) {
                // List<Employee> employeeList = emps.readCSV(filePath);
                return new StatusCode(200, "Success");
            } else
                return new StatusCode(402, "Invalid file type (Not .csv)");
        }
        return new StatusCode(401, "Field missing");
    }

    public StatusCode<Integer, String> employeeExistsCheck(Connection conn, Integer empId) {
        if (empId != null) {
            boolean exists = emps.employeeExists(conn, empId);
            return new StatusCode(200, "Success");

        }
        return new StatusCode(401, "Field missing");
    }

    public StatusCode<Integer, String> writeDataToDBCheck(String csvPath) {
        StatusCode<Integer, String> val = readCSVCheck(csvPath);
        if (val.first == 200) {
            emps.writeDataToDB(csvPath);
            return new StatusCode(20, "Sucess");
        }
        return val;
    }

    public StatusCode<Integer , String> getEmployeeDetails(Integer id)
    {
        boolean val=false;
        try{
        if(emps.employeeExists(dbUtil.getConnection(), id))
        {
            Employee emp = emps.getEmployeeDetails(id);
            String detail = emp.details();
            System.out.println(detail);
        }
        }
        catch(Exception e)
        {
            System.out.println();
        }
        if(val == true)
        {
            return new StatusCode(200,"sucess");
        }
        return new StatusCode(405,"employee deatils fetching f");
    }

    public void updateEmployeeDetails(Integer empId)
    {
        Employee emp = emps.getEmployeeDetails(empId);
        String detail = emp.details();
        System.out.println(detail);
         Scanner sc = new Scanner(System.in);
        String field;
        do
        {
            System.out.println("Choose Field to update(type \"back\" to exit) : ");
            field = sc.next();
            if(field.compareToIgnoreCase("back")==0)
                break;
            emp = emps.updateEmployeeDetails(empId,field);
            System.out.println(emp.details());
        }while(field!=null);
      
        sc.close();
    
    }

    public boolean deleteEmployeeDetails(Integer empId)
    {
        boolean status = false;
        if(emps.employeeExists(dbUtil.getConnection(), empId))
        {
            status = emps.deleteEmployeeDetails(empId);
        }
        else{
            System.out.println("Error : Employee details not found !");
        }
        if(status==true)
        System.out.println("Employee details succesfully deleted");
        return status;
    }

    public  StatusCode<Integer , String> addEmployee()
    {
        boolean result = emps.addEmployee();
        if(result==true) 
           return new StatusCode(20, "Sucess");
        else
           return new StatusCode(406,"Error adding employee to db");
    }

    public void addEmployeesInBatch() {
    Scanner sc = new Scanner(System.in);
    List<Employee> employees = new ArrayList<>();

    System.out.print("Enter number of employees to insert in batch: ");
    int count = Integer.parseInt(sc.nextLine());

    for (int i = 0; i < count; i++) {
        System.out.println("\nEnter details for Employee " + (i + 1));

        System.out.print("Employee ID: ");
        int empId = Integer.parseInt(sc.nextLine());

        System.out.print("First Name: ");
        String firstName = sc.nextLine();

        System.out.print("Last Name: ");
        String lastName = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        System.out.print("Department: ");
        String department = sc.nextLine();

        System.out.print("Salary: ");
        double salary = Double.parseDouble(sc.nextLine());

        System.out.print("Join Date (YYYY-MM-DD): ");
        String joinDate = sc.nextLine();

        employees.add(new Employee(empId, firstName, lastName, email, phone, department, salary, joinDate));
    }
    emps.addEmployeesInBatch(employees);
}


public void transferEmployeesToDepartment() { 
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter number of employees to update: ");
    int count = sc.nextInt();
    sc.nextLine();

    List<Integer> empIds = new ArrayList<>();
    System.out.print("Enter Employee IDs : ");
    for (int i = 0; i < count; i++) {
        empIds.add(sc.nextInt());
        sc.nextLine(); 
    }

    System.out.print("Enter new department for all employees: ");
    String newDepartment = sc.nextLine().trim();
    emps.transferEmployeesToDepartment(empIds, newDepartment);
}









   
}





