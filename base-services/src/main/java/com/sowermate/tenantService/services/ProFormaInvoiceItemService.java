package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceIndividualsOrdersProjection;
import com.sowermate.tenantService.entities.value.BucketManipulationValue;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.entities.value.ProformaInvoiceItemReqParam;

import java.util.List;

public interface ProFormaInvoiceItemService {


    public ProFormaInvoiceItemValue createProFormaInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue);
    public List<ProFormaInvoiceItemValue> saveAllProFormaInvoiceItem(String tenantUUID, List<ProFormaInvoiceItemValue> proFormaInvoiceItems);
    public String deleteProformaInvoiceItemFile(String tenantUuid,String parentDirectory, String fileName);
    public String deleteProformaInvoiceItemFileByUrl(String fileUrl);
    public ProFormaInvoiceItemValue editProFormaInvoiceItem(ProFormaInvoiceItemValue proFormaInvoiceItemValue);

    public ProFormaInvoiceItemValue getProFormaInvoiceItem(String tenantUuid, String proFormaInvoiceItemUuid);
    ProFormaInvoiceItemValue updateProformaInvoiceItemStatus(ProformaInvoiceItemReqParam proformaInvoiceItemReqParam);

    public int deleteProFormaInvoiceItem(String tenantUuid, String proFormaInvoiceItemUuid);

    public List<ProFormaInvoiceItemValue> getAllProFormaInvoiceItem(String tenantUuid);

    public List<ProFormaInvoiceIndividualsOrdersProjection> bucketManipulation(String actionType, BucketManipulationValue bucketManipulationValue);
    public List<ProFormaInvoiceIndividualsOrdersProjection> bucketManipulationCompleteAll(BucketManipulationValue bucketManipulationValue);
    public void toughenBatchProcess(String tenantUuid, String proFormaInvoiceItemUuid, boolean isCancel);
}
