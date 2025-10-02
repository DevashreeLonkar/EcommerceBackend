package com.ecommerce.jwt.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.jwt.dto.CartResponseDTO;
import com.ecommerce.jwt.entity.Cart;
import com.ecommerce.jwt.service.CartService;

@RestController
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PreAuthorize("hasRole('User')")
	@GetMapping({"/addToCart/{productId}"})
	public CartResponseDTO addToCart(@PathVariable(name = "productId") Long productId) {
		return cartService.addToCart(productId);
	}
	
	@PreAuthorize("hasRole('User')")
	@GetMapping("/getCartDetails")
	public Optional<Cart> getCartDetails(){
		return cartService.getCartDetails();
	}

}
