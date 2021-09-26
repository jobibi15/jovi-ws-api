package com.accenture.ws.impl;

import com.accenture.ws.entity.Order;

public class RegularBill extends OrderBill {

	public RegularBill(CafeClerk clerk) {
		super(clerk);
	}

	@Override
	public double getTotalBill() {
		if (this.getOrderList() == null || this.getOrderList().isEmpty()) {
			return 0d;
		}
		
		return this.getOrderList()
				.stream()
				.map(Order::getPrice)
				.reduce(0d, Double::sum);
	}

}
