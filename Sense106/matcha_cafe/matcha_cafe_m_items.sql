-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: matcha_cafe
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `m_items`
--

DROP TABLE IF EXISTS `m_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `m_items` (
  `items_id` int NOT NULL AUTO_INCREMENT,
  `items_category` varchar(45) NOT NULL,
  `items_name` varchar(45) NOT NULL,
  `items_prices` double NOT NULL,
  `items_amount` int NOT NULL,
  PRIMARY KEY (`items_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_items`
--

LOCK TABLES `m_items` WRITE;
/*!40000 ALTER TABLE `m_items` DISABLE KEYS */;
INSERT INTO `m_items` VALUES (1,'Coffee','Espresso',10,3),(2,'Coffee','Americno',10,20),(3,'Coffee','Cafe latte',11,6),(4,'Coffee','Espresso',10,20),(5,'Coffee','Cafe Mocha',9,10),(6,'Coffee','Cappucino',10,20),(7,'Coffee','Caramel Machiato',9,20),(8,'Tea','Matcha',11,11),(9,'Tea','Citrus',10,10),(10,'Tea','Earl Grey',11,19),(11,'Tea','Paper Mint',9,10),(12,'Dessert','Choco Chip Cookies',12,15),(13,'Dessert','Lemon Cheese Cake',10,20),(14,'Dessert','Blueberry Muffin',15,20),(15,'Dessert','Fudge Browines',13,10),(16,'Shesha','Apple',5,20),(17,'Shesha','Kudamono',5,20);
/*!40000 ALTER TABLE `m_items` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-27  2:01:14
