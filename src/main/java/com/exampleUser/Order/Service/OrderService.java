package com.exampleUser.Order.Service;

import java.util.List;

import com.exampleUser.Order.Entities.Order;

public interface OrderService {
	public Order createOrder(Order order);

   public Order updateOrderStatus(Long id,Order order);
	public List<Order> getAllOrders();

	public Order getOrderById(Long id);

	public List<Order> getOrderByUserId(Long userId);
}
