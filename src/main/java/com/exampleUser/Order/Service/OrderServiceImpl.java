package com.exampleUser.Order.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.exampleUser.Order.Entities.Order;
import com.exampleUser.Order.Entities.Product;
import com.exampleUser.Order.Exception.OrderNotFoundException;
import com.exampleUser.Order.Exception.OrderUpdateException;
import com.exampleUser.Order.Exception.ProductNotAvailableException;
import com.exampleUser.Order.External.Services.ProductService;
import com.exampleUser.Order.Repository.OrderRepo;

import feign.FeignException;


@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private ProductService productService;

	@Override
	public Order createOrder(Order order) {
		ResponseEntity<Product> response;
		try {
            response = productService.getProductById(order.getProductId());

		
			if (response == null || response.getBody() == null
					|| response.getBody().getAvailability() < order.getQuantity()) {
				throw new ProductNotAvailableException("Product is not available or quantity exceeds availability.");
			}

			Product product = response.getBody();
			product.setAvailability(product.getAvailability() - order.getQuantity());
			productService.updateProduct(order.getProductId(), product);
			return orderRepo.save(order);

		} catch (FeignException e) {
			throw new ProductNotAvailableException("Product Not Found with this Id: " + order.getProductId());
		}
	}

	@Override
	public Order updateOrderStatus(Long id, Order order) {
		Order orderStatus;
		try {
			orderStatus = orderRepo.findById(id)
					.orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
			if (order != null) {
				orderStatus.setStatus(order.getStatus());
				order.setLastModifiedDate(LocalDateTime.now());

				if ("cancelled".equalsIgnoreCase(order.getStatus())) {
					ResponseEntity<Product> response = productService.getProductById(order.getProductId());
					if (response != null && response.getBody() != null) {
						Product product = response.getBody();
						product.setAvailability(product.getAvailability() + order.getQuantity());
						productService.updateProduct(order.getProductId(), product);
					} else {
						throw new ProductNotAvailableException("Product not found with id: " + order.getProductId());
					}
				}
				return orderRepo.save(orderStatus);
			} else {
				throw new OrderUpdateException("Failed to update order.");
			}
		} catch (FeignException e) {
			throw new OrderUpdateException("Error updating order status: " + e.getMessage());
		}
	}

	@Override
	public List<Order> getAllOrders() {
		try {
			return orderRepo.findAll();
		} catch (Exception e) {
			throw new OrderNotFoundException("Failed to retrieve orders: " + e.getMessage());
		}
	}

	@Override
	public Order getOrderById(Long id) {
		try {
			return orderRepo.findById(id)
					.orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
		} catch (Exception e) {
			throw new OrderNotFoundException("No order exist with Orderid: " + id + ". Error: " + e.getMessage());
		}
	}

	@Override
	public List<Order> getOrderByUserId(Long userId) {
		try {
			List<Order> orders = orderRepo.findByUserId(userId);
			if (orders.isEmpty()) {
				throw new OrderNotFoundException("No orders found for user with id: " + userId);
			}
			return orders;
		} catch (Exception e) {
			throw new OrderNotFoundException(
					"Failed to retrieve orders for user with id: " + userId + ". Error: " + e.getMessage());
		}
	}

}