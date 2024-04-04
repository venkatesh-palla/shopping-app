package com.demo.shoppingapp.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupons {

    private int couponId;
    private String couponCode;
    private Double discount;
    private String validOrNot;


}
