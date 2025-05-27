-- ==================================================================
-- Author:        Vithoba Hipparkar
-- Create date:   16/05/2025
-- Description:   Initial database setup
-- File Name:     V1020__create_table_gate_pass_details.sql
-- ==================================================================

-- -----------------------------------------------------
-- Begin transaction
-- -----------------------------------------------------

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+05:30";

-- -----------------------------------------------------
-- Table Structure for `gate_pass_details`
-- -----------------------------------------------------
CREATE TABLE gate_pass_details (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid CHAR(36) UNIQUE NOT NULL DEFAULT (UUID()),
    Proforma_invoice_item_id INT(10) UNSIGNED NOT NULL,
    gate_pass_id INT(10) UNSIGNED NOT NULL,
    gate_pass_qty INT NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT 1,
    created_by VARCHAR(64) NOT NULL,
    created_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated_by VARCHAR(64) NOT NULL,
    last_updated_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version INT NOT NULL DEFAULT 1,
    FOREIGN KEY (gate_pass_id) REFERENCES gate_pass (id),
    FOREIGN KEY (Proforma_invoice_item_id) REFERENCES pro_forma_invoice_item (id)
);
-- -----------------------------------------------------
-- Commit transaction
-- -----------------------------------------------------
COMMIT;
