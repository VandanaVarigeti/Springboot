package com.org.capstone.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE", url = "http://localhost:9092/customer")
public interface CustomerIDClient {

    @GetMapping("/getcustomer/{ssnNumber}")
    public NewCustomerDto getcustomer(@PathVariable("ssnNumber") Long ssnNumber);

    @GetMapping("/email/{emailAddress}")
    public NewCustomerDto getCustomerByEmail(@PathVariable("emailAddress") String emailAddress);


}
