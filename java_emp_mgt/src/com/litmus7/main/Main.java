package com.litmus7.java_emp_mgt.main;
import com.litmus7.java_emp_mgt.controller.EmployeeManagerController;
public class Main {
    public static void main(String[] args) {
        EmployeeManagerController controller = new EmployeeManagerController();
        controller.writeDataToDBCheck("employees.csv");
    }
}
