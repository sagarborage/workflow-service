package com.sowermate.workflow.report.services;

import com.sowermate.workflow.domain.entities.value.ProFormaInvoiceValue;
import com.sowermate.workflow.report.dtos.GatePassRequestDto;
import com.sowermate.workflow.report.dtos.PIReportDetails;
import com.sowermate.workflow.report.dtos.StickerRequestDto;
import com.sowermate.workflow.report.dtos.ToughenBatchReportRequestDto;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface WorkflowPdfGenerationService {
    byte[] generateToughenSticker(StickerRequestDto stickerRequestDto) throws IOException;

    byte[] generateToughenStickers(StickerRequestDto stickerRequestDto) throws IOException;

    byte[] generateGatePass(GatePassRequestDto gatePassRequestDto) throws IOException;

    byte[] generateToughenBatch(ToughenBatchReportRequestDto toughenBatchReportRequestDto) throws IOException;

    byte[] generateProformaInvoice(ProFormaInvoiceValue piValue, PIReportDetails reportDetails, List<Map<Integer, String>> designs) throws IOException;

    byte[] generateWorkOrder(ProFormaInvoiceValue piValue, PIReportDetails reportDetails, List<Map<Integer, String>> designs) throws IOException;

    byte[] generateProformaInvoicePdfRoughGlass(ProFormaInvoiceValue piValue, PIReportDetails reportDetails, List<Map<Integer, String>> designs) throws IOException;

    byte[] generateToughenReport(LocalDate date) throws IOException;
}
