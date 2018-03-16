package com.banking.application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;


@Entity
@Table(name="ACCOUNTS_BALANCE")
public class AccountsBalance {
	
	@Id
	@Column(name="BALANCE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String balanceId;
	
	@Column(name="BALANCE_AMOUNT")
	private int balanceAmount;
	
	public String getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(String balanceId) {
		this.balanceId = balanceId;
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
