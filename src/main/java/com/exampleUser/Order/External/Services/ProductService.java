package com.exampleUser.Order.External.Services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.exampleUser.Order.Entities.Product;

@FeignClient(name = "USER-PRODUCT")
public interface ProductService {

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProduct();

	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product);

	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id);
}
