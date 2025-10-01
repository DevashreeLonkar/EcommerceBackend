package com.ecommerce.jwt.dto;


public class CartResponseDTO {
	
	private Long cartId;
	private String userName;
	private String productName;
	
	 public CartResponseDTO(Long cartId, String username, String productName) {
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
