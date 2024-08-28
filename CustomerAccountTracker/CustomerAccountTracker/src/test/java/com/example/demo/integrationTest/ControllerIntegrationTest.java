package com.example.demo.integrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.example.demo.CustomerAccountTrackerApplication;
import com.example.demo.model.BankAccount;
import com.example.demo.model.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CustomerAccountTrackerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerIntegrationTest {

	@LocalServerPort
	private int port;
	private String baseUrl = "http://localhost";

	@Autowired
	TestRestTemplate restTemplate = new TestRestTemplate();

	@Autowired
	private CustomerIntegrationRepository reposit;
	
	@Autowired
    private BankIntegrationRepository bankreposit;
    
	
	private Customer customer;
	private BankAccount account;

	@BeforeEach
	void setUp() throws Exception {
		baseUrl = baseUrl.concat(":").concat(port + "");

		customer = new Customer();
		customer.setId(1);
		customer.setName("Vamsi");
		customer.setGender("Male");
		customer.setEmail("vams123@gmail.com");
		customer.setAddress("kakinada");

		List<BankAccount> account = new ArrayList<>();
		BankAccount bank = new BankAccount();
		bank.setAccountId(12345);
		bank.setAccountName("HDFC");
		bank.setAccountType("Salary");
		bank.setAccountBalance(10000);
		;
		account.add(bank);
		customer.setBankAccount(account);

	}

	@AfterEach
	void Down() throws Exception {
		customer = null;
		account = null;
	}
	

	@Test
	void testAddCustomer() {

		baseUrl = baseUrl.concat("/add");
		this.restTemplate.postForObject(baseUrl, customer, Customer.class);
		assertEquals(1, customer.getId());
	}

	@Test
	void testUpdateCustomer() {
		customer.setId(2);
		customer.setName("sujan");
		customer.setGender("male");
		customer.setEmail("sujan123@gmail.com");
		customer.setAddress("kakinada");
		baseUrl = baseUrl.concat("/{id}");
		this.restTemplate.put(baseUrl, customer, 2);

		assertEquals(2, customer.getId());
		assertEquals("sujan", customer.getName());
		assertEquals("male", customer.getGender());
		assertEquals("sujan123@gmail.com", customer.getEmail());
		assertEquals("kakinada", customer.getAddress());
	}

	@Test
	void testDeleteCustomer() {
		reposit.save(customer);
		int size = reposit.findAll().size();
		assertEquals(2, size);
		baseUrl = baseUrl.concat("/delete");
		this.restTemplate.delete(baseUrl, 1);
		assertEquals(2, reposit.findAll().size());
	}

	@Test
	void testDeleteAllCustomers() {
		Customer customer1 = new Customer();
		customer1.setId(2);
		customer1.setName("Vamsi");
		customer1.setGender("Male");
		customer1.setEmail("vams123@gmail.com");
		customer1.setAddress("kakinada");

		List<BankAccount> account = new ArrayList<>();
		BankAccount bank = new BankAccount();
		bank.setAccountId(12345);
		bank.setAccountName("HDFC");
		bank.setAccountType("Salary");
		bank.setAccountBalance(10000);
		
		account.add(bank);
		customer.setBankAccount(account);

		reposit.save(customer);
		reposit.save(customer1);

		int size = reposit.findAll().size();
		assertEquals(2, size);
		baseUrl = baseUrl.concat("/deleteAll");
		this.restTemplate.delete(baseUrl);

	}
}
