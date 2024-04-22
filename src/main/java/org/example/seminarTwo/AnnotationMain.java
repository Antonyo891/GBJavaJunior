package org.example.seminarTwo;

import org.example.seminarTwo.annotRandom.RandomInteger;
import org.example.seminarTwo.annotRandom.RandomIntegerProcessor;

public class AnnotationMain {
    static  Buyer[] buyers = new Buyer[5];
    static String [] names = {"Anton","Alex","Alex","Adolf","Bernard"};
    public static void main(String[] args) {
        for (int i = 0; i < buyers.length; i++) {
            buyers[i] = new Buyer(names[i]);
            RandomIntegerProcessor.processRandomInteger(buyers[i]);
            System.out.println(buyers[i]);
        }
    }
}
