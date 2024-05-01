package com.example.coupon.data;

import lombok.Data;

import java.util.List;

@Data
public class CartSummary {
    private List<String> items;
    private double totalPrice;
    private int  totalItems;


    public CartSummary( List<String> items,double totalPrice,int totalItems) {
        this.items = items;
        this.totalPrice = totalPrice;
        this.totalItems = totalItems;
    }
}

