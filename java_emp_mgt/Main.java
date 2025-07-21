public class Main {
    public static void main(String[] args) {
        EmployeeManagerController controller = new EmployeeManagerController();
        controller.writeDataToDB("employees.csv");
    }
}
