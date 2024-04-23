package com.exampleUser.Order.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Order {
	 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    private Long userId;
    private Long productId;
    private int quantity;
    private String status;
    
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_modified_date")
	private LocalDateTime lastModifiedDate;
	@PrePersist
	protected void onCreate() {
		  lastModifiedDate=LocalDateTime.now();
	}
	
	
	
	
	public Order() {
		super();
	}
	public Order(Long id, Long userId, Long productId, int quantity, String status) {
		super();
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;
		this.status = status;
	}
	 public Order(LocalDateTime lastModifiedDate) {
			super();
			this.lastModifiedDate = lastModifiedDate;
		}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
   public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}
   public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	  
	  
      
	  
	
}




//@ManyToOne
//@JoinColumn(name="userId")
//@ManyToOne
//@JoinColumn(name="productId")
