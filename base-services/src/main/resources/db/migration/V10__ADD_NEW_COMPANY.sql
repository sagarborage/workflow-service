INSERT INTO `company` (`id`,`uuid`, `tenant_id`, `company_type_id`, `company_name`, `CIN`, `GSTIN`, `TAN`, `PAN`, `created_by`, `last_updated_by`, `is_active`) VALUES
(2000,UUID(), 1, 1, 'HIMANSHU GLASS WORK', '', '', '', '', 'ADMIN', 'ADMIN', 1);

INSERT INTO `address` (`uuid`, `company_id`, `address_type_id`, `address_line1`, `address_line2`, `address_line3`, `city`, `state_code`, `country_code`, `pin_code`, `work_phone`, `contact_person`, `aadhaar`, `fax`, `primary_phone_number`, `alternate_phone_number`, `email`, `website`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 2000, 1, 'HIMANSHU GLASS WORK', 'Datta Nagar', NULL, 'Bamnoli', 'MH', 'IN', '416436', NULL, 'Admin', NULL, NULL,'9876543210', NULL, NULL, NULL, 'ADMIN', 'ADMIN', 1);
