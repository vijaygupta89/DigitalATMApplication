package com.datm;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.datm.dto.UserAccountDTO;
import com.datm.entity.UserAccount;
import com.datm.repository.ATMRepository;

@SpringBootApplication
public class DigitalAtmApplication implements CommandLineRunner {

	@Autowired
	private ATMRepository aTMRepository;
	public static void main(String[] args) {
		SpringApplication.run(DigitalAtmApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		UserAccountDTO userAccountDTO = new UserAccountDTO(
	            "Saurabh Giri","saurabh@gmail.com","1234567890","savings",1234,1000.0,4321,new Date(12-11-2023));

		// Create an instance of UserAccount and set its properties
		UserAccount userAccount = UserAccount.builder()
		        .name(userAccountDTO.getName())
		        .email(userAccountDTO.getEmail())
		        .contact_number(userAccountDTO.getContact_number())
		        .accountType(userAccountDTO.getAccountType())
		        .CVV(userAccountDTO.getCVV())
		        .amount(userAccountDTO.getAmount())
		        .pin(userAccountDTO.getPin())
		        .date(userAccountDTO.getExpiryDate())
		        .cardNumber(userAccountDTO.getCardNumber())
		        .build();
		// Save the UserAccount object to the database
		UserAccount savedUserAccount = aTMRepository.save(userAccount);
		// Print the account number of the saved user account
		System.out.println("Saved User Account: " + savedUserAccount.getAccountNumber());
	}

}
