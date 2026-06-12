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
-- Dumping events for database 'rappelfacile'
--

--
-- Dumping routines for database 'rappelfacile'
--
/*!50003 DROP PROCEDURE IF EXISTS `spRdvList` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `spRdvList`(
    IN user_id SMALLINT,
    IN year_param SMALLINT
)
BEGIN
    DROP TEMPORARY TABLE IF EXISTS tmpListCat;
    DROP TEMPORARY TABLE IF EXISTS tmpResultatSp;

    SELECT 
        MONTH(r.date_rdv) AS mois,
        SUM(CASE WHEN c.name = 'medical' THEN 1 ELSE 0 END)       AS medical,
        SUM(CASE WHEN c.name = 'beaute' THEN 1 ELSE 0 END)        AS beaute,
        SUM(CASE WHEN c.name = 'soin' THEN 1 ELSE 0 END)          AS soin,
        SUM(CASE WHEN c.name = 'administratif' THEN 1 ELSE 0 END) AS administratif,
        SUM(CASE WHEN r.isok = 0 THEN 1 ELSE 0 END)               AS effectue,
        SUM(CASE WHEN r.isok = 3 THEN 1 ELSE 0 END)               AS rate,
        SUM(CASE WHEN r.isok = 1 THEN 1 ELSE 0 END)               AS avenir
    FROM rdv r
    INNER JOIN professions pr ON r.professions_id = pr.id
    INNER JOIN categories c ON pr.categories_id = c.id
    WHERE r.user_id = user_id
      AND YEAR(r.date_rdv) = year_param
    GROUP BY MONTH(r.date_rdv)
    ORDER BY mois;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-12 14:45:12
