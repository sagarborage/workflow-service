-- ==================================================================
-- Author:        Vithoba Hipparkar
-- Create date:   16/05/2025
-- Description:   Initial database setup
-- File Name:     V1007__create_table_pi_type.sql
-- ==================================================================

-- -----------------------------------------------------
-- Begin transaction
-- -----------------------------------------------------

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+05:30";

-- -----------------------------------------------------
-- Table Structure for `pi_type`
-- -----------------------------------------------------
CREATE TABLE pi_type (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	uuid CHAR(36) UNIQUE NOT NULL DEFAULT (UUID()),
	tenant_id INT(10) UNSIGNED NOT NULL,
	pi_type_name VARCHAR(10) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT 1,
    created_by VARCHAR(64) NOT NULL,
    created_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    last_updated_by VARCHAR(64) NOT NULL,
    last_updated_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP) ON UPDATE CURRENT_TIMESTAMP,
    version INT NOT NULL DEFAULT 1,
	INDEX tenant_id (tenant_id) USING BTREE,
	CONSTRAINT pi_type_tenant_ibfk_1 FOREIGN KEY (tenant_id) REFERENCES tenant (id)
);
-- -----------------------------------------------------
-- Commit transaction
-- -----------------------------------------------------
COMMIT;
