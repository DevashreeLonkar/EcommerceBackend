package com.ecommerce.jwt.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_seq")
    @SequenceGenerator(name = "cart_seq", sequenceName = "cart_seq", allocationSize = 1)
	private Long cartId;
	
//	@OneToOne
//	private Product product;
//	
//	@OneToOne
//	private User user;
	
	   @ManyToOne
	    private User user;

	    @ManyToMany
	    @JoinTable(
	        name = "cart_products",
	        joinColumns = @JoinColumn(name = "cart_id"),
	        inverseJoinColumns = @JoinColumn(name = "product_id")
	    )
	    private Set<Product> products = new HashSet<>();

}
