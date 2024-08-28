package com.example.demo.controllertest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.controller.CustomerController;

import com.example.demo.model.BankAccount;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
public class ControllerTest {

	@Autowired
	MockMvc mockmvc;
	
	static ObjectMapper mapper=new ObjectMapper();
	
	@MockBean
	private  CustomerService service;
	
	@Test
	public void testAddCustomer() throws Exception {
		
		BankAccount ac1 = new BankAccount(1,"SBI", "Saving", 1000);
		BankAccount ac2 = new BankAccount(2, "HDFC","Salary", 20000);
		List<BankAccount> list = new ArrayList<>();
		list.add(ac1);
		list.add(ac2);
		Customer customer = new Customer(1,"Vamsi","Male","vamsi123@gmail.com","Kakinada",list);
	   	
		String input =  this.convertToJson(customer);
		  
		MvcResult mvcResult = mockmvc.perform(MockMvcRequestBuilders.post("/customer/add")
				      .contentType(MediaType.APPLICATION_JSON_VALUE).content(input)).andReturn();
		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(201);
	}

	@Test
	public void testAddAccount() throws Exception {
		
		BankAccount ac1 = new BankAccount(1,"SBI", "Saving", 1000);
		BankAccount ac2 = new BankAccount(2, "HDFC","Salary", 20000);
		List<BankAccount> list = new ArrayList<>();
		list.add(ac1);
		list.add(ac2);
	   	
		String input =  this.convertToJson(list);
		
		Customer cust = new Customer();
		cust.setId(1);;
		cust.setName("Vamsi");
		cust.setGender("Male");
		cust.setEmail("vamsi123@gmail.com");
		cust.setAddress("kakinada");
		cust.setBankAccount(list);
		
		
		MvcResult mvcResult = mockmvc.perform(MockMvcRequestBuilders.post("/customer/account/{id}",1)
				      .contentType(MediaType.APPLICATION_JSON_VALUE)
				      .content(input)).andReturn();
		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(201);
	}
		

	@Test
	public void testGetCustomerById() throws Exception {
		
		BankAccount ac1 = new BankAccount(1,"SBI", "Saving", 1000);
		BankAccount ac2 = new BankAccount(2, "HDFC","Salary", 20000);
		List<BankAccount> list = new ArrayList<>();
		list.add(ac1);
		list.add(ac2);
		Customer customer = new Customer(1,"Vamsi","Male","vamsi123@gmail.com","Kakinada",list);
		
		when(service.getCustomerById(1)).thenReturn(customer);
		
		
		RequestBuilder request=MockMvcRequestBuilders.get("/customer/{id}",1).accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult=mockmvc.perform(request).andExpect(status().isFound()).andReturn();
		
		String json = mvcResult.getResponse().getContentAsString();
		//convert Json to customer object
		Customer cust = mapper.readValue(json, Customer.class);
		
		assertThat(cust.getName()).isEqualTo("Vamsi");
		
	}
	@Test
	public void testGetAllCustomers() throws Exception {
		
		BankAccount ac1 = new BankAccount(1,"SBI", "Saving", 1000);
		BankAccount ac2 = new BankAccount(2, "HDFC","Salary", 20000);
		List<BankAccount> list1 = new ArrayList<>();
		list1.add(ac1);
		List<BankAccount> list2 = new ArrayList<>();
		list2.add(ac2);
		Customer customer1 = new Customer(1,"Vamsi","Male","vamsi123@gmail.com","Kakinada",list1);
		Customer customer2 = new Customer(2,"Sujan","Male","sujankumari123@gmail.com","Kakinada",list2);
		List<Customer> list = new ArrayList<>();
		list.add(customer1);
		list.add(customer2);
		
		when(service.getCustomers()).thenReturn(list);
		
		RequestBuilder request=MockMvcRequestBuilders.get("/customer/getAll").accept(MediaType.APPLICATION_JSON);
		mockmvc.perform(request)
		.andExpect(status().isFound())
		.andExpect(MockMvcResultMatchers.jsonPath("$").exists());
		
				
	}

	@Test
	public void testGetByName() throws Exception {
		
		BankAccount ac1 = new BankAccount(1,"SBI", "Saving", 1000);
		BankAccount ac2 = new BankAccount(2, "HDFC","Salary", 20000);
		List<BankAccount> list = new ArrayList<>();
		list.add(ac1);
		list.add(ac2);
		Customer customer = new Customer(1,"Vamsi","Male","vamsi123@gmail.com","Kakinada",list);
	  	List<Customer> custList = new ArrayList<>();
		custList.add(customer);
		
		when(service.getByName("Vamsi")).thenReturn(custList);
		
		RequestBuilder request=MockMvcRequestBuilders.get("/customer/findByName/{name}","Vamsi").accept(MediaType.APPLICATION_JSON);
		mockmvc.perform(request)
		.andExpect(status().isFound())
		.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Vamsi"));
	}


	@Test
	public void testFundTransfer() throws Exception {
		
		RequestBuilder request = MockMvcRequestBuilders.put("/customer/account/fundtransfer/{transferAmount}/{sourceAccountId}/{targetAccountId}",100,1,2).accept(MediaType.APPLICATION_JSON);
		mockmvc.perform(request)
		.andExpect(status().isAccepted());
		
		verify(service,times(2)).fundTransfer(1, 2, 100);
	}

	@Test
	public void testDeleteCustomerById() throws Exception {
		
		RequestBuilder request = MockMvcRequestBuilders.delete("/customer/delete/{id}",1).accept(MediaType.APPLICATION_JSON);
		mockmvc.perform(request);
		
		verify(service,times(1)).deleteCustomerById(1);
	}
	

	@Test
	public void testDeleteAccountById() throws Exception {
		
		RequestBuilder request = MockMvcRequestBuilders.delete("/customer/account/delete/{accountId}",1).accept(MediaType.APPLICATION_JSON);
		mockmvc.perform(request);
		
		verify(service,times(1)).deleteAccountById(1);
	}

	@Test
	public void testDeleteAllCustomers() throws Exception {
		
		RequestBuilder request = MockMvcRequestBuilders.delete("/customer/deleteAll").accept(MediaType.APPLICATION_JSON);
		mockmvc.perform(request);
		
		verify(service,times(1)).deleteAllCustomer();
	}
	
	
	private String convertToJson(Object obj ) throws JsonProcessingException {
		
		return mapper.writeValueAsString(obj);
	}
}

			
			


