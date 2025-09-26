package com.ecommerce.jwt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.jwt.entity.Product;
import com.ecommerce.jwt.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public Product addNewProduct(Product product) {
		return productRepository.save(product);
	}
	
	public List<Product> getAllProducts(int pageNumber, String searchKey){
		Pageable pageable= PageRequest.of(pageNumber, 12);
		//return (List<Product>) productRepository.findAll(pageable);
		
		if(searchKey.equals("")) {
			Page<Product> page = productRepository.findAll(pageable);
		    return page.getContent();
		}
		else {
		return (List<Product>) productRepository.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase
				(searchKey, searchKey, pageable);
		}
	}
	
	public void deleteProductDetails(Long productId) {
		productRepository.deleteById(productId);
	}
	
	public Product getProductDetailsById(Long productId) {
	   return productRepository.findById(productId).get();
	}
	
	public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId) {
		if(isSingleProductCheckout) {
			
			List<Product> list= new ArrayList<>();
			Product product = productRepository.findById(productId.longValue()).orElse(null);
			list.add(product);
			return list;
		}else {
			
		}
		return new ArrayList<>();
	}
}
