ALTER TABLE pro_forma_invoice
MODIFY COLUMN insurance_percent DOUBLE(10,2),
MODIFY COLUMN insurance_percent_amount DOUBLE(10,2),
MODIFY COLUMN prox_sqft DOUBLE(10,2),
MODIFY COLUMN prox_per_sqft_rate DOUBLE(10,2),
MODIFY COLUMN prox_charges DOUBLE(10,2),
MODIFY COLUMN gst_charges DOUBLE(10,2),
MODIFY COLUMN grand_total DOUBLE(10,2),
MODIFY COLUMN round_off_amount DOUBLE(10,2),
MODIFY COLUMN payable_amount DOUBLE(10,2),
MODIFY COLUMN previous_balance DOUBLE(10,2);
