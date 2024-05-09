package org.example.seminarFour;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.UUID;

@Entity()
@Table(name = "students")

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long studentId;
    @Column(name = "first_name",length = 256)
    private  String firstName;
    @Column(name = "second_name",unique = true,length = 256)
    private String secondName;
    @Column(name = "number_group",length = 128)
    private String numberGroup;
    @Column(name = "StudentsUUID",length = 36)
    private String studentsUUID;


    public Student(String firstName, String secondName, String numberGroup) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.numberGroup = numberGroup;

    }

    public Student() {
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getNumberGroup() {
        return numberGroup;
    }

    public void setNumberGroup(String numberGroup) {
        this.numberGroup = numberGroup;
    }

    public String getStudentsUUID() {
        return studentsUUID;
    }

    public void setStudentsUUID(String studentsUUID) {
        if (studentsUUID==null) createStudentUUID();
        else this.studentsUUID = studentsUUID;
    }

    public void createStudentUUID(){
        this.studentsUUID = new UUID(studentId, DateTime.now().getMillis()).toString();
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", numberGroup='" + numberGroup + '\'' +
                ", studentsUUID='" + studentsUUID + '\'' +
                '}';
    }
}
