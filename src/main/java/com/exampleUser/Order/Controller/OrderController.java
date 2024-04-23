package com.exampleUser.Order.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exampleUser.Order.Entities.Order;
import com.exampleUser.Order.Service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
   @Autowired
   private OrderService orderService;
   
   @PostMapping("/create")
   public ResponseEntity<Order> createOrder(@RequestBody Order order) {
       Order newOrder = orderService.createOrder(order);
       return ResponseEntity.ok(newOrder);
   }

   
   @PutMapping("/status")
   public ResponseEntity<Order> updateOrderStatus(@RequestParam Long orderId, @RequestBody Order order) {
       Order updateOrder = orderService.updateOrderStatus(orderId, order);
       return ResponseEntity.ok(updateOrder);
   }
   
   @GetMapping
   public ResponseEntity<List<Order>> getAllOrders() {
       List<Order> orders = orderService.getAllOrders();
       return ResponseEntity.ok(orders);
   }
   
   @GetMapping("/{id}")
   public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
       Order order = orderService.getOrderById(id);
       return ResponseEntity.ok(order);
   }

   
   @GetMapping("/user/{userId}")
   public ResponseEntity<List<Order>> getOrderByUserId(@PathVariable("userId") Long userId) {
       List<Order> orders = orderService.getOrderByUserId(userId);
       return ResponseEntity.ok(orders);
   }
//   @GetMapping("user")
//   public ResponseEntity<List<Order>> getOrderByUserId(@RequestParam("userId") Long userId){
//	   List<Order> orders=orderService.getOrderByUserId(userId);
//	   return ResponseEntity.ok(orders);
//   }
}
