-- ==================================================================
-- Author:        Vithoba Hipparkar
-- Create date:   16/05/2025
-- Description:   Initial database setup
-- File Name:     V1006__create_table_hibernate_sequence.sql
-- ==================================================================

-- -----------------------------------------------------
-- Begin transaction
-- -----------------------------------------------------

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+05:30";

-- -----------------------------------------------------
-- Table Structure for `hibernate_sequence`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
   id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
   next_val bigint(20) DEFAULT NULL,
   is_active BOOLEAN NOT NULL DEFAULT 1,
   created_by VARCHAR(64) NOT NULL,
   created_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   last_updated_by VARCHAR(64) NOT NULL,
   last_updated_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   version INT UNSIGNED NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
-- -----------------------------------------------------
-- Commit transaction
-- -----------------------------------------------------
COMMIT;
