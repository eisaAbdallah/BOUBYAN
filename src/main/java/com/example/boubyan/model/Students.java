package com.example.boubyan.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter


@Entity
@Table(name="students")
public class Students {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name="student_name")
    String studentName;
    @Column(name="password")
    String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "courses_students",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    Set<Courses> enrolledCourses=new HashSet<>();
    @Column(name="time_slot")
    LocalDate localDate;
    public Students() {

    }
    public Students(long id, String studentName, Set<Courses> enrolledCourses) {
        this.id = id;
        this.studentName = studentName;
        this.enrolledCourses = enrolledCourses;
    }

    public Students(String studentName, String password) {
        this.studentName = studentName;
        this.password = password;
    }

    public void removeStudentCourse(Courses courses) {



    }

}
