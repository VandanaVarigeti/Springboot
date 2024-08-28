package com.example.demo.servicetest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;


import com.example.demo.exception.AccountAlreadyExistException;
import com.example.demo.exception.InsufficientBalanceException;
import com.example.demo.exception.AmountCannotTransferException;
import com.example.demo.exception.TransactionFailException;
import com.example.demo.model.BankAccount;
import com.example.demo.model.Customer;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {ServiceTest.class})
public class ServiceTest {
	
	

		@InjectMocks
		CustomerService service;
		
		@Mock
		CustomerRepository customerreposit;
		
		@Mock
		BankAccountRepository bankreposit;
		

			
		@Test
		void testAddCustomer()  {

			//Given : 
			BankAccount acc1 = new BankAccount(1,"SBI", "Saving", 1000);
			BankAccount acc2 = new BankAccount(2, "HDFC","Salary", 20000);
			List<BankAccount> list = new ArrayList<>();
			list.add(acc1);
			list.add(acc2);
			Customer customer = new Customer(1,"Sujan","Male","sujan123@gmail.com","Kakinada",list);
			
			//When
			service.addCustomer(customer);
			
			//Then
			verify(customerreposit,times(1)).save(customer);
		}

		@Test
		void testAddAccount() throws AccountAlreadyExistException{
			
			//Given
			BankAccount acc1 = new BankAccount(1,"SBI", "Saving", 3000);
			List<BankAccount> list1 = new ArrayList<>();
			list1.add(acc1);
			Customer cust = new Customer(1,"sujan","Male","sujan123@gmail.com","Kakinada",list1);
			
			BankAccount acc2 = new BankAccount(2, "HDFC","Salary", 20000);
			List<BankAccount> list2 = new ArrayList<>();
			list2.add(acc2);
			
			when(customerreposit.findById(1)).thenReturn(Optional.of(cust));
			//when
			service.addAccount(1, list2);
			//then
			verify(customerreposit,times(1)).findById(1);
		}

		@Test
		void testGetCustomerById() {
			
			//given
			Customer cust = new Customer();
			cust.setId(1);
			cust.setName("Sujan");
			cust.setGender("Male");
			cust.setEmail("sujan123@gmail.com");
			cust.setAddress("kakinada");

			
			when(customerreposit.findById(1)).thenReturn(Optional.of(cust));
			
			//when
			Customer cTest = service.getCustomerById(1);
			
			//then
			assertThat(cTest.getName()).isEqualTo("Sujan");
			verify(customerreposit,times(1)).findById(1);
			
		}
		
		@Test
		void testGetAccountById() {
			
			BankAccount ac = new BankAccount(1,"SBI", "Saving", 3000);
			
			when(bankreposit.findById(1)).thenReturn(Optional.of(ac));
			
			BankAccount ac1 = service.getAccountById(1);
			assertThat(ac1.getAccountId()).isEqualTo(1);
			verify(bankreposit,times(1)).findById(1);
		}

		@Test
		void testGetCustomers() {
			
			Customer c1 = new Customer();
			c1.setId(1);
			c1.setName("Vamsi");
			c1.setGender("Male");
			c1.setEmail("vamsi123@gmail.com");
			c1.setAddress("kakinada");

			Customer c2 = new Customer();
			c2.setId(2);
			c2.setName("Sujan");
			c2.setGender("Male");
			c2.setEmail("sujankumar123@gmail.com");
			c2.setAddress("kakinada");

			
			List<Customer> list = new ArrayList<>();
			list.add(c1);
			list.add(c2);
			
			when(customerreposit.findAll()).thenReturn(list);
			
			List<Customer> list2 = service.getCustomers();
			
			assertThat(list2.get(0).getName()).isEqualTo("Vamsi");
			verify(customerreposit,times(1)).findAll();
		}

		@Test
		void testGetByName() {
			
			BankAccount acc1 = new BankAccount(1,"SBI", "Saving", 3000);
			List<BankAccount> list1 = new ArrayList<>();
			list1.add(acc1);
			Customer cust = new Customer(1,"Sujan","Male","sujan123@gmail.com","Kakinada",list1);
			List<Customer> list2 = new ArrayList<>();
			list2.add(cust);
			when(customerreposit.findByName("Sujan")).thenReturn(list2);
			List<Customer>c = service.getByName("Sujan");
			
			assertThat(c.size()).isEqualTo(1);
			verify(customerreposit).findByName("Sujan");
		}



		@Test
		void testGetByAccountType() {
			BankAccount acc1 = new BankAccount(1,"SBI", "Saving", 3000);
			List<BankAccount> list1 = new ArrayList<>();
			list1.add(acc1);
			when(bankreposit.findByAccountType("Saving")).thenReturn(list1);
			service.getByAccountType("Saving");
			verify(bankreposit).findByAccountType("Saving");
		}


		@Test
		void testUpdateCustomer() {
			BankAccount acc1 = new BankAccount(1,"SBI", "Saving", 3000);
			List<BankAccount> list1 = new ArrayList<>();
			list1.add(acc1);
			Customer cust = new Customer(1,"Sujan","Male","sujan123@gmail.com","Kakinada",list1);
			
			when(customerreposit.findById(1)).thenReturn(Optional.of(cust));
			
			service.updateCustomer(1, cust);
			verify(customerreposit,times(1)).save(cust);
		}

		@Test
		void testDeleteCustomerById() {
			
			service.deleteCustomerById(1);
			verify(customerreposit,times(1)).deleteById(1);
		}

		@Test
		void testDeleteAllCustomer() {
			
			service.deleteAllCustomer();
			verify(customerreposit,times(1)).deleteAll();
		}

		@Test
		void testDeleteAccountById() {
			
			service.deleteAccountById(1);
			verify(bankreposit,times(1)).deleteById(1);
		}

		@Test
		void testFundTransfer() throws InsufficientBalanceException, TransactionFailException, AmountCannotTransferException {
			
			BankAccount acc1 = new BankAccount(1,"SBI", "Saving", 1000);
			BankAccount acc2 = new BankAccount(2, "HDFC","Salary", 20000);
			
			when(bankreposit.findById(1)).thenReturn(Optional.of(acc1));
			when(bankreposit.findById(2)).thenReturn(Optional.of(acc2));
			
			service.fundTransfer(1, 2, 300);
			
			verify(bankreposit,times(1)).findById(1);
			verify(bankreposit,times(1)).save(acc1);
			verify(bankreposit,times(1)).save(acc2);
		}


}
