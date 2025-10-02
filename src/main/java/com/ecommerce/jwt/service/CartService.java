package com.ecommerce.jwt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.jwt.configuration.JwtRequestFilter;
import com.ecommerce.jwt.dto.CartResponseDTO;
import com.ecommerce.jwt.entity.Cart;
import com.ecommerce.jwt.entity.Product;
import com.ecommerce.jwt.entity.User;
import com.ecommerce.jwt.repository.CartRepository;
import com.ecommerce.jwt.repository.ProductRepository;
import com.ecommerce.jwt.repository.UserRepository;

@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	public CartResponseDTO addToCart(Long productId) {
//		Optional<Product> product= productRepository.findById(productId);
//		
//		String username= JwtRequestFilter.CURRENT_USER;
//		
//		Optional<User> user =null;
//		if(username != null) {
//			user= userRepository.findByUserName(username);
//		}
//		
//		if(product != null && user !=null) {
//			Cart cart= new Cart(product, user);
//			return cartRepository.save(cart);
//		}
//		
//		 return null;
		
		String username= JwtRequestFilter.CURRENT_USER;		
		if(username == null || username.equals(null)) {
			throw new RuntimeException("User not authenticated");
		}
		
		User user= userRepository.findByUserName(username)
				.orElseThrow(()-> new RuntimeException("User not found"));
		
		Product product= productRepository.findById(productId)
				.orElseThrow(()-> new RuntimeException("Poduct not found"));
		
//		if(product != null && user != null) {
//			Cart cart= new Cart();
//			cart.setUser(user);
//			cart.setProduct(product);
//			cartRepository.save(cart);
//			
//			return new CartResponseDTO(cart.getCartId(), cart.getUser().getUserName(), cart.getProduct().getProductName());
//		}
//		return null;
		
	    // Check if user already has a cart
	    Cart cart = cartRepository.findByUser(user).orElse(null);
	    if (cart == null) {
	        cart = new Cart();
	        cart.setUser(user);
	    }

	    // Add product to cart (prevent duplicates if needed)
	    cart.getProducts().add(product);

	    cart = cartRepository.save(cart);

	    return new CartResponseDTO(cart.getCartId(), user.getUserName(),
	            cart.getProducts().stream().map(Product::getProductName).toList());
	}
	
	
	public Optional<Cart> getCartDetails() {
		String username= JwtRequestFilter.CURRENT_USER;		
		User user= userRepository.findByUserName(username)
				.orElseThrow(()-> new RuntimeException("User not found"));
		
		return cartRepository.findByUser(user);
				
	}

}
