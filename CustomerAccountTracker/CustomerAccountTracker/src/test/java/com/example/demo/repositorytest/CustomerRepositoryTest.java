package com.example.demo.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.CustomerAccountTrackerApplication;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;


@DataJpaTest
public class CustomerRepositoryTest {
	
		@Autowired
		private CustomerRepository customerreposit;
		
		@Test
		public void testFindByName() {
			
			Customer customer = new Customer();
			customer.setId(1);
			customer.setName("Vamsi");
			customer.setGender("Male");
			customer.setEmail("vamsi123@gmail.com");
			customer.setAddress("kakinada");

			Customer customer2 = new Customer();
			customer2.setId(2);
			customer2.setName("Sujan");;
			customer2.setGender("Male");
			customer2.setEmail("sujankumar123@gmail.com");
			customer2.setAddress("kakianda");
			//Saving the Customer
			customerreposit.save(customer);
			customerreposit.save(customer2);
			
			List<Customer> list = customerreposit.findByName("Sujan");
			String name="";
			for(Customer testCustomer : list) {
				name = testCustomer.getName();
			}
			
			
			assertThat(name).isEqualTo("Sujan");
		}
		

}
