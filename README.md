**Shopping App**

      Design and develop a shopping web app using Java and Spring Framework in which users can perform the following actions
1. Enter the quantity of products the user wants to order
2. Check if the product is available in the desired quantity
3. Check available coupons and apply one, if valid. A coupon can only be applied once per user
4. Make payment for the order
5. View the status of all orders

For the sake of simplicity, you can assume the app has only one type of product in a fixed quantity that can be fetched from a config file during startup.
For every user action, the response should be a JSON.

# How to run project in your local system

Follow the below steps to run the project in local mechine

1. Clone the git hub repository https://github.com/venkatesh-palla/shopping-app.git
2. Create Database and run script under https://github.com/venkatesh-palla/shopping-app/blob/master/src/main/resources/tableScript.sql. This will generate the Required tables
3. Run class https://github.com/venkatesh-palla/shopping-app/blob/master/src/main/java/com/demo/shoppingapp/ShoppingAppApplication.java
4. Start Executing the API's Mentioned in below.

 # Database Design and script 

```
                        ---->Users Table<----
CREATE TABLE `users` (
  `user_id` int NOT NULL,
  `user_name` varchar(245) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone_number` varchar(300) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
)



                            ----->Products Table<----
CREATE TABLE `products` (
  `product_id` int NOT NULL,
  `product_name` varchar(245) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `product_image` varchar(255) DEFAULT NULL,
  `inventory` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_id`)
)



                            ----->Transactions Table<-----
CREATE TABLE `transactions` (
  `transaction_id` varchar(275) NOT NULL,
  `amount` double DEFAULT NULL,
  `order_id` int DEFAULT NULL,
  `payment_status` varchar(195) DEFAULT NULL,
  `payment_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`transaction_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
)



                            ----->Orders table<-----
CREATE TABLE `orders` (
  `order_id` int NOT NULL,
  `product_id` int DEFAULT NULL,
  `coupon_id` int DEFAULT NULL,
  `orders_quantity` int DEFAULT NULL,
  `ordered_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `ordered_date` date DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `amount` double(16,8) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `product_id` (`product_id`),
  KEY `coupon_id` (`coupon_id`),
  KEY `fk_user_orders` (`user_id`),
  CONSTRAINT `fk_user_orders` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`coupon_id`)
)



                            ----->Coupons table <------
CREATE TABLE `coupons` (
  `coupon_id` int NOT NULL,
  `coupon_code` varchar(275) DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `valid_or_not` varchar(175) DEFAULT NULL,
  `expire_date` date DEFAULT NULL,
  PRIMARY KEY (`coupon_id`),
  UNIQUE KEY `coupon_code` (`coupon_code`)
);
```

# Test results and API Responses:
https://docs.google.com/document/d/1xG0sABigjRNuFlEyTeCrWnjpC5yOMoIrhPF5UEMZ9IU/edit?usp=sharing

 API Definations:
 Inventory:
 ```
curl --location 'http://localhost:8080/shopping/ecart/inventory' \
--data ''
```
Get Coupons:
```
curl --location 'http://localhost:8080/shopping/ecart/fetchCoupons' \
--data ''
```
Post Order:
```
curl --location --request POST 'http://localhost:8080/shopping/ecart/order/11/order?qty=10&coupon=OFF5' \
--data ''
```
Payment:
```
curl --location --request POST 'http://localhost:8080/shopping/ecart/payment/11/7214/pay?amount=66.5' \
--data ''
```
Get Order by Order id:
```
curl --location 'http://localhost:8080/shopping/ecart/order/7214' \
--data ''
```
Get Order by User id AND Order Id:
```
curl --location 'http://localhost:8080/shopping/ecart/11/orders/1' \
--data ''
```


