package com.datm.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datm.dto.UserAccountDTO;
import com.datm.entity.UserAccount;

import com.datm.repository.ATMRepository;
import com.datm.service.ATMService;

@Service
public class ATMServiceImpl implements ATMService {

	@Autowired
	private ATMRepository atmRepository;

	@Override
	public UserAccount createUser(UserAccountDTO userAccountDTO) {

		// DTO to Entity
		// Entity to DTO

		UserAccount _userAccount = UserAccount.builder().name(userAccountDTO.getName()).email(userAccountDTO.getEmail())
				.contact_number(userAccountDTO.getContact_number()).accountType(userAccountDTO.getAccountType())
				.CVV(userAccountDTO.getCVV()).date(userAccountDTO.getExpiryDate())
				.cardNumber(userAccountDTO.getCardNumber()).amount(userAccountDTO.getAmount())
				.pin(userAccountDTO.getPin()).build();
		
		String newPin = "congratulations your digital debit card has been created successfully, you card number is:"+userAccountDTO.getCardNumber();
		System.out.println(newPin);
		return atmRepository.save(_userAccount);
	}

	@Override
	public UserAccount updateUser(int cardNumber, UserAccountDTO userAccountDTO) {
		// first find the cardNumber and validate
		UserAccount _UserAccount = atmRepository.findByCardNumber(cardNumber);
		if (_UserAccount == null) {
			return null; // user account does not exist
		} else {	//after validation update value from Entity to DTO
			_UserAccount.setName(userAccountDTO.getName());
			_UserAccount.setEmail(userAccountDTO.getEmail());
			_UserAccount.setContact_number(userAccountDTO.getContact_number());
			_UserAccount.setAccountType(userAccountDTO.getAccountType());
			_UserAccount.setCVV(userAccountDTO.getCVV());
			_UserAccount.setDate(userAccountDTO.getExpiryDate());
			_UserAccount.setAmount(userAccountDTO.getAmount());

			return atmRepository.save(_UserAccount); // save the updated oject
		}
	}

	@Override
	public UserAccountDTO getDetailsById(int cardNumber) {
		UserAccount _userAccount = atmRepository.findByCardNumber(cardNumber); // fetch the data
		if(_userAccount == null)	// validate
		{
			return null; // means cardNumber is not present in the database
		} 	// fetch from DTO to entity
			UserAccountDTO _userAccountDTO = new UserAccountDTO();
			_userAccountDTO.setName(_userAccount.getName());
			_userAccountDTO.setEmail(_userAccount.getEmail());
			_userAccountDTO.setContact_number(_userAccount.getContact_number());
			_userAccountDTO.setAccountType(_userAccount.getAccountType());
			_userAccountDTO.setCardNumber(_userAccount.getCardNumber());
			_userAccountDTO.setCVV(_userAccount.getCVV());
			_userAccountDTO.setExpiryDate(_userAccount.getDate());
			_userAccountDTO.setAmount(_userAccount.getAmount());
			_userAccountDTO.setPin(_userAccount.getPin()); 
			
			return _userAccountDTO;	// return the object
	}

	
	@Override
	public String depositeAmount(int cardNumber, int pin, double amount) {
		// first we choose the account by card Number
		UserAccount _Account = atmRepository.findByCardNumber(cardNumber);
		if (_Account == null) {
			return "Invalid card number";
		}
		// then verify the pin
		if (_Account.getPin() != pin)
			return "Invalid Pin Number";

		// if both are correct then
		// we first get last amount
		double last_amount = _Account.getAmount(); // it will return amount & save in varaiable
		// now set the new amount = last + entered amount
		double latest_amount = (amount + last_amount);

		// now update amount in database
		_Account.setAmount(latest_amount);

		// now save the _account
		atmRepository.save(_Account);

		return "Amount deposited Successfully.. your latest amount status is:" + latest_amount;

	}

	@Override
	public String withdrawAmount(int cardNumber, int pin, double amount) {
		// the withdraw is something same as deposite
		UserAccount _Account = atmRepository.findByCardNumber(cardNumber);
		if (_Account == null) {
			return "Invalid card number";
		}
		// then verify the pin
		if (_Account.getPin() != pin)
			return "Invalid Pin Number";

		// now steps to withdraw
		double current_amount = _Account.getAmount();

		// check whether withdraw amount is not greater then current balance
		if (current_amount < amount) {
			return "Insufficient Balance, you current balance is" + current_amount;
		} else {
			double newBalance = (current_amount - amount);
			_Account.setAmount(newBalance);
			atmRepository.save(_Account);
			return "Successfully Amount :" + amount + "has been withdraw.. you current balance is" + newBalance;
		}
	}

	@Override
	public String changePin(int cardNumber, int pin, int newPin) {
		// first validate the parameters
		UserAccount _Account = atmRepository.findByCardNumber(cardNumber);
		if (_Account == null) {
			return "Invalid card number";
		}
		// then verify the pin
		if (_Account.getPin() != pin)
			return "Invalid Pin Number";
		
		// steps to change pin number
		int oldPin = _Account.getPin();
		if(oldPin == newPin) {
			return "Entered PIN match with older, please enter another PIN";
		} else 
		{
			_Account.setPin(newPin);
			atmRepository.save(_Account);
			return "PIN number has been changed Successfully";
		}
	}

	@Override
	public double checkBalance(int cardNumber, int pin) {
		// first find the cardNumber
		UserAccount _Account = atmRepository.findByCardNumber(cardNumber);
		// return the amount
		return _Account.getAmount();
	}

	@Transactional
	@Override
	public String deleteById(int cardNumber, int pin) {
		// first validate the parameters
		UserAccount _Account = atmRepository.findByCardNumber(cardNumber);
		if (_Account == null) {
			return "Invalid card number";
		}
		// then verify the pin
		if (_Account.getPin() != pin) {
			return "Invalid Pin Number";
				
		}		
		 atmRepository.deleteByCardNumber(cardNumber);
		return "Account with CardNumber:"+cardNumber+"has been deleted";
	}

	@Override
	public List<UserAccountDTO> getAllDetails() {
		// below is the process of fetching DTO data  without creating DTO repository
		List<UserAccount> accounts = atmRepository.findAll();	// first fetch all data
	    List<UserAccountDTO> accountDTOs = new ArrayList<>(); 
	    // then run a loop on Entity and set get the data within it for dto
	    for (UserAccount account : accounts) {
	        UserAccountDTO accountDTO = UserAccountDTO.builder()
	            .name(account.getName())
	            .email(account.getEmail())
	            .contact_number(account.getContact_number())
	            .accountType(account.getAccountType())
	            .CVV(account.getCVV())
	            .expiryDate(account.getDate())
	            .cardNumber(account.getCardNumber())
	            .amount(account.getAmount())
	            .pin(account.getPin())
	            .build();

	        accountDTOs.add(accountDTO);
	    }

	    return accountDTOs;

	}

}
