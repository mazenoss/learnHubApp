package com.mazen.learnhub;

public class list {
private String coursename;
private String instructorname;
private String area;
private String hours;
private String cost;


public list(){}

public list(String coursename, String instructorname, String area, String hours, String cost){
    this.coursename = coursename;
    this.instructorname = instructorname;
    this.area = area;
    this.hours = hours;
    this.cost = cost;
}

    public String getCoursename() {
        return coursename;
    }

    public String getInstructorname() {
        return instructorname;
    }

    public String getArea() {
        return area;
    }

    public String getHours() {
        return hours;
    }

    public String getCost() {
        return cost;
    }
}
