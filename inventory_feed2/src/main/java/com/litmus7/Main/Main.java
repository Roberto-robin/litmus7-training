package com.litmus7.Main;

import java.io.File;

import com.litmus7.Controller.Controller;
import com.litmus7.Response.Response;

public class Main {
    public static void main(String[] args) {
        String rootFolder = ".";
        Controller controller = new Controller();
        Response<Integer> response = controller.writeCsvFilestoDBPhase2(rootFolder);

        int statusCode = response.getStatusCode();
        Integer processedCount = response.getData();

        File inputFolder = new File(rootFolder + "/input");
        int totalFiles = inputFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv")).length;
        int failedCount = totalFiles - processedCount;


        if (statusCode == Response.Status.COMPLETED.getCode()) {
            System.out.println("All files processed successfully.");
            System.out.println("No. of files processed successfully: " + processedCount);
        } else if (statusCode == Response.Status.PROCESSED.getCode()) {
            System.out.println("Partial Success: Some files processed, some failed.");
            System.out.println("No. of files processed successfully: " + processedCount + "failed number of files : " + failedCount*-1);
        } else if (statusCode == Response.Status.FAILED.getCode()) {
            System.out.println("Failure: No files processed successfully.");
            System.out.println("No. of files processed successfully: " + processedCount);
        } else {
            System.out.println("Unknown status code returned: " + statusCode);
        }
    }
        
    }
