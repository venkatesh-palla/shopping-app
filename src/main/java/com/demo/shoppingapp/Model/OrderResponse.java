package com.demo.shoppingapp.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private int orderId;
    private Double amount;
    private Timestamp date;
    private String coupon;
    private String transactionId;
    private String status;
    private Integer quantity;
    private int userId;
    private String description;
}
