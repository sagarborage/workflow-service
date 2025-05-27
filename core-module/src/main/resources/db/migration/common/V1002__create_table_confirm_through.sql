-- ==================================================================
-- Author:        Vithoba Hipparkar
-- Create date:   16/05/2025
-- Description:   Initial database setup
-- File Name:     V1002__create_table_confirm_through.sql
-- ==================================================================

-- -----------------------------------------------------
-- Begin transaction
-- -----------------------------------------------------

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+05:30";

-- -----------------------------------------------------
-- Table Structure for `confirm_through`
-- -----------------------------------------------------
CREATE TABLE confirm_through (
   id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
   uuid CHAR(36) UNIQUE NOT NULL DEFAULT (UUID()),
   tenant_id int(10) UNSIGNED NOT NULL,
   name varchar(50) DEFAULT NULL,
   is_active BOOLEAN NOT NULL DEFAULT 1,
   created_by VARCHAR(64) NOT NULL,
   created_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),
   last_updated_by VARCHAR(64) NOT NULL,
   last_updated_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP) ON UPDATE CURRENT_TIMESTAMP,
   version INT NOT NULL DEFAULT 1,
   CONSTRAINT confirm_through_tenant_ibfk_1 FOREIGN KEY (tenant_id) REFERENCES tenant (id)
 );
-- -----------------------------------------------------
-- Commit transaction
-- -----------------------------------------------------
COMMIT;
