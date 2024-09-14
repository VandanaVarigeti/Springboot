package capstone.Client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "CUSTOMER-SERVICE", url = "http://localhost:9092/customer")
public interface CustomerIDClient {
    //    @PostMapping("/create")
//    public ResponseEntity<String> createCustomer(@RequestBody JsonNode payload);
    @GetMapping("/email/{emailAddress}")
    NewCustomerDto getCustomerByEmail(@PathVariable("emailAddress") String emailAddress);
    @PutMapping("/update/{ssnNumber}")
    public NewCustomerDto updateCustomer(@PathVariable Long ssnNumber, @RequestBody NewCustomerDto customer);
}
