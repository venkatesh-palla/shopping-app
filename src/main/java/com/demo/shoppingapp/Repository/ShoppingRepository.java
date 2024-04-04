package com.demo.shoppingapp.Repository;

import com.demo.shoppingapp.Model.Coupons;
import com.demo.shoppingapp.Model.Orders;
import com.demo.shoppingapp.Model.Products;
import com.demo.shoppingapp.Model.FetchCouponResponse;
import com.demo.shoppingapp.Model.InventoryResponse;
import com.demo.shoppingapp.Model.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Random;


@Repository
public class ShoppingRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShoppingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /*
    Retrieving  inventory details
     */
    public List<InventoryResponse> inventory() {
        return jdbcTemplate.query("SELECT  sum(orders_quantity) as ordered,\n" +
                        "max(price) as price, sum(inventory) as available  FROM\n" +
                        " shopping.orders \n" +
                        " INNER JOIN  shopping.products ON orders.product_id = products.product_id\n" +
                        " INNER JOIN  shopping.transactions ON orders.order_id = transactions.order_id\n" +
                        " where transactions.payment_status ='sucessfull' group by orders.product_id \n",
                new BeanPropertyRowMapper<>(InventoryResponse.class));
    }


    /*
     retrevie the Coupons
     */
    public List<FetchCouponResponse> fetchCoupon() {
        return jdbcTemplate.query("SELECT coupon_code,discount FROM coupons",
                new BeanPropertyRowMapper<>(FetchCouponResponse.class));

    }


    /**
     * Placing order
     * 1. Get Product from product
     * 2. Insert into orders table
     *
     * @param userId
     * @param qty        quntitty of the order
     * @param couponCode Coupon code for the discount
     * @return return the Order response
     * @throws InvalidQuantityException
     */
    public OrderResponse placeOrder(int userId, int qty, String couponCode) throws InvalidQuantityException {

        //1. Get Product from product
        Products p = jdbcTemplate.queryForObject("select * from products " +
                "order by product_id limit 1", new BeanPropertyRowMapper<>(Products.class));

        assert p != null;
        //if available quantity is greaterthan the ordered quntity
        if (p.getInventory() < qty) {
            throw new InvalidQuantityException("Invalid Quantity/ Quantity is morethan than the maximum quantity of product available");
        }
        Coupons coupon = jdbcTemplate.queryForObject("select * from coupons where " +
                "coupon_code= '" + couponCode + "'", new BeanPropertyRowMapper<>(Coupons.class));

        Random random = new Random();
        int orderId = random.nextInt(10000);
        Double price = p.getPrice();
        Double discount = coupon.getDiscount();
        Double amount = price * (1 - discount / 100);
        Timestamp timestamp = Timestamp.from(Instant.now());
        //Insert into orders table
        jdbcTemplate.update("INSERT INTO orders (order_id,product_id,coupon_id,orders_quantity," +
                        "ordered_at,ordered_date,user_id, amount) " +
                        "values(?,?,?,?,?,?,?,?) ", orderId, p.getProductId(), coupon.getCouponId(),
                qty, timestamp, timestamp, userId, amount);
        return new OrderResponse(orderId, amount, timestamp, couponCode, null, null, qty, userId, null);
    }


    /**
     * Payment process
     * 1. get order by order id
     * 2. generate transactionid
     * 3. stutus = success if trancation id is even number else failure
     * 4. insert record into transaction table
     */
    public OrderResponse doPayment(int userId, int orderId, Double amount) {
        // get order by order id
        Orders p = jdbcTemplate.queryForObject("select * from orders " +
                        "where order_id= '" + orderId + "'",
                new BeanPropertyRowMapper<>(Orders.class));
        // generate transactionid
        Random random = new Random();
        int nextInt = random.nextInt(10000);
        String transactionId = String.format("Trans-%s", nextInt);
        String status = "success";
        if (nextInt % 2 == 1) {
            status = "failure";
        }
        Timestamp timestamp = Timestamp.from(Instant.now());
        //insert record into transaction table
        jdbcTemplate.update("INSERT INTO transactions (transaction_id,order_id,amount, payment_status, " +
                        "payment_time) " +
                        "values(?,?,?,?,?) ", transactionId, orderId, amount,
                status, timestamp);
        if (status.equals("success")) {
            jdbcTemplate.update("UPDATE products SET  inventory = inventory-?" +
                    " where product_id = ?", p.getOrdersQuantity(), p.getProductId());
        }

        OrderResponse orderResponse = new OrderResponse(p.getOrderId(), amount, timestamp,
                null, transactionId, status, p.getOrdersQuantity(), userId, null
        );
        return orderResponse;

    }

    /**
     * Retrieving order details for a specific userID
     *
     * @param userId
     * @return return the List of orders by userid
     */
    public List<OrderResponse> getOrderByUserId(int userId) {
        return jdbcTemplate.query("SELECT orders.order_id,transactions.amount,orders.ordered_date as date,coupons.coupon_code as coupon " +
                        " FROM transactions INNER JOIN orders" +
                        " ON transactions.order_id = orders.order_id " +
                        "INNER JOIN users ON users.user_id = orders.user_id " +
                        "INNER JOIN coupons ON coupons.coupon_id = orders.coupon_id " +
                        "where users.user_id = ?  ",
                new Object[]{userId}, new BeanPropertyRowMapper<>(OrderResponse.class)
        );
    }


    /**
     * Retrieving order details for a specific userID
     *
     * @param userId
     * @param orderId
     * @return return the List of orders by userid and orderid
     */
    public List<OrderResponse> getDataByOrderIdAndUserId(int userId, int orderId) {
        return jdbcTemplate.query("SELECT orders.order_id,transactions.amount," +
                        "orders.ordered_date as date,transactions.transaction_id," +
                        "transactions.payment_status as status," +
                        "coupons.coupon_code\n" +
                        " FROM transactions \n" +
                        " INNER JOIN orders ON transactions.order_id = orders.order_id \n" +
                        " INNER JOIN users ON users.user_id = orders.user_id\n" +
                        " INNER JOIN coupons ON coupons.coupon_id = orders.coupon_id\n" +
                        "  where users.user_id = ? and orders.order_id = ?",
                new Object[]{userId, orderId},
                new BeanPropertyRowMapper<>(OrderResponse.class)
        );
    }


}
