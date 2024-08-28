package com.example.demo.integrationTest;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.BankAccount;

public interface BankIntegrationRepository extends JpaRepository<BankAccount ,Integer>{

}
