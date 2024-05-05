package org.example.jdbc;

import java.util.UUID;

public class Student {
    private String firstName;
    private String secondName;
    private String numberGroup;
    private UUID studentsUUID;
    private Long studentId;
    private Long groupId;
    private UUID groupUUID;

    public Student(String firstName, String secondName, String numberGroup) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.numberGroup = numberGroup;
    }
    public Student(String firstName, String secondName, String numberGroup,String uuid) {
        this(firstName,secondName,numberGroup);
        this.studentsUUID = UUID.fromString(uuid);
    }
    public Student(String firstName, String secondName, String numberGroup,String uuid,Long groupId) {
        this(firstName,secondName,numberGroup,uuid);
        this.groupId = groupId;
    }
    public Student(String firstName, String secondName, String numberGroup,String uuid,Long groupId,UUID groupUUID) {
        this(firstName,secondName,numberGroup,uuid,groupId);
        this.groupUUID = groupUUID;
    }


    public void setStudentsUUID(UUID studentsUUID) {
        if (this.studentsUUID ==null) this.studentsUUID = studentsUUID;
    }
    public void setStudentsUUID(String uuid) {
        if (this.studentsUUID ==null) this.studentsUUID = UUID.fromString(uuid);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getNumberGroup() {
        return numberGroup;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public void setGroupUUID(UUID groupUUID) {
        this.groupUUID = groupUUID;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public UUID getStudentsUUID() {
        if (this.studentsUUID ==null) throw  new RuntimeException();
        return studentsUUID;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String[] getStudentInfo(){
      return new String[] {firstName,secondName,numberGroup};
    };
    public String[] getFieldsName(){
        return new String[] {"first_name", "second_name", "number_group"};
    };


    public String[] getFullStudentInfo(){
        return new String[] {firstName,secondName,numberGroup, studentsUUID.toString()};
    };


    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", numberGroup='" + numberGroup + '\'' +
                ", studentsUUID=" + studentsUUID +
                ", studentId=" + studentId +
                ", groupId=" + groupId +
                ", groupUUID=" + groupUUID +
                '}';
    }
}
