package com.learn.fundTransfer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.fundTransfer.entity.FundTransfer;

public interface TransactionRepository extends JpaRepository<FundTransfer, Long> {
	
	List<FundTransfer> findByFromAccountOrToAccount(Long fromAccount, Long toAccount);
	
}
