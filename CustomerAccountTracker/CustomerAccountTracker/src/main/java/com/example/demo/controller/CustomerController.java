package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.FundTransfer;
import com.example.demo.exception.AccountAlreadyExistException;
import com.example.demo.exception.AmountCannotTransferException;
import com.example.demo.exception.InsufficientBalanceException;
import com.example.demo.exception.TransactionFailException;
import com.example.demo.model.BankAccount;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService service;

	@PostMapping("/add")
	public ResponseEntity<Customer> addCustomer(@RequestBody @Valid Customer customer) {

		return new ResponseEntity<Customer>(service.addCustomer(customer), HttpStatus.CREATED);
	}

	@PostMapping("/account/{id}")
	public ResponseEntity<List<BankAccount>> addAccount(@RequestBody List<BankAccount> account, @PathVariable int id)
			throws AccountAlreadyExistException {

		service.addAccount(id, account);
		return new ResponseEntity<List<BankAccount>>(account, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {

		Customer customer = service.getCustomerById(id);
		return new ResponseEntity<Customer>(customer, HttpStatus.FOUND);
	}

	@GetMapping("/account/{accountId}")
	public ResponseEntity<BankAccount> getAccountById(@PathVariable int accountId) {

		BankAccount account = service.getAccountById(accountId);
		return new ResponseEntity<BankAccount>(account, HttpStatus.FOUND);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<Customer>> getAllCustomers() {

		List<Customer> custList = service.getCustomers();
		return new ResponseEntity<List<Customer>>(custList, HttpStatus.FOUND);
	}

	@GetMapping("/findByName/{name}")
	public ResponseEntity<List<Customer>> getByName(@PathVariable String name) {

		List<Customer> custList = service.getByName(name);
		return new ResponseEntity<List<Customer>>(custList, HttpStatus.FOUND);
	}

	@GetMapping("/account/findByAccountType/{accountType}")
	public ResponseEntity<List<BankAccount>> getByAccountType(@PathVariable String accountType) {

		List<BankAccount> accList = service.getByAccountType(accountType);
		return new ResponseEntity<List<BankAccount>>(accList, HttpStatus.FOUND);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {

		return new ResponseEntity<Customer>(service.updateCustomer(id, customer), HttpStatus.CREATED);

	}
   // Business Logic
	@PutMapping("/account/fundtransfer/{transferAmount}/{sourceAccountId}/{targetAccountId}")
	public ResponseEntity<String> fundTransfer(@PathVariable int sourceAccountId, @PathVariable int targetAccountId,
			@PathVariable long transferAmount) throws InsufficientBalanceException,TransactionFailException, AmountCannotTransferException  {

		service.fundTransfer(sourceAccountId, targetAccountId, transferAmount);
		return new ResponseEntity<>(service.fundTransfer(sourceAccountId, targetAccountId, transferAmount),HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteCustomerById(@PathVariable int id) {
		service.deleteCustomerById(id);
		return "Customer deleted by "+id;
	
	}
	
	@DeleteMapping("/account/delete/{accountId}")
	public String deleteAccountById(@PathVariable int accountId) {

		service.deleteAccountById(accountId);
		return "Account deleted by "+accountId;
	}

	@DeleteMapping("/deleteAll")
	public String deleteAllCustomers() {

		service.deleteAllCustomer();
		return "All Customers are deleted";
	}

}
