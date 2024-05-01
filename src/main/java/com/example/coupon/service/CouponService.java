package com.example.coupon.service;

import com.example.coupon.data.CartSummary;
import com.example.coupon.response.CouponResponse;
import com.example.coupon.data.CouponRule;
import com.example.coupon.data.DiscountType;
import com.example.coupon.dto.OrdersDto;
import com.example.coupon.entity.Coupon;
import com.example.coupon.entity.Orders;
import com.example.coupon.repository.OrdersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final OrdersRepo ordersRepo;


    public CartSummary getCart() {
        List<Orders> orders = ordersRepo.findAll();
        double totalPrice = orders.stream()
                .mapToDouble(Orders::getProductPrice)
                .sum();
        int totalItems = orders.size();
        List<String> items = orders.stream()
                .map(Orders::getProductName)
                .collect(Collectors.toList());
        return new CartSummary(items, totalPrice, totalItems);
    }

    public CouponResponse applyCoupon(String couponCode, CartSummary cartSummary) {
        // Retrieve Coupon Details
        Coupon coupon = getCouponDetails(couponCode);

        // Is coupon is valid ?
        if (coupon == null) {
            return new CouponResponse("Invalid coupon code");
        }

        // Cross-check coupon rules
        if (!validateCouponRules(coupon, cartSummary)) {
            return new CouponResponse("Coupon rules not met");
        }

        // Set coupon details
        double adjustedTotalPrice = calculateAdjustedTotalPrice(coupon, cartSummary.getTotalPrice());
        double totalPrice = cartSummary.getTotalPrice();
        double discountAmount = calculateDiscount(coupon, totalPrice);
        // Prepare response
        return new CouponResponse(totalPrice ,discountAmount, adjustedTotalPrice);
    }


    private Coupon getCouponDetails(String couponCode) {
        return getCouponMap().getOrDefault(couponCode, null);
    }
    private Map<String, Coupon> getCouponMap() {
        Map<String, Coupon> couponMap = new HashMap<>();
        couponMap.put("FIXED10", new Coupon("FIXED10", Arrays.asList(CouponRule.MIN_TOTAL_PRICE, CouponRule.MIN_ITEM_COUNT), DiscountType.FIXED_AMOUNT_OFF, 10.0));
        couponMap.put("PERCENT10", new Coupon("PERCENT10", Arrays.asList(CouponRule.MIN_TOTAL_PRICE, CouponRule.MIN_ITEM_COUNT), DiscountType.PERCENT_OFF, 0.1));
        couponMap.put("MIXED10",new Coupon("MIXED10",Arrays.asList(CouponRule.MIN_TOTAL_PRICE,CouponRule.MIN_ITEM_COUNT),DiscountType.PERCENT_OFF_OR_FIXED_AMOUNT_OFF,10.00));
        return couponMap;
    }


    private boolean validateCouponRules(Coupon coupon, CartSummary cartSummary) {
        switch (coupon.getCode()) {
            case "FIXED10":
                return cartSummary.getTotalItems() > 1 && cartSummary.getTotalPrice() >= 50 && cartSummary.getTotalPrice()<100;
            case "PERCENT10":
                return cartSummary.getTotalItems() >= 2 && cartSummary.getTotalPrice() >= 100;
            case "MIXED10":
                return cartSummary.getTotalItems() >= 3 && cartSummary.getTotalPrice() >= 200;
            case "REJECTED10":
                return cartSummary.getTotalPrice() >= 1000;
            default:
                return false;
        }
    }
    private double calculateDiscount(Coupon coupon, double totalPrice) {
        switch (coupon.getDiscountType()) {
            case FIXED_AMOUNT_OFF:
                return Math.min(coupon.getDiscountValue(), totalPrice);
            case PERCENT_OFF:
                return totalPrice * coupon.getDiscountValue();
            case PERCENT_OFF_OR_FIXED_AMOUNT_OFF:
                double percentDiscount = totalPrice * 0.1; //  10% of the total price
                double fixedDiscount = coupon.getDiscountValue(); // Subtract $10 from the total price
                // check greater discount between percent and fixed
                return Math.max(percentDiscount, fixedDiscount);
            default:
                return 0.0;
        }
    }

    private double calculateAdjustedTotalPrice(Coupon coupon, double totalPrice) {
        double discountAmount = calculateDiscount(coupon, totalPrice);
        return totalPrice - discountAmount;
    }

//    public void addToCart(List<OrdersDto> ordersDtoList) {
//        for (OrdersDto ordersDto : ordersDtoList) {
//            Orders orders = new Orders();
//            orders.setProductName(ordersDto.getProductName());
//            orders.setProductPrice(ordersDto.getProductPrice());
//            orders.setProductDescription(ordersDto.getProductDescription());
//            ordersRepo.save(orders);
//        }
//    }

}
