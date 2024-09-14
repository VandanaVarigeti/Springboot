package com.org.capstone.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Embeddable
@Data
public class JointTenant {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int jointTenantId;
    private Long ssn;
    private String tenantName;
//    private String surName;
//    private String gender;
//    private String dateOfBirth;
//    private Long phoneNumber;
//    private String relationship;


//    @ManyToOne
//    @JoinColumn(name = "account_Id")
//    private Account account;
}