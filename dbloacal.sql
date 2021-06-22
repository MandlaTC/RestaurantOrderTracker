-- MySQL dump 10.13  Distrib 5.5.38, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: d2303145
-- ------------------------------------------------------
-- Server version	5.5.38-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `CARS`
--


--
-- Dumping data for table `CARS`
--

DROP TABLE IF EXISTS `ORDERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ORDERS` (
  `orderID` varchar(36) NOT NULL,
  `customerID` varchar(36) NOT NULL,
  `staffID` varchar(36) NOT NULL,
  `restaurantID` varchar(36) NOT  NULL,
  `orderCreatedAt` datetime DEFAULT NULL,
  `itemDescription` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`orderID`),
  KEY `customerID` (`customerID`),
  KEY `staffID` (`staffID`),
  KEY `restaurantID` (`restaurantID`),
  CONSTRAINT `ORDERS_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `USERS` (`userID`),
  CONSTRAINT `ORDERS_ibfk_2` FOREIGN KEY (`staffID`) REFERENCES `USERS` (`userID`),
  CONSTRAINT `ORDERS_ibfk_3` FOREIGN KEY (`restaurantID`) REFERENCES `RESTAURANT` (`restaurantID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ORDERS`
--

LOCK TABLES `ORDERS` WRITE;
/*!40000 ALTER TABLE `ORDERS` DISABLE KEYS */;
--INSERT INTO `ORDERS` VALUES ('60c1357494ce9','60c0ca7ca683e','60bf85fe551f1','5ece4797eaf5e','2021-06-09 23:41:08','burger'),('60c13d9b0d58b','60c0ca7ca683e','60bf85fe551f1','5ece4797eaf5e','2021-06-10 00:15:55','burger'),('60c1cc2f77177','60c0ca7ca683e','60bf85fe551f1','5ece4797eaf5e','2021-06-10 10:24:15','burger'),('60c1d20008c51',NULL,'60bf85fe551f1','5ece4797eaf5e','2021-06-10 10:49:04','burger'),('60c1d20314aee',NULL,'60bf85fe551f1','5ece4797eaf5e','2021-06-10 10:49:07','burger'),('60c74b654128e','60c1e61a50fdf','60c1e7477019f',NULL,'2021-06-14 14:28:21','burger'),('60c750868113f','60c0c4bc59221','60c1e7477019f',NULL,'2021-06-14 14:50:14','Burger and Chips'),('60c750fb374e7','60c0dc7d50f30','60c1e7477019f',NULL,'2021-06-14 14:52:11','Chips'),('60c7516c9fa59','60c1e61a50fdf','60c1e7477019f',NULL,'2021-06-14 14:54:04','Pizza'),('60c751c1b0970','60c1e61a50fdf','60c1e7477019f',NULL,'2021-06-14 14:55:29','Water only'),('60c7b96b18067','60c0c4bc59221','60bf85fe551f1','5ece4797eaf5e','2021-06-14 22:17:47','BigMac'),('60c7ba129f3a1',NULL,'60bf85fe551f1','5ece4797eaf5e','2021-06-14 22:20:34','BigMac'),('60c7baa9cdbbe',NULL,'60bf85fe551f1','5ece4797eaf5e','2021-06-14 22:23:05','ChickenBurger'),('60c7bad43938f','60c1ec378d3e5','60bf85fe551f1','5ece4797eaf5e','2021-06-14 22:23:48','ChickenBurger'),('60c877574923f','60c0dc7d50f30','60c1e7477019f',NULL,'2021-06-15 11:48:07','ChesaNyama'),('60c9ced937ef5','60c1e61a50fdf','60c1e7477019f','5ece4797eaf5e','2021-06-16 12:13:45','Senhor Peri Peri'),('60c9d10eee09e','60c1e61a50fdf','60c1e7477019f','5ece4797eaf5e','2021-06-16 12:23:10','box of chips'),('60c9d3f2bfa95','60c0dc7d50f30','60c9d317c25b7','5ece4797eaf5e','2021-06-16 12:35:30','Chesa Nyama and Chips'),('60cafe1217b99','60c9d317c25b7','60c1e7477019f','5ece4797eaf5e','2021-06-17 09:47:30','Test'),('60cb07cd24fa6','60cb011abc7dd','60bf85fe551f1','5ece4797eaf5e','2021-06-17 10:29:01','chickenBurger'),('60cb094c25329','60c1e61a50fdf','60cb0936d0721',NULL,'2021-06-17 10:35:24','street wise 2'),('60cb0a9728c85','60c1e61a50fdf','60cb0936d0721','60c4bd91d03d7','2021-06-17 10:40:55','zinger burger'),('60cb0b9cb231c','60cb0b4edb0f2','60cb0b7744578','60c1e4ea49afe','2021-06-17 10:45:16','1/2 Chicken and Chips'),('60cb0bbbb232b','60cb0b4edb0f2','60cb0b7744578','60c1e4ea49afe','2021-06-17 10:45:47','Milkshake'),('60cb0f0f26322','60cb0e6474bf1','60cb0e995f329','60c4bd91d03d7','2021-06-17 10:59:59','Streetwise 2'),('60cb0f3473907','60cb0e6474bf1','60cb0e995f329','60c4bd91d03d7','2021-06-17 11:00:36','Milkshake'),('60cb1c312df7e','60cb1ba379259','60cb1bd6966f6','60c1e4ee82936','2021-06-17 11:56:01','Burger and Chips'),('60cb1c4b751b1','60cb1ba379259','60cb1bd6966f6','60c1e4ee82936','2021-06-17 11:56:27','Milkshake'),('60cb1cf815916','60cb1ba379259','60cb1bd6966f6','60c1e4ee82936','2021-06-17 11:59:20','Chicken'),('60cb1fa592a3e','60cb1f54718c0','60cb1f54718c0','60c1e4ea49afe','2021-06-17 12:10:45','Burger and Chips'),('60cb1fb351857','60cb1f54718c0','60cb1f54718c0','60c1e4ea49afe','2021-06-17 12:10:59','Milkshake'),('60cb1fc1655ce','60cb1f54718c0','60cb1f54718c0','60c1e4ea49afe','2021-06-17 12:11:13','Coke'),('60cb212511a02','60cb20d64d861','60cb20d64d861','60c1e4ea49afe','2021-06-17 12:17:09','Burger'),('60cb21323da53','60cb20d64d861','60cb20d64d861','60c1e4ea49afe','2021-06-17 12:17:22','Chips'),('60cb2137d127f','60cb20d64d861','60cb20d64d861','60c1e4ea49afe','2021-06-17 12:17:27','Coke'),('60cb23b71151f','60cb235bb7b53','60cb237e3623b','60c1e4ea49afe','2021-06-17 12:28:07','Burger'),('60cb23bd20787','60cb235bb7b53','60cb237e3623b','60c1e4ea49afe','2021-06-17 12:28:13','Chips'),('60cb23c282ed8','60cb235bb7b53','60cb237e3623b','60c1e4ea49afe','2021-06-17 12:28:18','Coke');
/*!40000 ALTER TABLE `ORDERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ORDER_RATING`
--

DROP TABLE IF EXISTS `ORDER_RATING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ORDER_RATING` (
  `orderID` varchar(36) NOT NULL,
  `rating` int(11) DEFAULT NULL,
  PRIMARY KEY (`orderID`),
  CONSTRAINT `ORDER_RATING_ibfk_1` FOREIGN KEY (`orderID`) REFERENCES `ORDERS` (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ORDER_RATING`
--

LOCK TABLES `ORDER_RATING` WRITE;
/*!40000 ALTER TABLE `ORDER_RATING` DISABLE KEYS */;
--INSERT INTO `ORDER_RATING` VALUES ('60c74b654128e',1),('60c750868113f',1),('60c750fb374e7',1),('60c7516c9fa59',1),('60c751c1b0970',0),('60c7b96b18067',0),('60c7ba129f3a1',1),('60c7baa9cdbbe',1),('60c7bad43938f',0),('60c877574923f',0),('60c9ced937ef5',1),('60c9d10eee09e',NULL),('60c9d3f2bfa95',NULL),('60cafe1217b99',NULL),('60cb07cd24fa6',NULL),('60cb094c25329',NULL),('60cb0a9728c85',NULL),('60cb0b9cb231c',NULL),('60cb0bbbb232b',1),('60cb0f0f26322',1),('60cb0f3473907',0),('60cb1c312df7e',0),('60cb1c4b751b1',1),('60cb1cf815916',NULL),('60cb1fa592a3e',NULL),('60cb1fb351857',NULL),('60cb1fc1655ce',NULL),('60cb212511a02',NULL),('60cb21323da53',NULL),('60cb2137d127f',NULL),('60cb23b71151f',NULL),('60cb23bd20787',1),('60cb23c282ed8',0);
/*!40000 ALTER TABLE `ORDER_RATING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ORDER_STATUS`
--

DROP TABLE IF EXISTS `ORDER_STATUS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ORDER_STATUS` (
  `orderID` varchar(36) NOT NULL,
  `orderStatus` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`orderID`),
  CONSTRAINT `ORDER_STATUS_ibfk_1` FOREIGN KEY (`orderID`) REFERENCES `ORDERS` (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ORDER_STATUS`
--

LOCK TABLES `ORDER_STATUS` WRITE;
/*!40000 ALTER TABLE `ORDER_STATUS` DISABLE KEYS */;
--INSERT INTO `ORDER_STATUS` VALUES ('60c74b654128e','collection'),('60c750868113f','cooking'),('60c750fb374e7','cooking'),('60c7516c9fa59','collection'),('60c751c1b0970','completed'),('60c7b96b18067','cooking'),('60c7ba129f3a1','complete'),('60c7baa9cdbbe','cooking'),('60c7bad43938f','complete'),('60c877574923f','completed'),('60c9ced937ef5','completed'),('60c9d10eee09e','collection'),('60c9d3f2bfa95','cooking'),('60cafe1217b99','completed'),('60cb07cd24fa6','cooking'),('60cb094c25329','cooking'),('60cb0a9728c85','cooking'),('60cb0b9cb231c','cooking'),('60cb0bbbb232b','completed'),('60cb0f0f26322','completed'),('60cb0f3473907','completed'),('60cb1c312df7e','completed'),('60cb1c4b751b1','completed'),('60cb1cf815916','collection'),('60cb1fa592a3e','cooking'),('60cb1fb351857','cooking'),('60cb1fc1655ce','cooking'),('60cb212511a02','cooking'),('60cb21323da53','completed'),('60cb2137d127f','completed'),('60cb23b71151f','cooking'),('60cb23bd20787','completed'),('60cb23c282ed8','completed');
/*!40000 ALTER TABLE `ORDER_STATUS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESTAURANT`
--

DROP TABLE IF EXISTS `RESTAURANT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESTAURANT` (
  `restaurantID` varchar(36) NOT NULL,
  `restaurantName` varchar(255) DEFAULT NULL,
  `pictureURL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`restaurantID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESTAURANT`
--

LOCK TABLES `RESTAURANT` WRITE;
/*!40000 ALTER TABLE `RESTAURANT` DISABLE KEYS */;
--INSERT INTO `RESTAURANT` VALUES ('5ece4797eaf5e','McDonalds  Waterfall Estate',''),('60c1e46a35808','McDonalds Sandton','url=testURL'),('60c1e4ea49afe','Steers Melrose Arch','testURL'),('60c1e4ee82936','Steers Rosebank','testURL'),('60c4bc3999e5b','KFC Mall Of Africa','picURL'),('60c4bd91d03d7','KFC Norwood','picURL');
/*!40000 ALTER TABLE `RESTAURANT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USERS`
--

DROP TABLE IF EXISTS `USERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERS` (
  `userID` varchar(36) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `userType` varchar(36) DEFAULT NULL,
  `userPass` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERS`
--

LOCK TABLES `USERS` WRITE;
/*!40000 ALTER TABLE `USERS` DISABLE KEYS */;
--INSERT INTO `USERS` VALUES ('60bf4864e8b29','kingston','kingston@kingston.com','Staff','25d55ad283aa400af464c76d713c07ad'),('60bf85fe551f1','admin-mandla','admin@admin.com','Staff','21232f297a57a5a743894a0e4a801fc3'),('60bfb5450bbb9','admin-taufeeq','admin.t@admin.com','Staff','21232f297a57a5a743894a0e4a801fc3'),('60c0c4bc59221','user','user@test.com','Customer','ee11cbb19052e40b07aac0ca060c23ee'),('60c0ca7ca683e','user2','user2@test.com','Customer','7e58d63b60197ceb55a1c487989a3720'),('60c0dc7d50f30','taufeeqtest','taufeeqtest@gmail.com','Staff','25d55ad283aa400af464c76d713c07ad'),('60c1e61a50fdf','customer','customer@customer.com','Customer','25d55ad283aa400af464c76d713c07ad'),('60c1e7477019f','staff','staff@staff.com','Staff','25d55ad283aa400af464c76d713c07ad'),('60c1ec378d3e5','customer2','customer2@customer.com','Customer','25d55ad283aa400af464c76d713c07ad'),('60c9d317c25b7','amaani','amaani@amaani.com','Staff','25d55ad283aa400af464c76d713c07ad'),('60cb00e63b2b3','bob','bob@bob.com','Staff','25d55ad283aa400af464c76d713c07ad'),('60cb011abc7dd','chickenlover','chickenlover@hotwings.com','Customer','25d55ad283aa400af464c76d713c07ad'),('60cb0144458ea','chickenlickenstaff1','chl@chcikenlicken.com','Staff','25d55ad283aa400af464c76d713c07ad'),('60cb01c9a8e45','test10','test@test.com','Staff','fcea920f7412b5da7be0cf42b8c93759'),('60cb034ba449f','test10','test10@gmail.com','Staff','25d55ad283aa400af464c76d713c07ad'),('60cb0936d0721','staffmember','staffmember@staff.com','Staff','25d55ad283aa400af464c76d713c07ad'),('60cb0b07a82cf','videoteststaff','test@test.com','Staff','25d55ad283aa400af464c76d713c07ad'),('60cb0b4edb0f2','videotestcustomer1','test@test.com','Customer','25d55ad283aa400af464c76d713c07ad'),('60cb0b7744578','steersstaffmember','test@test.com','Staff','25d55ad283aa400af464c76d713c07ad'),('60cb0e6474bf1','Bob the chicken lover','bob@bob.com','Customer','94beb91c03971c9b03f56eb5d22e3679'),('60cb0e995f329','John the chicken server','john@john.com','Staff','25d55ad283aa400af464c76d713c07ad'),('60cb1ba379259','Joey Eater','joey@burger.com','Customer','78c304f8e67b0caec59db007e4ecc178'),('60cb1bd6966f6','Rachel Burger','rachel@gmail.com','Staff','4e2fe6dc5c296a7a548a7fd4eaf3ad07'),('60cb1f26daf21','Michael Scott','michael@paper.com','Customer','6c303b1d4f058918e84c78fce9fab1dc'),('60cb1f54718c0','Jim Halpert','jim@paper.com','Staff','4946f0e3a1c1a5e28e66418fe77fd405'),('60cb20b16f6a6','Monica','monica@gmail.com','Customer','f32e15e7af11d77eac7ca295b3e9a068'),('60cb20d64d861','chandler','chandler@gmail.com','Staff','2cb9edd8dbb4bc1be5b8b0cdd889a761'),('60cb235bb7b53','Jake','jake@jake.com','Customer','7f6b4c4b8d2070f44e9e79a51f9cd37b'),('60cb237e3623b','Amy','amy@gmail.com','Staff','c1ba18572a90fe2bf2db58fd728fd349');
/*!40000 ALTER TABLE `USERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_TYPE`
--

DROP TABLE IF EXISTS `USER_TYPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER_TYPE` (
  `userType` varchar(36) DEFAULT NULL,
  `userID` varchar(36) NOT NULL DEFAULT '',
  `restaurantID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`userID`),
  KEY `restaurantID` (`restaurantID`),
  CONSTRAINT `USER_TYPE_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `USERS` (`userID`),
  CONSTRAINT `USER_TYPE_ibfk_2` FOREIGN KEY (`restaurantID`) REFERENCES `RESTAURANT` (`restaurantID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_TYPE`
--

LOCK TABLES `USER_TYPE` WRITE;
/*!40000 ALTER TABLE `USER_TYPE` DISABLE KEYS */;
--INSERT INTO `USER_TYPE` VALUES ('staff','60bf85fe551f1','5ece4797eaf5e'),('staff','60c1e7477019f','5ece4797eaf5e'),('staff','60c9d317c25b7','5ece4797eaf5e'),('Staff','60cb00e63b2b3',NULL),('Customer','60cb011abc7dd',NULL),('Staff','60cb0144458ea',NULL),('Staff','60cb01c9a8e45',NULL),('Staff','60cb034ba449f',NULL),('Staff','60cb0936d0721','60c4bd91d03d7'),('Staff','60cb0b07a82cf','60c1e46a35808'),('Customer','60cb0b4edb0f2',NULL),('Staff','60cb0b7744578','60c1e4ea49afe'),('Customer','60cb0e6474bf1',NULL),('Staff','60cb0e995f329','60c4bd91d03d7'),('Customer','60cb1ba379259',NULL),('Staff','60cb1bd6966f6','60c1e4ee82936'),('Customer','60cb1f26daf21',NULL),('Staff','60cb1f54718c0','60c1e4ea49afe'),('Customer','60cb20b16f6a6',NULL),('Staff','60cb20d64d861','60c1e4ea49afe'),('Customer','60cb235bb7b53',NULL),('Staff','60cb237e3623b','60c1e4ea49afe');
/*!40000 ALTER TABLE `USER_TYPE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-17 15:13:45
