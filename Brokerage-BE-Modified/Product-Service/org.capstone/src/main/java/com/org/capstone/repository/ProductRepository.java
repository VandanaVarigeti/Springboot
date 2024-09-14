package com.org.capstone.repository;

import com.org.capstone.ennum.AllowedRoles;
import com.org.capstone.ennum.ProductType;
import com.org.capstone.ennum.RegistrationType;
import com.org.capstone.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Account,Long> {
    static boolean existsByEmailAddressAndSsnNumber(String emailAddress, Long ssnNumber) {
        return false;
    }

    public default boolean productExists(Long id){
        return existsById(id);
    }


    Optional<Account> findByCustomerIdAndProductTypeAndRegistrationTypeAndAllowedRoles(String customerId, ProductType productType, RegistrationType registrationType, AllowedRoles allowedRoles);

    Account findBySsnNumber(Long ssnNumber);


    boolean findByCustomerId(String customerId);
}
