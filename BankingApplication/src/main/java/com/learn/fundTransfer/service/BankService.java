package com.learn.fundTransfer.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.learn.fundTransfer.entity.AccountDetails;
import com.learn.fundTransfer.entity.UserDetails;
import com.learn.fundTransfer.exception.NoFundsException;
import com.learn.fundTransfer.exception.UserException;
import com.learn.fundTransfer.model.Login;
import com.learn.fundTransfer.model.SuccessResponse;
import com.learn.fundTransfer.entity.FundTransfer;
import com.learn.fundTransfer.repository.BankRepository;
import com.learn.fundTransfer.repository.UserAccountRepository;
import com.learn.fundTransfer.repository.TransactionRepository;

@Service
public class BankService {
	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private TransactionRepository transactionRepository;
	static Logger logger = Logger.getLogger(BankService.class.getName());

	public AccountDetails createBankingUser(UserDetails userDetails) {
		
		UserDetails user = bankRepository.save(userDetails);
		logger.log(Level.INFO, () ->  "c created: {0} " + user);
		
		AccountDetails account = createAccount(user.getCustomerId());
		
		return account;

	}

	public AccountDetails createAccount(Long userId) {
		
		AccountDetails accountDetails = new AccountDetails("Savings", "Home", userId, "BANK001", 500.00d);
		AccountDetails acc = userAccountRepository.save(accountDetails);
		
		logger.log(Level.INFO, () ->  "a created: {0} " + acc);
		return acc;
		
	}

	public FundTransfer transferMoney(FundTransfer fundTransferRequest) {
		
		Long fromSenderAccount = fundTransferRequest.getFromAccount();
		Long toReceiverAccount = fundTransferRequest.getToAccount();
		Double amount = fundTransferRequest.getAmount();
		
		AccountDetails fromAccount = userAccountRepository.findByAccountNumberEquals(fromSenderAccount);
		AccountDetails toAccount = userAccountRepository.findByAccountNumberEquals(toReceiverAccount);
		
		if (fromAccount.getOpening_balance().compareTo(1.0d) >= 1
				&& fromAccount.getOpening_balance().compareTo(amount) >= 1) {
			Double balance = fromAccount.getOpening_balance() - amount;
			fromAccount.setOpening_balance(balance);
			
			AccountDetails fromAccountDetails = userAccountRepository.save(fromAccount);
			logger.log(Level.INFO, () ->  "fromAccountDetails: {0} " + fromAccountDetails);
			Double toBalance = toAccount.getOpening_balance() + amount;
			toAccount.setOpening_balance(toBalance);
			
			AccountDetails toAccountDetails = userAccountRepository.save(toAccount);
			logger.log(Level.INFO, () ->  "toAccountDetails: {0} " + toAccountDetails);
			Timestamp datetime = new Timestamp(System.currentTimeMillis());
			FundTransfer request = transactionRepository.save(new FundTransfer(amount,
					fromSenderAccount, toReceiverAccount, datetime, fundTransferRequest.getType()));
			
			return request;
			
		}
		
		throw new NoFundsException();
	}
	
	
	private FundTransfer deductMoneyFromAccount(FundTransfer fundTransferRequest) {
		
		Long fromSenderAccount = fundTransferRequest.getFromAccount();
		Double amount = fundTransferRequest.getAmount();
		
		AccountDetails fromAccount = userAccountRepository.findByAccountNumberEquals(fromSenderAccount);
		
		if (null != fromAccount && fromAccount.getOpening_balance().compareTo(1.0d) >= 1
				&& fromAccount.getOpening_balance().compareTo(amount) >= 1) {
			Double balance = fromAccount.getOpening_balance() - amount;
			fromAccount.setOpening_balance(balance);
			
			AccountDetails fromAccountDetails = userAccountRepository.save(fromAccount);
			logger.log(Level.INFO, () ->  "fromAccountDetails: {0} " + fromAccountDetails);
			
			Timestamp datetime = new Timestamp(System.currentTimeMillis());
			
			FundTransfer request = transactionRepository.save(
					new FundTransfer(amount, fromSenderAccount, null, datetime, fundTransferRequest.getType()));
			
			return request;
			
		}
		return null;
		
	}

	public ResponseEntity<SuccessResponse> checkLogin(Login loginRequest) throws UserException {

		UserDetails customerDetails = bankRepository.findByUserNameAndPassword(loginRequest.getUserName(),
				loginRequest.getPassword());
		
		if (null != customerDetails) {
			return new ResponseEntity<>(new SuccessResponse("success", "Logged in Successfully"), HttpStatus.OK);
		}
		else {
			
         throw new  UserException("Unauthorized User");
		}
	}
	
	public List<FundTransfer> getAccountHistory(Long accountNumber) {
		
		return transactionRepository.findByFromAccountOrToAccount(accountNumber, accountNumber);
	}
}
