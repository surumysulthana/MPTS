/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.31 : Database - mpts
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mpts` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `mpts`;

/*Table structure for table `authority` */

DROP TABLE IF EXISTS `authority`;

CREATE TABLE `authority` (
  `authority_id` int NOT NULL AUTO_INCREMENT,
  `login_id` int DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  `designation` varchar(500) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `id_proof` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`authority_id`)
)

/*Data for the table `authority` */

insert  into `authority`(`authority_id`,`login_id`,`name`,`designation`,`phone`,`email`,`id_proof`) values 
(17,58,'ss','ss','122','surumy@gmail.com','qqq'),
(18,60,'ss','ss','122','surumy@gmail.com','qqq'),
(19,62,'autho','police ','8075294707','hajeesh@gmail.com','id'),
(11,28,'chummi','sherin','5678903247','chummi@123','oooo'),
(16,54,'sree','police ','123456','aa','vvv');

/*Table structure for table `complaints` */

DROP TABLE IF EXISTS `complaints`;

CREATE TABLE `complaints` (
  `complaint_id` int NOT NULL AUTO_INCREMENT,
  `sender_id` int DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `reply` varchar(500) DEFAULT NULL,
  `date` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) 

insert  into `complaints`(`complaint_id`,`sender_id`,`title`,`description`,`reply`,`date`) values 
(5,54,'complaints ','......','ok','2024-03-08'),
(6,54,'complaints ','......','ok','2024-03-08'),
(13,62,'sss','sss','ok','2024-03-14'),
(12,54,'aa','aa','ok','2024-03-14'),
(11,54,'aa','aa','ok','2024-03-14');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(500) DEFAULT NULL,
  `password` varchar(500) DEFAULT NULL,
  `usertype` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
)

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'admin','123','admin'),
(11,'anu','','user'),
(12,'anu','','user'),
(13,'surumy','123','autority'),
(14,'surumy','123','autority'),
(15,'surumy','123','autority'),
(16,'surumy','123','autority'),
(17,'surumy','123','autority'),
(18,'surumy','123','autority'),
(19,'surumy','123','autority'),
(20,'surumy','123','autority'),
(21,'surumy','123','autority'),
(22,'surumy','123','autority'),
(23,'surumy','123','autority'),
(24,'surumy','123','autority'),
(25,'surumy','123','autority'),
(26,'surumy','123','autority'),
(27,'chummi','123','autority'),
(28,'chummi','123','autority'),
(29,'chinu','123','autority'),
(30,'chinu','123','autority'),
(31,'','','autority'),
(32,'','','autority'),
(33,'','','autority'),
(34,'','','autority'),
(35,'srumy','333','autority'),
(36,'srumy','333','autority'),
(37,'nimz','123','user'),
(38,'nimz','123','user'),
(39,'nimz','123','user'),
(40,'nimz','123','user'),
(41,'nimz','123','user'),
(42,'nimz','123','user'),
(43,'nim','123','user'),
(44,'nim','123','user'),
(45,'nim','123','user'),
(46,'nim','123','user'),
(47,'nim','123','user'),
(48,'nim','123','user'),
(49,'nim','123surumy','user'),
(50,'nim','123surumy','user'),
(51,'a','123','user'),
(52,'a','123','user'),
(54,'sree','123','Authority'),
(55,'nim','12','user'),
(56,'nim','12','user'),
(57,'qq','123','Authority'),
(64,'cc','vg','User'),
(62,'autho','123','Authority'),
(67,'surumy','111','User'),
(66,'nnn','nn','User'),
(65,'surumy','123','User'),
(63,'aa','aa','User');

/*Table structure for table `notification` */

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(500) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`notification_id`)
)

insert  into `notification`(`notification_id`,`title`,`description`,`date`) values 
(1,'notification','ertyu',''),
(2,'notification','ertyu',''),
(3,'www','wwww','2024-01-31'),
(4,'eee','0000','2024-02-07'),
(5,'notification','ertyu','2024-02-07'),
(6,'eee','eee','eeee'),
(7,'noti','no','11'),
(8,'','',''),
(9,'hai','123','123');

/*Table structure for table `permit_request` */

DROP TABLE IF EXISTS `permit_request`;

CREATE TABLE `permit_request` (
  `permit_request_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `reason` varchar(500) DEFAULT NULL,
  `from_date` varchar(500) DEFAULT NULL,
  `from_time` varchar(500) DEFAULT NULL,
  `from_location` varchar(500) DEFAULT NULL,
  `to_location` varchar(500) DEFAULT NULL,
  `qr_code` varchar(500) DEFAULT NULL,
  `face_image` varchar(500) DEFAULT NULL,
  `date` varchar(500) DEFAULT NULL,
  `status` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`permit_request_id`)
) 
insert  into `permit_request`(`permit_request_id`,`user_id`,`title`,`reason`,`from_date`,`from_time`,`from_location`,`to_location`,`qr_code`,`face_image`,`date`,`status`) values 
(2,2,'bcbc','nvnvn','bm','jgjg','bbj','jbbj','mb','jj','nmbn','accept'),
(3,17,'qq','ww','11','surumy@gmail.com','tt','yy','pending','uu','2024-03-15','pending');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `login_id` int DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  `place` varchar(500) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `email` varchar(500) DEFAULT NULL,
  `id_proof` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
)

insert  into `user`(`user_id`,`login_id`,`name`,`place`,`phone`,`email`,`id_proof`) values 
(7,44,'nimz','Palakkad','1234567890','ss@gmail.com','bottle_art_2.jpg'),
(2,10,'anu','Palakkad','1233','111@gmail.com','wwww'),
(3,12,'anu','Palakkad','1233','111@gmail.com','wwww'),
(4,38,'nimz','eklm','11111','ss@gmail.com','111'),
(5,40,'nimz','eklm','11111','ss@gmail.com','111'),
(6,42,'nimzzz','eklm','11111','ss@gmail.com','111'),
(8,46,'nimz','Palakkad','0000000000','ss@gmail.com','bottle_art_1.jpg'),
(9,48,'nimz','Palakkad','5555555555','ss@gmail.com','bottle_art_6.jpg'),
(10,50,'nimz','Palakkad','1234567890','ss@gmail.com','bottle_art_9.jpg'),
(11,52,'admina','eklm','1234567890','jjhhjhj@gmail','bottle_art_9.jpg'),
(12,56,'nimz','pak','0000000000','ss@gmail.com','bottle_art_7.jpg'),
(13,63,'aa','androidx.appcompat.widget.AppCompatEditText','44','surumysulthana052@gmail.com','aa'),
(14,64,'aa','androidx.appcompat.widget.AppCompatEditText{4fecb30 VFED..CL. ........ 0,1163-1080,1299 ','44','surumychummi404@gmail.com','dd'),
(15,65,'surumy ','androidx.appcompat.widget.AppCompatEditText{b68165 VFED..CL. ........ 0,1163-1080,1299 ','1234567890','surumy@gmail.com','aa'),
(16,66,'mm','androidx.appcompat.widget.AppCompatEditText{7a6ce04 VFED..CL. ........ 0,1163-1080,1299 ','999','nnn','nnn'),
(17,67,'surumy ','plkd','12345678','surumy@gmail.com','qq');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
