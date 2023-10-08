INSERT INTO `tenant` (`uuid`, `tenant_name`, `address`, `city`, `state`, `country_id`, `pin_code`, `phone_number`, `email_id`, `activation_date`, `expiry_date`, `grace_period`, `is_active`) VALUES
("7977ff91-64d4-490b-914f-61bbacf75f0f", 'Test Company', 'Test Address', 'Sangola,Pune', 'Maharashtra', 1, '411028', '9049989010', 'test@gmail.com', '2022-12-02 00:00:00', '2050-05-02 00:00:00', 31, 1);

INSERT INTO `address_type` (`tenant_id`, `type`, `description`, `uuid`, `is_active`) VALUES
(1, 'HOME', 'Home address', "d0328fb0-3c0d-11ee-962a-14abc5fbd6fa", 1),
(1, 'BUSINESS', 'Business address', "d0329283-3c0d-11ee-962a-14abc5fbd6fa", 1),
(1, 'BILLING', 'Billing address', "d032961b-3c0d-11ee-962a-14abc5fbd6fa", 1),
(1, 'SHIPPING', 'Shipping address', "d0329742-3c0d-11ee-962a-14abc5fbd6fa", 1);

INSERT INTO `pi_type` ( `uuid`, `tenant_id`, `pi_type_name`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('a5596194-a483-4240-aa73-3448c78176a1', 1, 'MM', '2023-09-09 14:11:15', '2023-09-09 14:11:15', 'ADMIN', NULL, 1),
('16254a71-ac1d-4d68-8200-85c813426af3', 1, 'SQFT', '2023-09-09 14:11:25', '2023-09-09 14:11:25', 'ADMIN', NULL, 1);

--UUID()

INSERT INTO `company_type` (`uuid`, `tenant_id`, `type`, `description`, `is_active`) VALUES
("d03351ec-3c0d-11ee-962a-14abc5fbd6fa", 1, 'Self', 'subsidiary', 1),
("d0335437-3c0d-11ee-962a-14abc5fbd6fa", 1, 'Tri-Party', 'Customer', 1);

INSERT INTO `company` (`uuid`, `tenant_id`, `company_type_id`, `company_name`, `CIN`, `GSTIN`, `TAN`, `PAN`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('584bd17c-89a3-46ec-833b-f4502f046de4', 1, 1, 'Self test Company', 'CIN0000001', 'GSTIN0000000', 'TAN0000000', 'PAN0000000', '2023-09-09 14:39:57', '2023-09-09 14:39:57', 'ADMIN', NULL, 1);

INSERT INTO `company` (`uuid`, `tenant_id`, `company_type_id`, `company_name`, `CIN`, `GSTIN`, `TAN`, `PAN`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('2dc71856-ce6f-41a0-9732-ef97ae85eaf9', 1, 2, 'Tripart test Company', 'CIN0000001', 'GSTIN0000000', 'TAN0000000', 'PAN0000000', '2023-09-09 14:39:57', '2023-09-09 14:39:57', 'ADMIN', NULL, 1);

INSERT INTO `address` (`uuid`, `company_id`, `address_type_id`, `address_line1`, `address_line2`, `address_line3`, `city`, `state_code`, `country_code`, `pin_code`, `work_phone`, `fax`, `primary_phone_number`, `alternate_phone_number`, `email`, `website`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('28efd0b3-a0ba-4805-b432-5543013c0e73', 1, 2, 'Hadapsar, near Dmart', NULL, NULL, NULL, '123', '1234', '123', NULL, NULL, '9049989010', '9049989010', NULL, NULL, '2023-09-22 12:27:41', '2023-09-22 12:27:41', 'ADMIN', 'ADMIN', 1);


INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('8d9c4ba2-7f6d-4635-a6a6-470c8b49f41c', 1, 'Glass Test', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', NULL, 1);

INSERT INTO `glass_thickness` (`uuid`, `tenant_id`, `name`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('a15aa9f7-178a-44b6-b631-267bf4c3ca45', 1, '12MM', '2023-09-10 12:13:23', '2023-09-10 12:13:23', 'ADMIN', NULL, 1);

INSERT INTO `glass_specification` (`uuid`, `tenant_id`, `name`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('94771345-c309-4eb1-8e2b-bf147ec25f3f', 1, 'Test Glass Specification', '2023-09-10 12:15:10', '2023-09-10 12:15:10', 'ADMIN', NULL, 1);



