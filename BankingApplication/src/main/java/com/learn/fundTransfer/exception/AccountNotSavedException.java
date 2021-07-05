package com.learn.fundTransfer.exception;

public class AccountNotSavedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AccountNotSavedException(String message) {
		super(message);
	}
}
