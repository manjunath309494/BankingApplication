package com.learn.fundTransfer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.fundTransfer.entity.AccountDetails;
import com.learn.fundTransfer.entity.UserDetails;
import com.learn.fundTransfer.exception.UserException;
import com.learn.fundTransfer.model.Customer;
import com.learn.fundTransfer.model.Login;
import com.learn.fundTransfer.model.SuccessResponse;
import com.learn.fundTransfer.entity.FundTransfer;
import com.learn.fundTransfer.service.BankService;

@RestController
@RequestMapping("api")
public class BankController {

	@Autowired
	private BankService bankService;
	
	
	@PostMapping("/product/createaccount")
	public ResponseEntity<AccountDetails> createAccount(@RequestBody Customer customerDetails) {
		if (customerDetails.getAge() > 18) {
			AccountDetails accountDetails = bankService.createBankingUser(new UserDetails());
			return new ResponseEntity<>(accountDetails, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new AccountDetails(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<SuccessResponse> doLogin(@RequestBody Login loginRequest)  throws UserException {
		return bankService.checkLogin(loginRequest);
	}

	
	
	@PostMapping("/product/transferAmount")
	public ResponseEntity<FundTransfer> transferAmount(@RequestBody FundTransfer fundTransferRequest) {
		
		FundTransfer request = bankService.transferMoney(fundTransferRequest);
		
		if (request != null) {
			
		return new ResponseEntity<>(request,HttpStatus.OK);
		
		} else {
			
			request = new FundTransfer();
			request.setType("Not Successful due to insufficient funds");
			
			return new ResponseEntity<>(request, HttpStatus.BAD_REQUEST);	
		}
	}
	
	
	@GetMapping("/accounthistory/{accountNumber}")
	public ResponseEntity<List<FundTransfer>> accountHistory(@PathVariable Long accountnumber) {
		
		List<FundTransfer> accountHistory  = bankService.getAccountHistory(accountnumber);
		
		return new ResponseEntity<>(accountHistory,HttpStatus.OK);
	
	}
}

