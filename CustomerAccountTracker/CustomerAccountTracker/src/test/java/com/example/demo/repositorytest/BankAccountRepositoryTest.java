package com.example.demo.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.BankAccount;
import com.example.demo.repository.BankAccountRepository;

@DataJpaTest
public class BankAccountRepositoryTest {
	
	
		@Autowired
		private BankAccountRepository accountrepository;
		
		@Test
		void testFindByAccountType() {
			
			BankAccount account = new BankAccount(1,"SBI", "Saving", 2000);
			accountrepository.save(account);
			
			
			List<BankAccount> list = accountrepository.findByAccountType("Saving");
			String actualAccountType = "";
			String expectedAccountType = "Saving";
			for(BankAccount ac : list) {
				
				actualAccountType=ac.getAccountType();
			}
			
			assertThat(actualAccountType).isEqualTo(expectedAccountType);
		}

}
