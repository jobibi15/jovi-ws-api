package com.accenture.ws.controller;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.ws.entity.Order;
import com.accenture.ws.repository.OrderRepository;


@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class OrderAndBillingControllerTests {
	private static Order ORDER_HOLDER = new Order();
	
	@Autowired
	OrderAndBillingController controller;

	@Autowired
	OrderRepository orderRepo;

	@Test
	@org.junit.jupiter.api.Order(1)
	public void getOrderListTest() {
		Assertions.assertDoesNotThrow(()->{
			ResponseEntity<List<Order>> orderList = controller.getOrderList();
			Assertions.assertNotNull(orderList.getBody(), "getOrderList return null value");
		}, "getOrderList has thrown an error");
	}
	
	@Test
	@org.junit.jupiter.api.Order(2)
	@Transactional
	@Rollback(false)
	public void addOrderTest() {
		ORDER_HOLDER.setOrderName("Espresso");
		ORDER_HOLDER.setPrice(12);

		Assertions.assertDoesNotThrow(()->{
			controller.addOrder(ORDER_HOLDER);
		});
	}
	
	@Test
	@org.junit.jupiter.api.Order(3)
	@Transactional
	@Rollback(false)
	public void udpateOrderTest() {
		String updateName = "Something Test";
		Order order = new Order();
		order.setOrderName(updateName);
		order.setId(ORDER_HOLDER.getId());
		order.setPrice(ORDER_HOLDER.getPrice());
		order.setIsDiscountPercentage(ORDER_HOLDER.getIsDiscountPercentage());
		
		Assertions.assertDoesNotThrow(() -> {
			controller.updateOrder(order);
		}, "update order has trown an error");
		
		Order updateOrder = orderRepo.getById(order.getId());
		Assertions.assertEquals(updateName, updateOrder.getOrderName(), "field was not updated in DB");
	}
	
	@Test
	@org.junit.jupiter.api.Order(4)
	@Transactional
	@Rollback(false)
	public void deleteOrderTest() {
		Assertions.assertDoesNotThrow(()->{
			controller.deleteOrder(ORDER_HOLDER);
		}, "Error thrown while deleting order");
		
		Assertions.assertTrue(!orderRepo.existsById(ORDER_HOLDER.getId()), "Order still exists in db.");
	}
	
	@AfterTestClass
	public void cleanUp() {
		orderRepo.deleteById(ORDER_HOLDER.getId());
	}
	
}
