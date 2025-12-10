-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.6.12-MariaDB-0ubuntu0.22.04.1 - Ubuntu 22.04
-- Server OS:                    debian-linux-gnu
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for workflow
CREATE DATABASE IF NOT EXISTS `workflow` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `workflow`;

-- Dumping structure for table workflow.additional_charges
CREATE TABLE IF NOT EXISTS `additional_charges` (
  `additional_charges_id` int(10) DEFAULT NULL,
  `uuid` varchar(40) DEFAULT NULL,
  `tenant_id` int(10) NOT NULL,
  `extra_mm` decimal(10,2) DEFAULT NULL,
  `insurance` decimal(10,2) DEFAULT NULL,
  `admin_charges` decimal(10,2) DEFAULT NULL,
  `forwarding_charges` decimal(10,2) DEFAULT NULL,
  `gst` decimal(10,2) DEFAULT NULL,
  `created_dttm` datetime DEFAULT NULL,
  `updated_dttm` datetime DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `updated_by` varchar(100) DEFAULT NULL,
  KEY `tenant_id` (`tenant_id`),
  CONSTRAINT `additional_charges_tenant_ibfk_1` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.address
CREATE TABLE IF NOT EXISTS `address` (
  `address_id` int(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(40) DEFAULT NULL,
  `tenant_id` int(10) NOT NULL,
  `address_line1` varchar(100) DEFAULT NULL,
  `address_line2` varchar(100) DEFAULT NULL,
  `address_line3` varchar(100) DEFAULT NULL,
  `city` varchar(200) DEFAULT NULL,
  `state_code` varchar(50) NOT NULL,
  `country_code` varchar(50) NOT NULL,
  `pin_code` varchar(50) NOT NULL,
  `work_phone` varchar(20) DEFAULT NULL,
  `fax` varchar(40) DEFAULT NULL,
  `primary_phone_number` varchar(20) DEFAULT NULL,
  `alternate_phone_number` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `website` varchar(100) DEFAULT NULL,
  `created_dttm` datetime DEFAULT NULL,
  `updated_dttm` datetime DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `updated_by` varchar(100) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`address_id`) USING BTREE,
  UNIQUE KEY `uuid` (`uuid`),
  KEY `tenant_id` (`tenant_id`),
  KEY `address_type_id` (`tenant_id`),
  CONSTRAINT `address_tenant_ibfk_1` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.address_type
CREATE TABLE IF NOT EXISTS `address_type` (
  `address_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `tenant_id` int(10) NOT NULL,
  `type` varchar(40) DEFAULT NULL,
  `description` varchar(50) NOT NULL,
  `uuid` varchar(40) NOT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`address_type_id`),
  UNIQUE KEY `uk_address_type_uuid` (`uuid`),
  KEY `tenant_id` (`tenant_id`),
  CONSTRAINT `address_type_tenant_ibfk_1` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.company
CREATE TABLE IF NOT EXISTS `company` (
  `company_id` int(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(40) DEFAULT NULL,
  `tenant_id` int(40) NOT NULL,
  `company_name` varchar(100) DEFAULT NULL,
  `CIN` varchar(40) DEFAULT NULL,
  `GSTIN` varchar(40) DEFAULT NULL,
  `TAN` varchar(40) DEFAULT NULL,
  `PAN` varchar(20) DEFAULT NULL,
  `created_dttm` datetime DEFAULT NULL,
  `updated_dttm` datetime DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `updated_by` varchar(100) DEFAULT NULL,
  `company_type_id` int(10) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`company_id`) USING BTREE,
  UNIQUE KEY `UUID` (`uuid`),
  KEY `fk_company_tenant` (`tenant_id`),
  KEY `fk_company_company_type` (`company_type_id`),
  CONSTRAINT `fk_company_company_type` FOREIGN KEY (`company_type_id`) REFERENCES `company_type` (`company_type_id`),
  CONSTRAINT `fk_company_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.company_address
CREATE TABLE IF NOT EXISTS `company_address` (
  `company_address_id` int(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(40) DEFAULT NULL,
  `tenant_id` int(40) NOT NULL,
  `company_id` int(10) NOT NULL,
  `address_id` int(10) NOT NULL,
  `address_type_id` int(10) NOT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`company_address_id`) USING BTREE,
  UNIQUE KEY `UUID` (`uuid`),
  KEY `fk_company_address_tenant` (`tenant_id`),
  KEY `fk_company_address_address` (`address_id`),
  KEY `fk_company_address_address_type` (`address_type_id`),
  KEY `fk_company_address_company` (`company_id`),
  CONSTRAINT `fk_company_address_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`),
  CONSTRAINT `fk_company_address_address_type` FOREIGN KEY (`address_type_id`) REFERENCES `address_type` (`address_type_id`),
  CONSTRAINT `fk_company_address_company` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `fk_company_address_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.company_type
CREATE TABLE IF NOT EXISTS `company_type` (
  `company_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(40) NOT NULL,
  `tenant_id` int(10) NOT NULL,
  `type` varchar(40) DEFAULT NULL,
  `description` varchar(50) NOT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`company_type_id`),
  UNIQUE KEY `uk_company_type_uuid` (`uuid`),
  KEY `tenant_id` (`tenant_id`),
  CONSTRAINT `company_type_tenant_ibfk_1` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.confirm_through
CREATE TABLE IF NOT EXISTS `confirm_through` (
  `confirm_through_id` int(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) DEFAULT NULL,
  `tenant_id` int(10) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`confirm_through_id`) USING BTREE,
  KEY `tenant_id` (`tenant_id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.glass_specification
CREATE TABLE IF NOT EXISTS `glass_specification` (
  `glass_specification_id` int(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(100) NOT NULL,
  `tenant_id` int(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`glass_specification_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `tenant_id` (`tenant_id`),
  CONSTRAINT `glass_specification_tenant_ibfk_1` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.glass_thickness
CREATE TABLE IF NOT EXISTS `glass_thickness` (
  `glass_thickness_id` int(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(100) NOT NULL,
  `tenant_id` int(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`glass_thickness_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `tenant_id` (`tenant_id`),
  CONSTRAINT `glass_thickness_tenant_ibfk_1` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.glass_type
CREATE TABLE IF NOT EXISTS `glass_type` (
  `glass_type_id` int(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(100) NOT NULL,
  `tenant_id` int(10) NOT NULL,
  `glass_name` varchar(100) NOT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`glass_type_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `tenant_id` (`tenant_id`),
  CONSTRAINT `glass_type_tenant_ibfk_1` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.hibernate_sequence
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.pi_type
CREATE TABLE IF NOT EXISTS `pi_type` (
  `pi_type_id` int(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) NOT NULL,
  `tenant_id` int(10) NOT NULL,
  `pi_type_name` varchar(10) NOT NULL,
  PRIMARY KEY (`pi_type_id`) USING BTREE,
  UNIQUE KEY `uk_pi_type_uuid` (`uuid`),
  KEY `tenant_id` (`tenant_id`),
  CONSTRAINT `pi_type_tenant_ibfk_1` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.pro_forma_invoice
CREATE TABLE IF NOT EXISTS `pro_forma_invoice` (
  `pro_forma_invoice_id` int(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) NOT NULL,
  `tenant_id` int(10) NOT NULL,
  `id_bill_to` int(10) DEFAULT NULL,
  `id_ship_to` int(10) DEFAULT NULL,
  `pi_type_id` int(10) DEFAULT NULL,
  `pi_number` int(10) DEFAULT NULL,
  `invoice_date` datetime DEFAULT NULL,
  `pro_forma_invoice_amount` double DEFAULT NULL,
  `service_rate_invoice_amount` double DEFAULT NULL,
  `basic_amount` double DEFAULT NULL,
  `admin_charges` double DEFAULT NULL,
  `insurance_percent` float DEFAULT NULL,
  `insurance_percent_amount` float DEFAULT NULL,
  `urgency_percent` float DEFAULT NULL,
  `urgency_percent_amount` float DEFAULT NULL,
  `other_charges` double DEFAULT NULL,
  `transport_charges` double DEFAULT NULL,
  `gst_charges` float DEFAULT NULL,
  `grand_total` float DEFAULT NULL,
  `round_off_amount` int(10) DEFAULT NULL,
  `payable_amount` float DEFAULT NULL,
  `previous_balance` float DEFAULT NULL,
  `adjustment_amount` int(10) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `confirm_through_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`pro_forma_invoice_id`) USING BTREE,
  UNIQUE KEY `uk_pro_forma_invoice_uuid` (`uuid`),
  KEY `tenant_id` (`tenant_id`),
  KEY `FK_pro_forma_invoice_pi_type` (`pi_type_id`) USING BTREE,
  KEY `FK_pro_forma_invoice_confirm_through` (`confirm_through_id`) USING BTREE,
  CONSTRAINT `pro_forma_invoice_tenant_ibfk_1` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.pro_forma_invoice_item
CREATE TABLE IF NOT EXISTS `pro_forma_invoice_item` (
  `pro_forma_invoice_item_id` int(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tenant_id` int(10) NOT NULL,
  `pro_forma_invoice_id` int(10) DEFAULT NULL,
  `glass_type_id` int(10) DEFAULT NULL,
  `glass_specification_id` int(10) DEFAULT NULL,
  `glass_thickness_id` int(10) DEFAULT NULL,
  `width_inch` decimal(20,6) DEFAULT NULL,
  `width_measurement` decimal(20,6) DEFAULT NULL,
  `actual_width` decimal(20,6) DEFAULT NULL,
  `chargeable_width` decimal(20,6) DEFAULT NULL,
  `height_inch` decimal(20,6) DEFAULT NULL,
  `height_measurement` decimal(20,6) DEFAULT NULL,
  `actual_height` decimal(20,6) DEFAULT NULL,
  `chargeable_height` decimal(20,6) DEFAULT NULL,
  `extra_mm` decimal(20,6) DEFAULT NULL,
  `quantity` int(10) DEFAULT NULL,
  `sqft` decimal(20,6) DEFAULT NULL,
  `rate_per_sqft` decimal(20,6) DEFAULT NULL,
  `amount` decimal(20,6) DEFAULT NULL,
  PRIMARY KEY (`pro_forma_invoice_item_id`) USING BTREE,
  UNIQUE KEY `uk_pro_forma_invoice_item_uuid` (`uuid`),
  KEY `tenant_id` (`tenant_id`),
  KEY `FK_pro_forma_invoice_item_pro_forma_invoice` (`pro_forma_invoice_id`) USING BTREE,
  KEY `FK_pro_forma_invoice_item_glass_type` (`glass_type_id`) USING BTREE,
  KEY `FK_pro_forma_invoice_item_glass_specification` (`glass_specification_id`) USING BTREE,
  KEY `FK_pro_forma_invoice_item_glass_thickness` (`glass_thickness_id`) USING BTREE,
  CONSTRAINT `pro_forma_invoice_item_tenant_ibfk_1` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.service_rate
CREATE TABLE IF NOT EXISTS `service_rate` (
  `service_rate_id` int(11) DEFAULT NULL,
  `uuid` varchar(40) DEFAULT NULL,
  `tenant_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `rate` decimal(10,2) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  KEY `tenant_id` (`tenant_id`),
  CONSTRAINT `service_rate_ibfk_1` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.service_rate_invoice
CREATE TABLE IF NOT EXISTS `service_rate_invoice` (
  `service_rate_invoice_id` int(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tenant_id` int(10) NOT NULL,
  `pro_forma_invoice_id` int(10) DEFAULT NULL,
  `service_rate_id` int(10) DEFAULT NULL,
  `quantity` int(10) DEFAULT NULL,
  `rate` int(10) DEFAULT NULL,
  `total` int(10) DEFAULT NULL,
  PRIMARY KEY (`service_rate_invoice_id`) USING BTREE,
  UNIQUE KEY `uk_service_rate_invoice_uuid` (`uuid`),
  KEY `tenant_id` (`tenant_id`),
  KEY `FK_service_rate_invoice_pro_forma_invoice` (`pro_forma_invoice_id`) USING BTREE,
  KEY `FK_service_rate_invoice_service_rate` (`service_rate_id`) USING BTREE,
  CONSTRAINT `service_rate_invoice_tenant_ibfk_1` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.status
CREATE TABLE IF NOT EXISTS `status` (
  `status_id` int(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(100) NOT NULL,
  `tenant_id` int(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`status_id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `tenant_id` (`tenant_id`),
  CONSTRAINT `status_tenant_ibfk_1` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table workflow.tenant
CREATE TABLE IF NOT EXISTS `tenant` (
  `tenant_id` int(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(40) DEFAULT NULL,
  `tenant_name` varchar(100) DEFAULT NULL,
  `address` varchar(300) DEFAULT NULL,
  `city` varchar(200) DEFAULT NULL,
  `state` varchar(200) DEFAULT NULL,
  `country_id` int(10) DEFAULT NULL,
  `pin_code` varchar(10) DEFAULT NULL,
  `phone_number` varchar(40) DEFAULT NULL,
  `email_id` varchar(100) DEFAULT NULL,
  `activation_date` datetime DEFAULT NULL,
  `expiry_date` datetime DEFAULT NULL,
  `grace_period` int(10) DEFAULT NULL COMMENT 'Number of remaining days.',
  `is_active` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;