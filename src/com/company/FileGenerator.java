package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileGenerator {
    public static void generate_File(){
        String filePath = "D:\\College\\3-year\\term 2\\Multimedia\\Project\\src\\com\\company\\test_1GB.txt";
        File file = new File(filePath);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            long fileSize = 50L *1024L * 1024L; // 50MB
            long bytesWritten = 0L;
            while (bytesWritten < fileSize) {
                String line = "This is a test line."; // Replace with your own data
                bw.write(line);
                bw.newLine();
                bytesWritten += line.getBytes().length + 2; // +2 for the newline character
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

