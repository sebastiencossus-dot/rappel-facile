CREATE DATABASE  IF NOT EXISTS `rappelfacile` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `rappelfacile`;
-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: rappelfacile
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `exerce`
--

DROP TABLE IF EXISTS `exerce`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exerce` (
  `prestataires_id` int NOT NULL,
  `professions_id` int NOT NULL,
  `is_valide` bit(1) DEFAULT NULL,
  PRIMARY KEY (`prestataires_id`,`professions_id`),
  KEY `FKhtq7e2ogqwbskt6nltse0faos` (`professions_id`),
  CONSTRAINT `FKhtq7e2ogqwbskt6nltse0faos` FOREIGN KEY (`professions_id`) REFERENCES `professions` (`id`),
  CONSTRAINT `FKimnvxmwqm8pu801dygvq52yym` FOREIGN KEY (`prestataires_id`) REFERENCES `prestataires` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exerce`
--

LOCK TABLES `exerce` WRITE;
/*!40000 ALTER TABLE `exerce` DISABLE KEYS */;
INSERT INTO `exerce` VALUES (2,1,_binary ''),(22,2,_binary ''),(23,1,_binary ''),(24,2,_binary ''),(25,1,_binary ''),(27,2,_binary ''),(28,2,_binary ''),(29,1,_binary ''),(30,1,_binary ''),(31,1,_binary ''),(32,4,_binary '');
/*!40000 ALTER TABLE `exerce` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-12 14:45:10
