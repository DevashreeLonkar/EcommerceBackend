package com.ecommerce.jwt.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;
	private String orderFullName;
	private String orderFullAddres;
	private String orderContactNumber;
	private String orderAlternameContactNumber;
	private String orderStatus;
	private Double orderAmount;
	@ManyToOne
	private Product product;
	@ManyToOne
	private User user;
	
	private String transactionId;
}
