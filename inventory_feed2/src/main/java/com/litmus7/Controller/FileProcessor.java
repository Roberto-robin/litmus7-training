package com.litmus7.Controller;

import com.litmus7.services.Services;
import com.litmus7.Util.DbUtil;
import com.litmus7.Util.FileUtil;

import java.io.File;
import java.sql.Connection;
import java.util.concurrent.atomic.AtomicInteger;

public class FileProcessor implements Runnable {
    private final File file;
    private final Services service;
    private final String processedDir;
    private final String errorDir;
    private final AtomicInteger successCounter;

    public FileProcessor(File file,
                         Services service,
                         String processedDir,
                         String errorDir,
                         AtomicInteger successCounter) {
        this.file = file;
        this.service = service;
        this.processedDir = processedDir;
        this.errorDir = errorDir;
        this.successCounter = successCounter;
    }

    @Override
    public void run() {
        try (Connection conn = DbUtil.getConnection()) {
            boolean ok = service.processFile(conn, file);
            if (ok) {
                try {
                    FileUtil.moveFile(file, processedDir);
                    successCounter.incrementAndGet();
                    System.out.println("Processed: " + file.getName());
                } catch (Exception moveEx) {
                    successCounter.incrementAndGet();
                    System.err.println("DB commit OK but failed to move to processed: " + file.getName());
                    moveEx.printStackTrace();
                }
            } else {
                try {
                    FileUtil.moveFile(file, errorDir);
                    System.out.println("Moved to error: " + file.getName());
                } catch (Exception moveEx) {
                    System.err.println("Failed to move failed file to error: " + file.getName());
                    moveEx.printStackTrace();
                }
            }
        } catch (Exception e) {
          
            System.err.println("Unexpected error processing file: " + file.getName() + " -> " + e.getMessage());
            e.printStackTrace();
            try {
                FileUtil.moveFile(file, errorDir);
            } catch (Exception moveEx) {
                System.err.println("Also failed to move file to error: " + file.getName());
                moveEx.printStackTrace();
            }
        }
    }
}
