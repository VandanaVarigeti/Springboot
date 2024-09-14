//package com.org.capstone.service;
//
//import com.org.capstone.client.CustomerIDClient;
//import com.org.capstone.entity.Account;
//import com.org.capstone.repository.CustAccRoleRepository;
//import com.org.capstone.entity.CustAccRole;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustAccRoleService {
//
//    @Autowired
//    private CustAccRoleRepository custAccRoleRepository;
//
//    @Autowired
//    private CustomerIDClient customerIDClient;
//
//    public CustAccRoleService(CustAccRoleRepository custAccRoleRepository, CustomerIDClient customerIDClient) {
//        this.custAccRoleRepository = custAccRoleRepository;
//        this.customerIDClient = customerIDClient;
//    }
//
//    //saving CustAccRole based on the AccountFields
//    public CustAccRole createCustAccRole(Account accountFields) //for business logic
//    {
//        // Create and set up CustAccRole
//        CustAccRole custAccRole = new CustAccRole();
//       // custAccRole.setCustomerId(generateUniqueCustomerId()); // Setting dummy customer ID for now
//        custAccRole.setCustomerId(customerIDClient.getcustomer(accountFields.getSsnNumber()).getCustomerId());
//        custAccRole.setRoleTitleCd(accountFields.getAllowedRoles().name());
//        custAccRole.setAccountFields(accountFields); // Link to AccountFields
//
//        // Save CustAccRole using this service
//        return saveCustAccRole(custAccRole);
//    }
//
//    private long generateUniqueCustomerId() {
//        return System.currentTimeMillis();
//    }
//
//    public CustAccRole saveCustAccRole(CustAccRole custAccRole)//for repository logic
//    {
//        return custAccRoleRepository.save(custAccRole);
//    }
//
//    // get CustRoleAcc Entity using accId
////    public CustAccRole getCustAccRoleByAccId(Long customerId) {
////        return custAccRoleRepository.findByCustomerID(customerId);
////    }
//
//    // get CustRoleAcc Entities using list of accIds
////    public List<CustAccRole> getCustAccRolesByAccIds(List<Integer> accIds) {
////        return custAccRoleRepository.findByAccountFields_AccountNumberIDIn(accIds);
////    }
////    public List<CustAccRole> getAllCustAccRoles() {
////        return custAccRoleRepository.findAll();
////    }
//}