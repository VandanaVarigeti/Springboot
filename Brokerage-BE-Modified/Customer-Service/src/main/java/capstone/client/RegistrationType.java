package capstone.client;

import lombok.Getter;


@Getter
public enum RegistrationType {
    INDIVIDUAL("Individual"),
    JOINT_TENANT("Joint tenant"),
    RETIREMENT_ACCOUNT("Individual Retirement Account");

    private final String description;

    RegistrationType(String description){
        this.description = description;
    }
}
