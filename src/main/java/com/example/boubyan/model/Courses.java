package com.example.boubyan.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name="courses")
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Courses {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name="course_name")
    String courseName;

    @ManyToMany(mappedBy = "enrolledCourses",fetch = FetchType.LAZY)
    Set<Students> students;


    @OneToOne
    @JoinColumn(name="course_id")
    private CourseDetails courseDetails;


    public Courses(  String courseName) {

        this.courseName = courseName;
    }


    public Courses(String courseName, Set<Students> students) {
        this.courseName = courseName;
        this.students = students;
    }
}
