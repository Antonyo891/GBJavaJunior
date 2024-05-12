package org.example.seminarFour;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long studentId;
    @Column(name = "first_name",length = 256)
    private  String firstName;
    @Column(name = "second_name",unique = true,length = 256)
    private String secondName;
    @Column(name = "number_group",length = 128)
    private String numberGroup;

    @Column(name = "StudentsUUID",length = 36)
    private String studentsUUID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public Student(String firstName, String secondName, String numberGroup) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.numberGroup = numberGroup;
    }
    public Student() {

    }
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
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
    this.studentsUUID = studentsUUID;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void createStudentUUID(){
        if (this.studentsUUID==null)
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
                ", group=" + group +
                '}';
    }
}
