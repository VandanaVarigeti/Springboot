package capstone.client;


import lombok.Data;

import java.util.List;


@Data
public class Account {


    private long accountId;


    private String customerId;

    private ProductType productType;


    private Long ssnNumber;


    private RegistrationType registrationType;


    private AllowedRoles allowedRoles;

       private List<JointTenant> jointTenant;


    private List<Beneficiaries> beneficiaries;

    private String accountNickName;

    private String accountAddress;

    private String accountPhone;


    private String emailAddress;


    private String accountInvestmentObjective;


    private String accountAlternateContact;




}
