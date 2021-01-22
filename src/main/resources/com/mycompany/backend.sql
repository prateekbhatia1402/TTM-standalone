CREATE DATABASE IF NOT EXISTS TTMPRATEEKv3;
USE TTMPRATEEKv3;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `student`;
DROP TABLE IF EXISTS `faculty`;
DROP TABLE IF EXISTS `time slot`;
DROP TABLE IF EXISTS `room`;
DROP TABLE IF EXISTS `parent details`;
DROP TABLE IF EXISTS `subject`;
DROP TABLE IF EXISTS `account`;
DROP TABLE IF EXISTS `class`;
DROP TABLE IF EXISTS `class_subject_faculty`;
DROP TABLE IF EXISTS `day`;
DROP TABLE IF EXISTS `schedule`;
DROP TABLE IF EXISTS `admin`;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `account` (
  `USERNAME` varchar(20) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `ROLE` varchar(10) NOT NULL,
  PRIMARY KEY (`USERNAME`)
);

CREATE TABLE `admin` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) NOT NULL,
  `EMAIL` varchar(35) NOT NULL,
  `PHONE NUMBER` varchar(10) NOT NULL,
  `USERNAME` varchar(20) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `USERNAME` (`USERNAME`),
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`USERNAME`) REFERENCES `account` (`USERNAME`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `room` (
  `ID` int NOT NULL,
  `SITTING CAPACITY` int NOT NULL,
  PRIMARY KEY (`ID`)
);


CREATE TABLE `class` (
  `ID` varchar(6) NOT NULL,
  `NAME` varchar(20) NOT NULL,
  `ROOM ID` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NAME` (`NAME`),
  KEY `ROOM ID` (`ROOM ID`),
  CONSTRAINT `class_ibfk_1` FOREIGN KEY (`ROOM ID`) REFERENCES `room` (`ID`) ON DELETE SET NULL
);


CREATE TABLE `day` (
  `DAY ID` char(2) NOT NULL,
  `DAY NAME` varchar(10) NOT NULL,
  `DAY NUMBER` int NOT NULL,
  PRIMARY KEY (`DAY ID`)
);


CREATE TABLE `parent details` (
  `PARENT ID` varchar(10) NOT NULL,
  `father name` varchar(20) DEFAULT NULL,
  `father email id` varchar(35) DEFAULT NULL,
  `father mobile number` varchar(10) DEFAULT NULL,
  `FATHER DOB` date DEFAULT NULL,
  `mother name` varchar(20) DEFAULT NULL,
  `mother email` varchar(35) DEFAULT NULL,
  `mother mobile number` varchar(10) DEFAULT NULL,
  `MOTHER DOB` date DEFAULT NULL,
  `family income` varchar(21) DEFAULT NULL,
  PRIMARY KEY (`PARENT ID`),
  CONSTRAINT `parent_emails` CHECK (((`father email id` is not null) or (`mother email` is not null))),
  CONSTRAINT `parent_mobiles` CHECK (((`father mobile number` is not null) or (`mother mobile number` is not null)))
);


CREATE TABLE `student` (
  `REGISTRATION ID` varchar(6) NOT NULL,
  `STUDENT NAME` varchar(20) NOT NULL,
  `EMAIL` varchar(35) NOT NULL,
  `USERNAME` varchar(20) NOT NULL,
  `mobile number` varchar(10) DEFAULT NULL,
  `CLASS` varchar(6) DEFAULT NULL,
  `PERMANENT Address` varchar(60) NOT NULL,
  `CORR. ADDRESS` varchar(60) NOT NULL,
  `ROLL NUMBER` int NOT NULL,
  `DATE OF BIRTH` date NOT NULL,
  `date of registration` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `GENDER` char(1) NOT NULL,
  `BLOOD GROUP` varchar(3) DEFAULT NULL,
  `PARENT ID` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`REGISTRATION ID`),
  KEY `USERNAME` (`USERNAME`),
  KEY `PARENT ID` (`PARENT ID`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`USERNAME`) REFERENCES `account` (`USERNAME`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_ibfk_2` FOREIGN KEY (`PARENT ID`) REFERENCES `parent details` (`PARENT ID`) ON DELETE CASCADE
);


CREATE TABLE `subject` (
  `SUBJECT ID` varchar(10) NOT NULL,
  `SUBJECT NAME` varchar(25) NOT NULL,
  `LECTURES REQUIRED` int NOT NULL,
  `TOTAL LECTURES REQUIRED` int DEFAULT NULL,
  `CREDITS` int DEFAULT NULL,
  `syllabus` varchar(2000) DEFAULT NULL,
  `EVALUATION CRETERIA` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`SUBJECT ID`)
);

CREATE TABLE `time slot` (
  `ID` varchar(10) NOT NULL,
  `FROM` varchar(4) NOT NULL,
  `TO` varchar(4) NOT NULL,
  PRIMARY KEY (`ID`)
);


CREATE TABLE `faculty` (
  `FACULTY ID` varchar(10) NOT NULL,
  `NAME` varchar(20) NOT NULL,
  `EMAIL` varchar(35) NOT NULL,
  `USERNAME` varchar(20) NOT NULL,
  `PERMANENT ADDRESS` varchar(60) NOT NULL,
  `CORR. ADDRESS` varchar(60) NOT NULL,
  `DATE OF BIRTH` date NOT NULL,
  `date of registration` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `MOBILE NUMBER` varchar(10) NOT NULL,
  `GENDER` char(1) NOT NULL,
  `blood group` varchar(3) DEFAULT NULL,
  `experience` varchar(50) NOT NULL DEFAULT ' ',
  `subject speciality` varchar(25) NOT NULL DEFAULT ' ',
  PRIMARY KEY (`FACULTY ID`),
  KEY `USERNAME` (`USERNAME`),
  CONSTRAINT `faculty_ibfk_1` FOREIGN KEY (`USERNAME`) REFERENCES `account` (`USERNAME`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `qualification` (
  `FACULTY ID` varchar(10) NOT NULL,
  `degree` varchar(20) DEFAULT NULL,
  `YEAR` char(4) NOT NULL,
  `institute` varchar(30) DEFAULT NULL,
  `PERCENTAGE` decimal(5,2) DEFAULT NULL,
  KEY `FACULTY ID` (`FACULTY ID`),
  CONSTRAINT `qualification_ibfk_1` FOREIGN KEY (`FACULTY ID`) REFERENCES `faculty` (`FACULTY ID`) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `class_subject_faculty` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `CLASS ID` varchar(6) NOT NULL,
  `FACULTY ID` varchar(10) NOT NULL,
  `SUBJECT ID` varchar(10) NOT NULL,
  `ROOM ID` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `CLASS ID` (`CLASS ID`),
  KEY `FACULTY ID` (`FACULTY ID`),
  KEY `SUBJECT ID` (`SUBJECT ID`),
  KEY `ROOM ID` (`ROOM ID`),
  CONSTRAINT `class_subject_faculty_ibfk_1` FOREIGN KEY (`CLASS ID`) REFERENCES `class` (`ID`),
  CONSTRAINT `class_subject_faculty_ibfk_2` FOREIGN KEY (`FACULTY ID`) REFERENCES `faculty` (`FACULTY ID`),
  CONSTRAINT `class_subject_faculty_ibfk_3` FOREIGN KEY (`SUBJECT ID`) REFERENCES `subject` (`SUBJECT ID`),
  CONSTRAINT `class_subject_faculty_ibfk_4` FOREIGN KEY (`ROOM ID`) REFERENCES `room` (`ID`)
);


CREATE TABLE `schedule` (
  `SCHEDULE ID` int NOT NULL AUTO_INCREMENT,
  `TIME TABLE ID` varchar(10) not null,
  `LAST UPDATED` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATED BY` int not null,
  `FROM` date NOT NULL,
  `TO` date NOT NULL,
  `STATUS` enum('active', 'inactive', 'incoming', 'outgoing'),
  `DAY ID` char(2) NOT NULL,
  `TIMESLOT ID` varchar(10) NOT NULL,
  `CLASS ID` varchar(6) NOT NULL,
  `SUBJECT ID` varchar(10) NOT NULL,
  `FACULTY ID` varchar(10) NOT NULL,
  `ROOM ID` int DEFAULT NULL,
  PRIMARY KEY (`SCHEDULE ID`)
);


