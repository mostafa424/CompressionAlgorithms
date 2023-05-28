package com.company;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileReaderWriter {
    String path;
    String filename;
    String full_path;
    Charset charset;
    public FileReaderWriter(String full_path,String charset){
        this.full_path = full_path;
        File file = new File(full_path);
        this.path = file.getParent();
        this.filename = file.getName();
        if(charset .equals("ASCII")){
            this.charset = StandardCharsets.US_ASCII;
        }
        if(charset .equals("UTF-8")){
            this.charset = StandardCharsets.UTF_8;
        }

    }
    public String readUnCompressedFile() throws IOException {
        File file = new File(full_path);
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("reading file....");
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file),this.charset))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    public List<Integer> readCompressedFile(){
        File file = new File(full_path);
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("reading file....");
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file),this.charset))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] input_as_String = stringBuilder.toString().split(" ");
        List<Integer> comp_data = new ArrayList<>();
        for(String i:input_as_String){
            comp_data.add(Integer.valueOf(i));
        }
        return comp_data;
    }
    public double writeUnCompressedFile(String data){
        this.filename=this.filename.replace("_compressed.lzw","");
        String new_file_name = this.filename+"_decompressed.txt";
        try {
            FileWriter fileWriter = new FileWriter(path+new_file_name);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("files Decompressed Successfully!");
            System.out.println("Decompressed file can be found in same location of original file");
        } catch (IOException e) {
            System.out.println("An error occurred while writing integers to the file.");
            e.printStackTrace();
        }
        return getFileSize(path+new_file_name);
    }

    /*public double writeCompressedFile(List<Integer> comp_data){
        this.filename=this.filename.replace(".txt","");
        String new_file_name = this.filename+"_compressed.txt";
        try {
            FileWriter fileWriter = new FileWriter(path+new_file_name);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            if(charset==StandardCharsets.US_ASCII){
                for (Integer integer : comp_data) {
                    bufferedWriter.write(String.valueOf(integer.shortValue()));
                    bufferedWriter.write(' ');
                }
            }
            else{
                for (Integer integer : comp_data) {
                    bufferedWriter.write(String.valueOf(integer));
                    bufferedWriter.write(' ');
                }
            }
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("files Compressed Successfully!");
            System.out.println("Compressed file can be found in same location of original file");
        } catch (IOException e) {
            System.out.println("An error occurred while writing integers to the file.");
            e.printStackTrace();
        }
        return getFileSize(path+new_file_name);
    }*/
    public double writeToCompressedFile(List<Integer>comp_data) throws Exception {
        int max = Collections.max(comp_data);
        StringBuilder bits_to_write = new StringBuilder();
        String max_in_bits = convert_to_binary(max);
        int max_bits = max_in_bits.length();
        bits_to_write.append(max_bits);
        for(int i : comp_data){
            bits_to_write.append(convert_to_binary(i,max_bits));
        }
        this.filename=this.filename.replace(".txt","");
        String new_file_name = this.filename+"_compressed.txt";
        FileWriter fileWriter = new FileWriter(path+new_file_name);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(bits_to_write.toString());
        bufferedWriter.close();
        fileWriter.close();
        return getFileSize(path+new_file_name);
    }
    public String convert_to_binary(int x){
        if (x == 0) {
            return "0";
        }
        String binary = "";
        while (x > 0) {
            int rem = x % 2;
            binary = rem + binary;
            x /= 2;
        }
        return binary;
    }
    public String convert_to_binary(int x,int bits){
        String binary = "";
        for(int i=0;i<bits;i++) {
            int rem = x % 2;
            binary = rem + binary;
            x /= 2;
        }
        return binary;
    }
    public double getFileSize(String path){
        File f = new File(path);
        return f.length();
    }
}
