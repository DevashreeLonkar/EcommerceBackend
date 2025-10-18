package com.ecommerce.jwt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.jwt.configuration.JwtRequestFilter;
import com.ecommerce.jwt.entity.Cart;
import com.ecommerce.jwt.entity.Product;
import com.ecommerce.jwt.entity.User;
import com.ecommerce.jwt.repository.CartRepository;
import com.ecommerce.jwt.repository.ProductRepository;
import com.ecommerce.jwt.repository.UserRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	public Product addNewProduct(Product product) {
		return productRepository.save(product);
	}
	
	public List<Product> getAllProducts(int pageNumber, String searchKey){
		Pageable pageable= PageRequest.of(pageNumber, 12);
		//return (List<Product>) productRepository.findAll(pageable);
		
		if(searchKey == null || searchKey.trim().isEmpty()) {  //searchKey.equals("")
			Page<Product> page = productRepository.findAll(pageable);
		    return page.getContent();
		}
		else {
//		return (List<Product>) productRepository.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase
//				(searchKey, searchKey, pageable);
			   Page<Product> page = productRepository
			            .findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
			                searchKey, searchKey, pageable);
			        return page.getContent();
		}
	}
	
	public void deleteProductDetails(Long productId) {
		productRepository.deleteById(productId);
	}
	
	public Product getProductDetailsById(Long productId) {
	   return productRepository.findById(productId).get();
	}
	
	public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId) {
		if(isSingleProductCheckout && productId != 0) {
			//we are going to buy single product
			
			List<Product> list= new ArrayList<>();
			Product product = productRepository.findById(productId.longValue()).orElse(null);
			list.add(product);
			return list;
		}else {
			//we are checking out entire cart
			String username= JwtRequestFilter.CURRENT_USER;
			User user= userRepository.findByUserName(username).orElseThrow();
			Cart carts= cartRepository.findByUser(user).get();
			
			return carts.getProducts().stream()
			           .collect(Collectors.toList());	
		}
	}
	
}
