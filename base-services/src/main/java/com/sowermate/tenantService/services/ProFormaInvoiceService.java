package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceIndividualsOrdersProjection;
import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceMinimal;
import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceOrdersProjection;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import com.sowermate.tenantService.enums.ProformaInvoiceStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public interface ProFormaInvoiceService {

    public ProFormaInvoiceValue createProFormaInvoice(ProFormaInvoiceValue proFormaInvoiceValue);

    public ProFormaInvoiceValue editProFormaInvoice(ProFormaInvoiceValue proFormaInvoiceValue);
    public ProFormaInvoiceValue updateConfirmThrough(String tenantUuid, String proFprmaInvoiceUuid, String confirmThroughUuid);
    public ProFormaInvoiceValue updatePIStatus(String tenantUuid, String proFormaInvoiceUuid, ProformaInvoiceStatusEnum status, String statusDetails);

    public ProFormaInvoiceValue getProFormaInvoice(String tenantUuid, String proFormaInvoiceUuid);

    public int deleteProFormaInvoice(String tenantUuid,String proFormaInvoiceUuid);

    //public List<ProFormaInvoiceValue> getAllProFormaInvoice(String tenantUuid);
    public List<ProFormaInvoiceMinimal> getAllProFormaInvoice(String tenantUuid, LocalDateTime startDate, LocalDateTime endDate);

    public List<ProFormaInvoiceOrdersProjection> getAllProFormOrdersDetails(String tenantUuid, String deptType);

    public List<ProFormaInvoiceIndividualsOrdersProjection> getAllProFormIndividualsOrdersDetails(String tenantUuid,Integer workOrderNumber, String deptType);
}
