package com.accenture.ws.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

@Entity(name = "order_table")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	@NotNull
	private Long id;

	@Column(name = "order_name")
	@NotNull
	private String orderName;
	
	@Column(name = "price")
	@NotNull
	private double price;
	
	@Column(name = "is_discounted")
	@NotNull
	private boolean isDiscounted;
	
	@Column(name = "is_discount_percentage", columnDefinition = "double default 5.0")
	private double isDiscountPercentage = 5.0;

	public Long getId() {
		return id;
	}

	public void setId(Long i) {
		this.id = i;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isDiscounted() {
		return isDiscounted;
	}

	public void setDiscounted(boolean isDiscounted) {
		this.isDiscounted = isDiscounted;
	}

	public double getIsDiscountPercentage() {
		return isDiscountPercentage;
	}

	public void setIsDiscountPercentage(double isDiscountPercentage) {
		this.isDiscountPercentage = isDiscountPercentage;
	}

	@Override
	public String toString() {
		return this.getId()+" "+this.getOrderName()+" "+this.getPrice()+" "+this.getIsDiscountPercentage();
	}
	
	
}
