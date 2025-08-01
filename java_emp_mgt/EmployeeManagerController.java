import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class EmployeeManagerController {

    EmployeeManagerService emps = new EmployeeManagerService();
    public StatusCode<Integer , String> readCSVCheck(String filePath)
    {
        if(filePath!=null)
        {
            if(csvUtil.checkCSV(filePath)){
                //List<Employee> employeeList = emps.readCSV(filePath);
                return new StatusCode(200,"Success");
            }
            else
                return new StatusCode(402,"Invalid file type (Not .csv)");
        }
        return new StatusCode(401,"Field missing");
    }

   public StatusCode<Integer , String> employeeExistsCheck(Connection conn, Integer empId) {
    if(empId != null)
    {
        boolean exists = emps.employeeExists(conn,empId);
        return new StatusCode(200,"Success");

    }
     return new StatusCode(401, "Field missing");
    }


    EmployeeManagerService services = new EmployeeManagerService();
    public StatusCode<Integer, String> writeDataToDBCheck(String csvPath) {
        StatusCode<Integer, String> val = readCSVCheck(csvPath);
        if (val.first==200)
        {
            emps.writeDataToDB(csvPath);
            return new StatusCode(20, "Sucess");
        }
        return val;
    }
}
