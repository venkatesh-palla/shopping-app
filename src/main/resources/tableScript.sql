Create database shopping;

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
);



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
);



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
);



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
);



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

