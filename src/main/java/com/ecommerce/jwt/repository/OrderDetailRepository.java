package com.ecommerce.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.jwt.entity.OrderDetail;
import com.ecommerce.jwt.entity.User;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{

	public List<OrderDetail> findByUser(User user);
}
