package com.example.boubyan.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;



@Entity
@Table(name="authentication")
@Getter
@Setter
public class AuthenticationModel {


    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name="user_name")
    String userName;

    @Column(name="token")
    String token;

}
