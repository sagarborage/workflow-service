INSERT INTO app_config_settings
    (config_key, config_value, group_name, description, is_active, created_by, created_datetime, last_updated_by, last_updated_datetime, version)
    VALUES
    -- Swagger config
    ('springdoc.swagger-ui.serverUrls', 'http://localhost:3000,https://qa-workflow.sowermate.com/apis', 'Swagger', 'Base URL(s) for Swagger UI', 1, 'System', NOW(), 'System', NOW(), 1),
    ('springdoc.swagger-ui.title', 'Workflow', 'Swagger', 'Title of the Swagger documentation', 1, 'System', NOW(), 'System', NOW(), 1),
    ('springdoc.swagger-ui.description', 'This is Workflow project developed by Sowermate Technologies Private Limited.', 'Swagger', 'Description of the Swagger API', 1, 'System', NOW(), 'System', NOW(), 1),
    ('springdoc.swagger-ui.version-name', '1.0', 'Swagger', 'Version of the API', 1, 'System', NOW(), 'System', NOW(), 1),
    ('springdoc.swagger-ui.contact-name', 'Sowermate Technologies Private Limited', 'Swagger', 'Contact name for API support', 1, 'System', NOW(), 'System', NOW(), 1),
    ('springdoc.swagger-ui.contact-emails', 'abhijit.salunkhe@sowermate.com,anil.jadhav@sowermate.com,vithoba.hipparkar@sowermate.com,minakshi.sawant@sowermate.com,jaydeep.shinde@sowermate.com,vaibhav.kodag@sowermate.com', 'Swagger', 'Contact emails for API support', 1, 'System', NOW(), 'System', NOW(), 1),
    ('springdoc.swagger-ui.licence-name', 'Sowermate 2.0', 'Swagger', 'License name of the API', 1, 'System', NOW(), 'System', NOW(), 1),
    ('springdoc.swagger-ui.licence-url', 'https://sowermate.com/', 'Swagger', 'License URL of the API', 1, 'System', NOW(), 'System', NOW(), 1),
    ('springdoc.swagger-ui.powered-by', 'Powered By : Sowermate Technologies Private Limited', 'Swagger', 'Company powering the API', 1, 'System', NOW(), 'System', NOW(), 1),
    ('springdoc.swagger-ui.powered-by-url', 'https://sowermate.com/', 'Swagger', 'URL of the company powering the API', 1, 'System', NOW(), 'System', NOW(), 1),

    -- Cors config
    ('cors.allowedOrigins', 'http://localhost:3000,https://qa-workflow.sowermate.com', 'Cors', 'Allowed origins for CORS requests', 1, 'System', NOW(), 'System', NOW(), 1),
    ('cors.allowedMethods', 'GET,POST,PUT,DELETE,OPTIONS', 'Cors', 'Allowed HTTP methods for CORS requests', 1, 'System', NOW(), 'System', NOW(), 1),
    ('cors.allowedHeaders', '*', 'Cors', 'Allowed headers for CORS requests (wildcard for all headers)', 1, 'System', NOW(), 'System', NOW(), 1),
    ('cors.allowCredentials', 'true', 'Cors', 'Allow credentials in CORS requests', 1, 'System', NOW(), 'System', NOW(), 1),
    ('cors.maxAge', '600', 'Cors', 'Max age for caching CORS preflight responses (in seconds)', 1, 'System', NOW(), 'System', NOW(), 1),

    -- Pagination config
    ('pagination.default.page-size', '10', 'Pagination', 'Default number of items per page for pagination', 1, 'System', NOW(), 'System', NOW(), 1),

    -- Notification config
    ('is.in-app.notification.enabled', 'true', 'Notification', 'Enable or disable in-app notifications', 1, 'System', NOW(), 'System', NOW(), 1),
    ('is.email.notification.enabled', 'true', 'Notification', 'Enable or disable email notifications', 1, 'System', NOW(), 'System', NOW(), 1),
    ('email.send.mode', 'console', 'Notification', 'Email sending mode: live, console, or both', 1, 'System', NOW(), 'System', NOW(), 1),
    ('spring.mail.username', 'testemailt008@gmail.com', 'Notification', 'Email sender address', 1, 'System', NOW(), 'System', NOW(), 1),

    -- Geocoding config
    ('geonames.username', 'jayshinde___', 'Geocoding', 'Username for accessing the GeoNames API', 1, 'System', NOW(), 'System', NOW(), 1),
    ('is.geocoding.using', 'true', 'Geocoding', 'Enable or disable geocoding services', 1, 'System', NOW(), 'System', NOW(), 1),

    -- Code validity
    ('account.confirmation.code.validity', '15', 'CodeValidity', 'Validity period (in minutes) for account confirmation codes', 1, 'System', NOW(), 'System', NOW(), 1),
    ('forgot.password.token.validity', '15', 'CodeValidity', 'Validity period (in minutes) for password reset tokens', 1, 'System', NOW(), 'System', NOW(), 1),
    ('email.verification.code.validity', '1440', 'CodeValidity', 'Validity period (in minutes) for email verification codes', 1, 'System', NOW(), 'System', NOW(), 1),
    ('confirmation.code.cleanup.validity', '0 0 0 * * *', 'CodeValidity', 'Cron expression for periodic cleanup of expired confirmation codes', 1, 'System', NOW(), 'System', NOW(), 1),
    ('sms.otp.rate.limit.validity', '60', 'CodeValidity', 'OTP rate-limiting duration in seconds (within which repeated OTP requests are restricted)', 1, 'System', NOW(), 'System', NOW(), 1),
    ('sms.otp.rate.limit.count', '5', 'CodeValidity', 'Maximum number of OTP requests allowed within the validity duration before rate limiting is enforced', 1, 'System', NOW(), 'System', NOW(), 1),

    -- UI page URL config
    ('email.confirmation.link', 'http://localhost:3000/verify?token=%s', 'UIPageUrl', 'URL for email confirmation link with token placeholder', 1, 'System', NOW(), 'System', NOW(), 1),
    ('account.confirmation.link', 'http://localhost:3000/set-password?token=%s', 'UIPageUrl', 'URL for account confirmation page with token', 1, 'System', NOW(), 'System', NOW(), 1),
    ('website.home.page.link', 'http://localhost:3000/GeneratePassword', 'UIPageUrl', 'URL for website home page', 1, 'System', NOW(), 'System', NOW(), 1),
    ('set.password.page.link.for.forgot.password', 'http://localhost:3000/reset-password?token=%s', 'UIPageUrl', 'URL for password reset page with token placeholder', 1, 'System', NOW(), 'System', NOW(), 1),

    -- JWT config
    ('authentication.jwt.token.secret', 'workflowsecretkey1234567890workflowsecretkey1234567890workflowsecretkey1234567890', 'JWT', 'Secret key used for signing JWT tokens', 1, 'System', NOW(), 'System', NOW(), 1),
    ('authentication.jwt.accessToken.expiry', '86400000', 'JWT', 'JWT access token expiry time in milliseconds (1 day)', 1, 'System', NOW(), 'System', NOW(), 1),
    ('authentication.jwt.refreshToken.expiry', '864000000', 'JWT', 'JWT refresh token expiry time in milliseconds (10 days)', 1, 'System', NOW(), 'System', NOW(), 1),
    ('authentication.jwt.token.endpoint', '', 'JWT', 'Endpoint for obtaining JWT tokens', 1, 'System', NOW(), 'System', NOW(), 1),

    -- Auth config
    ('login.authenticate.by', 'all', 'Auth', 'Defines authentication methods allowed: username, email, phone, or all', 1, 'System', NOW(), 'System', NOW(), 1),
    ('auth.max-failed-attempts', '5', 'Auth', 'Maximum number of failed login attempts before account lockout', 1, 'System', NOW(), 'System', NOW(), 1),
    ('auth.lockout-duration-minutes', '15', 'Auth', 'Duration in minutes before a locked-out user can attempt to log in again', 1, 'System', NOW(), 'System', NOW(), 1),

    -- Tenant config
    ('is.child.tenants.enabled', 'true', 'Tenant', 'Enable or disable child tenants in the system', 1, 'System', NOW(), 'System', NOW(), 1),
    ('is.tenant.doc.required', 'false', 'Tenant', 'Specify if tenant documents are required during registration', 1, 'System', NOW(), 'System', NOW(), 1),

    -- User config
    ('is.user.doc.required', 'false', 'User', 'Specify if user documents are required during registration', 1, 'System', NOW(), 'System', NOW(), 1);

    -- Tenant subscription config
    ('subscription.expiry.check.validity', '0 10 0 * * *', 'TenantSubscription', 'Cron expression to schedule daily check for expired tenant subscriptions at 12:10 AM', 1, 'System', NOW(), 'System', NOW(), 1),
    ('subscription.reminder.validity', '0 0 9 * * ?', 'TenantSubscription', 'Cron expression to schedule sending of tenant subscription reminder emails at 9:00 AM daily', 1, 'System', NOW(), 'System', NOW(), 1),
    ('subscription.reminder.days', '7,3,1,0,-1,-3,-7', 'TenantSubscription', 'Comma-separated list of days relative to subscription expiry for sending reminder emails (negative for post-expiry)', 1, 'System', NOW(), 'System', NOW(), 1);