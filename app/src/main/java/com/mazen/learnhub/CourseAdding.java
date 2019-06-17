package com.mazen.learnhub;

public class CourseAdding {

    String courseId;
    String courseName;
    String courseCategory;
    String courseArea;

    public CourseAdding(){}

    public CourseAdding(String courseID, String courseName, String courseCategory, String courseArea) {
        this.courseId = courseID;
        this.courseName = courseName;
        this.courseCategory = courseCategory;
        this.courseArea = courseArea;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCategory() {
        return courseCategory;
    }

    public String getCourseArea() {
        return courseArea;
    }
}
