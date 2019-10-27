/*
SQLyog Enterprise v12.5.0 (64 bit)
MySQL - 5.7.25 : Database - jilijili
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`jilijili` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `jilijili`;

/*Table structure for table `ji_admin` */

DROP TABLE IF EXISTS `ji_admin`;

CREATE TABLE `ji_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` tinyint(4) DEFAULT '0',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `account` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `ji_admin` */

/*Table structure for table `ji_user` */

DROP TABLE IF EXISTS `ji_user`;

CREATE TABLE `ji_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` tinyint(4) DEFAULT '0',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `account` varchar(255) DEFAULT NULL,
  `birthday` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `integral` int(11) DEFAULT NULL,
  `is_up` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `ji_user` */

/*Table structure for table `ji_user_video` */

DROP TABLE IF EXISTS `ji_user_video`;

CREATE TABLE `ji_user_video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` tinyint(4) DEFAULT '0',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `num_of_collect` int(11) DEFAULT NULL,
  `num_of_good` int(11) DEFAULT NULL,
  `num_ofjb` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4y0jsvk2peaylrrdcvn3g7j06` (`category_id`),
  KEY `FKn3x01a1kxayhah4200mnmclpu` (`uid`),
  CONSTRAINT `FK4y0jsvk2peaylrrdcvn3g7j06` FOREIGN KEY (`category_id`) REFERENCES `ji_user_video_category` (`id`),
  CONSTRAINT `FKn3x01a1kxayhah4200mnmclpu` FOREIGN KEY (`uid`) REFERENCES `ji_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `ji_user_video` */

/*Table structure for table `ji_user_video_category` */

DROP TABLE IF EXISTS `ji_user_video_category`;

CREATE TABLE `ji_user_video_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` tinyint(4) DEFAULT '0',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(100) DEFAULT '',
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `ji_user_video_category` */

/*Table structure for table `ji_user_video_hot` */

DROP TABLE IF EXISTS `ji_user_video_hot`;

CREATE TABLE `ji_user_video_hot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` tinyint(4) DEFAULT '0',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_collect` bit(1) DEFAULT NULL,
  `is_givejb` bit(1) DEFAULT NULL,
  `is_good` bit(1) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `vid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `ji_user_video_hot` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
