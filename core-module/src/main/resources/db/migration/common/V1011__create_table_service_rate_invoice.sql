-- ==================================================================
-- Author:        Vithoba Hipparkar
-- Create date:   16/05/2025
-- Description:   Initial database setup
-- File Name:     V1011__create_table_service_rate_invoice.sql
-- ==================================================================

-- -----------------------------------------------------
-- Begin transaction
-- -----------------------------------------------------

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+05:30";

-- -----------------------------------------------------
-- Table Structure for `service_rate_invoice`
-- -----------------------------------------------------
CREATE TABLE `service_rate_invoice` (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid CHAR(36) UNIQUE NOT NULL DEFAULT (UUID()),
	pro_forma_invoice_id INT(10) UNSIGNED NOT NULL,
	service_rate_id INT(10) UNSIGNED NOT NULL,
	quantity INT(10) NOT NULL,
	rate decimal(10,2) NOT NULL,
	total decimal(10,2) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT 1,
    created_by VARCHAR(64) NOT NULL,
    created_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    last_updated_by VARCHAR(64) NOT NULL,
    last_updated_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP) ON UPDATE CURRENT_TIMESTAMP,
    version INT NOT NULL DEFAULT 1,
	UNIQUE INDEX `uk_service_rate_invoice_uuid` (`uuid`) USING BTREE,
	INDEX `FK_service_rate_invoice_pro_forma_invoice` (`pro_forma_invoice_id`) USING BTREE,
	INDEX `FK_service_rate_invoice_service_rate` (`service_rate_id`) USING BTREE,
	CONSTRAINT `service_rate_invoice_invoice_ibfk_1` FOREIGN KEY (`pro_forma_invoice_id`) REFERENCES `pro_forma_invoice` (`id`)
);
-- -----------------------------------------------------
-- Commit transaction
-- -----------------------------------------------------
COMMIT;
