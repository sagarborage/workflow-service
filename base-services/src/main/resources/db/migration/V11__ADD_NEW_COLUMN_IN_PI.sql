ALTER TABLE `pro_forma_invoice`
  ADD COLUMN `creation_type` VARCHAR(50) NOT NULL DEFAULT 'pi' AFTER `pi_type_id`;

ALTER TABLE `pro_forma_invoice`
	CHANGE COLUMN `pi_type_id` `pi_type_id` INT(10) NULL AFTER `id_ship_to`;
