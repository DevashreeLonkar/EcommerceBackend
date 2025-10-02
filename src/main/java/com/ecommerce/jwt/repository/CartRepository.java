package com.ecommerce.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.jwt.entity.Cart;
import com.ecommerce.jwt.entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

	Optional<Cart> findByUser(User user);

}
