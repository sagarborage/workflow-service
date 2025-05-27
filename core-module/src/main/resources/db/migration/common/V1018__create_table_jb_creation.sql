-- ==================================================================
-- Author:        Vithoba Hipparkar
-- Create date:   16/05/2025
-- Description:   Initial database setup
-- File Name:     V1018__create_table_jb_creation.sql
-- ==================================================================

-- -----------------------------------------------------
-- Begin transaction
-- -----------------------------------------------------

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+05:30";

-- -----------------------------------------------------
-- Table Structure for `jb_creation`
-- -----------------------------------------------------
CREATE TABLE jb_creation (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid CHAR(36) UNIQUE NOT NULL DEFAULT (UUID()),
    toughen_batch_process_id INT(10) UNSIGNED NOT NULL,
    glass_thickness_id int(10) UNSIGNED NOT NULL,
    party_name VARCHAR(50) NOT NULL,
    width_mm DECIMAL(20,6) DEFAULT NULL,
    height_mm DECIMAL(20,6) DEFAULT NULL,
    quantity INT DEFAULT NULL,
    status VARCHAR(50) DEFAULT NULL,
    is_active BOOLEAN NOT NULL DEFAULT 1,
    created_by VARCHAR(64) NOT NULL,
    created_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated_by VARCHAR(64) NOT NULL,
    last_updated_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version INT NOT NULL DEFAULT 1,
    CONSTRAINT TBP_TBPD_FK_2 FOREIGN KEY (toughen_batch_process_id) REFERENCES toughen_batch_process (id),
    CONSTRAINT PI_JBC_FK_1 FOREIGN  KEY (glass_thickness_id) REFERENCES glass_thickness (id)
);
-- -----------------------------------------------------
-- Commit transaction
-- -----------------------------------------------------
COMMIT;
