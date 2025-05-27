-- ==================================================================
-- Author:        Vithoba Hipparkar
-- Create date:   16/05/2025
-- Description:   Initial database setup
-- File Name:     V1017__create_table_glass_breakage_details.sql
-- ==================================================================

-- -----------------------------------------------------
-- Begin transaction
-- -----------------------------------------------------

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+05:30";

-- -----------------------------------------------------
-- Table Structure for `glass_breakage_details`
-- -----------------------------------------------------
CREATE TABLE glass_breakage_details (
     id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
     uuid CHAR(36) UNIQUE NOT NULL DEFAULT (UUID()),
     tenant_id int(10) UNSIGNED NOT NULL,
     proforma_invoice_id int(10) UNSIGNED NOT NULL,
     proforma_invoice_item_id int(10) UNSIGNED NOT NULL,
     dept_name varchar(50) NOT NULL,
     details varchar(500) DEFAULT NULL,
     is_active BOOLEAN NOT NULL DEFAULT 1,
     created_by VARCHAR(64) NOT NULL,
     created_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),
     last_updated_by VARCHAR(64) NOT NULL,
     last_updated_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP) ON UPDATE CURRENT_TIMESTAMP,
     version INT NOT NULL DEFAULT 1,
     CONSTRAINT T_GBD_FK_1 FOREIGN KEY (tenant_id) REFERENCES tenant (id),
     CONSTRAINT PI_GBD_FK_1 FOREIGN KEY (proforma_invoice_id) REFERENCES pro_forma_invoice (id),
     CONSTRAINT PII_GBD_FK_1 FOREIGN KEY (proforma_invoice_item_id) REFERENCES pro_forma_invoice_item (id)
    );
-- -----------------------------------------------------
-- Commit transaction
-- -----------------------------------------------------
COMMIT;
