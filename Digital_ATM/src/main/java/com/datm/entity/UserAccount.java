package com.datm.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity			// use for declaing class as table in database
@Table(name = "account_details") // set the table name
public class UserAccount {

	@Id			//pk
	@Column(name = "account_number", unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer accountNumber;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false , unique = true)
	private String email;

	@Column(name = "contact_number", nullable = false , unique = true)
	private String contact_number;

	@Column(name = "account_type", nullable = false)
	private String accountType; // current/saving

	@Column(name = "User_PIN", unique = true)
	private Integer pin;

	@Column(name = "debit_Card", unique = true)
	private Integer cardNumber;
	
	@Column(name = "expiryDate")
	private Date date;
	
	@Column(name = "CVV", unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String CVV;
	
	@Column(name = "amount")
	private Double amount;

	

}
