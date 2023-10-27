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
("lfk35437-3c0d-5643-ythj-56abc5fbd6gj", 1, 'Tri-Party', 'Customer', 1);

INSERT INTO `role_type` (`uuid`, `tenant_id`, `name`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('9b8759db-1684-4fea-aaf3-c3906e43a385', 1, 'Optimize', '2023-10-19 10:18:35', '2023-10-19 10:18:36', 'ADMIN', 'ADMIN', 1),
('8976314d-8e0c-47e1-9646-d96f7525632c', 1, 'Cutting', '2023-10-19 10:18:35', '2023-10-19 10:18:36', 'ADMIN', 'ADMIN', 1),
('39e4136e-5e82-44d5-9940-cf07a012c68d', 1, 'Toughen', '2023-10-19 10:18:35', '2023-10-19 10:18:36', 'ADMIN', 'ADMIN', 1),
('8140a78c-4e69-4e4c-bfea-7663fa3a2534', 1, 'Dispatch', '2023-10-19 10:18:35', '2023-10-19 10:18:36', 'ADMIN', 'ADMIN', 1);


INSERT INTO `company` (`uuid`, `tenant_id`, `company_type_id`, `company_name`, `CIN`, `GSTIN`, `TAN`, `PAN`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('584bd17c-89a3-46ec-833b-f4502f046de4', 1, 1, 'Self Company One', 'CIN0000001', 'GSTIN0000000', 'TAN0000000', 'PAN0000000', '2023-09-09 14:39:57', '2023-09-09 14:39:57', 'ADMIN', NULL, 1);

INSERT INTO `company` (`uuid`, `tenant_id`, `company_type_id`, `company_name`, `CIN`, `GSTIN`, `TAN`, `PAN`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('584bd17c-okld-45fh-34fg-f4502f046de4', 1, 1, 'Self Company Two', 'CIN0000001', 'GSTIN0000000', 'TAN0000000', 'PAN0000000', '2023-09-09 14:39:57', '2023-09-09 14:39:57', 'ADMIN', NULL, 1);


INSERT INTO `company` (`uuid`, `tenant_id`, `company_type_id`, `company_name`, `CIN`, `GSTIN`, `TAN`, `PAN`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('2dc71856-ce6f-41a0-9732-ef97ae85eaf9', 1, 2, 'Tri party test Company', 'CIN0000001', 'GSTIN0000000', 'TAN0000000', 'PAN0000000', '2023-09-09 14:39:57', '2023-09-09 14:39:57', 'ADMIN', NULL, 1);

INSERT INTO `company` (`uuid`, `tenant_id`, `company_type_id`, `company_name`, `CIN`, `GSTIN`, `TAN`, `PAN`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('2dc71856-ce6f-41a0-9732-ef97ae85eabc', 1, 2, 'Tri party test Company One', 'CIN0000001', 'GSTIN0000000', 'TAN0000000', 'PAN0000000', '2023-09-09 14:39:57', '2023-09-09 14:39:57', 'ADMIN', NULL, 1);

INSERT INTO `address` (`uuid`, `company_id`, `address_type_id`, `address_line1`, `address_line2`, `address_line3`, `city`, `state_code`, `country_code`, `pin_code`, `work_phone`, `fax`, `primary_phone_number`, `alternate_phone_number`, `email`, `website`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('28efd0b3-a0ba-4805-b432-5543013c0e73', 1, 2, 'Test address one', NULL, NULL, NULL, '123', '1234', '123', NULL, NULL, '9049989010', '9049989010', NULL, NULL, '2023-09-22 12:27:41', '2023-09-22 12:27:41', 'ADMIN', 'ADMIN', 1);

INSERT INTO `address` (`uuid`, `company_id`, `address_type_id`, `address_line1`, `address_line2`, `address_line3`, `city`, `state_code`, `country_code`, `pin_code`, `work_phone`, `fax`, `primary_phone_number`, `alternate_phone_number`, `email`, `website`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('shbsd0b3-a0ba-4805-efgh-5543013cjkrf', 2, 2, 'Test address two', NULL, NULL, NULL, '123', '1234', '123', NULL, NULL, '9049989010', '9049989010', NULL, NULL, '2023-09-22 12:27:41', '2023-09-22 12:27:41', 'ADMIN', 'ADMIN', 1);

INSERT INTO `address` (`uuid`, `company_id`, `address_type_id`, `address_line1`, `address_line2`, `address_line3`, `city`, `state_code`, `country_code`, `pin_code`, `work_phone`, `fax`, `primary_phone_number`, `alternate_phone_number`, `email`, `website`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('45hgd0b3-a0ba-4805-ijkl-5543013c3gfh', 3, 2, 'Test address three', NULL, NULL, NULL, '123', '1234', '123', NULL, NULL, '9049989010', '9049989010', NULL, NULL, '2023-09-22 12:27:41', '2023-09-22 12:27:41', 'ADMIN', 'ADMIN', 1);

INSERT INTO `address` (`uuid`, `company_id`, `address_type_id`, `address_line1`, `address_line2`, `address_line3`, `city`, `state_code`, `country_code`, `pin_code`, `work_phone`, `fax`, `primary_phone_number`, `alternate_phone_number`, `email`, `website`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('fjeyd0b3-a0ba-4805-abcd-5543013cergh', 4, 2, 'Test address four', NULL, NULL, NULL, '123', '1234', '123', NULL, NULL, '9049989010', '9049989010', NULL, NULL, '2023-09-22 12:27:41', '2023-09-22 12:27:41', 'ADMIN', 'ADMIN', 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('8d9c4ba2-7f6d-4635-a6a6-470c8b49f41c', 1, 'Glass Window', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', NULL, 1);

INSERT INTO `glass_type` (`uuid`, `tenant_id`, `glass_name`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('8d9c4ba2-5tgj-ukld-wndp-470c8b49f41c', 1, 'Glass Door', '2023-09-10 12:09:31', '2023-09-10 12:09:31', 'ADMIN', NULL, 1);

INSERT INTO `glass_thickness` (`uuid`, `tenant_id`, `name`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('a15aa9f7-178a-44b6-b631-267bf4c3ca45', 1, '12MM', '2023-09-10 12:13:23', '2023-09-10 12:13:23', 'ADMIN', NULL, 1);

INSERT INTO `glass_thickness` (`uuid`, `tenant_id`, `name`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('a15aa9f7-178a-44b6-b631-adg456c3ca45', 1, '8MM', '2023-09-10 12:13:23', '2023-09-10 12:13:23', 'ADMIN', NULL, 1);

INSERT INTO `glass_specification` (`uuid`, `tenant_id`, `name`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('94771345-c309-4eb1-8e2b-bf147ec25f3f', 1, 'Specification One', '2023-09-10 12:15:10', '2023-09-10 12:15:10', 'ADMIN', NULL, 1);

INSERT INTO `glass_specification` (`uuid`, `tenant_id`, `name`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('94771345-c309-23df-346h-bf147ec25f3f', 1, 'Specification Two', '2023-09-10 12:15:10', '2023-09-10 12:15:10', 'ADMIN', NULL, 1);

INSERT INTO `service_rate` (`uuid`, `tenant_id`, `name`, `rate`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('3c0f293b-c5bb-4f92-a138-4a8400716d01', 1, 'Big Hole', 75, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1);

INSERT INTO `service_rate` (`uuid`, `tenant_id`, `name`, `rate`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('3c0f293b-c5bb-4f92-a138-4a8400ioswef', 1, 'Small Hole', 80.55, '2023-10-08 23:11:38', '2023-10-08 23:11:38', 'ADMIN', 'ADMIN', 1);

INSERT INTO `additional_charges` (`uuid`, `tenant_id`, `extra_mm`, `insurance`, `admin_charges`, `forwarding_charges`, `gst`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('02987ac2-0e69-4a99-b727-b63345ff246f', 1, 44.00, 55.00, 200.00, 200.00, 18.00, '2023-10-17 11:45:46', '2023-10-17 11:45:48', 'ADMIN', 'ADMIN', 1);

INSERT INTO `user` (`uuid`, `tenant_id`, `role_id`, `name`, `user_name`, `password`, `salt`, `mobile_number`, `email_id`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('3376c86c-ec01-4fc5-bb7e-b6a433fa9522', 1, 1, 'User One', 'test', 'test', 'test', '9049989010', 'abc@abc.com', '2023-10-19 10:47:02', '2023-10-19 10:47:02', 'ADMIN', 'ADMIN', 1);

INSERT INTO `confirm_through` (`uuid`, `tenant_id`, `name`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('lsn8e7f7-ghej-4f2d-b303-dje947a25fkd', 1, 'Call', '2023-10-27 10:04:23', '2023-10-27 10:04:23', 'ADMIN', 'ADMIN', 1);

INSERT INTO `confirm_through` (`uuid`, `tenant_id`, `name`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('dhene7f7-82ce-4f2d-qwdf-724947a250ed', 1, 'Text', '2023-10-27 10:04:23', '2023-10-27 10:04:23', 'ADMIN', 'ADMIN', 1);

INSERT INTO `confirm_through` (`uuid`, `tenant_id`, `name`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('ssgee7f7-ahsn-4f2d-sdvf-7we947a250ed', 1, 'Whatsapp', '2023-10-27 10:04:23', '2023-10-27 10:04:23', 'ADMIN', 'ADMIN', 1);

INSERT INTO `confirm_through` (`uuid`, `tenant_id`, `name`, `created_dttm`, `last_updated_dttm`, `created_by`, `last_updated_by`, `is_active`) VALUES
('snagebf7-widm-4f2d-sde1-3e4947a250ed', 1, 'Self', '2023-10-27 10:04:23', '2023-10-27 10:04:23', 'ADMIN', 'ADMIN', 1);

