package org.example.seminarFour;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "students_group")
public class Group {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private Long groupId;
@Column(name = "name")
private String groupName;
@Column(name = "teacher",unique = true)
private String teachersName;
@OneToMany(fetch = FetchType.LAZY)
@JoinColumn(name = "group_id")
private List<Student> students;

public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group() {
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTeachersName() {
        return teachersName;
    }

    public void setTeachersName(String teachersName) {
        this.teachersName = teachersName;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public List<Student> getStudents() {
        return students;
    }
    public List<Long> getStudentsId(){
        if (students!=null) return students.stream().map(Student::getStudentId).toList();
        return null;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", teachersName='" + teachersName + '\'' +
                ", students= " + getStudentsId() +
                "}";
    }
}
