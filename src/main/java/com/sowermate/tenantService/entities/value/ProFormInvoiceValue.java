package com.sowermate.tenantService.entities.value;

import lombok.Data;

import java.util.Date;

@Data
public class ProFormInvoiceValue {
    private int proFormaInvoiceId;
    private String uuid;
    private int idBillTo;
    private int idShipTo;
    private int piNumber;
    private Date invoiceDate;
    private Double proFormaInvoiceAmount;
    private Double serviceRateInvoiceAmount;
    private Double basicAmount;
    private Double adminCharges;
    private Float insurancePercent;
    private Float insurancePercentAmount;
    private Float urgencyPercent;
    private Float urgencyPercentAmount;
    private Double otherCharges;
    private Double transportCharges;
    private Float gstCharges;
    private Float grandTotal;
    private int roundOffAmount;
    private Float payableAmount;
    private Float previousBalance;
    private int adjustmentAmount;
    private String status;
    private  int confirmThroughId;
    private  int piTypeId;
}
