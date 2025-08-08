package com.litmus7.util;
import java.io.File;
public class csvUtil {
    public static boolean checkCSV(String filePath){
        File file = new File(filePath);
        String fileName = file.getName();
        return fileName.endsWith(".csv");
    }
}
