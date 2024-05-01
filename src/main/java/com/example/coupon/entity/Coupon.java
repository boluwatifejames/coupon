package com.example.coupon.entity;

import com.example.coupon.data.CouponRule;
import com.example.coupon.data.DiscountType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private List<CouponRule> rules;
    private DiscountType discountType;
    private double discountValue;
    public Coupon() {}

    public Coupon(String code, List<CouponRule> rules, DiscountType discountType, double discountValue) {
        this.code = code;
        this.rules = rules;
        this.discountType = discountType;
        this.discountValue = discountValue;
    }
}
