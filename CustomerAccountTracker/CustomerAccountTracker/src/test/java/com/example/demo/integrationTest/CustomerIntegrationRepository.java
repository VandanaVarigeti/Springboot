package com.example.demo.integrationTest;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Customer;

public interface CustomerIntegrationRepository extends JpaRepository<Customer , Integer> {

}
