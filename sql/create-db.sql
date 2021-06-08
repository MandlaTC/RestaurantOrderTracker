CREATE DATABASE restaurant_order_tracker;

CONNECT restaurant_order_tracker;

CREATE TABLE USERS (
    userID VARCHAR(36) NOT NULL PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR (255),
    userType VARCHAR (36),
    userPass VARCHAR (255)
);

/* alter table
 users
 ADD
 column userType varchar(36)
 after
 userPass; */
CREATE TABLE USER_TYPE (
    userID VARCHAR(36) NOT NULL PRIMARY KEY,
    userType VARCHAR (36),
    FOREIGN KEY (userID) REFERENCES USERS(userID)
);

CREATE TABLE RESTAURANT(
    restaurantID VARCHAR(36) NOT NULL PRIMARY KEY,
    restaurantName VARCHAR(255),
    pictureURL VARCHAR(255)
);

CREATE TABLE ORDERS(
    orderID VARCHAR(36) NOT NULL PRIMARY KEY,
    customerID VARCHAR (36),
    staffID VARCHAR (36),
    restaurantID VARCHAR (36),
    orderCreatedAt DATETIME,
    itemDescription VARCHAR(255),
    FOREIGN KEY (customerID) REFERENCES USERS(userID),
    FOREIGN KEY (staffID) REFERENCES USERS(userID),
    FOREIGN KEY (restaurantID) REFERENCES RESTAURANT(restaurantID)
);

CREATE TABLE ORDER_STATUS(
    orderID VARCHAR(36) NOT NULL PRIMARY KEY,
    orderStatus VARCHAR (255),
    FOREIGN KEY (orderID) REFERENCES ORDERS(orderID)
);

CREATE TABLE ORDER_RATING(
    orderID VARCHAR(36) NOT NULL PRIMARY KEY,
    rating VARCHAR (255),
    FOREIGN KEY (orderID) REFERENCES ORDERS(orderID)
);