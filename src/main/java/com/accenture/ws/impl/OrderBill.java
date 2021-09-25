package com.accenture.ws.impl;

import java.util.List;

import com.accenture.ws.entity.Order;

public abstract class OrderBill {

	private List<Order> orderList;
	private CafeClerk clerk;

	public OrderBill(CafeClerk clerk) {
		this.clerk = clerk;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public CafeClerk getClerk() {
		return clerk;
	}

	public abstract double getTotalBill();

}