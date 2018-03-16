package com.banking.application.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.application.entity.Accounts;
import com.banking.application.entity.AccountsBalance;
import com.banking.application.entity.AccountsRepository;
import com.banking.application.exception.InSufficientBalanceException;
import com.banking.application.model.AccountsRequest;
import com.banking.application.model.CheckBalanceResponse;
import com.banking.application.model.DepositRequest;
import com.banking.application.model.WithDrawRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("bank")
public class BankingApplicationController {
	
	@Autowired
	AccountsRepository accountsRepository;

	@PostMapping(value = "/create")
	public Accounts create(@RequestBody AccountsRequest accountsRequest) throws Exception {
		
		System.out.println(accountsRequest);
		
		AccountsBalance accountsBalance = new AccountsBalance();
		accountsBalance.setBalanceAmount(accountsRequest.getBalanceAmount());
		
		Accounts accounts = new Accounts();
		accounts.setName(accountsRequest.getName());
		accounts.setPhoneNumber(accountsRequest.getPhoneNumber());
		accounts.setAccountsBalance(accountsBalance);
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(accounts));
		return accountsRepository.save(accounts);
	}

	@PostMapping(value = "/deposit")
	public Accounts Deposit(@RequestBody DepositRequest depositRequest) {
		
		System.out.println("Account No "+depositRequest.getAccountNo());
		Accounts accounts = accountsRepository.findOne(depositRequest.getAccountNo());
		accounts.getAccountsBalance().setBalanceAmount(accounts.getAccountsBalance().getBalanceAmount()+depositRequest.getDepositAmount());
		
		return accountsRepository.save(accounts);
	}
	
	@PostMapping(value = "/withdraw")
	public Accounts withdraw(@RequestBody WithDrawRequest withDrawRequest) {
		
		Accounts accounts = accountsRepository.findOne(withDrawRequest.getAccountNo());
		
		if(accounts.getAccountsBalance().getBalanceAmount() < withDrawRequest.getWithdrawalAmount()) {
			throw new InSufficientBalanceException("Your account has insufficient balance");
		}else {
			accounts.getAccountsBalance().setBalanceAmount(accounts.getAccountsBalance().getBalanceAmount()-withDrawRequest.getWithdrawalAmount());
			return accountsRepository.save(accounts);
		}
	}
	
	@GetMapping(value = "/checkbalance/{accountNo}")
	public CheckBalanceResponse checkBalance(@PathVariable("accountNo") String accountNo) {
		Accounts accounts = accountsRepository.findOne(accountNo);
			return new CheckBalanceResponse(accounts.getAccountsBalance().getBalanceAmount());
	}
}
