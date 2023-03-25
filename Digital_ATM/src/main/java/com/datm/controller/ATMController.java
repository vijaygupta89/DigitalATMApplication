package com.datm.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datm.dto.UserAccountDTO;
import com.datm.entity.UserAccount;
import com.datm.service.ATMService;

@RestController
public class ATMController {

	@Autowired
	private ATMService service; // to use service functions
	
	@PostMapping(value="/newuser") // to create a new user
	public ResponseEntity<UserAccount> createUserData(@RequestBody @Valid UserAccountDTO dto)
	{
		UserAccount _Account = service.createUser(dto);
		return new ResponseEntity<>(_Account,HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{cardNumber}") 	//to fetch the user data using cardNumber 
	public ResponseEntity<UserAccountDTO> getUserDataById(@PathVariable int cardNumber)
	{
		UserAccountDTO _UserAccountDTO = service.getDetailsById(cardNumber);
		if(_UserAccountDTO == null) {	// exception handeling
			  return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(_UserAccountDTO);
	}
	
	@GetMapping("/users")	// to get all user data
	public ResponseEntity<List<UserAccountDTO>> getAllUserData()	
	{
		
		return ResponseEntity.ok(service.getAllDetails());
	}
	
	@PutMapping("/updateuser/{cardNumber}")	 // to update the existing user
	public ResponseEntity<UserAccount> updateUserData(@PathVariable int cardNumber, @RequestBody @Valid UserAccountDTO dto)
	{
		UserAccount _Account = service.updateUser(cardNumber, dto);
		return new ResponseEntity<>(_Account,HttpStatus.ACCEPTED);
	}
	
	@Transactional	// it showing error if i do not use this annotation
	@DeleteMapping("/deleteuser")	// delete user 
	public ResponseEntity<String> deleteUserById(@RequestParam("cardNumber") int cardNumber, @RequestParam("pin") int pin )
	{
		
		return  ResponseEntity.ok( service.deleteById(cardNumber, pin));
	}
	
	@PostMapping("/deposite") // to deposite money
	public ResponseEntity<String> deposite(@RequestParam("cardNumber") int cardNumber, @RequestParam("pin") int pin , @RequestParam("amount") double amount)
	{
		return ResponseEntity.ok( service.depositeAmount(cardNumber, pin, amount));
		 
	}
	
	@PostMapping("/withdraw") // to withdraw money
	public ResponseEntity<String> withdraw(@RequestParam("cardNumber") int cardNumber, @RequestParam("pin") int pin , @RequestParam("amount") double amount)
	{
		return ResponseEntity.ok(service.withdrawAmount(cardNumber, pin, amount));
		 
	}
	
	@PutMapping("/changepin/{cardNumber}")	// change pin
	public ResponseEntity<String> changePinNumber(@PathVariable int cardNumber, @RequestParam("oldPin") int oldPin, @RequestParam("newPin") int newPin)
	{
		return ResponseEntity.ok(service.changePin(cardNumber, oldPin, newPin));
	}
	
	@GetMapping("/checkbalance") // check balance
	public double checkBalance(@RequestParam("cardNumber") int cardNumber , @RequestParam("Pin") int Pin)
	{
		return service.checkBalance(cardNumber, Pin);
	}
	
}
