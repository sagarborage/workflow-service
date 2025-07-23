-- ==================================================================
-- Author:        Vithoba Hipparkar
-- Create date:   16/05/2025
-- Description:   Initial database setup
-- File Name:     V1019__create_table_jb_creation.sql
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
CREATE TABLE gate_pass (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid CHAR(36) UNIQUE NOT NULL DEFAULT (UUID()),
    tenant_id INT(10) UNSIGNED NOT NULL,
    company_id INT(10) UNSIGNED NOT NULL,
    party_company_id INT(10) UNSIGNED NOT NULL,
    proforma_invoice_id INT(10) UNSIGNED NOT NULL,
    gate_pass_no INT NOT NULL DEFAULT 1,
    driver_name VARCHAR(64) DEFAULT NULL,
    vehicle_no VARCHAR(64) DEFAULT NULL,
    driver_contact_no VARCHAR(16) DEFAULT NULL,
    is_active BOOLEAN NOT NULL DEFAULT 1,
    created_by VARCHAR(64) NOT NULL,
    created_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated_by VARCHAR(64) NOT NULL,
    last_updated_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version INT NOT NULL DEFAULT 1,
    FOREIGN KEY (tenant_id) REFERENCES tenant (id),
    FOREIGN KEY (company_id) REFERENCES tenant (id),
    FOREIGN KEY (party_company_id) REFERENCES tenant (id),
    FOREIGN KEY (proforma_invoice_id) REFERENCES pro_forma_invoice (id)
);
-- -----------------------------------------------------
-- Commit transaction
-- -----------------------------------------------------
COMMIT;
