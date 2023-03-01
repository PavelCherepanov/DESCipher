package org.example;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DES {
    private final int sizeOfBlock = 64; //в DES размер блока 64 бит, но поскольку в unicode символ в два раза длинее, то увеличим блок тоже в два раза
    private final int sizeOfChar = 8; //размер одного символа (in Unicode 16 bit)
    private final int shiftKey = 2; //сдвиг ключа
    private final int quantityOfRounds = 16; //количество раундов
    List<String> blocks = new ArrayList<>(); //сами блоки в двоичном формате

    private String text;
    private String key;

    public DES(String text, String key) {
        this.text = text;
        this.key = key;
    }

    public String addSymbols(){
        StringBuilder newText = new StringBuilder(text.toString());
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        while ((text.length() * sizeOfChar) % sizeOfBlock != 0){
            text += "*";
        }
        System.out.println("Длинна текта: "+text.length());
        return text.toString();
    }

    public List<String> divisionBlocks(){
        for (int i = 0; i < text.length(); i+=8) {
            blocks.add(text.substring(i, i+8));
        }
        return blocks;
    }

    public String getCorrectKey(int lengthKey){
        if (key.length() > lengthKey){
            key.substring(0, lengthKey);
        } else{
            while(key.length() < lengthKey){
                key += "0";
            }
        }
        System.out.println("Длинна ключа: "+lengthKey);
        return key;
    }

    public String encodeOneRound(String block, String key) throws UnsupportedEncodingException {
        String L = block.substring(0, block.length() / 2);
        System.out.println("3"+L);
        String R = block.substring(block.length() / 2);
        System.out.println("3"+R);
        return (R + xor(L, f(R, key)));
    }

    private String getBinaryString(String string) throws UnsupportedEncodingException {
        byte[] bytes = null;
        bytes = string.getBytes(StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append("0"+Integer.toBinaryString(b));
            System.out.println((char) b + Integer.toBinaryString(b));
        }
        return stringBuilder.toString();
    }

    private boolean convertIntToBoolean(int integer){
        boolean result = false;
        if(integer == 1){
            result = true;
        }
        return result;
    }
    public String xor(String string1, String string2) throws UnsupportedEncodingException {
        String result = "";
        String bytes1 = getBinaryString(string1);
        String bytes2 = getBinaryString(string2);
        System.out.println(bytes1);
        System.out.println(bytes2);
        for (int i = 0; i < bytes1.length(); i++) {
            boolean a = convertIntToBoolean(Integer.parseInt(String.valueOf(bytes1.charAt(i))));
            boolean b = convertIntToBoolean(Integer.parseInt(String.valueOf(bytes2.charAt(i))));
            if (a ^ b)
                result += "1";
            else
                result += "0";
        }
        return result;
    }

    private String f(String s1, String s2) throws UnsupportedEncodingException {
        return xor(s1, s2);
    }

    private String getKeyToNextRound(String key) {
        for (int i = 0; i < shiftKey; i++) {
            key = key.charAt(key.length() - 1) + key;
            key = key.replace(String.valueOf(key.charAt(key.length() - 1)), "");
        }
        return key;
    }


    public String encrypt() throws UnsupportedEncodingException {
        List<String> encryptedText = new ArrayList<String>();
        text = addSymbols();
        blocks = divisionBlocks();
        key = getCorrectKey(text.length()/(2*blocks.size()));
        for (int i = 0; i < quantityOfRounds; i++) {
            for (int j = 0; j < blocks.size(); j++) {
                encryptedText.add(encodeOneRound(blocks.get(j), key));
            }
        }
        return String.join(", ", encryptedText);
    }

    public void decrypt(){

    }
}
