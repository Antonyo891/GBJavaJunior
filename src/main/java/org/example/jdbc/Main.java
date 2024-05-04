package org.example.jdbc;

import java.sql.*;
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
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    public static void main(String[] args) {
       try (Connection connection = DriverManager.getConnection(
               URL,USER,PASSWORD)){
           Statement statement = connection.createStatement();
           statement.execute("""
                   CREATE database if not exists JDBC;
                   """);
           statement.execute("""
                   USE JDBC;""");
           System.out.println(statement.executeUpdate("""
                   DROP TABLE IF EXISTS Students"""));
           statement.execute("""
                   CREATE TABLE Students(
                   id bigint auto_increment not null primary key,
                   first_name varchar(256) not null,
                   second_name varchar(256) not null UNIQUE,
                   number_group varchar(128) not null);
                   """);
           System.out.println(statement.executeUpdate("""
                   INSERT INTO Students (first_name, second_name, number_group) VALUES
                   ('Anton','Baimurzaev','106642'),
                   ('Denis','Narivon','54545'),
                   ('Alex','Buriy','555555')
                   """));
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
                   WHERE number_group = '555555';
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
                   CREATE TABLE IF NOT EXISTS Groupss(
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
                   FOREIGN KEY (group_id) REFERENCES Groupss(id)
                   """);
           System.out.println(statement.executeUpdate("""
                   INSERT INTO Groupss (name) VALUES
                   ('106642'),
                   ('54545'),
                   ('555555')
                   """));
           statement.executeUpdate("""
                   update Students
                   set group_id =
                   (select id from Groupss where Groupss.name = Students.number_group
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
           System.out.println(statement.getGeneratedKeys());
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }
}
