package capstone.Service;

import capstone.Entity.NewCustomerDto;
import capstone.Repository.CustomerRepository;
import capstone.client.Account;
import capstone.client.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductClient productClient;

    @Transactional
    public NewCustomerDto addCustomer(NewCustomerDto customer) {
        // Validate the customers details
        if(!customerRepository.customerExists(customer.getSsnNumber()) && customer.getSsnNumber() != 0) {
            // Save the theatre to the database
            customer.setCustomerId(generateUniqueCustId());
           return customerRepository.save(customer);

        }else{
            throw new IllegalArgumentException("Customer already exists");
        }
    }
//@Transactional
//public void addCustomer(NewCustomerDto customer) {
//    // Validate the customer's details
//    if (customer.getSsnNumber() != null && !customerRepository.customerExists(customer.getSsnNumber())) {
//        // Generate and set a unique 5-digit customer ID
//        customer.setCustomerId(generateUniqueCustId());
//        customerRepository.save(customer);
//    } else {
//        throw new IllegalArgumentException("Customer already exists or SSN is null");
//    }
//}

    public NewCustomerDto updateCustomer(Long id, NewCustomerDto updatedCustomer) {
        Optional<NewCustomerDto> existingRegistration = customerRepository.findById(id);
        if (existingRegistration.isPresent()) {
            NewCustomerDto customer = existingRegistration.get();

            // Check if the customer exists
            if (customerRepository.customerExists(id)) {
                // Check if the email address already exists
                if (customerRepository.existsByEmailAddress(updatedCustomer.getEmailAddress())) {
                    throw new RuntimeException("Email address already exists!");
                }
                // productClient.updateEmailAddress(updatedCustomer.getSsnNumber(), updatedCustomer.getEmailAddress());
                Account account;
                try {
                    account = productClient.getCustomerBySsn(updatedCustomer.getSsnNumber());
                    if (account == null) {
                        throw new RuntimeException("Customer not found for email: " + updatedCustomer.getEmailAddress());
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error fetching customer by email: " + updatedCustomer.getEmailAddress(), e);
                }

                // Update customer email address
                account.setEmailAddress(updatedCustomer.getEmailAddress());
                productClient.updateEmailAddress(updatedCustomer.getSsnNumber(), account);
                updatedCustomer.setCustomerId(customer.getCustomerId());
                return customerRepository.save(updatedCustomer);
            } else {
                throw new IllegalArgumentException("Invalid customer details provided");
            }
        }
        return updatedCustomer;
    }
    public String deleteCustomer(Long ssnNumber){
        // Check if the customer exists
        Optional<NewCustomerDto> customerOptional = customerRepository.findById(ssnNumber);
        if (customerOptional.isPresent()) {
            // Delete the customer
            customerRepository.delete(customerOptional.get());
            return "Customer deleted Successfully";
        } else {
            // If customer does not exist
            throw new NoSuchElementException("Customer with ID " + ssnNumber + " does not exist");
        }
    }


    public List<NewCustomerDto> getAllCustomer(){
       return customerRepository.findAll();
    }
    public NewCustomerDto getCustomerById(Long ssnNumber){
        try {
            return customerRepository.findById(ssnNumber)
                    .orElseThrow(() -> new NoSuchElementException("Customer with ID " + ssnNumber + " does not exist"));
        }catch (Exception e){
            throw e;
        }
    }
    public String generateUniqueCustId() {
        Random random = new Random();
        String uniqueId;
        do {
            uniqueId =String.format("%05d", random.nextInt(100000)); // Generate a 5-digit number
        } while (customerRepository.existsByCustomerId(uniqueId)); // Ensure the ID is unique
        return uniqueId;
    }

    public NewCustomerDto findCustomerByEmail(String emailAddress) {
        return customerRepository.findByEmailAddress(emailAddress);
   }
//    @Transactional
//    public void updateEmailAddress(String customerId, String newEmailAddress) {
//        NewCustomerDto customer = customerRepository.findByCustomerId(customerId);
//        if (customer != null) {
//            customer.setEmailAddress(newEmailAddress);
//            customerRepository.save(customer);
//            Registration registration = registrationRepository.findByCustomerId(customerId);
//            if (registration != null) {
//                registration.setEmailAddress(newEmailAddress);
//                registrationRepository.save(registration);
//            }
//        }
//    }
}
