package com.demo.shoppingapp.Service;

import com.demo.shoppingapp.Model.FetchCouponResponse;
import com.demo.shoppingapp.Model.InventoryResponse;
import com.demo.shoppingapp.Model.OrderResponse;
import com.demo.shoppingapp.Repository.InvalidQuantityException;
import com.demo.shoppingapp.Repository.ShoppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingServiceImpl implements ShoppingService {

    @Autowired
    ShoppingRepository shoppingRepository;


    /**
     * retrieve inventory
     */
    @Override
    public List<InventoryResponse> inventory() {
        return shoppingRepository.inventory();
    }


    /**
     * Retrieve coupons
     */
    @Override
    public List<FetchCouponResponse> fetchCoupon() {
        return shoppingRepository.fetchCoupon();
    }


    /**
     * Place an order with specified details
     */
    @Override
    public OrderResponse placeOrder(int userId, int qty, String coupon) throws InvalidQuantityException {
        return shoppingRepository.placeOrder(userId, qty, coupon);
    }

    //

    /**
     * Payment process  for an order
     */
    @Override
    public OrderResponse doPayment(int userId, int orderId, Double amount) {
        return shoppingRepository.doPayment(userId, orderId, amount);
    }


    /**
     * retrrive data by specific userId and orderId
     */
    @Override
    public List<OrderResponse> getDataByOrderIdAndUserId(int userId, int orderId) {
        return shoppingRepository.getDataByOrderIdAndUserId(userId, orderId);
    }


    /**
     * retrieve Data   by userId
     */
    @Override
    public List<OrderResponse> getOrderByUserId(int userId) {
        return shoppingRepository.getOrderByUserId(userId);
    }

}
