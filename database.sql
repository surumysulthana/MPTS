/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - mpts
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mptsnew` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `mptsnew`;

/*Table structure for table `authority` */

DROP TABLE IF EXISTS `authority`;

CREATE TABLE `authority` (
  `authority_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  `designation` varchar(500) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `id_proof` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`authority_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `authority` */

insert  into `authority`(`authority_id`,`login_id`,`name`,`designation`,`phone`,`email`,`id_proof`) values 
(1,2,'shine thomx','authority','7012131415','shineps0007@gmail.com','static/0b907e3f-d28c-4187-9ba1-1531c1bab0dfabc.pdf');

/*Table structure for table `complaints` */

DROP TABLE IF EXISTS `complaints`;

CREATE TABLE `complaints` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `reply` varchar(500) DEFAULT NULL,
  `date` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `complaints` */

insert  into `complaints`(`complaint_id`,`sender_id`,`title`,`description`,`reply`,`date`) values 
(1,4,'hh','hj','qoq','2024-04-11'),
(2,4,'','unhbbbbbbbbbbxbdbdndndndndnddndndndndndndndndnsndnsndndnsnsnsndnsnsndmdmsmssmsmsnsnsnsnsn','ok sir','2024-04-11'),
(3,4,'mmss','jfjdjdndndndnsnddjdd; jsdjsnsnsnsnsndndnsnsnsnsnxxnxnxnx','pending','2024-04-11'),
(4,2,'hey','des','pending','2024-04-11');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(500) DEFAULT NULL,
  `password` varchar(500) DEFAULT NULL,
  `usertype` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'admin','admin','admin'),
(2,'a','a','Authority'),
(3,'s','s','user'),
(4,'ammu','ammu','user'),
(5,'ss','ss','Authority'),
(6,'ll','ll','user');

/*Table structure for table `notification` */

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `notification_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(500) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`notification_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `notification` */

insert  into `notification`(`notification_id`,`title`,`description`,`date`) values 
(1,'dfgh','dfghqfghj','dfgh');

/*Table structure for table `permit_request` */

DROP TABLE IF EXISTS `permit_request`;

CREATE TABLE `permit_request` (
  `permit_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `reason` varchar(500) DEFAULT NULL,
  `from_date` varchar(500) DEFAULT NULL,
  `from_time` varchar(500) DEFAULT NULL,
  `from_location` varchar(500) DEFAULT NULL,
  `to_location` varchar(500) DEFAULT NULL,
  `route` varchar(200) DEFAULT NULL,
  `qr_code` varchar(500) DEFAULT NULL,
  `face_image` varchar(500) DEFAULT NULL,
  `date` varchar(500) DEFAULT NULL,
  `status` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`permit_request_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `permit_request` */

insert  into `permit_request`(`permit_request_id`,`user_id`,`title`,`reason`,`from_date`,`from_time`,`from_location`,`to_location`,`route`,`qr_code`,`face_image`,`date`,`status`) values 
(2,1,'ff','ccg','vvv','bb','Thrissur ','Ernakulam','Sakthan Thampuran Private Bus Stand,thalore,chalakkudi','static/qr_image/ff.png','static/c6a3b2c5-6342-4554-8992-650432d8ce53abc.jpg','2024-04-11','accept');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  `place` varchar(500) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `email` varchar(500) DEFAULT NULL,
  `id_proof` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`login_id`,`name`,`place`,`phone`,`email`,`id_proof`) values 
(1,4,'jini','kk','7012134114','kk@jsns','static/6509e737-4735-442b-a0d9-710f216df37babc.pdf');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
