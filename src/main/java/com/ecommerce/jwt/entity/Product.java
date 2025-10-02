package com.ecommerce.jwt.entity;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Product {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
	private Long productId;
	private String productName;
	@Column(length = 2000)
	private String productDescription;
	private Double productDiscountedPrice;
	private Double productActualPrice;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "productImage_key")
//	private ImageModel productImage;
	
//	@JoinTable(name = "product_images",
//			joinColumns = {
//					@JoinColumn(name="product_id")
//			},
//			inverseJoinColumns = {
//					@JoinColumn(name="image_id")
//			}
//			)
//	
//	private Set<ImageModel> productImage;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")  
    private Set<ImageModel> productImageSet;  
	
	// ✅ equals & hashCode based only on productId
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return productId != null && productId.equals(product.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
	
}
