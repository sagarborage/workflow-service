-- Rename columns in MariaDB
ALTER TABLE pro_forma_invoice CHANGE COLUMN urgency_percent prox_sqft FLOAT DEFAULT 0;
ALTER TABLE pro_forma_invoice CHANGE COLUMN urgency_percent_amount prox_per_sqft_rate FLOAT DEFAULT 0;

-- Add new column
ALTER TABLE pro_forma_invoice ADD COLUMN prox_charges FLOAT DEFAULT 0;
