package com.example.boubyan.repository.impl;

import com.example.boubyan.exception.NotFound;
import com.example.boubyan.model.AuthenticationModel;
import com.example.boubyan.model.CourseDetails;
import com.example.boubyan.model.Courses;
import com.example.boubyan.model.Students;
import com.example.boubyan.repository.BoubyanRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BoubyanRepositoryImpl implements BoubyanRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    @Transactional
    public Students findStudentByUserName(String userName)   {

       TypedQuery<Students> students= entityManager.createQuery("from Students s where s.studentName=:studentName",Students.class);
        Students studentsResult=new Students();
        students.setParameter("studentName",userName);
        try{
            studentsResult=students.getSingleResult();}
catch (NoResultException nre){

            }


        return studentsResult;
    }

    @Override
    @Transactional
    public void saveToken(AuthenticationModel authenticationModel) throws Exception {
        this.entityManager.persist(authenticationModel);
    }

    @Override
    @Transactional
    public List<Students> getStudentsCourses(String name) throws Exception {
        TypedQuery<Students> students= entityManager.createQuery("from Students s where s.studentName=:studentName",Students.class);

        students.setParameter("studentName",name);


        return students.getResultList();
    }

    @Override
    @Transactional
    public  List<Courses>  findCourseByName( List<String>  courses) throws Exception {



        TypedQuery<Courses> coursesTypedQuery= entityManager.createQuery("from Courses   where  courseName IN (:coursesName)",Courses.class);

        coursesTypedQuery.setParameter("coursesName",courses);


        return coursesTypedQuery.getResultList();
    }

    @Override
    @Transactional
    public void addCourseForUser(Students students) throws Exception {
        this.entityManager.persist(students);
    }

    @Override
    @Transactional
    public void deleteCourses( String  coursesName,String studentName) throws Exception {
       Students students=  this.findStudentByUserName(studentName);
        Courses course=this.findSingleCoursesByName(coursesName);
        students.getEnrolledCourses().remove(course);

this.entityManager.persist(students);



    }
@Transactional
    private Courses findSingleCoursesByName(String coursesNames) {
    TypedQuery<Courses> coursesTypedQuery= entityManager.createQuery("from Courses c where c.courseName=:courseName",Courses.class);

    coursesTypedQuery.setParameter("courseName",coursesNames);


    return coursesTypedQuery.getSingleResult();
    }

    @Override
    @Transactional
    public void saveStudent(Students students) throws Exception {
        this.entityManager.persist(students);
    }

    @Override
    @Transactional
    public void saveCourseDB(Courses course) throws Exception {
        this.entityManager.persist(course);
    }

    @Override
    @Transactional
    public void saveCourseDetails(CourseDetails courseDetails) {
        this.entityManager.persist(courseDetails);
    }

    @Override
    @Transactional
    public List<CourseDetails> getCourseDetailsByStudent(String  studentNames) {
        TypedQuery<CourseDetails> coursesTypedQuery= entityManager.createQuery("from CourseDetails cd where cd.studentName=:studentName",CourseDetails.class);

        coursesTypedQuery.setParameter("studentName",studentNames);


        return coursesTypedQuery.getResultList();
    }



}
