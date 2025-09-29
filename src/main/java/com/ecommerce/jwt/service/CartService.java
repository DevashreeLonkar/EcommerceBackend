package com.ecommerce.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.jwt.repository.CartRepository;

@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;

}
