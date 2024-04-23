package com.exampleUser.Order.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exampleUser.Order.Entities.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

	List<Order> findByUserId(Long userId);

}
