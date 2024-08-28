package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.FundTransfer;
import com.example.demo.model.BankAccount;
import com.example.demo.model.Customer;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.exception.*;
import lombok.extern.slf4j.Slf4j;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerreposit;

	@Autowired
	private BankAccountRepository bankreposit;

	public Customer addCustomer(Customer customer) {

		// Getting all account details from customer
		List<BankAccount> account = customer.getBankAccount();

		return customerreposit.save(customer);

	}

	public void addAccount(int id, List<BankAccount> bankAccount) throws AccountAlreadyExistException {

		// Selecting customer using incoming customer Id
		Customer customer = getCustomerById(id);

		// Getting all accounts available in the selected Customer
		List<BankAccount> account = customer.getBankAccount();

		// Checking if account is already present in Selected customer
		for (BankAccount accDb : account) {

			for (BankAccount accUser : bankAccount) {

				if (accDb.getAccountType().equals(accUser.getAccountType())) {

					throw new AccountAlreadyExistException(
							accUser.getAccountType() + " Account Already Exists for this Customer!!!!");
				}
			}
		}

		// Adding the account details which are not present in selected customer in the
		// list
		for (BankAccount accUser : bankAccount) {
			account.add(accUser);
		}

		// Setting the account list to customer with new Accounts
		customer.setBankAccount(account);

		// Saving the customer to the Database
		customerreposit.save(customer);
	}

	public Customer getCustomerById(int id) {

		Customer customer = null;

		try {
			customer = customerreposit.findById(id).get();
		} catch (NoSuchElementException ex) {

			throw new NoSuchElementException("Customer Id : " + id + " is not Present");
		}

		return customer;
	}

	public BankAccount getAccountById(int accountId) {

		BankAccount account = null;
		try {

			account = bankreposit.findById(accountId).get();

		} catch (NoSuchElementException ex) {

			throw new NoSuchElementException("Account Id : " + accountId + " is not Present");
		}

		return account;
	}

	public List<Customer> getCustomers() {

		List<Customer> list = customerreposit.findAll();

		if (list.isEmpty()) {

			throw new NoSuchElementException("Customer Records are not Present");
		}

		return list;
	}

	public List<Customer> getByName(String name) {

		List<Customer> list = customerreposit.findByName(name);

		if (list.isEmpty()) {

			throw new NoSuchElementException("Customers with Name as : " + name + " are not Present");
		}
		return list;
	}

	public List<BankAccount> getByAccountType(String accountType) {

		List<BankAccount> list = bankreposit.findByAccountType(accountType);
		if (list.isEmpty()) {

			throw new NoSuchElementException("Accounts with Account Type : " + accountType + " are not Present");
		}
		return list;
	}

	public Customer updateCustomer(int id, Customer customer) {

		Customer cust = getCustomerById(id);
		List<BankAccount> list = cust.getBankAccount();

		// Updating only personal details ignoring account details
		cust.setId(id);
		cust.setName(customer.getName());
		cust.setGender(customer.getGender());
		cust.setEmail(customer.getEmail());
		cust.setAddress(customer.getAddress());

		// Assuming account details can't be changed so setting account details as it is
		cust.setBankAccount(list);

		return customerreposit.save(cust);
	}

	public Customer deleteCustomerById(int id) {
		
		Customer customer = null;

		try {

			customerreposit.deleteById(id);

		} catch (EmptyResultDataAccessException ex) {

			throw new NoSuchElementException("Customer Id : " + id + " is not Present");
		}

		return customer;
	}

	public void deleteAllCustomer() {
		
		customerreposit.deleteAll();
//		return "All Customers Deleted";
	
		
	}

	public void deleteAccountById(int accountId) {

		try {

			bankreposit.deleteById(accountId);
		} catch (EmptyResultDataAccessException ex) {

			throw new NoSuchElementException("Account Id : " + accountId + " is not Present");
		}

	}

	public String fundTransfer(int sourceAccountId, int targetAccountId, long transferAmount)
			throws InsufficientBalanceException, TransactionFailException, AmountCannotTransferException {

		BankAccount source = null;
		BankAccount target = null;

		// getting source account details using id
		source = getAccountById(sourceAccountId);

		// getting target account details using id
		target = getAccountById(targetAccountId);

		// Balance available in Source account
		long sourceAmount = source.getAccountBalance();
		
       //Transaction Can't done if two accounts are same	
		if(source==target) {
			
				throw new TransactionFailException("Transaction will not done for two same accounts");

				}
		//negative amount can't transfer from one account to another account
		if (transferAmount<1) 
			
			throw new AmountCannotTransferException("Amount Can't be Transfered");
			
		// checking amount is less than balance available in source account
		if (transferAmount > sourceAmount)
			throw new InsufficientBalanceException(
					"Account id : " + sourceAccountId + " does not have Sufficient Balance");

		// adding amount to target account
		target.setAccountBalance(transferAmount + target.getAccountBalance());
		// saving target account balance
		bankreposit.save(target);

		// Subtracting balance in source account after transfer
		sourceAmount = sourceAmount - transferAmount;
		// saving source account balance
		source.setAccountBalance(sourceAmount);
		bankreposit.save(source);
		
		return "Money Transfered from"+" "+source.getAccountId()+" "+" To Account Id"+" "+target.getAccountId();

}
}
