package com.demo.shoppingapp.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {


    private int orderId;
    private int productId;
    private int couponId;
    private  Integer ordersQuantity;
}
