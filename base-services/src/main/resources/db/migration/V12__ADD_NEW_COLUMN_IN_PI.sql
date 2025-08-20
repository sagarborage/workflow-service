ALTER TABLE pro_forma_invoice
ADD prox_gst_charges FLOAT NULL DEFAULT NULL,
ADD is_prox_gst_applicable TINYINT(1) NOT NULL DEFAULT 0;