package com.demo.shoppingapp.Controller;

import com.demo.shoppingapp.Model.FetchCouponResponse;
import com.demo.shoppingapp.Model.InventoryResponse;
import com.demo.shoppingapp.Model.OrderResponse;
import com.demo.shoppingapp.Repository.InvalidQuantityException;
import com.demo.shoppingapp.Service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/shopping/ecart")
public class ShoppingController {

    @Autowired
    ShoppingService shoppingService;


    /**
     * Endpoint to fetch inventory information
     */
    @GetMapping("/inventory")
    public List<InventoryResponse> inventory() {
        return shoppingService.inventory();
    }


    /**
     * Endpoint to fetch coupons
     *
     * @return Coupons table
     */

    @GetMapping("/fetchCoupons")
    public List<FetchCouponResponse> fetchCoupons() {
        return shoppingService.fetchCoupon();
    }


    /**
     * Endpoint to place an order
     *
     * @param userId
     * @param qty
     * @param coupon
     * @return` return the order response
     */
    @PostMapping("/order/{userId}/order")
    public ResponseEntity<OrderResponse> placeOrder(@PathVariable int userId,
                                                    @RequestParam int qty,
                                                    @RequestParam String coupon
    ) {
        //if Quantity is lessthan or Equals to zero
        try {
            if (qty <= 0) {
                throw new InvalidQuantityException("Quantity should not be lessthan zero");
            }
            return ResponseEntity.status(200).body(shoppingService.placeOrder(userId, qty, coupon));
        } catch (InvalidQuantityException e) {
            OrderResponse response = new OrderResponse();
            response.setDescription(e.getMessage());
            return ResponseEntity.status(404).body(response);

        }
    }

    /**
     * Endpoint to process payment for an order
     *
     * @param userId
     * @param orderId
     * @param amount
     * @return return the order response after the payment
     */
    @PostMapping("/payment/{userId}/{orderId}/pay")
    public ResponseEntity<OrderResponse> doPayment(@PathVariable int userId,
                                                   @PathVariable int orderId,
                                                   @RequestParam Double amount
    ) {
        OrderResponse orderResponse = shoppingService.doPayment(userId, orderId, amount);
        // Handling payment failure scenarios
        if (orderResponse.getStatus().equals("failure")) {
            Random random = new Random();
            int nextInt = random.nextInt(5);
            if (nextInt == 0) {
                orderResponse.setDescription("Payment Failed as amount is invalid");
                return ResponseEntity.status(400).body(orderResponse);
            } else if (nextInt == 1) {
                orderResponse.setDescription("Payment Failed from bank");
                return ResponseEntity.status(400).body(orderResponse);
            } else if (nextInt == 2) {
                orderResponse.setDescription("Payment Failed due to invalid order");
                return ResponseEntity.status(400).body(orderResponse);
            } else if (nextInt == 3) {
                orderResponse.setDescription("No resonse from payment server");
                return ResponseEntity.status(504).body(orderResponse);
            } else if (nextInt == 4) {
                orderResponse.setDescription("Order is already placed");
                return ResponseEntity.status(405).body(orderResponse);
            }
        }
        return ResponseEntity.ok(orderResponse);
    }

    /**
     * Endpoint to retrieve orders for a user
     *
     * @param userId
     * @return return the List of orders by userid
     */
    @GetMapping("/order/{userId}")
    public List<OrderResponse> getOrderByUserId(@PathVariable int userId) {
        return shoppingService.getOrderByUserId(userId);
    }


    /**
     * Endpoint to retrieve order details by user ID and order ID
     *
     * @param userId
     * @param orderId
     * @return return the List of orders by userid and orderid
     */
    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<List<OrderResponse>> getDataByUserIdAndOrderId(@PathVariable int userId, @PathVariable int orderId) {

        List<OrderResponse> orderResponseList = shoppingService.getDataByOrderIdAndUserId(userId, orderId);
        //return shoppingService.getDataByOrderIdAndUserId(userId,orderId);
        if (orderResponseList.isEmpty()) {
            OrderResponse orderResponse1 = new OrderResponse();
            orderResponse1.setOrderId(orderId);
            orderResponse1.setDescription("Order not found ");
            return ResponseEntity.status(400).body(List.of(orderResponse1));
        }
        return ResponseEntity.status(200).body(orderResponseList);
    }
}
