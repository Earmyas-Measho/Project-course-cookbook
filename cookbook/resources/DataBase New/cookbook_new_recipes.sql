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
-- Table structure for table `recipes`
--

DROP TABLE IF EXISTS `recipes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `ingredients_list` varchar(1000) NOT NULL,
  `category` varchar(500) NOT NULL,
  `short_desc` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipes`
--

LOCK TABLES `recipes` WRITE;
/*!40000 ALTER TABLE `recipes` DISABLE KEYS */;
INSERT INTO `recipes` VALUES (1,'Waffles','You’ll love the sweet, simpe and fast to prepare recipe! You can add toppings and/or sides. For breakfast or all day snacks!','200g Wheat flour, 2 eggs, 100 ml Oat Milk','Gluten free','Tasty, easy to make '),(2,'Cheese and ham croissant','An easy and impressive breakfast or brunch. Serve warm with a coffe! ','1 Croissant, 5g of Ham, 5g of Cheese','Breakfast','Simple and delicious'),(3,'Banana bread','The perfect solution for an healthy breakfast. Try to add some dried berries and taste it!','2 Bananas, 250g Wheat Flour, 150ml Oat Milk, 100g Nuts, ','Vegan, Gluten free','Tastes like banana (:'),(4,'Eggs benedict','A classic for your breakfast! You will only need eggs, butter and bread.','2 Eggs, 200g of  Bread, 50g of Butter,  1 teaspoon of Olive Oil, 2g of Salt','Breakfast, Gluten free','Healthy and quick breakfast'),(5,'Broccoli soup','Blend roasted broccoli and vegetable broth to make a simple, yet delicious soup. Roasting broccoli enhances its flavor, allowing nutty and sweet flavors to develop.','900g Broccoli, 1000ml of Water, 1 Zucchini, 300g of Spinach, 1 teaspoon of Salt','Vegan, Gluten free, Healthy','The kids will love it!'),(6,'Carrot soup','A simple yet mouth-watering soup. Carrots, water and some vegetables will be the only ingredients that you will need.','900g Carrots, 1000ml of Water, 1 Zucchini, 4 potatoes, Salt','Vegan, Gluten free, Healthy','You will love it!'),(7,'Mushrooms Risotto','This mushroom risotto is easy to make and quicker than a classic risotto. ','900g of Rice, 1 glass of Wine, 900ml of Water, 50g of Butter, 600g of Mushrooms, 150g of Parmesan, Salt ','Italian, Vegan','Perfect for a fancy dinner'),(8,'Pizza Napolitana ','Sourdoug base, tomatoes and mushrooms. The perfect pizza!','Pizza dough, 200ml of Tomato sauce, 250g of Fresh Mozzarela, 200g of Mushrooms','Italian','Simple home made pizza'),(9,'Gnocchi ','Fluffy potato gnocchis with  marinated mozzarella.','400g of Gnocchi, 150ml of Tomato sauce, 150g of Mozzarela, 90g of Parmesan, Salt, ','Italian','A filling meal'),(11,'Lasagna','Creamy and tasty Italian Lasagna','600g of Lasagna dough, 900g of Meat, 200ml of Bechamel sauce, 200g of Mozzarela, Salt','Italian','A classic italian recipe'),(12,'Avocado Salad','A easy and fresh summer salad! ','300g of Lettuce, 2 Avocados, 1 Carrot, 80g of Parmesan, 1 Tomato','Healthy, Gluten free','A green salad'),(13,'Cinnamon and Nuts Cake','The right cake for every ocasion! You can do it with a small amount of ingredients. ','250g of Wheat flour, 200ml of Milk, 4 Eggs, 50g of Butter, 100g of Sugar, 40g of Cinnamon, 80g of Nuts','Dessert','Perfect for birthdays'),(14,'Fish Curry with Ginger','Fresh and spicy for Indian food lovers! ','200ml of Coconut milk, 30g of Ginger, Fish, Garlic, Onion, 30g of Curry, 200g of Basmatti Rice, Salt','Gluten free, Meat free','Spicy food from the sea'),(15,'Yogurt with Granola','The perfect snack! Add some berries and cocoa on top of it!','100ml of Yogurt, 80g of Granola, 1 Orange','Healthy','Sweet and tasty'),(16,'Scramble eggs','Ideas for your breakfast? Here you have! Add some tasty bacon and enjoy it!','2 Eggs, 80g of Bacon','Breakfast','High protein meal'),(25,'Healthy Wrap','Easy to prepare, healthy and fresh! This is your summer meal!','1 Wrap, 100g Cucumber, Onion, Spinach,  Carrot, Tuna','Healthy, Gluten free','Its a wrap...');
/*!40000 ALTER TABLE `recipes` ENABLE KEYS */;
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
