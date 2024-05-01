package com.example.coupon.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column
    private String productName ;
    @Column
    private Double productPrice;
    @Column
    private String productDescription;
    @Column
    private String couponCode;
}
