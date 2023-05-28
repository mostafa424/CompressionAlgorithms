package com.company;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Compressor_LZW {
    private HashMap<String,Integer> charToAscii;
    private HashMap<Integer,String> asciiToChar;
    int tableSize;
    public Compressor_LZW(String charset){
        charToAscii = new HashMap<>();
        asciiToChar = new HashMap<>();
        if(charset.equals("ASCII")){
            this.tableSize=256;
        }
        else{
            this.tableSize=10175;
        }
        for(int i=0;i<this.tableSize;i++){
         charToAscii.put(String.valueOf((char)i),i);
        }
        for(int i=0;i<this.tableSize;i++){
            asciiToChar.put(i,String.valueOf((char)i));
        }
    }
    public List<Integer> encode(char[] data){
        int dictSize = this.tableSize;
        StringBuilder foundChars = new StringBuilder();
        String charToAdd = "";
        List<Integer> comp_data = new ArrayList<>();
        for(int i=0;i<data.length;i++){
            foundChars.append(data[i]);
            if(!charToAscii.containsKey(foundChars.toString())){
                charToAdd = foundChars.toString();
                charToAscii.put(charToAdd,dictSize++);
                foundChars.delete(foundChars.length()-1,foundChars.length());
                comp_data.add(charToAscii.get(foundChars.toString()));

                foundChars.setLength(0);
                foundChars.append(data[i]);
            }
        }
        if(!foundChars.isEmpty()){
            comp_data.add(charToAscii.get(foundChars.toString()));
            foundChars.setLength(0);
        }
        return comp_data;
    }
    public  String decode(List<Integer> comp_dta) {
        int dictSize = this.tableSize;
        String characters = String.valueOf((char) comp_dta.remove(0).intValue());
        StringBuilder result = new StringBuilder(characters);
        for (int code : comp_dta) {
            String entry = asciiToChar.containsKey(code)
                    ? asciiToChar.get(code)
                    : characters + characters.charAt(0);
            result.append(entry);
            asciiToChar.put(dictSize++, characters + entry.charAt(0));
            characters = entry;
        }
        return result.toString();
    }
    private String hashString(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    public boolean checkForValidity(String in,String out) throws NoSuchAlgorithmException {
        System.out.println("Validity check:");
        return hashString(in).equals(hashString(out));
    }
}
