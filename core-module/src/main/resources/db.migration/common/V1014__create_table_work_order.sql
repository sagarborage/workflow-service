-- ==================================================================
-- Author:        Vithoba Hipparkar
-- Create date:   16/05/2025
-- Description:   Initial database setup
-- File Name:     V1014__create_table_work_order.sql
-- ==================================================================

-- -----------------------------------------------------
-- Begin transaction
-- -----------------------------------------------------

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+05:30";

-- -----------------------------------------------------
-- Table Structure for `work_order`
-- -----------------------------------------------------
CREATE TABLE work_order (
   id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
   uuid varchar(36) NOT NULL DEFAULT (UUID()),
   proforma_invoice_id int(10) UNSIGNED NOT NULL,
   tenant_id int(10) UNSIGNED NOT NULL,
   firm_id int(10) UNSIGNED NOT NULL,
   is_active BOOLEAN NOT NULL DEFAULT 1,
   created_by VARCHAR(64) NOT NULL,
   created_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),
   last_updated_by VARCHAR(64) NOT NULL,
   last_updated_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP) ON UPDATE CURRENT_TIMESTAMP,
   version INT NOT NULL DEFAULT 1,
   UNIQUE KEY ONE_WORK_ORDER_PER_PI (tenant_id, firm_id, proforma_invoice_id),
   CONSTRAINT work_order_tenant_ibfk_1
   FOREIGN KEY (tenant_id) REFERENCES tenant (id),
   FOREIGN KEY (proforma_invoice_id) REFERENCES pro_forma_invoice (id),
   FOREIGN KEY (firm_id) REFERENCES company (id)
 );
-- -----------------------------------------------------
-- Commit transaction
-- -----------------------------------------------------
COMMIT;
