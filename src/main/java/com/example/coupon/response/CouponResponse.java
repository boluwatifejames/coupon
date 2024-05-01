package com.example.coupon.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CouponResponse {
    private double totalAmount;
    private double adjustedTotalPrice;
    private double discountAmount;
    private String errorMessage;

    public CouponResponse(double totalAmount, double discountAmount, double adjustedTotalPrice ) {
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.adjustedTotalPrice = adjustedTotalPrice;
    }
    public CouponResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public boolean isSuccess() {
        return errorMessage == null || errorMessage.isEmpty();
    }
}
