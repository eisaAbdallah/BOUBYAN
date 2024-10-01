package com.example.boubyan.security;

import com.example.boubyan.dto.StudentsResponseDTO;
import com.example.boubyan.model.Students;
import com.example.boubyan.repository.BoubyanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
@Autowired
    BoubyanRepository boubyanRepository;
    @Override
    public UserDetails loadUserByUsername(String username)  {
        Students students= null;

    students = boubyanRepository.findStudentByUserName(username);

try{
        if (students.getStudentName().equals(username)) {
            return new User(students.getStudentName(), students.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }catch(Exception ex){

}
return new User(students.getStudentName(), students.getPassword(),
                new ArrayList<>());


    }}