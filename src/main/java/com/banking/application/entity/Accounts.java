package com.banking.application.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name="ACCOUNTS")
public class Accounts {
	
	@Id
	@Column(name="ACCOUNT_NO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String accountNo;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="PHONE_NUMBER")
	private String phoneNumber;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "BALANCE_ID")
	private AccountsBalance accountsBalance;
	
	public AccountsBalance getAccountsBalance() {
		return accountsBalance;
	}

	public void setAccountsBalance(AccountsBalance accountsBalance) {
		this.accountsBalance = accountsBalance;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String toString() {
	    return ToStringBuilder.reflectionToString(this);
	}
	
}
