package capstone.Client;


import jakarta.persistence.Id;
import lombok.Data;


@Data
public class NewCustomerDto {


    private String customerId;
    @Id
    private Long ssnNumber;

    private String firstName;

    private String middleName;

    private String lastName;

    private String title;
    // Address 1

    private String addressLine1;

    private String addressLine2;

    private String city;


    private String state;


    private String country;


    private String zipCode;
    // Address 2
    private String address2Line1;
    private String address2Line2;
    private String city2;
    private String state2;
    private String country2;
    private String zipCode2;

    private boolean internationalAddressIndicator2;
    // Address 3
    private String address3Line1;
    private String address3Line2;
    private String city3;
    private String state3;
    private String country3;
    private String zipCode3;
    private boolean internationalAddressIndicator3;

    private String phoneNumber;

    private String internationalPhoneNumber;

    private String emailAddress;

    private String citizenship1;

    private String citizenship2;


    private String residentCountry;

    private String politicallyInfluencedPerson;

    private String financialObjective;
}
