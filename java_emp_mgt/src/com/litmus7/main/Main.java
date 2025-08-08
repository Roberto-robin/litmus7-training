package com.litmus7.main;
import com.litmus7.controller.EmployeeManagerController;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int choice =0;
        EmployeeManagerController controller = new EmployeeManagerController();
        System.out.println("choose functionality : \n1. Write data from csv file to database\n2.Get Employee details\n3.Update Employee details\n4.Delete Employee details\n5.Add Employee");
        Scanner input = new Scanner(System.in);
        choice = input.nextInt();
        int id=0;

        switch (choice) {
            case 1:
                controller.writeDataToDBCheck("src/com/litmus7/employees.csv");
                break;
            case 2:
                System.out.println("enter employee id :");
                id = input.nextInt();
                controller.getEmployeeDetails(id);
            case 3:
                System.out.println("enter employee id:");
                id = input.nextInt();
                controller.updateEmployeeDetails(id);


        
            default:
                break;
        }
         input.close();
    }
}
