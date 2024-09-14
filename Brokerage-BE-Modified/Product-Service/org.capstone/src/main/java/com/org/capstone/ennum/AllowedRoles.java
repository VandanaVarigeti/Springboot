package com.org.capstone.ennum;

import lombok.Getter;

@Getter
public enum AllowedRoles {
    INDIVIDUAL("Individual"),
    JT("Joint tenant"),
    BENEFICIARY("Beneficiary");

    private final String description;

    AllowedRoles(String description){
        this.description = description;
    }
}
