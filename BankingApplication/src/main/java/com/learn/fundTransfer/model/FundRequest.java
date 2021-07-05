package com.learn.fundTransfer.model;

public class FundRequest {
	private Double amount;

	private Long fromAccount;

	private Long toAccount;

	private String type;

	public FundRequest() {
		super();		
	}

	public FundRequest(Double amount, Long fromAccount, Long toAccount, String type) {
		super();
		this.amount = amount;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(Long fromAccount) {
		this.fromAccount = fromAccount;
	}

	public Long getToAccount() {
		return toAccount;
	}

	public void setToAccount(Long toAccount) {
		this.toAccount = toAccount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}