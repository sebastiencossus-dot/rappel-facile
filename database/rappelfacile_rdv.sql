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
-- Table structure for table `rdv`
--

DROP TABLE IF EXISTS `rdv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rdv` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_rdv` datetime(6) DEFAULT NULL,
  `isok` int DEFAULT NULL,
  `motif` varchar(255) DEFAULT NULL,
  `adresses_id` int DEFAULT NULL,
  `prestataires_id` int DEFAULT NULL,
  `professions_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `adresse_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5tyag4cka36gkokdcug1ftvsr` (`adresses_id`),
  KEY `FKqdlrtymym3k3s02ks1nr3aof0` (`prestataires_id`),
  KEY `FK1t9v3lk7lx3oegs137ep590nb` (`professions_id`),
  KEY `FKml5gclha0ajhopw3idl2whn3f` (`user_id`),
  KEY `FK31iwhkwes6x2689swthbeywpg` (`adresse_id`),
  CONSTRAINT `FK1t9v3lk7lx3oegs137ep590nb` FOREIGN KEY (`professions_id`) REFERENCES `professions` (`id`),
  CONSTRAINT `FK31iwhkwes6x2689swthbeywpg` FOREIGN KEY (`adresse_id`) REFERENCES `adresses` (`id`),
  CONSTRAINT `FK5tyag4cka36gkokdcug1ftvsr` FOREIGN KEY (`adresses_id`) REFERENCES `adresses` (`id`),
  CONSTRAINT `FKml5gclha0ajhopw3idl2whn3f` FOREIGN KEY (`user_id`) REFERENCES `user` (`id_user`),
  CONSTRAINT `FKqdlrtymym3k3s02ks1nr3aof0` FOREIGN KEY (`prestataires_id`) REFERENCES `prestataires` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rdv`
--

LOCK TABLES `rdv` WRITE;
/*!40000 ALTER TABLE `rdv` DISABLE KEYS */;
INSERT INTO `rdv` VALUES (13,'2026-05-29 08:53:32.000000',3,'Consultation générale',1,2,1,1,NULL),(14,'2026-05-29 09:53:32.000000',3,'Suivi médical',1,2,1,1,NULL),(15,'2026-05-29 10:53:32.000000',0,'Bilan de santé',1,2,3,1,NULL),(16,'2026-05-31 07:54:19.000000',3,'Dentiste',1,2,1,1,NULL),(17,'2026-06-03 07:54:19.000000',0,'Ophtalmologue',1,2,1,1,NULL),(18,'2026-06-08 07:54:19.000000',3,'Consultation spécialiste',1,2,1,1,NULL),(19,'2026-05-28 07:55:02.000000',3,'Ancien RDV 1',1,2,1,1,NULL),(20,'2026-05-26 07:55:02.000000',0,'Ancien RDV 2',1,2,1,1,NULL),(21,'2026-05-22 07:55:02.000000',0,'Ancien RDV 3',1,2,1,1,NULL),(24,'2026-06-01 22:10:00.000000',1,'Test Sam',1,2,1,2,NULL),(34,'2026-06-05 20:14:00.000000',3,'uuuhg',1,2,2,1,NULL),(35,'2026-06-05 20:32:00.000000',0,'enfin ca fonctionne',1,2,1,1,NULL),(41,'2026-06-09 12:32:00.000000',0,'ajout rdv de test',12,25,2,1,NULL),(42,'2026-06-17 14:25:00.000000',1,'coupe',14,29,2,1,14),(43,'2026-06-10 22:30:00.000000',3,'coiffure avant rdv important',13,27,2,1,NULL),(44,'2026-06-11 23:59:00.000000',3,'visite annuelle',1,27,1,1,NULL),(45,'2026-06-14 14:00:00.000000',1,'hjg',1,29,2,1,NULL),(46,'2026-06-11 16:00:00.000000',0,'dsfdsdfsfsf',1,29,1,1,NULL),(47,'2026-06-11 13:50:00.000000',1,'test cron',1,29,1,3,NULL),(48,'2026-06-11 16:00:00.000000',1,'test envois mail auto',1,29,1,3,NULL),(49,'2026-01-01 10:00:00.000000',0,'er',1,29,1,1,NULL),(50,'2026-02-12 10:00:00.000000',0,'jhh',1,29,3,1,NULL),(51,'2026-03-10 10:00:00.000000',3,'ghhghhg',1,32,4,1,NULL),(52,'2026-06-12 12:00:00.000000',1,'petit massage des muscles',13,32,4,1,NULL);
/*!40000 ALTER TABLE `rdv` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-12 14:45:12
