package org.example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import static org.example.jdbc.Main.*;

public class Test {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(Arrays.asList("Anton","Sergh","Denis","Alex","Dmitry","Anna","Julia","Mike"));
        List<String> secondNames = new ArrayList<>(Arrays.asList("Orehov","Serov","Popov","Murovec","Dmitriev","Kuzmina","Marchenko","Bosson"));
        List<String> group = new ArrayList<>(Arrays.asList("333","222","111"));
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            students.add(new Student(names.get(i),secondNames.get(i),
                    group.get(new Random().nextInt(3))));
        }
//        System.out.println(students);
        try (Connection connection = DriverManager.getConnection(
                URL, USER, PASSWORD);) {
            insertIntoTable(STUDENTS_DATABASE,connection,students.get(0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
