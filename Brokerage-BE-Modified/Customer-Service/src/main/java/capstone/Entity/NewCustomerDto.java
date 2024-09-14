package capstone.Entity;

import capstone.validations.SSNValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Entity
@Data
@Table(name = "Customer")
public class NewCustomerDto {

    @Column(name = "customerId",unique = true)
    private String  customerId;
    @Id
    @Column(name = "ssnNumber",nullable = false,unique = true)
    @SSNValidator
    private Long ssnNumber;

    @NotBlank(message = "First Name is mandatory")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last Name is mandatory")
    private String lastName;

    private String title;
    // Address 1
    @NotBlank(message = "AddressLine must not null")
    @Column(name = "AddressLine1")
    private String addressLine1;
    @NotBlank(message = "AddressLine must not null")
    @Column(name = "AddressLine2")
    private String addressLine2;
    @NotBlank(message = "City must not null")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "state must not null")
    @Column(name = "state")
    private String state;

    @NotBlank(message = "country must not null")
    @Column(name = "country")
    private String country;

    @NotBlank(message = "zipCode must not null")
    @Pattern(regexp = ".*\\d{5}$", message = "ZIP code must be a 5-digit code")
    @Column(name = "zipcode")
    private String zipCode;
    // Address 2
    private String address2Line1;
    private String address2Line2;
    private String city2;
    private String state2;
    private String country2;
    private String zipCode2;
    @NotNull
    private boolean internationalAddressIndicator2;
    // Address 3
    private String address3Line1;
    private String address3Line2;
    private String city3;
    private String state3;
    private String country3;
    private String zipCode3;
    private boolean internationalAddressIndicator3;
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+1\\d{10}$", message = "Phone number must be a valid US number")
    private String phoneNumber;

    private String internationalPhoneNumber;

    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String emailAddress;

    @NotBlank(message = "Citizenship 1 is mandatory")
    private String citizenship1;

    private String citizenship2;

    @NotBlank(message = "Resident Country is mandatory")
    @Pattern(regexp = "^(?!.*(Sudan|Iran)).*$", message = "Resident country should not be a restricted country (Sudan, Iran)")
    private String residentCountry;

    @NotNull(message = "politicallyInfluencedPerson is mandatory")
    private String politicallyInfluencedPerson;

    @NotBlank(message = "financialObjective is mandatory")
    private String financialObjective;
//    @OneToOne(mappedBy = "customer")
//    private Registration registration;


    // Custom validation to ensure at least one US address
    public boolean hasUSAddress() {
        List<String> allowedCountries = Arrays.asList("US", "USA", "America");
        if (!allowedCountries.contains(getCountry())) {
            throw new IllegalArgumentException("The Address should be in US Country");
        }
        return true;
    }

    public boolean isValidStateCode() {
        List<String> validStates = Arrays.asList("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME",
                "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT",
                "VT", "VA", "WA", "WV", "WI", "WY");
        if (!validStates.contains(getState())) {
            throw new IllegalArgumentException("The State should be US State");
        }
        return true;
    }
//    public boolean hasUSAddress(){
//        return country.equalsIgnoreCase("US") || country.equalsIgnoreCase("USA");
//    }
}

