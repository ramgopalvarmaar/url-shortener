package com.banking.application.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CheckBalanceResponse {
	
	private int yourBalance;

	public CheckBalanceResponse(int yourBalance) {
		super();
		this.yourBalance = yourBalance;
	}

	public int getYourBalance() {
		return yourBalance;
	}

	public void setYourBalance(int yourBalance) {
		this.yourBalance = yourBalance;
	}
	
	@Override
	public String toString() {
	    return ToStringBuilder.reflectionToString(this);
	}

}
