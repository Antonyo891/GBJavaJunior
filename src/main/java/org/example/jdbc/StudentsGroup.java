package org.example.jdbc;

import java.util.UUID;

public class StudentsGroup {
    private Long id;
    private UUID groupsUUID;
    private String name;

    public StudentsGroup(Long id, UUID groupsUUID, String name) {
        this.id = id;
        this.groupsUUID = groupsUUID;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getGroupsUUID() {
        return groupsUUID;
    }

    public void setGroupsUUID(UUID groupsUUID) {
        this.groupsUUID = groupsUUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StudentsGroup{" +
                "id=" + id +
                ", groupsUUID=" + groupsUUID +
                ", name='" + name + '\'' +
                '}';
    }
}
