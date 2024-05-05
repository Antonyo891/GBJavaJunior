package org.example.jdbc;

import org.joda.time.DateTime;

import java.sql.*;
import java.util.*;

/**
 * Повторить все, что было на семниаре на таблице Student с полями
 * 1. id - bigint
 * 2. first_name - varchar(256)
 * 3. second_name - varchar(256)
 * 4. group - varchar(128)
 *
 * Написать запросы:
 * 1. Создать таблицу
 * 2. Наполнить таблицу данными (insert)
 * 3. Поиск всех студентов
 * 4. Поиск всех студентов по имени группы
 *
 * Доп. задания:
 * 1. ** Создать таблицу group(id, name); в таблице student сделать внешний ключ на group
 * 2. ** Все идентификаторы превратить в UUID
 *
 * Замечание: можно использовать ЛЮБУЮ базу данных: h2, postgres, mysql, ...
 */

public class Main {
    static final String URL = "jdbc:mysql://localhost:3306";
    static final String USER = "root";
    static final String PASSWORD = "root";
    static final String STUDENTS_DATABASE = "JDBC.Students";
    static String sqlAddGroup;
    static StringBuilder sqlQuery = new StringBuilder();
    static List<String> names = new ArrayList<>(Arrays.asList("Anton","Sergh","Denis","Alex","Dmitry","Anna","Julia","Mike"));
    static List<String> secondNames = new ArrayList<>(Arrays.asList("Orehov","Serov","Popov","Murovec","Dmitriev","Kuzmina","Marchenko","Bosson"));
    static List<String> groupsName = new ArrayList<>(Arrays.asList("333","222","111"));
    static List<Student> students = new ArrayList<>();

    static void insertIntoStudents(Connection connection, Student student){
        int indexPreparedValues = 1;
        StringBuilder sqlQuery = new StringBuilder("INSERT INTO ");
        StringBuilder column = new StringBuilder(" (");
        StringBuilder preparedValues = new StringBuilder("(");
        for (int i = 0; i < student.getFieldsName().length; i++) {
            if (i< student.getStudentInfo().length-1) {
                preparedValues.append("?,");
                column.append(student.getFieldsName()[i]).append(",");
            }
            if (i== student.getFieldsName().length-1) {
                preparedValues.append("?);");
                column.append(student.getFieldsName()[i]).append(")");
            }
        }
        sqlQuery.append(Main.STUDENTS_DATABASE).append(column).append(" VALUES ").append(preparedValues);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.toString(),
                    Statement.RETURN_GENERATED_KEYS);

            for (String col : student.getStudentInfo()) {
                preparedStatement.setString(indexPreparedValues++,col);
            }
            if (preparedStatement.executeUpdate()<1)
                throw new SQLException("The row was not add");
            try (ResultSet generatedKey = preparedStatement.getGeneratedKeys()){
                if (generatedKey.next()) {
                    UUID uuid = new UUID(DateTime.now().getMillis(),generatedKey.getLong(1));
                    student.setStudentsUUID(uuid);
                    student.setStudentId(generatedKey.getLong(1));
                    System.out.println(student);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 8; i++) {
            students.add(new Student(names.get(i),secondNames.get(i),
                    groupsName.get(new Random().nextInt(3))));

        }

       try (Connection connection = DriverManager.getConnection(
               URL,USER,PASSWORD);){
           Statement statement = connection.createStatement();
           statement.execute("""
                   CREATE database if not exists JDBC;""");
           statement.execute("""
                   USE JDBC;""");
           statement.execute("""
                   DROP TABLE IF EXISTS Students;""");
           statement.execute("""
                   CREATE TABLE Students(
                   id bigint auto_increment not null primary key,
                   first_name varchar(256) not null,
                   second_name varchar(256) not null UNIQUE,
                   number_group varchar(128) not null);
                   """);
           for (Student student : students) {
               insertIntoStudents(connection,student);
           }

//           } catch (SQLException ex) {
//           throw new RuntimeException(ex);
//       }

           ResultSet resultSet = statement.executeQuery("""
                   SELECT id,first_name, second_name, number_group FROM Students
                   """);
           StringBuilder output = new StringBuilder();
           while (resultSet.next()){
               output.append(resultSet.getLong("id")).append(" ")
                       .append(resultSet.getString("first_name")).append(" ")
                       .append(resultSet.getString("second_name")).append(" ")
                       .append(resultSet.getString("number_group")).append("\n");
           }
           System.out.println(output);
           resultSet = statement.executeQuery("""
                   SELECT id,first_name, second_name, number_group FROM Students
                   WHERE number_group = '333';
                   """);
           output = new StringBuilder();
           while (resultSet.next()){
               output.append(resultSet.getLong("id")).append(" ")
                       .append(resultSet.getString("first_name")).append(" ")
                       .append(resultSet.getString("second_name")).append(" ")
                       .append(resultSet.getString("number_group")).append("\n");
           }
           System.out.println(output);
           statement.execute("""
                   DROP TABLE IF EXISTS StudentsGroup;""");
           statement.execute("""
                   CREATE TABLE IF NOT EXISTS StudentsGroup(
                   id bigint auto_increment not null primary key,
                   name varchar(128) not null);
                   """);
           statement.execute("""
                   ALTER TABLE Students
                   ADD group_id bigint;
                   """);
           statement.execute("""
                   ALTER TABLE Students
                   ADD CONSTRAINT fk_group_id
                   FOREIGN KEY (group_id) REFERENCES StudentsGroup(id)
                   """);
           sqlQuery = new StringBuilder("INSERT INTO StudentsGroup (name) VALUES ");
           for (int i = 0; i < groupsName.size(); i++) {
               if (i< groupsName.size()-1) sqlQuery.append("(\"")
                       .append(groupsName.get(i))
                       .append("\"),");
               else sqlQuery.append("(\"")
                       .append(groupsName.get(i))
                       .append("\");");
           }
           System.out.println(statement.executeUpdate(sqlQuery.toString()));
           statement.executeUpdate("""
                   update Students
                   set group_id =
                   (select id from StudentsGroup where StudentsGroup.name = Students.number_group
                   limit 1) where group_id is null;
                   """);
           resultSet = statement.executeQuery("""
                   SELECT id,first_name, second_name, group_id FROM Students;
                   """);
           output = new StringBuilder();
           while (resultSet.next()){
               output.append(resultSet.getLong("id")).append(" ")
                       .append(resultSet.getString("first_name")).append(" ")
                       .append(resultSet.getString("second_name")).append(" ")
                       .append(resultSet.getInt("group_id")).append("\n");
           }
           System.out.println(output);
           statement.execute("""
                   Alter table Students
                   ADD StudentsUUID varchar(36);
                   """);
           for (Student student : students) {
               sqlQuery = new StringBuilder("UPDATE Students SET StudentsUUID = \"")
                       .append(student.getStudentsUUID().toString())
                       .append("\" WHERE id = ").append(student.getStudentId())
                       .append(";");
               System.out.println(sqlQuery);
               statement.executeUpdate(sqlQuery.toString());
           }
       }
       catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }
}
