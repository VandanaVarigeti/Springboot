package capstone.Service;

import capstone.Client.CustomerIDClient;
import capstone.Client.NewCustomerDto;
import capstone.Entity.Registration;
import capstone.Repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class RegistrationService {
    @Autowired
    RegistrationRepository registrationRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    CustomerIDClient customerIDClient;

    @Transactional
    public Registration registerUser(Registration registration) {
        // Check if the username already exists
        if (registrationRepository.existsByUserName(registration.getUserName())) {
            throw new RuntimeException("UserName already exists!");
        }

        // Check if the email address already exists
        if (registrationRepository.existsByEmailAddress(registration.getEmailAddress())) {
            throw new RuntimeException("Email address already exists!");
        }

        // Fetch customer by email
       NewCustomerDto customer = customerIDClient.getCustomerByEmail(registration.getEmailAddress());
        if (customer == null) {
            throw new RuntimeException("Customer not found for email: " + registration.getEmailAddress());
        }

        // Set registration details
        registration.setCustomerId(customer.getCustomerId());
        registration.setSsnNumber(customer.getSsnNumber());
        registration.setPassword(bCryptPasswordEncoder.encode(registration.getPassword()));
        registration.setConfirmPassword(bCryptPasswordEncoder.encode(registration.getConfirmPassword()));

        // Save registration
        registrationRepository.save(registration);

        // Send a confirmation email
        sendWelcomeEmail(registration.getEmailAddress());

        return registration;
    }

    public Optional<Registration> getUser(String userName) {
        return registrationRepository.findById(userName);
    }

    public void deleteUser(String userName) {
        registrationRepository.deleteById(userName);
    }

    public Boolean checkPassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(encodedPassword, rawPassword);
    }

    public Registration updateUser(String userName, Registration updatedRegistration) {
        Optional<Registration> existingRegistration = registrationRepository.findById(userName);
        if (existingRegistration.isPresent()) {
            Registration registration = existingRegistration.get();

            // Check if the new email address already exists for a different user
            if (registrationRepository.existsByEmailAddressAndUserNameNot(updatedRegistration.getEmailAddress(), userName)) {
                throw new RuntimeException("Email address already exists!");
            }
            // Fetch customer by email
            NewCustomerDto customer;
            try {
                customer = customerIDClient.getCustomerByEmail(registration.getEmailAddress());
                if (customer == null) {
                    throw new RuntimeException("Customer not found for email: " + registration.getEmailAddress());
                }
            } catch (Exception e) {
                throw new RuntimeException("Error fetching customer by email: " + registration.getEmailAddress(), e);
            }

            // Update customer email address
            customer.setEmailAddress(updatedRegistration.getEmailAddress());
            customerIDClient.updateCustomer(registration.getSsnNumber(),customer);

            // Update registration details
            registration.setEmailAddress(updatedRegistration.getEmailAddress());
            registration.setFirstName(updatedRegistration.getFirstName());
            registration.setLastName(updatedRegistration.getLastName());
            registration.setPassword(bCryptPasswordEncoder.encode(updatedRegistration.getPassword()));
            registration.setConfirmPassword(bCryptPasswordEncoder.encode(updatedRegistration.getConfirmPassword()));

            // Save updated registration
            return registrationRepository.save(registration);
        } else {
            throw new RuntimeException("User not found!");
        }
    }


    private void sendWelcomeEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registration successful!");
        message.setText("Dear User" + ",\n\nYour account has been successfully created.\n" +
                "\nThank you for joining us!");
        mailSender.send(message);
    }

    //    public Registration getRegistration(Long ssnNumber){
//        NewCustomerDto CustomerDto = customerIDClient.getcustomer(ssnNumber);
//        return mapCustomerToRegistration(CustomerDto);
//    }
//    private Registration mapCustomerToRegistration(NewCustomerDto customerDto) {
//        Registration registration = new Registration();
//        registration.setUserName(customerDto.getCustomerId());
//        registration.setFirstName(customerDto.getFirstName());
//        registration.setLastName(customerDto.getLastName());
//        registration.setEmailAddress(customerDto.getEmailAddress());
//        // Map other fields as neededreturn registration;
//        return registration;
//    }
    public void updateEmailAddress(String customerId, String newEmailAddress) {
        Registration registration = registrationRepository.findByCustomerId(customerId);
        if (registration != null) {
            registration.setEmailAddress(newEmailAddress);
            NewCustomerDto customer = customerIDClient.getCustomerByEmail(registration.getEmailAddress());
//          customerIDClient.getCustomerByEmail(updatedregistration.getEmailAddress());
            customer.setEmailAddress(newEmailAddress);
            registrationRepository.save(registration);
        }

    }
    public Registration updateUserPassword(String userName, String newPassword) {
        Optional<Registration> existingRegistration = registrationRepository.findById(userName);
        if (existingRegistration.isPresent()){
            Registration registration =  existingRegistration.get();
            registration.setPassword(bCryptPasswordEncoder.encode(newPassword));
            registration.setConfirmPassword(bCryptPasswordEncoder.encode(newPassword));
            return  registrationRepository.save(registration);
        }else {
            throw  new RuntimeException("User not found");
        }
    }


}
