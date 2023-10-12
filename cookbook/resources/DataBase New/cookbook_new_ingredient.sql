-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: cookbook_new
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `quantity` int NOT NULL,
  `unit` varchar(45) NOT NULL,
  `recipes_names` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `unit_fk_idx` (`unit`),
  KEY `recipe_id_fk_idx` (`recipes_names`),
  CONSTRAINT `unit_fk` FOREIGN KEY (`unit`) REFERENCES `units` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient`
--

LOCK TABLES `ingredient` WRITE;
/*!40000 ALTER TABLE `ingredient` DISABLE KEYS */;
INSERT INTO `ingredient` VALUES (1,'Avocado',1,'portion','Avocado salad'),(2,'Bananas',1,'portion','Banana bread'),(3,'Bread',100,'gram','Eggs benedict'),(4,'Butter',100,'gram','Eggs benedict, Mushrooms risotto,  Cinnamon and Nuts Cake'),(6,'Cheese',100,'gram','Cheese and ham croissant'),(7,'Cinnamon',100,'gram','Cinnamon and Nuts Cake'),(8,'Egg',1,'portion','Waffles, Eggs benedict,  Cinnamon and Nuts Cake, Scramble eggs'),(9,'Fish',1,'portion','Fish Curry with Ginger'),(10,'Garlic',1,'portion','Fish Curry with Ginger'),(11,'Ginger',100,'gram','Fish Curry with Ginger'),(12,'Meat',100,'gram','Lasagna, '),(13,'Milk',1,'liter','Cinnamon and Nuts Cake'),(14,'Onion',1,'portion','Fish Curry with Ginger, Healthy Wrap'),(15,'Oranges',1,'kilogram','Yogurt with Granola'),(16,'Potatoes',5,'portion','Carrots soup, '),(17,'Basmatti Rice',1,'kilogram','Fish Curry with Ginger'),(18,'Spinach',100,'gram','Broccoli soup, Healthy Wrap'),(19,'Sugar',1,'kilogram','Cinnamon and Nuts Cake, '),(20,'Tomato',1,'portion','Avocado salad '),(21,'Granola',100,'gram','Yogurt with Granola'),(22,'Yogurt',1,'soup_spoon','Yogurt with Granola'),(23,'Wheat Flour',100,'gram','Waffles, Banana bread, Cinnamon and Nuts Cake, '),(24,'Croissant',1,'portion','Cheese and ham croissant, '),(25,'Ham',100,'gram','Cheese and ham croissant, '),(26,'Oat Milk',100,'mililiter','Waffles, Banana bread'),(27,'Nuts',100,'gram','Banana bread,  Cinnamon and Nuts Cake, '),(28,'Olive Oil',100,'mililiter','Eggs benedict, '),(29,'Water',100,'mililiter','Broccoli soup, Carrots soup, Mushrooms risotto, '),(30,'Broccoli',100,'gram','Broccoli soup, '),(31,'Zucchini',1,'portion','Broccoli soup, Carrots soup, '),(32,'Rice',500,'gram','Mushrooms risotto, '),(33,'Mushrooms',100,'gram','Mushrooms risotto, Pizza Napolitana,'),(34,'Wine',1,'glass','Mushrooms risotto, '),(35,'Salt',1,'teaspoon','Fish Curry with Ginger, Lasagna, Gnocchi, Mushrooms risotto, Broccoli soup, Carrot soup, '),(36,'Tomato sauce',100,'mililiter','Pizza Napolitana, Gnocchi'),(37,'Bacon',100,'gram','Scramble eggs'),(38,'Gnocchi',100,'gram','Gnocchi'),(39,'Fresh Mozzarela',100,'gram','Pizza Napolitana'),(40,'Lasagna dough',100,'gram','Lasagna'),(41,'Parmesan',100,'gram','Mushrooms risotto, Gnocchi, Avocado salad'),(57,'Pizza dough',1,'portion','Pizza Napolitana'),(58,'Mozzarela',100,'gram','Gnocchi, Lasagna'),(59,'Bechamel sauce',100,'mililiter','Lasagna'),(60,'Lettuce',100,'gram','Avocado salad'),(61,'Cocunut Milk',100,'mililiter','Fish Curry with Ginger'),(62,'Indian Curry',50,'gram','Fish Curry with Ginger'),(63,'Wrap',1,'portion','Healthy Wrap'),(64,'Cucumber',100,'gram','Healthy Wrap'),(65,'Tuna',100,'gram','Healthy Wrap');
/*!40000 ALTER TABLE `ingredient` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-22 23:35:48
