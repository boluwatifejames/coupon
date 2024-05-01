package com.example.coupon;

import com.example.coupon.dto.OrdersDto;
import com.example.coupon.entity.Orders;
import com.example.coupon.repository.OrdersRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class CouponApplication {

	private final OrdersRepo ordersRepo;

	public static void main(String[] args) {
		SpringApplication.run(CouponApplication.class, args);
	}


	@PostConstruct
	public void initializeCart() {
		manuallyAddToCart();
	}

	private void manuallyAddToCart() {
		List<Orders> ordersDtoList = new ArrayList<>();

		Orders ordersDto1 = new Orders();
		ordersDto1.setProductName("iPhone 11");
		ordersDto1.setProductPrice(100.00);
		ordersDto1.setProductDescription("Premium Quality iPhone");

		Orders ordersDto2 = new Orders();
		ordersDto2.setProductName("Samsung");
		ordersDto2.setProductPrice(10.00);
		ordersDto2.setProductDescription("Premium Quality Samsung");

		Orders ordersDto3 = new Orders();
		ordersDto3.setProductName("HP Pavilion");
		ordersDto3.setProductPrice(100.00);
		ordersDto3.setProductDescription("Premium Quality Laptop");

		ordersDtoList.add(ordersDto1);
		ordersDtoList.add(ordersDto2);
		ordersDtoList.add(ordersDto3);
        ordersRepo.saveAll(ordersDtoList);

	}

}
