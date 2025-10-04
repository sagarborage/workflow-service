-- ==================================================================
-- Author:        Vithoba Hipparkar
-- Create date:   16/05/2025
-- Description:   Initial database setup
-- File Name:     V1008__create_table_pro_forma_invoice.sql
-- ==================================================================

-- -----------------------------------------------------
-- Begin transaction
-- -----------------------------------------------------

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+05:30";

-- -----------------------------------------------------
-- Table Structure for `pro_forma_invoice`
-- -----------------------------------------------------
CREATE TABLE pro_forma_invoice (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  uuid CHAR(36) UNIQUE NOT NULL DEFAULT (UUID()),
  tenant_id int(10) UNSIGNED NOT NULL,
  firm_id int(10) UNSIGNED NOT NULL,
  id_bill_to int(10) UNSIGNED NOT NULL,
  id_ship_to int(10) UNSIGNED DEFAULT NULL,
  pi_type_id int(10) UNSIGNED NOT NULL,
  confirm_through_id int(10) UNSIGNED DEFAULT NULL,
  creation_type varchar(10) NOT NULL,
  pi_number varchar(10) NOT NULL,
  invoice_date datetime NOT NULL,
  pro_forma_invoice_amount double DEFAULT NULL,
  service_rate_invoice_amount double DEFAULT NULL,
  basic_amount double DEFAULT NULL,
  admin_charges double DEFAULT NULL,
  insurance_percent DOUBLE(10,2) DEFAULT NULL,
  insurance_percent_amount DOUBLE(10,2) DEFAULT NULL,
  prox_sqft DOUBLE(10,2) DEFAULT NULL,
  prox_per_sqft_rate DOUBLE(10,2) DEFAULT NULL,
  prox_charges DOUBLE(10,2) DEFAULT NULL,
  other_charges double DEFAULT NULL,
  transport_charges double DEFAULT NULL,
  gst_charges DOUBLE(10,2) DEFAULT NULL,
  grand_total DOUBLE(10,2) DEFAULT NULL,
  round_off_amount DOUBLE(10,2) DEFAULT NULL,
  payable_amount DOUBLE(10,2) DEFAULT NULL,
  previous_balance DOUBLE(10,2) DEFAULT NULL,
  adjustment_amount int(10) DEFAULT NULL,
  shipping_address varchar(500) DEFAULT NULL,
  is_prox_gst_applicable BOOLEAN DEFAULT NULL,
  prox_gst_charges DOUBLE(10,2) DEFAULT NULL,
  status varchar(50) DEFAULT NULL,
  status_details varchar(50) DEFAULT NULL,
  is_gst_applicable bit NOT NULL DEFAULT 0,
  is_active BOOLEAN NOT NULL DEFAULT 1,
  created_by VARCHAR(64) NOT NULL,
  created_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP),
  last_updated_by VARCHAR(64) NOT NULL,
  last_updated_datetime TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP) ON UPDATE CURRENT_TIMESTAMP,
  version INT NOT NULL DEFAULT 1,
  KEY tenant_id (tenant_id),
  KEY FK_pro_forma_invoice_pi_type (pi_type_id) USING BTREE,
  KEY FK_pro_forma_invoice_confirm_through (confirm_through_id) USING BTREE,
  CONSTRAINT pro_forma_invoice_tenant_ibfk_1 FOREIGN KEY (tenant_id) REFERENCES tenant (id)
);
-- -----------------------------------------------------
-- Commit transaction
-- -----------------------------------------------------
COMMIT;
