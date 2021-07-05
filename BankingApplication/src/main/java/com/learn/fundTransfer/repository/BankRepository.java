package com.learn.fundTransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.fundTransfer.entity.UserDetails;

public interface BankRepository extends JpaRepository<UserDetails, Long> {
	
	UserDetails findByUserNameAndPassword(String userName, String password);

}

