INSERT INTO `tenant` (`uuid`, `tenant_name`, `address`, `city`, `state`, `country_id`, `pin_code`, `phone_number`, `email_id`, `activation_date`, `expiry_date`, `grace_period`, `is_active`) VALUES
("d032667f-3c0d-11ee-962a-14abc5fbd6fa", 'Test Company', 'Test Address', 'Sangola,Pune', 'Maharashtra', 1, '411028', '9049989010', 'test@gmail.com', '2022-12-02 00:00:00', '2050-05-02 00:00:00', 31, '1');

INSERT INTO `address_type` (`tenant_id`, `type`, `description`, `uuid`, `is_active`) VALUES
(1, 'HOME', 'Home address', "d0328fb0-3c0d-11ee-962a-14abc5fbd6fa", 1),
(1, 'BUSINESS', 'Business address', "d0329283-3c0d-11ee-962a-14abc5fbd6fa", 1),
(1, 'BILLING', 'Billing address', "d032961b-3c0d-11ee-962a-14abc5fbd6fa", 1),
(1, 'SHIPPING', 'Shipping address', "d0329742-3c0d-11ee-962a-14abc5fbd6fa", 1);
--UUID()

INSERT INTO `company_type` (`uuid`, `tenant_id`, `type`, `description`, `is_active`) VALUES
("d03351ec-3c0d-11ee-962a-14abc5fbd6fa", 1, 'Self', 'subsidiary', 1),
("d0335437-3c0d-11ee-962a-14abc5fbd6fa", 1, 'Tri-Party', 'Customer', 1);

