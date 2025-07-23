-- ==================================================================
-- Author:        Vithoba Hipparkar
-- Create date:   16/05/2025
-- Description:   Initial database setup
-- File Name:     V1015__create_table_toughen_batch_process.sql
-- ==================================================================

-- -----------------------------------------------------
-- Begin transaction
-- -----------------------------------------------------

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+05:30";

-- -----------------------------------------------------
-- Table Structure for `toughen_batch_process`
-- -----------------------------------------------------
CREATE TABLE toughen_batch_process (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  uuid CHAR(36) UNIQUE NOT NULL DEFAULT (UUID()),
  batch_no int(10) NOT NULL,
  tenant_id int(10) UNSIGNED NOT NULL,
  firm_id int(10) UNSIGNED NOT NULL,
  status varchar(50) DEFAULT NULL,
  is_active BOOLEAN NOT NULL DEFAULT 1,
  created_by VARCHAR(64) NOT NULL,
  created_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),
  last_updated_by VARCHAR(64) NOT NULL,
  last_updated_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP) ON UPDATE CURRENT_TIMESTAMP,
  version INT NOT NULL DEFAULT 1,
  CONSTRAINT T_BD_FK_1 FOREIGN KEY (tenant_id) REFERENCES tenant (id),
  CONSTRAINT C_BD_FK_1 FOREIGN KEY (firm_id) REFERENCES tenant (id)
 );
-- -----------------------------------------------------
-- Commit transaction
-- -----------------------------------------------------
COMMIT;
