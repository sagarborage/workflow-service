package com.sowermate.report.services;


import com.sowermate.report.dtos.*;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PdfGenerationService {
    byte[] generateToughenSticker(StickerRequestDto stickerRequestDto) throws IOException;

    byte[] generateGatePass(GatePassRequestDto gatePassRequestDto) throws IOException;

    byte[] generateToughenBatch(ToughenBatchReportRequestDto toughenBatchReportRequestDto) throws IOException;

    byte[] generateProformaInvoice(ProFormaInvoiceValue piValue, PIReportDetails reportDetails, List<Map<Integer, String>> designs) throws IOException;

    byte[] generateWorkOrder(ProFormaInvoiceValue piValue, PIReportDetails reportDetails, List<Map<Integer, String>> designs) throws IOException;
}
