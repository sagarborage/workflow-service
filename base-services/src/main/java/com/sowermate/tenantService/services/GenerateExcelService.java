package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.ProFormaInvoiceExcelReport;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceExcelReportData;
import com.sowermate.tenantService.entities.value.WorkOrderExcelReport;
import com.sowermate.tenantService.entities.value.WorkOrderExcelReportData;

import java.io.IOException;
import java.util.List;

public interface GenerateExcelService {

    String generateProformaInvoiceReportsExcelSheet(List<ProFormaInvoiceExcelReportData> proFormaInvoiceExcelReportData, ProFormaInvoiceExcelReport proFormaInvoiceExcelReport) throws IOException;

    String generateWorkOrderDetailsExcelSheet(List<WorkOrderExcelReportData> workOrderExcelReportData, WorkOrderExcelReport workOrderExcelReport) throws IOException;

}
