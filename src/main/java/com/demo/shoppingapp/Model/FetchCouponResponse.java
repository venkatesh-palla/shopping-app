package com.demo.shoppingapp.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchCouponResponse {
    private String couponCode;
    private Double discount;
}
