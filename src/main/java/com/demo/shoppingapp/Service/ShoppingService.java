package com.demo.shoppingapp.Service;

import com.demo.shoppingapp.Model.FetchCouponResponse;
import com.demo.shoppingapp.Model.InventoryResponse;
import com.demo.shoppingapp.Model.OrderResponse;
import com.demo.shoppingapp.Repository.InvalidQuantityException;

import java.util.List;

public interface ShoppingService {

    List<InventoryResponse> inventory();

    List<FetchCouponResponse> fetchCoupon();

    OrderResponse placeOrder(int userId, int qty, String coupon) throws InvalidQuantityException;

    OrderResponse doPayment(int userId, int orderId, Double amount);

    List<OrderResponse> getDataByOrderIdAndUserId(int userId, int orderId);

    List<OrderResponse> getOrderByUserId(int userId);


}
