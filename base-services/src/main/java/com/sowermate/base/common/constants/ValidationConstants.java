package com.sowermate.base.common.constants;

/**
 * <h1>StatusConstants</h1>
 * This class is responsible for maintaining the common constants used across the application.
 *
 * @author asalunkhe
 * @version 1.0
 * @since 2023-11-18
 */
public class ValidationConstants {

    // Generic Errors
    public static final String ERROR_GENERIC = "error.generic";


    // Common validations
    public static final String VALIDATION_EMAIL_FORMAT = "validation.email.format";
    public static final String VALIDATION_SIZE_MIN_MAX = "validation.length";

    public static final String VALIDATION_DATE = "validation.date.message";
    public static final String VALIDATION_DATE_FORMAT = "validation.date.format";
    public static final String VALIDATION_PHONE_NUMBER_FORMAT = "validation.phonenumber.format";
    public static final String VALIDATION_NAME_REQUIRED = "validation.name.required";
    public static final String VALIDATION_FIRST_NAME_REQUIRED = "validation.firstname.required";
    public static final String VALIDATION_LAST_NAME_REQUIRED = "validation.lastname.required";
    public static final String VALIDATION_EMAIL_REQUIRED = "validation.email.required";
    public static final String VALIDATION_PHONE_REQUIRED = "validation.phonenumber.required";
    public static final String VALIDATION_DATE_PAST = "validation.past.message";

    //foreign key validation
    public static final String VALIDATION_TENANT_UUID_REQUIRED = "validation.tenantUuid.required";
    public static final String VALIDATION_SHIFT_UUID_REQUIRED = "validation.shiftUuid.required";
    public static final String VALIDATION_UNIT_UUID_REQUIRED = "validation.unitUuid.required";
    public static final String VALIDATION_EMPLOYEE_UUID_REQUIRED = "validation.employeeUuid.required";
    public static final String VALIDATION_MANAGER_UUID_REQUIRED = "validation.managerUuid.required";
    public static final String VALIDATION_COUNTRY_UUID_REQUIRED = "validation.countryUuid.required";
    public static final String VALIDATION_ROLE_UUID_REQUIRED = "validation.roleUuid.required";
    public static final String VALIDATION_EMAIL_OTP_UUID_REQUIRED = "validation.emailOtpUuid.required";
    public static final String VALIDATION_JOB_TITLE_UUID_REQUIRED = "validation.jobTitleUuid.required";
    public static final String VALIDATION_INVOICE_UUID_REQUIRED = "validation.invoiceUuid.required";
    public static final String VALIDATION_TAX_TYPE_UUID_REQUIRED = "validation.taxTypeUuid.required";
    public static final String VALIDATION_GST_TYPE_UUID_REQUIRED = "validation.gstTypeUuid.required";
    public static final String VALIDATION_SUBSCRIPTION_PLAN_UUID_REQUIRED = "validation.subscriptionPlanUuid.required";
    public static final String VALIDATION_PAYMENT_HISTORY_UUID_REQUIRED = "validation.paymentHistoryUuid.required";
    public static final String VALIDATION_TERRITORIES_UUID_REQUIRED = "validation.territoryUuid.required";

    //GlobalGstTypesDto Validation
    public static final String VALIDATION_GLOBAL_GST_TYPES_SGST_REQUIRED = "validation.GlobalGstTypes.sgst.required";
    public static final String VALIDATION_GLOBAL_GST_TYPES_CGST_REQUIRED = "validation.GlobalGstTypes.cgst.required";
    public static final String VALIDATION_GLOBAL_GST_TYPES_IGST_REQUIRED = "validation.GlobalGstTypes.igst.required";



    //EmailTemplatesDto
       public static final String VALIDATION_SUBJECT_REQUIRED="validation.subject.required";
    public static final String VALIDATION_CONTENT_REQUIRED="validation.content.required";

    //EmailOtp
    public static final String VALIDATION_EMAIL_OTP_REQUIRED = "validation.email.otp.required";
    public static final String VALIDATION_EMAIL_OTP_NUMERIC = "validation.email.otp.Numeric";
    public static final String VALIDATION_EMAIL_MESSAGE_BODY_REQUIRED = "validation.email.messageBody.required";
    public static final String VALIDATION_EMAIL_CREATION_TIME_REQUIRED = "validation.email.creationTime.required";
    public static final String VALIDATION_EMAIL_EXPIRATION_TIME_REQUIRED = "validation.email.expirationTime.required";
    public static final String VALIDATION_CREATION_TIME_PAST_OR_PRESENT = "validation.creationTime.pastOrPresent";
    public static final String VALIDATION_EXPIRATION_TIME_FUTURE ="validation.expirationTime.future";
    public static final String VALIDATION_IS_USED_REQUIRED = "validation.isUsed.required.boolean";


    //TenantDto validations
    public static final String VALIDATION_TENANT_NAME_REQUIRED = "validation.tenantname.required";

    //RoleDetailsDto validations
    public static final String VALIDATION_ROLE_NAME_REQUIRED = "validation.rolename.required";

    //UnitDetailsDto validations
    public static final String VALIDATION_UNIT_NAME_REQUIRED = "validation.unitname.required";

    //ShiftDetailsDto validations
    public static final String VALIDATION_SHIFT_NAME_REQUIRED = "validation.shiftname.required";
    public static final String VALIDATION_SHIFT_HOUR_POSITIVE = "validation.shifthour.positive";
    public static final String VALIDATION_SHIFT_HOUR_REQUIRED = "validation.shifthour.required";

    //JobTitleDto validations
    public static final String VALIDATION_JOB_TITLE_NAME_REQUIRED = "validation.jobtitlename.required";

    //EmployeeDetailsDto validations
    public static final String VALIDATION_HR_EMPLOYEE_ID_REQUIRED = "validation.hremployeeid.required";
    public static final String VALIDATION_EMPLOYEE_NAME_REQUIRED = "validation.employeename.required";
    public static final String VALIDATION_EMPLOYEE_GENDER_REQUIRED = "validation.employeegender.required";
    public static final String VALIDATION_HR_EMPLOYEE_ID_POSITIVE = "validation.hremployeeid.positive";

    //InvoiceTaxesDto and InvoiceGstDto validations
    public static final String VALIDATION_AMOUNT_POSITIVE = "validation.amount.positive";
    public static final String VALIDATION_AMOUNT_REQUIRED = "validation.amount.required";



    //InvoiceDto Validation
    public static final String VALIDATION_INVOICE_INVOICE_NUMBER_REQUIRED = "validation.Invoice.invoiceNumber.required";
    public static final String VALIDATION_INVOICE_CURRENCY_REQUIRED = "validation.Invoice.currency.required";
    public static final String VALIDATION_INVOICE_STATUS_REQUIRED = "validation.Invoice.status.required";
    public static final String VALIDATION_INVOICE_SUBTOTAL_AMOUNT_REQUIRED = "validation.Invoice.subTotalAmount.required";
    public static final String VALIDATION_INVOICE_TOTAL_TAX_AMOUNT_REQUIRED = "validation.Invoice.totalTaxAmount.required";
    public static final String VALIDATION_INVOICE_TOTAL_GST_AMOUNT_REQUIRED = "validation.Invoice.totalGstAmount.required";
    public static final String VALIDATION_INVOICE_TOTAL_AMOUNT_REQUIRED = "validation.Invoice.totalAmount.required";

    //TenantSubscriptionDto Validation
    public static final String VALIDATION_TENANT_SUBSCRIPTION_STATUS_REQUIRED = "validation.TenantSubscription.status.required";

    //CountriesDto Validation
    public static final String VALIDATION_COUNTRY_ISO2_CODE_REQUIRED = "validation.Country.iso2Code.required";
    public static final String VALIDATION_COUNTRY_ISO3_CODE_REQUIRED = "validation.Country.iso3Code.required";
    public static final String VALIDATION_COUNTRY_CURRENCY_CODE_REQUIRED = "validation.Country.currencyCode.required";

}
