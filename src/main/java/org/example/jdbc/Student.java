package org.example.jdbc;

import java.util.UUID;

public class Student {
    private String firstName;
    private String secondName;
    private String numberGroup;
    private UUID uuid;

    public Student(String firstName, String secondName, String numberGroup) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.numberGroup = numberGroup;
    }
    public Student(String firstName, String secondName, String numberGroup,String uuid) {
        this(firstName,secondName,numberGroup);
        this.uuid = UUID.fromString(uuid);
    }

    public void setUuid(UUID uuid) {
        if (this.uuid==null) this.uuid = uuid;
    }
    public void setUuid(String uuid) {
        if (this.uuid==null) this.uuid = UUID.fromString(uuid);
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

    public UUID getUuid() {
        if (this.uuid==null) throw  new RuntimeException();
        return uuid;
    }

    public String[] getStudentInfo(){
      return new String[] {firstName,secondName,numberGroup};
    };
    public String[] getFieldsName(){
        return new String[] {"first_name", "second_name", "number_group"};
    };


    public String[] getFullStudentInfo(){
        return new String[] {firstName,secondName,numberGroup,uuid.toString()};
    };

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", numberGroup='" + numberGroup + '\'' +
                ", uuid=" + uuid +
                '}';
    }
}
