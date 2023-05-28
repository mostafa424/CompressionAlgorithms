package com.company;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
            System.out.println("Please Enter number that to corresponds to the Operation to want to do:");
            System.out.println("1.Compress");
            System.out.println("2.Decompress");
            System.out.println("3.check if valid");
            System.out.println("4.exit");
            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            if(option == 1){
                System.out.println("Specify File Location:");
                sc.nextLine();
                String filePath = sc.nextLine();
                Compressor_LZW lzw = new Compressor_LZW("ASCII");
                FileReaderWriter frw = new FileReaderWriter(filePath,"ASCII");
                String input = frw.readUnCompressedFile();
                double input_size = frw.getFileSize(filePath);
                System.out.println("reading ended, compressing file....");
                List<Integer> comp_data = lzw.encode(input.toCharArray());
                System.out.println("compression Complete,Writing File....");
                double output_size = frw.writeToCompressedFile(comp_data);
                System.out.println("CompressionRatio:");
                System.out.println(output_size/input_size);
                System.out.println("Exiting...");
            }
            else if(option==2){
                System.out.println("Specify File Location:");
                sc.nextLine();
                String filePath = sc.nextLine();
                Compressor_LZW lzw = new Compressor_LZW("ASCII");
                FileReaderWriter frw = new FileReaderWriter(filePath,"ASCII");
                List<Integer> comp_data = frw.readCompressedFile();
                System.out.println("reading ended, Decompressing file....");

                String output = lzw.decode(comp_data);

                frw.writeUnCompressedFile(output);
                System.out.println("Exiting...");
            }
            else if(option == 3){
                Compressor_LZW lzw = new Compressor_LZW("ASCII");
                System.out.println("Enter original file location: ");
                sc.nextLine();
                String filePath = sc.nextLine();
                FileReaderWriter frw = new FileReaderWriter(filePath,"ASCII");
                String input = frw.readUnCompressedFile();
                System.out.println("Enter compressed file location: ");
                sc.nextLine();
                String file2Path = sc.nextLine();
                FileReaderWriter frw2 = new FileReaderWriter(file2Path,"ASCII");
                List<Integer> comp_input = frw2.readCompressedFile();
                String output = lzw.decode(comp_input);
                System.out.println("checking for validity:");
                System.out.println(lzw.checkForValidity(input,output));
                System.out.println("Exiting...");
            }
    }
}
