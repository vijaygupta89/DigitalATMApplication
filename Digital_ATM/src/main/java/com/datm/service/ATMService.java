package com.datm.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.datm.dto.UserAccountDTO;
import com.datm.entity.UserAccount;

@Component
public interface ATMService {

	// first we write CRUD operation
	
	// create 
	public UserAccount createUser(UserAccountDTO userAccountDTO);
	
	// update
	public UserAccount updateUser(int cardNumber, UserAccountDTO userAccountDTO);
	
	// getById
	public UserAccountDTO getDetailsById(int cardNumber);
	
	// getAllDetails
	public List<UserAccountDTO> getAllDetails();	// working
	
	// delete
	public String deleteById(int cardNumber , int pin);
	
	// deposite	
	public String depositeAmount(int cardNumber, int pin, double amount);
	
	// withdraw
	public String withdrawAmount(int cardNumber, int pin, double amount);	
	
	// check Balance
	public double checkBalance(int cardNumber , int pin);
	
	// change pin
	String changePin(int cardNumber, int pin, int newPin);
	
	
}
