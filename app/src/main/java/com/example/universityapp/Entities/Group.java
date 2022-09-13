package com.example.universityapp.Entities;

public class Group {
    private long _id;
    private String nameOfFaculty;
    private String groupNumber;

    public Group() {
    }

    public Group(String nameOfFaculty, String groupNumber) {
        this.nameOfFaculty = nameOfFaculty;
        this.groupNumber = groupNumber;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long ID) {
        this._id = ID;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getNameOfFaculty() {
        return nameOfFaculty;
    }

    public void setNameOfFaculty(String nameOfFaculty) {
        this.nameOfFaculty = nameOfFaculty;
    }
}
