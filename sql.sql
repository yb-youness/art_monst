CREATE DATABASE `POSTS`;
USE `POSTS`;

CREATE TABLE `post`(
    `Id`  INT  PRIMARY KEY AUTO_INCREMENT,
    `Title` VARCHAR(50),
    `Description` VARCHAR(100),
    `photo` mediumblob
);

CREATE TABLE `admin`(
  `Id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50),
  `email` VARCHAR(50),
  `pass`  VARCHAR(150)
);


--- Pass = admin = $2a$10$J0BgVhIU7Wus6CQOjqnjbOUJEo8XZSMK4fbeXC1vbLX7O0IBd2l0C 
INSERT INTO `admin` VALUES (null,'admin','admin@admin.com','$2a$10$J0BgVhIU7Wus6CQOjqnjbOUJEo8XZSMK4fbeXC1vbLX7O0IBd2l0C');