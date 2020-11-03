package com.example.crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TB_STUDENT")
public class Student {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="EMAIL_ID")
    private String emailId;

    @Column(name="MOBILE_NO")
    private String mobileNo;

    @Column(name="USERNAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;
}
