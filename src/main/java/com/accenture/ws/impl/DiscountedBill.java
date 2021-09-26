package com.accenture.ws.impl;

public class DiscountedBill extends OrderBill {
	public DiscountedBill(CafeClerk clerk) {
		super(clerk);
	}

	@Override	
	public double getTotalBill() {
		if (this.getOrderList() == null || this.getOrderList().isEmpty()) {
			return 0d;
		}
		
		return this.getOrderList()
				.stream()
				.map(order -> {
					if (order.isDiscounted()) {
						return ((100 - order.getIsDiscountPercentage()) / 100) * order.getPrice();
					} else {
						return order.getPrice();
					}
				})
				.reduce(0d, Double::sum);
	}
}
