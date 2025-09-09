package com.litmus7.Controller;

import com.litmus7.services.Services;
import com.litmus7.Util.DbUtil;
import com.litmus7.Util.FileUtil;
import com.litmus7.Response.Response;
import com.litmus7.Controller.FileProcessor;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {

    private final Services service = new Services();

   
    public Response<Integer> writeCsvFilestoDBPhase2(String rootFolder) {
        String inputPath = rootFolder + "/input";
        String processedPath = rootFolder + "/processed";
        String errorPath = rootFolder + "/error";

        File folder = new File(inputPath);
        System.out.println("Looking in: " + inputPath + "  Exists? " + new File(inputPath).exists());

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if (files == null || files.length == 0) {
            return new Response<>(0, Response.Status.COMPLETED); 
        }

     
        int started = 0;
        AtomicInteger successCounter = new AtomicInteger(0);
        List<Thread> threads = new ArrayList<>();

      

        for (File file : files) {
            Thread t = new Thread(new FileProcessor(file, service, processedPath, errorPath, successCounter));
            t.start();
           threads.add(t);
        
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Interrupted while waiting for threads to finish");
            }
        }

        int successCount = successCounter.get();

        if (successCount == files.length) {
            return new Response<>(successCount, Response.Status.COMPLETED);
        } else if (successCount > 0) {
            return new Response<>(successCount, Response.Status.PROCESSED);
        } else {
            return new Response<>(0, Response.Status.FAILED);
        }
    }
}
