package com.org.capstone.entity;

//import com.org.capstone.accountvalidation.validators.ValidProductType;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.org.capstone.accountvalidation.validators.ValidProductType;
import com.org.capstone.ennum.AllowedRoles;
import com.org.capstone.ennum.ProductType;
import com.org.capstone.ennum.RegistrationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;


import java.util.List;

@Entity
@Data
@ValidProductType
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    @Column(name = "customer_id")
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "Product_Type", nullable = false)
    @NotNull(message = "Product Type is mandantory")
    private ProductType productType;

    @Column(name = "SSN_Number")
    private Long ssnNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "Registartion_Type", nullable = false)
    @NotNull(message = "Registartion Type is mandantory")
    private RegistrationType registrationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "Allowed_Roles", nullable = false)
    @NotNull(message = "Allowed Roles is mandantory")
    private AllowedRoles allowedRoles;

    @ElementCollection
    @CollectionTable(name = "Acc_JointTenant", joinColumns = @JoinColumn(name = "account_id"))
    private List<JointTenant> jointTenant;

    @ElementCollection
    @CollectionTable(name = "Acc_Beneficiary", joinColumns = @JoinColumn(name = "account_id"))
    private List<Beneficiaries> beneficiaries;

    @NotBlank(message = "Account Nickname is mandantory")
    private String accountNickName;

    @NotBlank(message = "Account Address is mandantory")
    private String accountAddress;

    @NotBlank(message = "Account Phone is mandatory")
    private String accountPhone;

    @NotBlank(message = "Account Email Address is mandatory")
    private String emailAddress;

    @NotBlank(message = "Account Investment Objective is mandantory")
    private String accountInvestmentObjective;

    @NotBlank(message = "Account Alternate Contact is mandantory")
    private String accountAlternateContact;



}
