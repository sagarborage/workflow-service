-- ==================================================================
-- Author:        Vithoba Hipparkar
-- Create date:   16/05/2025
-- Description:   Initial database setup
-- File Name:     V1016__create_table_toughen_batch_process_details.sql
-- ==================================================================

-- -----------------------------------------------------
-- Begin transaction
-- -----------------------------------------------------

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+05:30";

-- -----------------------------------------------------
-- Table Structure for `toughen_batch_process_details`
-- -----------------------------------------------------
CREATE TABLE toughen_batch_process_details (
     id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
     uuid CHAR(36) UNIQUE NOT NULL DEFAULT (UUID()),
     proforma_invoice_id int(10) UNSIGNED NOT NULL,
     work_order_id int(10) UNSIGNED NOT NULL,
     proforma_invoice_item_id int(10) UNSIGNED NOT NULL,
     toughen_batch_process_id int(10) UNSIGNED NOT NULL,
     sticker_number int(5) NOT NULL,
     status varchar(50) DEFAULT NULL,
     is_active BOOLEAN NOT NULL DEFAULT 1,
     created_by VARCHAR(64) NOT NULL,
     created_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),
     last_updated_by VARCHAR(64) NOT NULL,
     last_updated_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP) ON UPDATE CURRENT_TIMESTAMP,
     version INT NOT NULL DEFAULT 1,
     CONSTRAINT PI_TBPD_FK_1 FOREIGN KEY (proforma_invoice_id) REFERENCES pro_forma_invoice (id),
     CONSTRAINT WO_TBPD_FK_1 FOREIGN KEY (work_order_id) REFERENCES work_order (id),
     CONSTRAINT PII_TBPD_FK_1 FOREIGN KEY (proforma_invoice_item_id) REFERENCES pro_forma_invoice_item (id),
     CONSTRAINT TBP_TBPD_FK_1 FOREIGN KEY (toughen_batch_process_id) REFERENCES toughen_batch_process (id)
    );
-- -----------------------------------------------------
-- Commit transaction
-- -----------------------------------------------------
COMMIT;
