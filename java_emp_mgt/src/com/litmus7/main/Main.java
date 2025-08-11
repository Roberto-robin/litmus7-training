package com.litmus7.main;
import com.litmus7.controller.EmployeeManagerController;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int choice =0;
        EmployeeManagerController controller = new EmployeeManagerController();
        
        Scanner input = new Scanner(System.in);
        
        int id=0;

        do
        {
            System.out.println("\nchoose functionality : \n\n0.Exit\n1. Write data from csv file to database\n2.Get Employee details\n3.Update Employee details\n4.Delete Employee details\n5.Add new Employee");
            choice = input.nextInt();
            switch (choice) {
            case 1:
                controller.writeDataToDBCheck("src/com/litmus7/employees.csv");
                break;
            case 2:
                System.out.println("enter employee id :");
                id = input.nextInt();
                controller.getEmployeeDetails(id);
                break;
            case 3:
                System.out.println("enter employee id:");
                id = input.nextInt();
                controller.updateEmployeeDetails(id);
                break;
            
            case 4:
                System.out.println("enter employee id:");
                id = input.nextInt();
                controller.deleteEmployeeDetails(id);
                break;
            
            case 5:
                controller.addEmployee();
                break;

            case 6:
                controller.addEmployeesInBatch();



            default:
                break;
            }
        }while(choice!=0);
        input.close();


    }
}
