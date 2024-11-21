INSERT INTO `tenant` (`uuid`, `tenant_name`, `address`, `city`, `state`, `country_id`, `pin_code`, `phone_number`, `email_id`, `activation_date`, `expiry_date`, `grace_period`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 'Test Company', 'Test Address', 'Sangola,Pune', 'Maharashtra', 1, '411028', '9049989010', 'test@gmail.com', '2022-12-02 00:00:00', '2050-05-02 00:00:00', 31, 'ADMIN', 'ADMIN', 1);

INSERT INTO `address_type` (`tenant_id`, `type`, `description`, `uuid`, `created_by`, `last_updated_by`, `is_active`) VALUES
(1, 'BILLING', 'Billing address', UUID(), 'ADMIN', 'ADMIN', 1),
(1, 'SHIPPING', 'Shipping address', UUID(), 'ADMIN', 'ADMIN', 1),
(1, 'BUSINESS', 'Business address', UUID(), 'ADMIN', 'ADMIN', 1),
(1, 'HOME', 'Home address', UUID(), 'ADMIN', 'ADMIN', 1);

INSERT INTO `pi_type` ( `uuid`, `tenant_id`, `pi_type_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'MM', '2023-09-09 14:11:15', '2023-09-09 14:11:15', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'SQFT', '2023-09-09 14:11:25', '2023-09-09 14:11:25', 'ADMIN', 'ADMIN', 1);

-- UUID()

INSERT INTO `company_type` (`uuid`, `tenant_id`, `type`, `description`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'Self', 'subsidiary', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'Tri-Party', 'Customer', 'ADMIN', 'ADMIN', 1);

INSERT INTO `role_type` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'Optimize', '2023-10-19 10:18:35', '2023-10-19 10:18:36', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'Cutting', '2023-10-19 10:18:35', '2023-10-19 10:18:36', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'Toughen', '2023-10-19 10:18:35', '2023-10-19 10:18:36', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'Dispatch', '2023-10-19 10:18:35', '2023-10-19 10:18:36', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'ProformaInvoice', '2023-10-19 10:18:35', '2023-10-19 10:18:36', 'ADMIN', 'ADMIN', 1);

INSERT INTO `company` (`uuid`, `tenant_id`, `company_type_id`, `company_name`, `CIN`, `GSTIN`, `TAN`, `PAN`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
('2e5b17ca-27be-11ef-9241-e14cd64d021f', 1, 1, 'HIMYOUG TUFF GLASS INDUSTRIES PVT. LTD.', 'CIN0000001', 'GSTIN0000000', 'TAN0000000', 'PAN0000000', '2023-09-09 14:39:57', '2023-09-09 14:39:57', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 2, 'Test party one', 'CIN0000001', 'GSTIN0000000', 'TAN0000000', 'PAN0000000', '2023-09-09 14:39:57', '2023-09-09 14:39:57', 'ADMIN', 'ADMIN', 1);

INSERT INTO `address` (`uuid`, `company_id`, `address_type_id`, `address_line1`, `address_line2`, `address_line3`, `city`, `state_code`, `country_code`, `pin_code`, `work_phone`, `contact_person`, `aadhaar`, `fax`, `primary_phone_number`, `alternate_phone_number`, `email`, `website`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 1, 'Test address one', NULL, NULL, NULL, 'MH', '1234', '123', NULL, 'Admin', '394395905436', NULL, '9049989010', '9049989010', NULL, NULL, '2023-09-22 12:27:41', '2023-09-22 12:27:41', 'ADMIN', 'ADMIN', 1),
(UUID(), 2, 1, 'Test address two', NULL, NULL, NULL, 'MH', '1234', '123', NULL, 'Admin', '394395905437', NULL, '9049989010', '9049989010', NULL, NULL, '2023-09-22 12:27:41', '2023-09-22 12:27:41', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'BLOCK', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'TEPAR (BLOCK)', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'DOOR', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'DRAWING', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'FR (DRAWING)', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'TEPER DRAWING', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'FR (BLOCK)', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'TEPER (DOOR)', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'round', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'ST-167 TOUGHEN GLASS', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'FR (DOOR)', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '5MM OPAL PEARL GREY', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'ROUND DRAWING', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'ST 750', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'rubika', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'HALF ROUND', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'HS TOUGHEND 6MM', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'HS TOUGHEND 8MM', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'BROWN GLASS', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'FR ROUND', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'KACHA GLASS', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'KACHA GLASS BLOCK', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '6 MM MIRAR', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'BROWN GLASS DRAWING', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '5MM MIRROR', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '5MM FOREST GREEN', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '5MM GRAY MIRROR', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'Brown Tinted Block', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'Brown Glass Block', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '5 MM BROWN MIRROR', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '5 MM GRAY MIRROR', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'SHEET', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'CLEAR', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'BROWN TINTED FR DR', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'BROWN GLASS DOOR', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'BROWN TINTED DRAWING', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '12MM GRAY TINTED BLOCK', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'KACHI FLUTED CLASS', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'FOREST GREEN', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'GRAY TINTED', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'TEPAR BROWN TINTED', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'BROWN TINTED', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'GRAY TINTED DRAWING', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '5MM DARK BLUE BLOCK', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', 'ADMIN', 1);


INSERT INTO `glass_thickness` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '12MM', '2023-09-10 12:13:23', '2023-09-10 12:13:23', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_thickness` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '10MM', '2023-09-10 12:13:23', '2023-09-10 12:13:23', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_thickness` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '8MM', '2023-09-10 12:13:23', '2023-09-10 12:13:23', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_thickness` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '6MM', '2023-09-10 12:13:23', '2023-09-10 12:13:23', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_thickness` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '5MM', '2023-09-10 12:13:23', '2023-09-10 12:13:23', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_thickness` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '4MM', '2023-09-10 12:13:23', '2023-09-10 12:13:23', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_thickness` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '3.5MM', '2023-09-10 12:13:23', '2023-09-10 12:13:23', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_thickness` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, '3MM', '2023-09-10 12:13:23', '2023-09-10 12:13:23', 'ADMIN', 'ADMIN', 1);


INSERT INTO `glass_specification` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'TOUGHEN GLASS(HSN:7007)', '2023-09-10 12:15:10', '2023-09-10 12:15:10', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_specification` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'MIRROR GLASS(HSN:7009)', '2023-09-10 12:15:10', '2023-09-10 12:15:10', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_specification` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'BROWN MIRROR', '2023-09-10 12:15:10', '2023-09-10 12:15:10', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_specification` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'BLUE ONE WAY', '2023-09-10 12:15:10', '2023-09-10 12:15:10', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_specification` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'LAMINATION', '2023-09-10 12:15:10', '2023-09-10 12:15:10', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_specification` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'EXTRA CLEAR TOUGHEN GLASS', '2023-09-10 12:15:10', '2023-09-10 12:15:10', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_specification` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'ST-167 TOUGHEN GLASS', '2023-09-10 12:15:10', '2023-09-10 12:15:10', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_specification` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'GRAY MIRROR', '2023-09-10 12:15:10', '2023-09-10 12:15:10', 'ADMIN', 'ADMIN', 1);

INSERT INTO `service_rate` (`uuid`, `tenant_id`, `name`, `rate`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`)
VALUES (UUID(), 1, 'CUT OUT', 100.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'HOLE', 30.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'BIG HOLE', 50.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CORNER ROUND', 50.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'BIG CUT OUT', 500.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CUT OUT', 400.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'BIG CUT OUT', 1500.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CUT OUT 800', 800.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CUT OUT 70', 70.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'FROSTING', 100.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CUT OUT 150', 150.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CHAMFER', 50.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'ROUND', 250.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'BIG CUT OUT 700', 700.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CORNER', 20.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'BIG HOLE 100', 100.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'BIG HOLE', 1000.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'BIG HOLE', 750.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'ETCHING', 55.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CUT OUT 200', 200.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CUT OUT 300', 300.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CUT OUT 250', 250.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'KATRA', 60.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'ROUND 100', 100.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'ROUND 50', 50.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'ROUND 30', 30.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'BIG CUT OUT 1000', 1000.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CUT OUT 600', 600.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'KATRA', 45.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'HOLE', 35.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'HARDWARE AND HANDLING CHARGES', 40.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'BIG HOLE', 200.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'POLISH', 12.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'ROUND 200', 200.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CUTOUT 250', 250.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'ROUND', 800.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'HOLE 40', 40.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CUT OUT 130', 130.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CORNER ROUND 12', 12.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'POLISH CHARGE', 10.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CUTOUT', 125.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'BIG HOLE', 300.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'HOLE 300', 300.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'HOLE', 25.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'ROUND 150', 150.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CUT OUT 400', 400.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CUT OUT 500', 500.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CUT OUT 350', 350.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'HOLE 50', 50.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CORNER ROUND 25', 25.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'FROSTING 80', 80.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'FROSTING 40', 40.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CUT OUT', 118.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'HOLE 35', 35.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'BIG CUTOUT', 2000.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'HOLE 22', 22.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'BIG HOLE', 270.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CORNER ROUND 15', 15.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'CUT OUT 225', 225.00, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1);


INSERT INTO `additional_charges` (`uuid`, `tenant_id`, `extra_mm`, `insurance`, `admin_charges`, `forwarding_charges`, `gst`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 11.00, 11.00, 11.00, 11.00, 11.00, '2023-10-17 11:45:46', '2023-10-17 11:45:48', 'ADMIN', 'ADMIN', 1);

INSERT INTO `user_auth` (`id`, `uuid`, `tenant_id`, `role_id`, `first_name`, `last_name`, `phone`, `username`, `password_hash`, `is_enabled`, `is_email_verified`, `is_phone_verified`, `is_account_non_expired`,
`is_account_non_locked`, `is_credentials_non_expired`, `failed_attempt`, `is_active`, `created_by`, `created_datetime`, `last_updated_by`, `last_updated_datetime`, `version`) VALUES
(1, UUID(), 1, 1, 'optimize', 'User1', '9322861801', 'optimize', '$2a$12$lpzxVmw2gj4oEOpSAqwYpOVxC9W1bnGYoTXE3ndr1Se51oRKF/3QK', 0, 0, 0, 1, 1, 1, 1, 1, 'System', '2024-01-17 23:24:45', 'System', '2024-01-17 23:25:58', 1),
(2, UUID(), 1, 2, 'cutting', 'User2', '9322861802', 'cutting', '$2a$12$lpzxVmw2gj4oEOpSAqwYpOVxC9W1bnGYoTXE3ndr1Se51oRKF/3QK', 0, 0, 0, 1, 1, 1, 1, 1, 'System', '2024-01-17 23:24:45', 'System', '2024-01-17 23:25:58', 1),
(3, UUID(), 1, 3, 'toughen', 'User3', '9322861803', 'toughen', '$2a$12$lpzxVmw2gj4oEOpSAqwYpOVxC9W1bnGYoTXE3ndr1Se51oRKF/3QK', 0, 0, 0, 1, 1, 1, 1, 1, 'System', '2024-01-17 23:24:45', 'System', '2024-01-17 23:25:58', 1),
(4, UUID(), 1, 4, 'dispatch', 'User4', '9322861804', 'dispatch', '$2a$12$lpzxVmw2gj4oEOpSAqwYpOVxC9W1bnGYoTXE3ndr1Se51oRKF/3QK', 0, 0, 0, 1, 1, 1, 1, 1, 'System', '2024-01-17 23:24:45', 'System', '2024-01-17 23:25:58', 1),
(5, UUID(), 1, 5, 'Vinod', 'Thorat', '9322861805', 'vinod', '$2a$12$lpzxVmw2gj4oEOpSAqwYpOVxC9W1bnGYoTXE3ndr1Se51oRKF/3QK', 0, 0, 0, 1, 1, 1, 1, 1, 'System', '2024-01-17 23:24:45', 'System', '2024-01-17 23:25:58', 1);

INSERT INTO `user_profile` (`id`, `uuid`, `user_id`, `first_name`, `last_name`, `address`, `gender`, `dob`, `profile_url`, `profile_back_url`, `is_active`, `created_by`, `created_datetime`, `last_updated_by`, `last_updated_datetime`, `version`) VALUES
(1, UUID(), 1, 'Optimize', 'Borage', NULL, NULL, NULL, NULL, NULL, 1, 'System', '2024-01-17 23:24:45', 'System', '2024-01-18 10:30:44', 1),
(2, UUID(), 2, 'Cutting', 'Borage', NULL, NULL, NULL, NULL, NULL, 1, 'System', '2024-01-17 23:24:45', 'System', '2024-01-17 23:25:58', 1),
(3, UUID(), 3, 'Toughen', 'Borage', NULL, NULL, NULL, NULL, NULL, 1, 'System', '2024-01-17 23:24:45', 'System', '2024-01-17 23:25:58', 1),
(4, UUID(), 4, 'Dispatch', 'Borage', NULL, NULL, NULL, NULL, NULL, 1, 'System', '2024-01-17 23:24:45', 'System', '2024-01-17 23:25:58', 1),
(5, UUID(), 5, 'ProformaInvoice', 'Borage', NULL, NULL, NULL, NULL, NULL, 1, 'System', '2024-01-17 23:24:45', 'System', '2024-01-17 23:25:58', 1);


INSERT INTO `confirm_through` (`uuid`, `tenant_id`, `name`, `created_datetime`, `last_updated_datetime`, `created_by`, `last_updated_by`, `is_active`) VALUES
(UUID(), 1, 'Self', '2023-10-27 10:04:23', '2023-10-27 10:04:23', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'Call', '2023-10-27 10:04:23', '2023-10-27 10:04:23', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'Whatsapp', '2023-10-27 10:04:23', '2023-10-27 10:04:23', 'ADMIN', 'ADMIN', 1),
(UUID(), 1, 'Text', '2023-10-27 10:04:23', '2023-10-27 10:04:23', 'ADMIN', 'ADMIN', 1);

INSERT INTO `email_templates` ( `uuid`, `template_code`, `name`, `subject`, `content`, `is_active`, `created_by`, `created_datetime`, `last_updated_by`, `last_updated_datetime`, `version`) VALUES ( 'da52b9a4-cdf3-403b-8cba-57c9bebbc9c5', 'welcome001', 'Welcome Email', 'Confirm your Account', '<!DOCTYPE html>\n<html lang="en">\n<head>\n    <meta charset="UTF-8">\n    <meta name="viewport" content="width=device-width, initial-scale=1.0">\n    <title>Welcome Email</title>\n</head>\n<body style="font-family: \'Georgia\', serif; margin: 0; padding: 0; padding-bottom: 20px; background-color: transparent;">\n\n    <div style="width: 100%; margin: 0 auto; overflow: hidden; background-color: #ECF0F1; border-bottom: 2px solid #BDC3C7;">\n        <header style="background: #F1C40F; color: white; padding-top: 30px; min-height: 70px;">\n            <input type="checkbox" id="menu-btn" style="float: right; display: none;">\n            <label for="menu-btn" class="menu-icon" style="display: none;"></label>\n            <nav style="float: right; display: inline; margin-top: 10px; margin-right: 20px;">\n                <center>\n                    <h1 style="font-family: \'Georgia\', serif; color: #000000; padding-bottom: 10px; margin: 0;">Welcome to DMD jewellers, {userName}.</h1>\n                </center>\n            </nav>\n        </header>\n    </div>\n\n    <div style="width: 100%; margin: 0 auto; border: 2px solid #BDC3C7; padding: 10px;">\n        <p style="font-size: 16px; line-height: 1.5; margin-left: 20px;">\n            Dear {userName},<br/><br/>\n            Welcome to DMD! We\'re excited to have you join our community. Please confirm your account and establish your password by following the instructions below.<br/><br/>\n            <strong>Confirmation Code: </strong>{code}<br/><br/>\n            <strong>Instructions:</strong><br/>\n            1. Click on the following link to confirm your account: <a href="{link}" target="_blank" style="font-family: \'Georgia\', serif; color: #007BFF; text-decoration: underline;">Confirmation Link</a><br/>\n            2. Once confirmed, you\'ll be prompted to set your password securely.<br/>\n            3. Select a robust password that addresses our security guidelines.<br/>\n            4. If you encounter any challenges or have questions, reach out to our support team at:\n            <a href="mailto:support@dmd.com" style="font-family: \'Georgia\', serif; color: #007BFF; text-decoration: mailto:underline;">support@dmd.com</a>.<br/>\n            Thank you for choosing DMD. We\'re eager to support you every step of the way!<br/><br/>\n            Thank you,<br/>\n            The DMD Jewellers Team\n        </p>\n    </div>\n</body>\n</html>', 1, 'abhijit', '2023-12-19 17:12:13', 'abhijit', '2023-12-23 17:23:32', 1),
 (UUID(), 'regenerate001', 'Regenerate Confirmation Code', 'Your confirmation code', '<!DOCTYPE html>\r\n<html lang="en">\r\n<head>  \r\n  <meta charset="UTF-8">  \r\n    <meta name="viewport" content="width=device-width, initial-scale=1.0"> \r\n       <title>Email Confirmation</title>\r\n       </head>\r\n       <body style="font-family: Arial, Helvetica, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;"> \r\n          <div style="max-width: 600px; margin: 20px auto; background-color: #ffffff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);">  \r\n                <h2 style="color: #333333;">Email Confirmation</h2>  \r\n                 <p style="color: #666666;">Thank you for using our service. Your confirmation code is: <br/><br/> \r\n           <strong style="font-size: 24px; font-weight: bold; color: #4285f4;">{code}</strong></p>  \r\n             <p style="color: #666666;">Please use the code to complete your registration process.</p>   \r\n           <a href="{link}" target="_blank" style="display: inline-block; padding: 10px 20px; font-size: 16px; text-align: center; text-decoration: none; background-color: #4285f4; color: #ffffff; border-radius: 5px; cursor: pointer;">Confirm Now</a>    \r\n           <p style="color: #666666;">If you did not request this code, please ignore this email.</p>  \r\n                  </div>\r\n                  </body>\r\n                   </html>', 1, 'abhijit', '2023-12-19 17:12:29', 'abhijit', '2023-12-23 17:23:33', 1),
 (UUID(), 'changePassword001', 'Change Password', 'Password Change Confirmation', '<!DOCTYPE html>\r\n<html lang="en">\r\n\r\n<head>\r\n    <meta charset="UTF-8">\r\n    <meta http-equiv="X-UA-Compatible" content="IE=edge">\r\n    <meta name="viewport" content="width=device-width, initial-scale=1.0">\r\n    <title>Password Change Confirmation</title>\r\n</head>\r\n\r\n<body style="font-family: \'Arial\', sans-serif; line-height: 1.6; color: #333; background-color: #f5f5f5; margin: 0; padding: 0;">\r\n\r\n    <div style="max-width: 600px; margin: 20px auto; padding: 20px; background-color: #fff; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);">\r\n        <h2 style="color: #007BFF;">Password Change Confirmation</h2>\r\n        <p>Hello {userName},</p>\r\n        <p>Your password has been successfully changed.</p>\r\n        <p>If you did not make this change, please contact our support team immediately.</p>\r\n        <p>Thank you for using our service!</p>\r\n        <a href="{link}" target="_blank" style="display: inline-block; padding: 10px 20px; font-size: 16px; text-decoration: none; background-color: #007BFF; color: #fff; border-radius: 5px;">Visit Our Website</a>\r\n    </div>\r\n\r\n</body>\r\n\r\n</html>\r\n', 1, 'abhijit', '2023-12-21 16:23:34', 'abhijit', '2023-12-23 17:23:35', 1),
 (UUID(), 'setPassword001', 'Password Reset', 'Password reset', '<!DOCTYPE html>\r\n<html lang="en">\r\n\r\n<head>\r\n    <meta charset="UTF-8">\r\n    <meta name="viewport" content="width=device-width, initial-scale=1.0">\r\n</head>\r\n\r\n<body style="margin: 0; padding: 0; font-family: Arial, sans-serif; line-height: 1.6;">\r\n\r\n    <!-- Container -->\r\n    <div style="max-width: 600px; margin: 0 auto; padding: 20px;">\r\n\r\n        <!-- Header -->\r\n        <div style="background-color: #3498db; color: #ffffff; text-align: center; padding: 10px;">\r\n            <h1>Password Reset</h1>\r\n        </div>\r\n\r\n        <!-- Content -->\r\n        <div style="padding: 20px; background-color: #f4f4f4;">\r\n\r\n            <p>Hello {userName},</p>\r\n            <p>We received a request to reset your password. Click the button below to reset it:</p>\r\n            <p>\r\n                <a href="{link}" target="_blank" style="display: inline-block; padding: 10px 20px; background-color: #3498db; color: #ffffff; text-decoration: none; border-radius: 5px;">Reset Password</a>\r\n            </p>\r\n            <p>If you didn\'t request a password reset, please ignore this email.</p>\r\n\r\n        </div>\r\n\r\n        <!-- Footer -->\r\n        <div style="text-align: center; margin-top: 20px; color: #777;">\r\n            <p>Best regards,<br>DMD</p>\r\n        </div>\r\n\r\n    </div>\r\n\r\n</body>\r\n\r\n</html>\r\n', 1, 'abhijit', '2023-12-22 14:48:33', 'abhijit', '2023-12-23 17:23:37', 1),
 (UUID(), 'emailVerification001', 'Email Confirmation', 'Email Verification', '<!DOCTYPE html>\r\n<html lang="en">\r\n<head>\r\n    <meta charset="UTF-8">\r\n    <meta name="viewport" content="width=device-width, initial-scale=1.0">\r\n    <title>Email Verification</title>\r\n</head>\r\n<body style="font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;">\r\n\r\n    <div style="width: 100%; max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; box-sizing: border-box; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); margin-top: 20px;">\r\n\r\n        <h2 style="color: #333333;">Email Verification</h2>\r\n\r\n        <p style="color: #555555;">Dear user,</p>\r\n\r\n        <p style="color: #555555;">Thank you for signing up. To verify your email, please click the following link:</p>\r\n\r\n        <a style="display: inline-block; padding: 10px 20px; text-decoration: none; background-color: #4caf50; color: #ffffff; border-radius: 5px;" href="{link}">Verify Email</a>\r\n\r\n        <p style="color: #555555;">Alternatively, open the following url in your browser:</p>\r\n\r\n        <p style="color: #555555;">https://example.com/verify?token={code}</p>\r\n\r\n        <p style="color: #555555;">This verification link will expire in 24 hours.</p>\r\n\r\n        <div style="margin-top: 20px; text-align: center; color: #888888;">\r\n            <p>If you did not sign up for this service, please ignore this email.</p>\r\n        </div>\r\n\r\n    </div>\r\n\r\n</body>\r\n</html>\r\n', 1, 'abhijit', '2023-12-23 14:21:32', 'abhijit', '2023-12-23 17:23:38', 1),
 (UUID(), 'emailUpdateVerification001', 'Email Update Verification', 'Email Address Update', '<!DOCTYPE html>\r\n<html lang="en">\r\n<head>\r\n    <meta charset="UTF-8">\r\n    <meta name="viewport" content="width=device-width, initial-scale=1.0">\r\n    <title>Email Update Verification</title>\r\n</head>\r\n<body style="font-family: \'Arial\', sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; color: #333;">\r\n\r\n    <div style="max-width: 600px; margin: 20px auto; background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);">\r\n        <h2 style="color: #333;">Email Address Update</h2>\r\n        <p>You are just a step away from updating your email address.</p>\r\n        <p>We are sharing a one-time use verification code (OTP) for this request.</p>\r\n        \r\n        <div style="font-size: 24px; font-weight: bold; color: #3498db;">Verification Code (OTP): {code}</div>\r\n        <p>Validity: 10 minutes</p>\r\n        <p style="color: #e74c3c; font-weight: bold;">Important note:</p>\r\n        <p>Please do not share this code with anyone for the security of your DMD account.</p>\r\n\r\n        <p>Thanks,<br>DMD</p>\r\n    </div>\r\n\r\n</body>\r\n</html>\r\n', 1, 'abhijit', '2023-12-24 11:42:46', 'abhijit', '2023-12-24 11:44:11', 1);
