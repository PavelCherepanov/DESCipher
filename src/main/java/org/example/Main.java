package org.example;

import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        DES des = new DES("mama mamayjbgbbg ", "keyuu");
        System.out.println(des.encrypt());
    }
}