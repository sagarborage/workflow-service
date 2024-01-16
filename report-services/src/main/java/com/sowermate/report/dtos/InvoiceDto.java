package com.sowermate.report.dtos;

import com.sowermate.report.utils.DateInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceDto {
    private String toName;
    private String toAddress;
    private String documentType;
    private String documentNumber;

    private String invoiceNumber;
    private DateInfo dateInfo;
    private String terms;

    private String subscriptionType;
    private double noOfUsersSubscription;
    private double subscriptionCost;

    private double noOfUsersInstallationCharges;
    private double installationChargesCost;

    private String applicableGstType;
    private double applicableGstRate;


}

