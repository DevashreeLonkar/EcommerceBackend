package com.ecommerce.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.jwt.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

}
