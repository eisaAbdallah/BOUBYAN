package com.example.boubyan.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Table(name="courses_students")

public class CoursesStudents {


    @Column(name="student_id")
    long studentId;

    @Column(name="course_id")
    long courseId;



    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }


}
