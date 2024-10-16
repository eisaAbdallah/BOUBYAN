package com.example.boubyan.controller;

import com.example.boubyan.dto.*;
import com.example.boubyan.model.AuthenticationModel;
import com.example.boubyan.model.CourseDetails;
import com.example.boubyan.model.Courses;
import com.example.boubyan.model.Students;
import com.example.boubyan.security.JwtTokenUtil;
import com.example.boubyan.security.JwtUserDetailsService;
import com.example.boubyan.service.BackingService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.example.boubyan.controller.urls.URLS.*;

@RestController
public class CoursesController {
    @Autowired
    BackingService backingService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping(LOGIN_REQUEST_API)
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) throws Exception {


        StudentsCoursesResponse studentsCoursesResponse = null;
        if (loginRequest != null) {
            StudentsResponseDTO studentsResponseDTO = this.backingService.login(loginRequest);


            authenticate(loginRequest.getUserName(), loginRequest.getPassword());

            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(loginRequest.getUserName());

            final String token = jwtTokenUtil.generateToken(userDetails);
            AuthenticationModel authenticationModel = new AuthenticationModel();
            authenticationModel.setToken(token);
            authenticationModel.setUserName(loginRequest.getUserName());
            this.backingService.saveToken(authenticationModel);
            return ResponseEntity.ok("Welcome To Boubyan " + studentsResponseDTO.getStudentName());

        } else {


            return new ResponseEntity<>("UnAuthorized", HttpStatus.UNAUTHORIZED);
        }


    }

    @PostMapping(ADD_STUDENT_COURSES)
    public ResponseEntity addCourses(@RequestParam("courses")  List<String>  courses) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String result = this.backingService.addCousrses(authentication.getName(), courses);


        return ResponseEntity.ok(result);
    }

    @PostMapping(ADD_STUDENT)
    public ResponseEntity addStudent(@RequestBody StudentRequest studentRequest) throws Exception {


        String result = this.backingService.addStudent( studentRequest);


        return ResponseEntity.ok(result);
    }

    @PostMapping(ADD_COURSE)
    public ResponseEntity addCourseDB(@RequestBody CourseRequest courseRequest) throws Exception {


        String result = this.backingService.addCousrsesDB( courseRequest);


        return ResponseEntity.ok(result);
    }

    @GetMapping(GET_STUDENT_COURSES)
    public  Set<Courses>  getCourses() throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<Courses> courses = this.backingService.getCousrses(authentication.getName());


        return  courses;
    }

    @GetMapping(EXPORT)
    public  ResponseEntity  export() throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try
        {


            List<CourseDetails> courseDetails=this.backingService.getCourseDetails(authentication.getName());

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("courses.pdf"));

            document.open();

            PdfPTable table = new PdfPTable(2);
            Stream.of("Student", "Time-Slot")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(columnTitle));
                        table.addCell(header);
                    });

for(CourseDetails courseDetailsResult:courseDetails) {


    table.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(courseDetailsResult.getStudentName()+"  -   "+courseDetailsResult.getCreatedOn());
}




            document.add(table);
            document.close();

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("PDF CREATED");
    }


    @DeleteMapping(DELETE_STUDENT_COURSES)
    public ResponseEntity deleteCoursesFromUser(@RequestParam("course")  String  coursesNames) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       this.backingService.deleteCourses(coursesNames,authentication.getName());


        return ResponseEntity.ok("Courses Deleted: " + coursesNames);
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }




}
