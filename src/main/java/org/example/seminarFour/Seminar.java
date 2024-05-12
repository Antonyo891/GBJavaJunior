package org.example.seminarFour;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.lang.module.Configuration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*Выполнять домашнее задание с семниара!!!

Перенести структуру дз третьего урока на JPA:
1. Описать сущности Student и Group
2. Написать запросы: Find, Persist, Remove
3. ... поупражняться с разными запросами ...

Задание: Настройте связь между вашим приложением и базой данных MySQL с использованием Hibernate.
Создайте несколько объектов Person и сохраните их в базу данных.*/
public class Seminar {
    public static void main(String[] args) {
        Connector connector = new Connector();
        try (Session session = connector.getSession()){
            Student student1 = new Student("Pavel","Durov","106427");
            Student student2 = new Student("Anton","Durovs","106427");
            Student student3 = new Student("Anton","Durovc","106427");
            Student student4 = new Student("Hariton","Durovv","106427");
            Student student5 = new Student("Julia","Marchenko","106417");
            Student student6 = new Student("Dmitry","Lisovin","106417");
            List<Student> students = new ArrayList<>(Arrays.asList(student1,student2,student3,student4,student5,student6));
            List<Group> groups = new ArrayList<>();
            groups.add(new Group("106427"));
            groups.add(new Group("106417"));
            Transaction transaction = session.beginTransaction();
            groups.forEach(session::persist);
            transaction.commit();
            transaction = session.beginTransaction();
            students.forEach(s->
                s.setGroup(session.createQuery("Select u from Group u where u.groupName = ?1", Group.class)
                        .setParameter(1,s.getNumberGroup())
                        .getResultList().get(0)));
            students.forEach(session::persist);
            transaction.commit();
            Student student = session.find(Student.class,1L);
            if (student!=null){
                transaction = session.beginTransaction();
                session.remove(student);
                transaction.commit();
                System.out.println(student.getSecondName() + " was deleted.");
            }
            List<Student> students1 = session.createQuery("Select u from Student u", Student.class).getResultList();
            transaction = session.beginTransaction();
            students1.forEach(Student::createStudentUUID);
            students1.forEach(session::merge);
            transaction.commit();
            Group group1 = session.find(Group.class,1L);
            group1.setTeachersName("Nerezko");
            System.out.println(group1);
            transaction = session.beginTransaction();
            session.merge(group1);
            transaction.commit();
            session.createQuery("Select u from Student u", Student.class).getResultList().forEach(System.out::println);
            Query<Student> studentsQuery = session.createQuery("Select u from Student u where u.secondName in (?1,?2)", Student.class)
                    .setParameter(1,"Durovc")
                    .setParameter(2,"Durov");
            List<Student> studentList = studentsQuery.getResultList();
            transaction = session.beginTransaction();
            studentList.forEach(session::remove);
            transaction.commit();
            List<Student> finalStudents = session.createQuery("Select u from Student u", Student.class)
                    .getResultList();
            System.out.println("****************************");
            System.out.println("From table students");
            finalStudents.forEach(System.out::println);
            List<Group> finalGroups = session.createQuery("Select u from Group u",Group.class)
                    .getResultList();
            System.out.println("****************************");
            System.out.println("From table students_group");
            finalGroups.forEach(System.out::println);
            System.out.println("****************************");
            System.out.println("After setters groups");
            groups.forEach(g->{g.setStudents(finalStudents.stream()
                        .filter(s->s.getNumberGroup().equals(g.getGroupName()))
                        .toList());
                System.out.println(g);
            });
            System.out.println("****************************");
            System.out.println("After setters students");
            finalStudents.forEach(s->{
                s.setGroup(finalGroups.stream()
                        .filter(g->g.getGroupName().equals(s.getNumberGroup())).findFirst().get());
                System.out.println(s);
            });
            System.out.println("*****************");
            System.out.println("From tables");
            session.createQuery("Select u from Student u", Student.class).getResultList().forEach(System.out::println);
            session.createQuery("Select u from Group u",Group.class).getResultList().forEach(System.out::println);

        }
    }
}
