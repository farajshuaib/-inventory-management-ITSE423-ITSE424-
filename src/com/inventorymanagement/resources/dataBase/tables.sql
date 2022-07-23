CREATE DATABASE IF NOT EXISTS `inventorymanagement` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `categories` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `category_name` varchar(32) NOT NULL,
                              `category_description` varchar(64) DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `category_name` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `products` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `product_name` varchar(32) NOT NULL,
                            `product_description` varchar(64) NOT NULL,
                            `product_price` double NOT NULL,
                            `number_in_stock` int NOT NULL,
                            `product_category` varchar(32) NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=509 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `records` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `product_name` varchar(32) NOT NULL,
                           `product_price` double NOT NULL,
                           ` product_description` varchar(64) NOT NULL,
                           `product_category` varchar(30) NOT NULL,
                           `action` varchar(32) NOT NULL,
                           `date` varchar(32) NOT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `users` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `first_name` varchar(32) NOT NULL,
                         `last_name` varchar(32) NOT NULL,
                         `email` varchar(64) NOT NULL,
                         `gender` varchar(10) NOT NULL,
                         `role` varchar(10) NOT NULL default 'employee',
                         `mobile_number` varchar(15) NOT NULL,
                         `password` varchar(1024) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `roles` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `name` varchar(32) NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
