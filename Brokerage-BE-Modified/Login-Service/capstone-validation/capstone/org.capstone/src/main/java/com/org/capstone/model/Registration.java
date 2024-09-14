package com.org.capstone.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Registration {

    private String userName;

    private String password;

    private String confirmPassword;

    private String firstName;

    private String lastName;

    private String emailAddress;
}
