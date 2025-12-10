ALTER TABLE pro_forma_invoice
ADD COLUMN is_gst_applicable bit NOT NULL DEFAULT 0;
update pro_forma_invoice set is_gst_applicable = 1 where pi_type_id=1;