package com.litmus7.Controller;

import com.litmus7.services.Services;
import com.litmus7.Util.DbUtil;
import com.litmus7.Util.FileUtil;
import com.litmus7.Response.Response;

import java.io.File;
import java.sql.Connection;

public class Controller {

    private final Services service = new Services();

   
    public Response<Integer> writeCsvFilestoDB(String rootFolder) {
        String inputPath = rootFolder + "/input";
        String processedPath = rootFolder + "/processed";
        String errorPath = rootFolder + "/error";

        File folder = new File(inputPath);
        System.out.println("Looking in: " + inputPath + "  Exists? " + new File(inputPath).exists());

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if (files == null || files.length == 0) {
            return new Response<>(0, Response.Status.COMPLETED); 
        }

        int successCount = 0;
      

        for (File file : files) {
            System.out.println("Processing file: " + file.getName());

            try (Connection conn = DbUtil.getConnection()) {
                boolean success = service.processFile(conn, file);

                if (success) {
                    FileUtil.moveFile(file, processedPath);
                    System.out.println("File processed successfully: " + file.getName());
                    successCount++;
                } else {
                    FileUtil.moveFile(file, errorPath);
                    System.out.println("File moved due to error: " + file.getName());
           
                }
            } catch (Exception e) {
                e.printStackTrace();
       
            }
        }

        

        if (successCount == files.length) {
            return new Response<>(successCount, Response.Status.COMPLETED);
        } else if (successCount > 0) {
            return new Response<>(successCount, Response.Status.PROCESSED);
        } else {
            return new Response<>(0, Response.Status.FAILED);
        }
    }
}
