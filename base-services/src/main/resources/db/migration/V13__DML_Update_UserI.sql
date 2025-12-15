update user_auth set password_hash= '$2a$10$PGMPVc0OGQ8Hi4rMDerP.u6/ZLLMyICXDxTdU17BNJPnjTdW3TlAq'  where id=15;
update user_auth set password_hash= '$2a$10$Fe4hHlT6BNYHzYyyyYnrMeD5MOpMVgp7dPqYi/RBSY2yiB1Y7AbXu'  where id=13;
update user_auth set password_hash= '$2a$10$AAQcbGEExvUg46cakkqI4eYEqzbtrhhedLIM0Wl6yZyT0rNzXYO2G'  where id=14;
update user_auth set password_hash= '$2a$10$J848fbM9sXJCLZNKSSmSbu2b8yF52SEztC/2zsr4N/jfJnMfOpmoa'  where id=10;
update user_auth set password_hash= '$2a$10$TceZ/tkzyWKzg0N5wZipSucZzXzgBD8CkgiOOr2kCaDAMarYzfP86'  where id=12;
update user_auth set password_hash= '$2a$10$EsiIu6/Gu6sMGVWyyszuf.nux3Mruh09IOy8M9nxVnxumFzPL7CCO'  where id=5;
update user_auth set password_hash= '$2a$10$ubjLRAF8CQtYMhOHO9RVheq25yIA0I2jU75haNQr8znhuRDLkiI8W'  where id=6;
update user_auth set password_hash= '$2a$10$zFIG/iMosh8Ps.mSeDG.I.EPm8qgBvlhaebO6xrViqBCo1bJBxcJW'  where id=11;
update user_auth set password_hash= '$2a$10$2IHgiPdvDijNb9e2EJSQt.HyznXsNFanzmRJg5gaF3/05KfSsi0v2'  where id in(7,8,9,16);

INSERT INTO `user_auth` (`id`, `uuid`, `tenant_id`, `role_id`, `first_name`, `last_name`, `phone`, `username`, `password_hash`, `is_enabled`, `is_email_verified`, `is_phone_verified`, `is_account_non_expired`,
`is_account_non_locked`, `is_credentials_non_expired`, `failed_attempt`, `is_active`, `created_by`, `created_datetime`, `last_updated_by`, `last_updated_datetime`, `version`) VALUES
(17, UUID(), 1, 5, 'Swapna', '', '9999999913', 'swapna', '$2a$10$S9JEgtiYSU0XUdo.ciiQFuVJ6u5QUJ7J.t4Dv.08wosN/29GOM3Jy', 0, 0, 0, 1, 1, 1, 1, 1, 'System', '2025-11-06 23:24:45', 'System', '2025-11-06 10:30:58', 1),
(18, UUID(), 1, 5, 'Pramod', '', '9999999914', 'pramod', '$2a$10$mAG7WllAuG4fLdUW2u/eLOhpgdYLMTSyfNTZBWjYuiKlx8NYr0Axe', 0, 0, 0, 1, 1, 1, 1, 1, 'System', '2025-11-06 23:24:45', 'System', '2025-11-06 10:30:58', 1);

