package com.example.boubyan.repository;

import com.example.boubyan.model.AuthenticationModel;
import com.example.boubyan.model.CourseDetails;
import com.example.boubyan.model.Courses;
import com.example.boubyan.model.Students;

import java.util.List;
import java.util.Set;

public interface BoubyanRepository {
    Students findStudentByUserName(String userName)  ;

    void saveToken(AuthenticationModel authenticationModel) throws  Exception;

    List<Students> getStudentsCourses(String name)throws  Exception;

     List<Courses>  findCourseByName( List<String>  courses) throws  Exception;

    void addCourseForUser(Students students) throws  Exception;

    void deleteCourses( String  coursesNames,String studentName) throws  Exception;

    void saveStudent(Students students) throws  Exception;

    void saveCourseDB(Courses course) throws  Exception;

    void saveCourseDetails(CourseDetails courseDetails);

    List<CourseDetails> getCourseDetailsByStudent(String studentName);


}
