package com.example.boubyan.service.impl;

import com.example.boubyan.dto.CourseRequest;
import com.example.boubyan.dto.LoginRequest;
import com.example.boubyan.dto.StudentRequest;
import com.example.boubyan.dto.StudentsResponseDTO;
import com.example.boubyan.exception.NotFound;
import com.example.boubyan.model.AuthenticationModel;
import com.example.boubyan.model.CourseDetails;
import com.example.boubyan.model.Courses;
import com.example.boubyan.model.Students;
import com.example.boubyan.repository.BoubyanRepository;
import com.example.boubyan.service.BackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BackingServiceImpl implements BackingService {

    @Autowired
    PasswordEncoder encoder ;
    @Autowired
    BoubyanRepository boubyanRepository;
    @Override
    public StudentsResponseDTO login(LoginRequest loginRequest) throws Exception {

        Students student= this.boubyanRepository.findStudentByUserName(loginRequest.getUserName());

        String studentName= null;

      if (student==null){

    throw new NotFound("Student IS not Found");

}else if(loginRequest.getUserName()==null||loginRequest.getPassword()==null){

          throw new RuntimeException("UnAUthorized");
      }
        if(loginRequest.getUserName().equals(student.getStudentName())){
            studentName=loginRequest.getUserName();
            return new StudentsResponseDTO(studentName);
        }else{
             studentName="student Not Found";
            return  new StudentsResponseDTO(studentName) ;

        }
    }

    @Override
    public void saveToken(AuthenticationModel authenticationModel) throws Exception {
        this.boubyanRepository.saveToken(authenticationModel);
    }

    @Override
    @CacheEvict(value="courses", allEntries=true)
    public Set<Courses> getCousrses(String name) throws Exception {
       Students students= this.boubyanRepository.findStudentByUserName(name);
        Courses courses=null;

        Set<Courses> coursesSet=new HashSet<>();


        for(Courses result:students.getEnrolledCourses()){
            courses=new Courses(result.getCourseName());


            coursesSet.add(courses);
        }

        return coursesSet;
    }

    @Override
    public String addCousrses(String name, String  courses) throws Exception {

        Students students= this.boubyanRepository.findStudentByUserName(name);

       List<Courses> coursesList=this.boubyanRepository.findCoursesByName(courses);
        CourseDetails courseDetails=new CourseDetails();
        Set<Courses> coursesStream= new HashSet<>(coursesList);
        students.setEnrolledCourses(coursesStream);

        students.setLocalDate( LocalDate.now());
        this.boubyanRepository.addCourseForUser(students);
        courseDetails.setStudentName(name);
     Courses coursesNames =new Courses(courses);
        courseDetails.setCourses(coursesNames);


        this.boubyanRepository.saveCourseDetails(courseDetails);
        return "Courses Added Successfully";
    }

    @Override
    public void deleteCourses( String  coursesNames,String studentName) throws Exception {


        this.boubyanRepository.deleteCourses(coursesNames,studentName);
    }

    @Override
    public String addStudent(StudentRequest studentRequest) throws Exception {


        Students students=new Students(studentRequest.getStudentName(),encoder.encode(studentRequest.getPassword()));

        this.boubyanRepository.saveStudent(students);

        return "User added Successfully";
    }

    @Override
    public String addCousrsesDB(CourseRequest courseRequest) throws Exception {
Courses course= new Courses( );
course.setCourseName(courseRequest.getCourseName());
        this.boubyanRepository.saveCourseDB(course);
        return "Course Added";
    }



    @Override
    public List<CourseDetails> getCourseDetails(String studentName) {
        List<CourseDetails> courseDetails=this.boubyanRepository.getCourseDetailsByStudent(studentName);
    return courseDetails;

    }
}
