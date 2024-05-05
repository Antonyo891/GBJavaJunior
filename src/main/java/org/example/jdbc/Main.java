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
    static final String URL = "jdbc:mysql://localhost:3306";
    static final String USER = "root";
    static final String PASSWORD = "root";
    static final String STUDENTS_DATABASE = "JDBC.Students";
    static void insertIntoTable(String dataBase,
                                Connection connection, Student student){
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
        sqlQuery.append(STUDENTS_DATABASE).append(column).append(" VALUES ").append(preparedValues);
        System.out.println(sqlQuery.toString());
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.toString(),
                    Statement.RETURN_GENERATED_KEYS);
//            preparedStatement.setString(indexPreparedValues++,dataBase);

            for (String col : student.getStudentInfo()) {
                preparedStatement.setString(indexPreparedValues++,col);
            }
            if (preparedStatement.executeUpdate()<1)
                throw new SQLException("The row was not add");
            try (ResultSet generatedKey = preparedStatement.getGeneratedKeys()){
                if (generatedKey.next())
                    student.setUuid(generatedKey.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private static String sqlInsertIntoStudents = """
                   INSERT INTO Students (first_name, second_name, number_group) VALUES
                   (?,?,?);
                   """;
    private static Long id;
    public static void main(String[] args) {
        Long key = null;
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
                   id bigint auto-increment not null primary key,
                   first_name varchar(256) not null,
                   second_name varchar(256) not null UNIQUE,
                   number_group varchar(128) not null);
                   """);
//           ('Anton','Baimurzaev','106642'),
//                   ('Denis','Narivon','54545'),
//                   ('Alex','Buriy','555555')
                PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertIntoStudents, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1,"Anton");
                preparedStatement.setString(2,"Baimurzaev");
                preparedStatement.setString(3,"106642");
                if (preparedStatement.executeUpdate()>0) {
                    try (ResultSet generatedKey = preparedStatement.getGeneratedKeys()) {
                        if (generatedKey.next()) {
                            System.out.println(generatedKey.getLong(1));
                        }
                    }
                }

           } catch (SQLException ex) {
           throw new RuntimeException(ex);
       }
//           try(PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertIntoStudents,
//                   Statement.RETURN_GENERATED_KEYS);){
//               try (ResultSet generatedKey = preparedStatement.getGeneratedKeys()){
//                   Long id = generatedKey.getLong(1);
//                   System.out.println(id);
//                   preparedStatement.setLong(1,id);
//                   preparedStatement.setString(2,"Denis");
//                   preparedStatement.setString(3,"Narivon");
//                   preparedStatement.setString(4,"54545");
//               }
//           }
//           try(PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertIntoStudents,
//                   Statement.RETURN_GENERATED_KEYS);){
//               try (ResultSet generatedKey = preparedStatement.getGeneratedKeys()){
//                   Long id = generatedKey.getLong(1);
//                   System.out.println(id);
//                   preparedStatement.setLong(1,id);
//                   preparedStatement.setString(2,"Alex");
//                   preparedStatement.setString(3,"Buriy");
//                   preparedStatement.setString(4,"555555");
//               }
//           }
//           ResultSet resultSet = statement.executeQuery("""
//                   SELECT id,first_name, second_name, number_group FROM Students
//                   """);
//           StringBuilder output = new StringBuilder();
//           while (resultSet.next()){
//               output.append(resultSet.getLong("id")).append(" ")
//                       .append(resultSet.getString("first_name")).append(" ")
//                       .append(resultSet.getString("second_name")).append(" ")
//                       .append(resultSet.getString("number_group")).append("\n");
//           }
//           System.out.println(output);
//           resultSet = statement.executeQuery("""
//                   SELECT id,first_name, second_name, number_group FROM Students
//                   WHERE number_group = '555555';
//                   """);
//           output = new StringBuilder();
//           while (resultSet.next()){
//               output.append(resultSet.getLong("id")).append(" ")
//                       .append(resultSet.getString("first_name")).append(" ")
//                       .append(resultSet.getString("second_name")).append(" ")
//                       .append(resultSet.getString("number_group")).append("\n");
//           }
//           System.out.println(output);
//           statement.execute("""
//                   CREATE TABLE IF NOT EXISTS Groupss(
//                   id bigint auto_increment not null primary key,
//                   name varchar(128) not null);
//                   """);
//           statement.execute("""
//                   ALTER TABLE Students
//                   ADD group_id bigint;
//                   """);
//           statement.execute("""
//                   ALTER TABLE Students
//                   ADD CONSTRAINT fk_group_id
//                   FOREIGN KEY (group_id) REFERENCES Groupss(id)
//                   """);
//           System.out.println(statement.executeUpdate("""
//                   INSERT INTO Groupss (name) VALUES
//                   ('106642'),
//                   ('54545'),
//                   ('555555')
//                   """));
//           statement.executeUpdate("""
//                   update Students
//                   set group_id =
//                   (select id from Groupss where Groupss.name = Students.number_group
//                   limit 1) where group_id is null;
//                   """);
//           resultSet = statement.executeQuery("""
//                   SELECT id,first_name, second_name, group_id FROM Students;
//                   """);
//           output = new StringBuilder();
//           while (resultSet.next()){
//               output.append(resultSet.getLong("id")).append(" ")
//                       .append(resultSet.getString("first_name")).append(" ")
//                       .append(resultSet.getString("second_name")).append(" ")
//                       .append(resultSet.getInt("group_id")).append("\n");
//           }
//           System.out.println(output);
//           System.out.println(statement.getGeneratedKeys());
//       }
//       catch (SQLException e) {
//           throw new RuntimeException(e);
//       }
    }
}
