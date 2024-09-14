package com.org.capstone.repository;

import com.org.capstone.entity.CustAccRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustAccRoleRepository extends JpaRepository<CustAccRole, Long> {

    // Find by account acctId
//    public CustAccRole findByCustomerID(long customerId);


    // Find by a list of account acctId
    // List<CustAccRole> findByAccountFields_AccountNumberIDIn(List<Integer> accIds);
}
