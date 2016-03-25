CREATE DATABASE  IF NOT EXISTS `jbossdb` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `jbossdb`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: 172.16.10.130    Database: jbossdb
-- ------------------------------------------------------
-- Server version	5.5.28

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
-- Table structure for table `HILOSEQUENCES`
--

DROP TABLE IF EXISTS `HILOSEQUENCES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HILOSEQUENCES` (
  `SEQUENCENAME` varchar(50) NOT NULL,
  `HIGHVALUES` int(11) NOT NULL,
  PRIMARY KEY (`SEQUENCENAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TIMERS`
--

DROP TABLE IF EXISTS `TIMERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TIMERS` (
  `TIMERID` varchar(80) NOT NULL,
  `TARGETID` varchar(250) NOT NULL,
  `INITIALDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TIMERINTERVAL` bigint(20) DEFAULT NULL,
  `INSTANCEPK` longblob,
  `INFO` longblob,
  PRIMARY KEY (`TIMERID`,`TARGETID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TimeoutMethod_methodParams`
--

DROP TABLE IF EXISTS `TimeoutMethod_methodParams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TimeoutMethod_methodParams` (
  `TimeoutMethod_id` bigint(20) NOT NULL,
  `methodParams` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `calendar_timer`
--

DROP TABLE IF EXISTS `calendar_timer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calendar_timer` (
  `autoTimer` bit(1) NOT NULL,
  `scheduleExprDayOfMonth` varchar(255) DEFAULT NULL,
  `scheduleExprDayOfWeek` varchar(255) DEFAULT NULL,
  `scheduleExprEndDate` datetime DEFAULT NULL,
  `scheduleExprHour` varchar(255) DEFAULT NULL,
  `scheduleExprMinute` varchar(255) DEFAULT NULL,
  `scheduleExprMonth` varchar(255) DEFAULT NULL,
  `scheduleExprSecond` varchar(255) DEFAULT NULL,
  `scheduleExprStartDate` datetime DEFAULT NULL,
  `scheduleExprTimezone` varchar(255) DEFAULT NULL,
  `scheduleExprYear` varchar(255) DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  `timeoutMethod_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2B697F04E6E6EF93` (`id`),
  KEY `FK2B697F04B7DE2D8A` (`timeoutMethod_id`),
  CONSTRAINT `FK2B697F04B7DE2D8A` FOREIGN KEY (`timeoutMethod_id`) REFERENCES `timeout_method` (`id`),
  CONSTRAINT `FK2B697F04E6E6EF93` FOREIGN KEY (`id`) REFERENCES `timer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hilosequences`
--

DROP TABLE IF EXISTS `hilosequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hilosequences` (
  `SEQUENCENAME` varchar(50) NOT NULL,
  `HIGHVALUES` int(11) NOT NULL,
  PRIMARY KEY (`SEQUENCENAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `timeout_method`
--

DROP TABLE IF EXISTS `timeout_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timeout_method` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `declaringClass` varchar(255) NOT NULL,
  `methodName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `timeoutmethod_methodparams`
--

DROP TABLE IF EXISTS `timeoutmethod_methodparams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timeoutmethod_methodparams` (
  `TimeoutMethod_id` bigint(20) NOT NULL,
  `methodParams` varchar(255) DEFAULT NULL,
  KEY `FKF294C964B7DE2D8A` (`TimeoutMethod_id`),
  CONSTRAINT `FKF294C964B7DE2D8A` FOREIGN KEY (`TimeoutMethod_id`) REFERENCES `timeout_method` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `timer`
--

DROP TABLE IF EXISTS `timer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timer` (
  `id` varchar(255) NOT NULL,
  `info` tinyblob,
  `initialDate` datetime DEFAULT NULL,
  `nextDate` datetime DEFAULT NULL,
  `previousRun` datetime DEFAULT NULL,
  `repeatInterval` bigint(20) NOT NULL,
  `timedObjectId` varchar(255) NOT NULL,
  `timerState` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `timers`
--

DROP TABLE IF EXISTS `timers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timers` (
  `TIMERID` varchar(80) NOT NULL,
  `TARGETID` varchar(250) NOT NULL,
  `INITIALDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TIMERINTERVAL` bigint(20) DEFAULT NULL,
  `INSTANCEPK` longblob,
  `INFO` longblob,
  PRIMARY KEY (`TIMERID`,`TARGETID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'jbossdb'
--

--
-- Dumping routines for database 'jbossdb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-07-09 11:42:59
