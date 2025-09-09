package com.litmus7.Util;
import java.io.File;
public class CsvUtil {
    public static boolean checkCSV(String filePath){
        File file = new File(filePath);
        String fileName = file.getName();
        return fileName.endsWith(".csv");
    }
}
