package com.example.boubyan.service;

import com.example.boubyan.dto.CourseRequest;
import com.example.boubyan.dto.LoginRequest;
import com.example.boubyan.dto.StudentRequest;
import com.example.boubyan.dto.StudentsResponseDTO;
import com.example.boubyan.model.AuthenticationModel;
import com.example.boubyan.model.CourseDetails;
import com.example.boubyan.model.Courses;
import com.example.boubyan.model.Students;

import java.util.List;
import java.util.Set;

public interface BackingService {
    StudentsResponseDTO login(LoginRequest loginRequest) throws Exception;

    void saveToken(AuthenticationModel authenticationModel)throws Exception;

    Set<Courses> getCousrses(String name) throws Exception;

    String addCousrses(String name,  String  courses)  throws Exception;

    void deleteCourses( String  coursesNames,String studentName) throws Exception;

    String addStudent(StudentRequest studentRequest) throws Exception;
    String addCousrsesDB(CourseRequest courseRequest)  throws Exception;



    List<CourseDetails> getCourseDetails(String studentName);
}
