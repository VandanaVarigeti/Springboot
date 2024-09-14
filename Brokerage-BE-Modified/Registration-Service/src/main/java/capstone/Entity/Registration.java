package capstone.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Registration")
public class Registration {
    @Id
    @Column(name = "userName",unique = true)
    private String userName;
    @NotNull(message = "At least one address is mandatory")
    private String password;
    @NotNull(message = "At least one address is mandatory")
    private String confirmPassword;
    @NotNull(message = "At least one address is mandatory")
    private String firstName;
    @NotNull(message = "At least one address is mandatory")
    private String lastName;
    @NotNull(message = "Email should be mandatory")
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String emailAddress;
    @Column(unique = true,nullable = false)
    private String customerId;
    @Column(unique = true,nullable = false)
    private Long ssnNumber;
    @Embedded
    @ElementCollection
    private List<SecurityQuestions> securityQuestions;
//    @OneToOne
//    @JoinColumn(name = "cust_id", referencedColumnName = "customerId")
//    private NewCustomerDto customer;


}
