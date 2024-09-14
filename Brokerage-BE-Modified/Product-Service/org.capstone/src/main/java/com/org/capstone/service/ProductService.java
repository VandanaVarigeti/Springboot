package com.org.capstone.service;

import com.org.capstone.client.CustomerIDClient;
import com.org.capstone.client.NewCustomerDto;
import com.org.capstone.entity.Account;

import com.org.capstone.entity.JointTenant;
import com.org.capstone.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;


    @Autowired
    CustomerIDClient customerIDClient;
    @Autowired
    private JavaMailSender mailSender;



    @Async
    public CompletableFuture<Account> addProduct(Account productEntity) {
        //validate Product details

       NewCustomerDto newCustomerDto= customerIDClient.getCustomerByEmail(productEntity.getEmailAddress());
       productEntity.setCustomerId(newCustomerDto.getCustomerId());
       productEntity.setSsnNumber(newCustomerDto.getSsnNumber());
       Optional<Account> existingAccount = productRepository.findByCustomerIdAndProductTypeAndRegistrationTypeAndAllowedRoles(
               productEntity.getCustomerId(),
               productEntity.getProductType(),
               productEntity.getRegistrationType(),
               productEntity.getAllowedRoles()
       );
       if(existingAccount.isPresent()){
           throw new RuntimeException("You already have an account for this product type and registration type");
       }
       validateJointTenants(productEntity, newCustomerDto.getSsnNumber());
        Account saveAcc= productRepository.save(productEntity);
      // custAccRoleService.createCustAccRole(saveAcc);
        sendWelcomeEmail(productEntity.getEmailAddress(),productEntity);
       return CompletableFuture.completedFuture(saveAcc);

       }


private void validateJointTenants(Account account, Long customerSsn){
        if(account.getJointTenant() != null && account.getJointTenant().size() == 1){
            JointTenant firstTenant = account.getJointTenant().get(0);
            Long firstTenantSsn = firstTenant.getSsn();
            if(!firstTenantSsn.equals(customerSsn)){
                throw new RuntimeException("First joint tenant's SSN Must match the customer SSN");
            }else{
                throw new RuntimeException("Two joint tenant details are required");
            }
        }
}
    @Cacheable("products")
    public CompletableFuture<List<Account>> getAllProduct() {
        //do Product Exists
        try{
            List<Account> findAccount = productRepository.findAll();
            return CompletableFuture.completedFuture(findAccount);
        } catch (Exception e){
            throw e;
        }


    }

    @CachePut(value = "products", key = "#productId")
    public Account updateProduct(Long productId, Account updateProduct) {
        //Check Product Exists
        if(productRepository.productExists(productId) && productId != null){
            return productRepository.save(updateProduct);
        }else {
            throw new IllegalArgumentException("Invalid Product Details");
        }
    }

    @CacheEvict(value = "products", allEntries = true)
    public void deleteProduct(Long productId) {
        //Optional<Product>
        Optional<Account> productOptional = productRepository.findById(productId);
        if(productOptional.isPresent()){
            productRepository.delete(productOptional.get());
        }else{
            throw new NoSuchElementException("Product Id " + productId + "doesn't exists");
        }
    }

    @Cacheable(value = "products", key = "#accountId")
    public Account getProduct(Long accountId) {

            return productRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("Account Id :" + accountId + "does not exists"));


    }

    @Async
    private void sendWelcomeEmail(String email,Account Registration){
        SimpleMailMessage message =new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Product registration successful!");
        message.setText("Dear User" + ",\n\nYour "+Registration.getRegistrationType()+" has been successfully created.\n\nThank you for joining us!");
        mailSender.send(message);
    }

    @Async
    public CompletableFuture<Account> updateEmailBySsn(Long ssnNumber, Account account) {
//        if(productRepository.findByCustomerId(account.getCustomerId())){
        Account account1=productRepository.findBySsnNumber(ssnNumber);

        if (ProductRepository.existsByEmailAddressAndSsnNumber(account1.getEmailAddress(), ssnNumber)) {
            throw new RuntimeException("Email address already exists!");
        }
            account1.setEmailAddress(account.getEmailAddress());
        Account updatedAccount = productRepository.save(account);
            return CompletableFuture.completedFuture(updatedAccount);

    }

    @Cacheable(value = "customers", key = "#ssnNumber")
    public Account getCustomerBySsn(Long ssnNumber) {
        return productRepository.findBySsnNumber(ssnNumber);
    }
}
