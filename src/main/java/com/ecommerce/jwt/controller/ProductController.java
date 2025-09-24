package com.ecommerce.jwt.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.jwt.entity.ImageModel;
import com.ecommerce.jwt.entity.Product;
import com.ecommerce.jwt.repository.ImageModelRepository;
import com.ecommerce.jwt.repository.ProductRepository;
import com.ecommerce.jwt.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ImageModelRepository imageModelRepository;
	
	@PreAuthorize("hasRole('Admin')")
	@PostMapping(value = {"/addNewProduct"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Product addNewProduct(@RequestPart("product") Product product,
								@RequestPart("imageFile") MultipartFile[] file) {
//		return productService.addNewProduct(product);
//		ImageModel im = new ImageModel();
//		im.setType()
		
		//imageModelRepository.save(im);
		System.out.println("line 37");
		try {
			product.setProductId(null);
			Set<ImageModel> images= uploadImage(file);
			
			//product.setProductImage(images);
			product.setProductImageSet(images); 
			return productService.addNewProduct(product);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException{
		Set<ImageModel> imageModels= new HashSet<>();
		
		for(MultipartFile file:multipartFiles) {
			ImageModel imageModel= new ImageModel(
					null, file.getOriginalFilename(),
					file.getContentType(),
					file.getBytes()
					);
			imageModels.add(imageModel);
		}
		return imageModels;
	}
	

	@GetMapping({"/getAllProducts"})
	public List<Product> getAllProducts(){
		return productService.getAllProducts();
	}
	
	@PreAuthorize("hasRole('Admin')")
	@DeleteMapping({"/deleteProductDetails/{productId}"})
	public void deleteProductDetails(@PathVariable("productId") Long productId) {
		productService.deleteProductDetails(productId);
	}

	@GetMapping({"/getProductDetails/{productId}"})
	public Product getProductDetails(@PathVariable("productId") Long productId) {
		return productService.getProductDetailsById(productId);
	}
	
	@GetMapping({"/getProductDetails/{isSingleProductCheckout}/{productId}"})
	public List<Product> getProductDetails(@PathVariable(name= "isSingleProductCheckout") boolean isSingleProductCheckout, 
			@PathVariable(name= "productId") Integer productId) {
		
	return productService.getProductDetails(isSingleProductCheckout, productId);
	}

	
}
