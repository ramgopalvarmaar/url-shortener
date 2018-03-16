package com.banking.application.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AccountsRequest {
	
	private String name;
	private String phoneNumber;
	private int balanceAmount;
	
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
	public int getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(int balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	
	@Override
	public String toString() {
	    return ToStringBuilder.reflectionToString(this);
	}
	
}
