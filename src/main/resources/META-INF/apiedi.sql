-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server Version:               10.4.11-MariaDB - mariadb.org binary distribution
-- Server Betriebssystem:        Win64
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Exportiere Datenbank Struktur für apiedi
DROP DATABASE IF EXISTS `apiedi`;
CREATE DATABASE IF NOT EXISTS `apiedi` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `apiedi`;

-- Exportiere Struktur von Tabelle apiedi.angebot
DROP TABLE IF EXISTS `angebot`;
CREATE TABLE IF NOT EXISTS `angebot` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BehArt` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Betrag` float DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Exportiere Daten aus Tabelle apiedi.angebot: ~3 rows (ungefähr)
DELETE FROM `angebot`;
/*!40000 ALTER TABLE `angebot` DISABLE KEYS */;
INSERT INTO `angebot` (`ID`, `BehArt`, `Betrag`) VALUES
	(1, 'BehKurz', 50),
	(2, 'BehMittel', 85),
	(3, 'BehLang', 90);
/*!40000 ALTER TABLE `angebot` ENABLE KEYS */;

-- Exportiere Struktur von Tabelle apiedi.behandlung
DROP TABLE IF EXISTS `behandlung`;
CREATE TABLE IF NOT EXISTS `behandlung` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Date` date DEFAULT NULL,
  `AngebotId` int(11) DEFAULT NULL,
  `Anzahl` int(11) DEFAULT 0,
  `Summe` float DEFAULT 0,
  PRIMARY KEY (`ID`),
  KEY `FK_behandlung_angebot` (`AngebotId`),
  CONSTRAINT `FK_behandlung_angebot` FOREIGN KEY (`AngebotId`) REFERENCES `angebot` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Exportiere Daten aus Tabelle apiedi.behandlung: ~0 rows (ungefähr)
DELETE FROM `behandlung`;
/*!40000 ALTER TABLE `behandlung` DISABLE KEYS */;
/*!40000 ALTER TABLE `behandlung` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
