package com.learn.fundTransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.fundTransfer.entity.AccountDetails;
import com.learn.fundTransfer.entity.UserDetails;

public interface UserAccountRepository extends JpaRepository<AccountDetails, Long> {
	
	AccountDetails findByAccountNumberEquals(Long fromAccountNumber);
}
