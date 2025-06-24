-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        10.11.5-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 employee 的数据库结构
DROP DATABASE IF EXISTS `employee`;
CREATE DATABASE IF NOT EXISTS `employee` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `employee`;

-- 导出  表 employee.departments 结构
DROP TABLE IF EXISTS `departments`;
CREATE TABLE IF NOT EXISTS `departments` (
  `dept_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  employee.departments 的数据：~9 rows (大约)
DELETE FROM `departments`;
INSERT INTO `departments` (`dept_id`, `name`) VALUES
	(1, '总经理办公室'),
	(2, '人力资源部'),
	(3, '财务部'),
	(4, '生产技术部'),
	(5, '计划营销部'),
	(6, '安全监察部'),
	(7, '后勤部'),
	(8, '宣传部'),
	(9, '开发部');

-- 导出  表 employee.employees 结构
DROP TABLE IF EXISTS `employees`;
CREATE TABLE IF NOT EXISTS `employees` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `gender` enum('MALE','FEMALE') NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(64) NOT NULL,
  `dept` int(11) unsigned NOT NULL,
  `hire_date` date NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT unix_timestamp(),
  PRIMARY KEY (`id`),
  KEY `FK_employees_departments` (`dept`),
  CONSTRAINT `FK_employees_departments` FOREIGN KEY (`dept`) REFERENCES `departments` (`dept_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  employee.employees 的数据：~30 rows (大约)
DELETE FROM `employees`;
INSERT INTO `employees` (`id`, `name`, `gender`, `phone`, `email`, `dept`, `hire_date`, `create_time`) VALUES
	(1, '小王', 'MALE', '13000000001', 'example1@example.com', 1, '2025-06-01', '2025-06-21 13:25:29'),
	(2, '小希', 'FEMALE', '13000000002', 'example2@example.com', 1, '2025-06-02', '2025-06-21 13:25:29'),
	(3, '小力', 'MALE', '13000000003', 'example3@example.com', 2, '2025-06-03', '2025-06-21 13:25:29'),
	(4, '小萱', 'FEMALE', '13000000004', 'example4@example.com', 2, '2025-06-04', '2025-06-21 13:25:29'),
	(5, '小军', 'MALE', '13000000005', 'example5@example.com', 2, '2025-06-05', '2025-06-21 13:25:29'),
	(6, '小蓉', 'FEMALE', '13000000006', 'example6@example.com', 9, '2025-06-06', '2025-06-21 13:25:29'),
	(7, '小雄', 'MALE', '13000000007', 'example7@example.com', 9, '2025-06-07', '2025-06-21 13:25:29'),
	(8, '小绿', 'FEMALE', '13000000008', 'example8@example.com', 9, '2025-06-08', '2025-06-21 13:25:29'),
	(9, '小乐', 'MALE', '13000000009', 'example9@example.com', 3, '2025-06-09', '2025-06-21 13:25:29'),
	(10, '小秀', 'FEMALE', '13000000010', 'example10@example.com', 3, '2025-06-10', '2025-06-21 13:25:29'),
	(11, '小智', 'MALE', '13000000011', 'example11@example.com', 9, '2025-06-11', '2025-06-21 13:25:29'),
	(12, '小叆', 'FEMALE', '13000000012', 'example12@example.com', 4, '2025-06-12', '2025-06-21 13:25:29'),
	(13, '小琦', 'MALE', '13000000013', 'example13@example.com', 4, '2025-06-13', '2025-06-21 13:25:29'),
	(14, '小莹', 'FEMALE', '13000000014', 'example14@example.com', 4, '2025-06-14', '2025-06-21 13:25:29'),
	(15, '小谚', 'MALE', '13000000015', 'example15@example.com', 4, '2025-06-15', '2025-06-21 13:25:29'),
	(16, '小露', 'FEMALE', '13000000016', 'example16@example.com', 5, '2025-06-16', '2025-06-21 13:25:29'),
	(17, '小祖', 'MALE', '13000000017', 'example17@example.com', 5, '2025-06-17', '2025-06-21 13:25:29'),
	(18, '小菀', 'FEMALE', '13000000018', 'example18@example.com', 5, '2025-06-18', '2025-06-21 13:25:29'),
	(19, '小慕', 'MALE', '13000000019', 'example19@example.com', 5, '2025-06-19', '2025-06-21 13:25:29'),
	(20, '小洁', 'FEMALE', '13000000020', 'example20@example.com', 5, '2025-06-20', '2025-06-21 13:25:29'),
	(21, '小旲', 'MALE', '13000000021', 'example21@example.com', 6, '2025-06-21', '2025-06-21 13:25:29'),
	(22, '小悦', 'FEMALE', '13000000022', 'example22@example.com', 6, '2025-06-22', '2025-06-21 13:25:29'),
	(23, '小功', 'MALE', '13000000023', 'example23@example.com', 7, '2025-06-23', '2025-06-21 13:25:29'),
	(24, '小卓', 'FEMALE', '13000000024', 'example24@example.com', 7, '2025-06-24', '2025-06-21 13:25:29'),
	(25, '小超', 'MALE', '13000000025', 'example25@example.com', 7, '2025-06-25', '2025-06-21 13:25:29'),
	(26, '小玲', 'FEMALE', '13000000026', 'example26@example.com', 8, '2025-06-26', '2025-06-21 13:25:29'),
	(27, '小先', 'MALE', '13000000027', 'example27@example.com', 8, '2025-06-27', '2025-06-21 13:25:29'),
	(28, '小倩', 'FEMALE', '13000000028', 'example28@example.com', 8, '2025-06-28', '2025-06-21 13:25:29'),
	(29, '小圣', 'MALE', '13000000029', 'example29@example.com', 9, '2025-06-29', '2025-06-21 13:25:29'),
	(30, '小岚', 'FEMALE', '13000000030', 'example30@example.com', 9, '2025-06-30', '2025-06-21 13:25:29');

-- 导出  表 employee.user 结构
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` enum('ADMIN','USER') NOT NULL DEFAULT 'USER',
  `last_login_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 正在导出表  employee.user 的数据：~2 rows (大约)
DELETE FROM `user`;
INSERT INTO `user` (`user_id`, `username`, `password`, `role`, `last_login_time`) VALUES
	(1, 'admin', 'admin', 'ADMIN', '2025-06-22 19:53:51'),
	(2, 'user', 'user', 'USER', '2025-06-22 01:39:33');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
