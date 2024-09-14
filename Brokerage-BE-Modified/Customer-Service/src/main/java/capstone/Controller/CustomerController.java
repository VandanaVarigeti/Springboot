package capstone.Controller;

import capstone.Entity.CustomerProfileMapper;
import capstone.Entity.NewCustomerDto;
import capstone.Repository.CustomerRepository;
import capstone.Service.CustomerService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/create")
    public NewCustomerDto createCustomer(@Valid @RequestBody JsonNode payload) {
        NewCustomerDto profile = CustomerProfileMapper.mapToCustomerProfile(payload);
        if ((profile.hasUSAddress() && profile.isValidStateCode())) {
            customerService.addCustomer(profile);
            return profile;
        }
        return profile;
    }

    @GetMapping("/allcustomers")
    public List<NewCustomerDto> getAllCustomer(){
        return customerService.getAllCustomer();
    }


    @PutMapping("/update/{ssnNumber}")
    public NewCustomerDto updateCustomer(@PathVariable Long ssnNumber, @RequestBody NewCustomerDto customer){
        return customerService.updateCustomer(ssnNumber,customer);
    }
    @DeleteMapping("/delete/{ssnNumber}")
    public String deleteCustomer(@PathVariable Long ssnNumber){
        return customerService.deleteCustomer(ssnNumber);
    }

    @GetMapping("/getcustomer/{ssnNumber}")
    public NewCustomerDto getcustomer(@PathVariable Long ssnNumber){
        return customerService.getCustomerById(ssnNumber);
    }
    @GetMapping("/email/{emailAddress}")
    public ResponseEntity<NewCustomerDto> getCustomerByEmail(@PathVariable("emailAddress") String emailAddress) {
        NewCustomerDto customer = customerService.findCustomerByEmail(emailAddress);
        return ResponseEntity.ok(customer);
    }
}
