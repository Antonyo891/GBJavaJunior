package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String string = "@Error Client with name already exists.";
        System.out.println(string);
        String [] arr = string.split(" ");
        for (String s : arr) {
            System.out.println(s);
        }
        String[] login = arr[0].split("@");
        String log = login[1];
        System.out.println(log);
        StringBuilder list = new StringBuilder();
                Arrays.stream(arr)
                .filter(s->!s.contains("@"))
                        .map(s->s+" ")
                        .forEach(list::append);
        System.out.println(list);
    }
}
