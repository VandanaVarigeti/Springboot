package com.org.capstone.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Embeddable
public class Beneficiaries {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int beneficiariesId;

    private String name;
    private int percentage;
//    private String surName;
//    private String gender;
//
//    private String dateOfBirth;
//    private Long phoneNumber;
//    private String relationship;


//    @ManyToOne
//    @JoinColumn(name = "account_Id")
//    private Account account;


}
