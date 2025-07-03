package com.sowermate.workflow.service.services;

import com.sowermate.workflow.domain.entities.value.GatePassExcelReport;
import com.sowermate.workflow.domain.entities.value.GatePassExcelReportData;
import com.sowermate.workflow.domain.entities.value.ProFormaInvoiceExcelReport;
import com.sowermate.workflow.domain.entities.value.ProFormaInvoiceExcelReportData;
import com.sowermate.workflow.domain.entities.value.WorkOrderExcelReport;
import com.sowermate.workflow.domain.entities.value.WorkOrderExcelReportData;

import java.io.IOException;
import java.util.List;

public interface GenerateExcelService {

    String generateProformaInvoiceReportsExcelSheet(List<ProFormaInvoiceExcelReportData> proFormaInvoiceExcelReportData, ProFormaInvoiceExcelReport proFormaInvoiceExcelReport) throws IOException;

    String generateWorkOrderDetailsExcelSheet(List<WorkOrderExcelReportData> workOrderExcelReportData, WorkOrderExcelReport workOrderExcelReport) throws IOException;

    String generateGatePassExcelSheet(List<GatePassExcelReportData> gatePassExcelReportData, GatePassExcelReport gatePassExcelReport) throws IOException;
}
