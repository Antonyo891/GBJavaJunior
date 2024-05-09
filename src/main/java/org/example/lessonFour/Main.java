package org.example.lessonFour;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLIntegrityConstraintViolationException {
        Connector connector = new Connector();
        Session session1 = connector.getSession();
        Student student = new Student("Anna","Pavlova","333");
        session1.beginTransaction();
        session1.save(student);
        student = new Student("Inna","Dunaeva","111");
        session1.save(student);
        session1.getTransaction().commit();
        session1.close();
        try (Session session = connector.getSession()){
            Transaction transaction = session.beginTransaction();
            List<Student> students= session.createQuery("From Student",Student.class).getResultList();
//            students.stream().
//            filter(s->s.getStudentId()==12)
//                    .forEach(session::delete);
            students.stream().filter(s->s.getStudentId()>21).forEach(System.out::println);
            students.stream().filter(s->{
                boolean b = s.getStudentsUUID() == null;
                System.out.println(s.getStudentId() + " " + b);
                return b;
            }).forEach(Student::createStudentUUID);
            students.stream().filter(s->s.getStudentId()>21).forEach(System.out::println);
            students.forEach(session::update);
            transaction.commit();

        } catch (ConstraintViolationException e){
            System.out.println(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
