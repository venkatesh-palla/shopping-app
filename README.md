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

**UML  diagram**
https://www.plantuml.com/plantuml/png/x5ThKrev6Fxi-HMPqvcgNGlYfVKYpY8Nbrsg3B2VEfsTUF82QKDockHecOx_VPES0m6YjExOVjbYno7l_V8yYVrTQInrDc5HUHCHYaSIJz04D4O4crzE6oM23-cesjSoItGceUxEcSIQ2duQVOqGYbCfFaAYwmmhHOSqSRhu15cbKIl0CXc_4mICBBvEWNTpXtW7bSjeGR23QemLtrNkJ7d2CW9a9hKXufrSbs8zRdBo5qplX2HEzQf49ocR6L1LPnIuxXf3x-ZbLtW2o_81gD3QIKFRTpXDAH-5veKYz6XWZMj90ag5JbxtiDzy0zPrZJhqRdCDSeWJcDluzEkCmaXDhkMqAwYVQAWBfwebODsY7BCM1KPKpGo84wo5d1gp8MOAZE6zjOuD1jDn3vIMD5b6KSAm7Llo2O-W-SM1eaMP1bca2epQVCNerPALhN9z76En9eWXobYGDL6yIH36cqYOY5kucBRv62JLc2VWr8uPr0lBc7EX7R7G3LP0qD35CZ13L24YE1XpX942mrY2b8PqEKDj7k2X_FM5mVa24WQOPodIOq1kCmWFJT8d5XPc2g2LPBeWZPf1mckQF9iOgS7bWj1IKWNIdDQ1s7ChK3s0-mVTrwWlGPiODKxwxajObE3AUGjPEA_WVI3nBKY5cKVxKCWxB4a3KiUp_IBQZTbG87yMSZm7DvwN6cDA8025LmLBMJNeIdgBDRnpcUPRy_hvvkkOiPc-u1sX4jpzSuBiUm8Ju0N6NvyZEdD4MrGX4ue0tpQA5TCya-1isX-BkrgQik9bz3UbBzkcbK9en0K1IvpPkvT7oLJHXwkr9UIyyqR-Rjl1jNBCN9kCiOstSDv15qAF6xEkr0ww4dCC4j2OCkN1XqSZ2QFvNN4fHPOMnCtlXUAmWnPL9kQSxCfqx8INm47IP31DFN6yoT9kGaKNcyv0WxGu-RRLsYGzCDFYrYIqA3ZHWLjmOzXRq3NSS82uRkPJNwMnNPspiFDAnAH4O29Anj_1ru2uL1TSIy4Oo5A_47jHx8B05mrSKS7jaYtJyyfjIBVFpnbcT4Y1b1m5Izoq_S8J2Rr6YeuujlMTe3IxCNX3oc8nGNtjBkJSU2i_RKVtKVXN7t22DikH-H2QN_cZ0XOIyq-65zLuS14xWJA8vg2ggM1J-yq9esZJGbYXI6KtUG03PAL1_hE_-_RupM45VKNMSeFX6s1LEwWe__xHc5LPj36aZ2M22PFg1IJs9uusb9uog5gLuuGB4MIeLFOFzj_k7LTsbvCiB69DBd9aVnx95IwjidUmUtHulBl_FYhId9qDQXUT9hguxZMQlMfSYL6zsUdCZhlviLjhDDfNbzNu83_tkxMwELlzUNGsw9sV3HgercbVNbNZJhCrYD7vLdqxp7zM7ZJEpQ-UyVgZLtxCzuKTLE7S1tbB4uEE_h80lQzy-Cvmlo4YkdXgRxcjJ85ianDx8UuW8Sd22Kz4nlK9QWY3GzW-GJrGgU0AcbnJFNt9zEcrTPY9NuxqgQ_WqTn3qCcuBZBhisMYTgZIDbRBovyOSGJkUR-O_ksQgN5odJVoU7SFTbDK4dRr_NKuLrl8QdhVw7yQ6kMtmBMGqyM9jMV29mPB6Kx0LR4yeCzwMdnBt1fEYZldwQilEnATdw_mS6E2vOZowgwHZt4ALLE9oGTVz3ABM-uJHnzEesW6ynwaGb7RRSNS3mU7Ru-FAZ-LjRw0xwJPQuJGHtAJ361Y-jI6id_wa-7hRgdqmocxjAMdaVEPkUagSUGCoU7RM4UL_oaBwuA0PsAmqH_6EV-qj0rYAtk7bJULWyF3evz3kh22fxoL0MND00FPjr_CN_JxHR-VIxrxPFz5cq16e93vH6l-QBN_NrrFLIobk1i39bKkE9ZuQyrjlBNFRSxxEAHNPAWKc_2kulie-hSTEoG000O105RjOOu37Fr9WLk8cTbe6ssZRRIDjj4ssaRRQ1jjessqZRRHDjf6wx1pXJRQHkDdestqY_vJkG00
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


