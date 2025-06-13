-- ==================================================================
-- Author:        Vithoba Hipparkar
-- Create date:   16/05/2025
-- Description:   Initial database setup
-- File Name:     V1009__create_table_pro_forma_invoice_item.sql
-- ==================================================================

-- -----------------------------------------------------
-- Begin transaction
-- -----------------------------------------------------

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+05:30";

-- -----------------------------------------------------
-- Table Structure for `pro_forma_invoice_item`
-- -----------------------------------------------------
CREATE TABLE pro_forma_invoice_item (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  uuid CHAR(36) UNIQUE NOT NULL DEFAULT (UUID()),
  tenant_id int(10) UNSIGNED NOT NULL,
  pro_forma_invoice_id int(10) UNSIGNED NOT NULL,
  glass_type_id int(10) UNSIGNED NOT NULL,
  glass_specification_id int(10) UNSIGNED NOT NULL,
  glass_thickness_id int(10) UNSIGNED NOT NULL,
  width_inch decimal(20,6) DEFAULT NULL,
  width_measurement decimal(20,6) DEFAULT NULL,
  width_measurement_label varchar(20),
  actual_width decimal(20,6) DEFAULT NULL,
  chargeable_width decimal(20,6) DEFAULT NULL,
  height_inch decimal(20,6) DEFAULT NULL,
  height_measurement decimal(20,6) DEFAULT NULL,
  height_measurement_label varchar(20),
  actual_height decimal(20,6) DEFAULT NULL,
  chargeable_height decimal(20,6) DEFAULT NULL,
  extra_mm decimal(20,6) DEFAULT NULL,
  quantity int(10) DEFAULT NULL,
  unit_value decimal(20,6) DEFAULT NULL,
  rate_per_unit decimal(20,6) DEFAULT NULL,
  unit_measurement_label varchar(20),
  amount decimal(20,6) DEFAULT NULL,
  status ENUM('IN_PROGRESS','HOLD','REJECTED','CANCEL','COMPLETED') NOT NULL,
  file_url VARCHAR(500) DEFAULT NULL,
  status_details varchar(50) DEFAULT NULL,
  optimize_bucket int(5) UNSIGNED NOT NULL DEFAULT 0 ,
  cutting_bucket int(5) UNSIGNED NOT NULL DEFAULT 0 ,
  toughen_bucket int(5) UNSIGNED NOT NULL DEFAULT 0 ,
  dispatch_bucket int(5) UNSIGNED NOT NULL DEFAULT 0 ,
  gate_pass_bucket int(5) UNSIGNED NOT NULL DEFAULT 0 ,
  optimize_completed int(5) UNSIGNED NOT NULL DEFAULT 0 ,
  cutting_completed int(5) UNSIGNED NOT NULL DEFAULT 0 ,
  toughen_completed int(5) UNSIGNED NOT NULL DEFAULT 0 ,
  dispatch_completed int(5) UNSIGNED NOT NULL DEFAULT 0 ,
  gate_pass_completed int(5) UNSIGNED NOT NULL DEFAULT 0 ,
  is_active BOOLEAN NOT NULL DEFAULT 1,
  created_by VARCHAR(64) NOT NULL,
  created_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),
  last_updated_by VARCHAR(64) NOT NULL,
  last_updated_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP) ON UPDATE CURRENT_TIMESTAMP,
  version INT NOT NULL DEFAULT 1,
  KEY tenant_id (tenant_id),
  KEY FK_pro_forma_invoice_item_pro_forma_invoice (pro_forma_invoice_id) USING BTREE,
  KEY FK_pro_forma_invoice_item_glass_type (glass_type_id) USING BTREE,
  KEY FK_pro_forma_invoice_item_glass_specification (glass_specification_id) USING BTREE,
  KEY FK_pro_forma_invoice_item_glass_thickness (glass_thickness_id) USING BTREE,
  CONSTRAINT pro_forma_invoice_item_tenant_ibfk_1 FOREIGN KEY (tenant_id) REFERENCES tenant (id)
);
-- -----------------------------------------------------
-- Commit transaction
-- -----------------------------------------------------
COMMIT;
