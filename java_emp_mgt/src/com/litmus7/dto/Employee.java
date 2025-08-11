package com.litmus7.dto;

import java.sql.Date;

public class Employee {
    private int empId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String department;
    private double salary;
    private String joinDate;

    // Constructor
    public Employee(int empId, String firstName, String lastName, String email,
                    String phone, String department, double salary, String dateStr) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.salary = salary;
        this.joinDate = dateStr;
    }

    // Getters
    public int getEmpId() { return empId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public String getJoinDate() { return joinDate; }


    public String details(){
         return "Employee : " + "emp_id=" + empId + ", first_name = '" + firstName + '\'' + ", last_name = '" + lastName + '\'' + ", email = '" + email + '\'' +", phone ='" + phone + '\'' +", department = '" + department + '\'' +", salary = " + salary +", join_date = '" + joinDate + '\'';

    }
}
