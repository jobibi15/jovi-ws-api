package com.accenture.ws.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.ws.entity.Order;
import com.accenture.ws.impl.CafeClerk;
import com.accenture.ws.impl.DiscountedBill;
import com.accenture.ws.impl.OrderBill;
import com.accenture.ws.impl.RegularBill;
import com.accenture.ws.repository.OrderRepository;

@RestController
class OrderAndBillingController {

	@Autowired
    private OrderRepository orderRepo;
	private CafeClerk clerk;
	
    OrderAndBillingController() {
    	this.clerk = new CafeClerk();
    	clerk.setName("Jane Doe");
    }
    
    @GetMapping("/getClerk")
    ResponseEntity<CafeClerk> getClerk(){
        return ResponseEntity.ok(this.clerk);
    }

    @GetMapping("/getOrderList")
    ResponseEntity<List<Order>> getOrderList(){
        return ResponseEntity.ok(orderRepo.findAll());
    }

    @PostMapping("/addOrder")
    ResponseEntity<Void> addOrder(@RequestBody Order order){
    	orderValidation(order);
    	orderRepo.save(order);
    	return ResponseEntity.ok().build();
    }
    
    @PostMapping("/updateOrder")
    ResponseEntity<Void> updateOrder(@RequestBody Order order){
    	orderValidation(order);
    	if(!orderRepo.existsById(order.getId())){
    		throw new RuntimeException("Order does not exist");
    	}
    	orderRepo.save(order);
    	return ResponseEntity.ok().build();
    }

    @PostMapping("/deleteOrder")
    ResponseEntity<Void> deleteOrder(@RequestBody Order order){
    	orderRepo.deleteById(order.getId());
    	return ResponseEntity.ok().build();
    }

    @GetMapping("/getTotalRegularBill")
    ResponseEntity<OrderBill> getTotalRegularBill() {
    	RegularBill regularBill = new RegularBill(clerk);
    	regularBill.setOrderList(orderRepo.findAll());
    	return ResponseEntity.ok(regularBill);
    }

    @GetMapping("/getTotalDiscountedBill")
    ResponseEntity<OrderBill> getTotalDiscountedBill() {
    	DiscountedBill discountedBill = new DiscountedBill(clerk);
    	discountedBill.setOrderList(orderRepo.findAll());
    	return ResponseEntity.ok(discountedBill);
    }
    
    private void orderValidation(Order order) {
    	if (StringUtils.isBlank(order.getOrderName())) {
    		throw new RuntimeException("Order Name must not be empty!");
    	}
    	
    	if (order.getPrice() <= 0) {
    		throw new RuntimeException("Order Price must not be equal to or less than 0!");
    	}
    }
}
