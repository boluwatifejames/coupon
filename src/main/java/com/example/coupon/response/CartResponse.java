package com.example.coupon.response;

import com.example.coupon.dto.OrdersDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


import java.util.List;

@Data
public class CartResponse {
    private List<OrdersDto> items;
    private double totalPrice;
    private double adjustedPrice;

    public CartResponse(List<OrdersDto> items, double totalPrice,double adjustedPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
        this.adjustedPrice = adjustedPrice;
    }
}
