package com.org.capstone.controller;

import com.org.capstone.entity.Account;
import com.org.capstone.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;



    @PostMapping("/addproduct")
    public CompletableFuture<ResponseEntity<Account>> addProduct(@Valid @RequestBody Account account){
        //productService.simple(account);
        return productService.addProduct(account)
                .thenApply(saveAccount -> new ResponseEntity<>(saveAccount, HttpStatus.CREATED));

    }

    @GetMapping("/allproducts")
    public  CompletableFuture<List<Account>> getAllProduct(){

        return  productService.getAllProduct();
    }
    @GetMapping("product/{accountId}")
    public Account getProduct(@PathVariable Long accountId){

        return productService.getProduct(accountId);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<Account> updateProduct(@PathVariable Long accountId,@RequestBody Account account){
        Account updateProduct = productService.updateProduct(accountId, account);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    @PutMapping("/{ssnNumber}")
    public CompletableFuture<ResponseEntity<Account>> updateEmailAddress(@PathVariable Long ssnNumber, @RequestBody Account account){
        return productService.updateEmailBySsn(ssnNumber, account)
                .thenApply(updatedAccount -> new ResponseEntity<>(updatedAccount, HttpStatus.OK));
    }
    @GetMapping("/{ssnNumber}")
    Account getCustomerBySsn(@PathVariable Long ssnNumber){
        return productService.getCustomerBySsn(ssnNumber);
    }
    @DeleteMapping("/product/{productId}")
    public void deleteProduct(@PathVariable Long accountId){

        productService.deleteProduct(accountId);
    }








}
