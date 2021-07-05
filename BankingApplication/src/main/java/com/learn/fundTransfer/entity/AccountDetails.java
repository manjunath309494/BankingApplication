package com.learn.fundTransfer.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="account_details")
public class AccountDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountNumber;
    
    private String accountType;
    
    private Long userId;
    
    private String bankAddress;
    
    private String ifscCode;
    
    private Double opening_balance;

    
	public AccountDetails() {
		super();
	}


	public AccountDetails(String accountType, String bankAddress, Long userId, String ifscCode,
			Double opening_balance) {
		
		this.accountType = accountType;
		this.bankAddress = bankAddress;
		this.userId = userId;
		this.ifscCode = ifscCode;
		this.opening_balance = opening_balance;
		
	}


	public Long getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}


	public String getAccountType() {
		return accountType;
	}


	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public String getbankAddress() {
		return bankAddress;
	}


	public void setbankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}


	public Long getuserId() {
		return userId;
	}


	public void setuserId(Long userId) {
		this.userId = userId;
	}


	public String getIfscCode() {
		return ifscCode;
	}


	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}


	public Double getOpening_balance() {
		return opening_balance;
	}


	public void setOpening_balance(Double opening_balance) {
		this.opening_balance = opening_balance;
	}
    
}
