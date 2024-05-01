package com.example.coupon.controller;

import com.example.coupon.data.CartSummary;
import com.example.coupon.response.CouponResponse;
import com.example.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CouponController {
  private final CouponService couponService;



//    @PostMapping("addToCart")
//    public void addToCart(@RequestBody List<OrdersDto> ordersDtoList){
//        couponService.addToCart(ordersDtoList);
//    }

    @PostMapping("/coupon")
    public ResponseEntity<CouponResponse> applyCoupon(@RequestParam String couponCode) {
        // Retrieve the cart summary to get total price and total items
        CartSummary cartSummary = couponService.getCart();

        // Apply coupon based on the provided coupon code
        CouponResponse couponResponse = couponService.applyCoupon(couponCode, cartSummary);
        // Check if the coupon was successfully applied
        if (couponResponse.isSuccess()) {
            return ResponseEntity.ok(new CouponResponse(couponResponse.getTotalAmount(),couponResponse.getDiscountAmount(),couponResponse.getAdjustedTotalPrice()));
        } else {
            CouponResponse errorResponse = new CouponResponse(couponResponse.getErrorMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }


    @GetMapping("cart")
    public ResponseEntity<CartSummary> getCart() {
         return  ResponseEntity.ok(couponService.getCart());
    }
}











