package capstone.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "PRODUCT-SERVICE", url = "http://localhost:9093/product")
public interface ProductClient {


    @GetMapping("/{ssnNumber}")
    Account getCustomerBySsn(@PathVariable Long ssnNumber);
    @PutMapping("/{ssnNumber}")
    public Account updateEmailAddress(@PathVariable Long ssnNumber, @RequestBody Account account);
}
