package com.ecommerce.jwt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TransactionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
	private String orderId;
	private String currency;
	private Double amount;
	private String key;
	
	public TransactionDetails(String orderId, String currency, Double amount, String key) {
		this.orderId = orderId;
		this.currency = currency;
		this.amount = amount;
		this.key = key;
	}
	
	
}
