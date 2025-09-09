package com.litmus7.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.Dto.Dto;

public class FileUtil {
    public static List<Dto> parseCsv(File file) throws IOException {
        List<Dto> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    Dto dto = new Dto(
                            parts[0].trim(),                    
                            parts[1].trim(),                   
                            Integer.parseInt(parts[2].trim()), 
                            Double.parseDouble(parts[3].trim())
                    );
                    records.add(dto);
                }
            }
        }
         return records;
    
}
public static void moveFile(File src, String destDir) throws IOException {
        Files.move(src.toPath(),
                Paths.get(destDir, src.getName()),
                StandardCopyOption.REPLACE_EXISTING);
    }
}

