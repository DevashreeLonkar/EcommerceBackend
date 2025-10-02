package com.ecommerce.jwt.dto;

import java.util.List;

public class CartResponseDTO {
	
	private Long cartId;
	private String userName;
	private List<String> productName;
	
	 public CartResponseDTO(Long cartId, String username, List<String> productName) {
	        this.cartId = cartId;
	        this.userName = userName;
	        this.productName = productName;
	    }

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getProductName() {
		return productName;
	}

	public void setProductName(List<String> productName) {
		this.productName = productName;
	}
}

	
