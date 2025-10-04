package com.sowermate.workflow.api.controllers;

import com.sowermate.workflow.domain.entities.minimal.ProFormaInvoiceIndividualsOrdersProjection;
import com.sowermate.workflow.domain.entities.minimal.ProFormaInvoiceOrdersProjection;
import com.sowermate.workflow.domain.entities.value.ProFormaInvoiceExcelReport;
import com.sowermate.workflow.domain.entities.value.ProFormaInvoiceExcelReportData;
import com.sowermate.workflow.domain.entities.value.ProFormaInvoiceHomeDetails;
import com.sowermate.workflow.domain.entities.value.ProFormaInvoiceValue;
import com.sowermate.workflow.domain.entities.value.WorkOrderExcelReport;
import com.sowermate.workflow.domain.entities.value.WorkOrderExcelReportData;
import com.sowermate.workflow.domain.enums.ProformaInvoiceStatusEnum;
import com.sowermate.workflow.service.services.GenerateExcelService;
import com.sowermate.workflow.service.services.ProFormaInvoiceService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.sowermate.core.common.constants.TimeConstant.BEGINNING;
import static com.sowermate.core.common.constants.TimeConstant.ENDING;
import static com.sowermate.core.common.constants.TimeConstant.FORMATTER;

@RestController
@RequestMapping("/proforma-invoices")
public class ProFormaInvoiceController {
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(ProFormaInvoiceController.class);

    @Autowired
    private ProFormaInvoiceService proFormaInvoiceService;

    @Autowired
    private GenerateExcelService generateExcelService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ProFormaInvoiceValue> createProFormaInvoice(@RequestBody ProFormaInvoiceValue proFormaInvoiceValue) {
        proFormaInvoiceValue = proFormaInvoiceService.createProFormaInvoice(proFormaInvoiceValue);
        return new ResponseEntity<ProFormaInvoiceValue>(proFormaInvoiceValue, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ProFormaInvoiceValue> editProFormaInvoice(@RequestBody ProFormaInvoiceValue proFormaInvoiceValue) {
        ProFormaInvoiceValue proFormaInvoiceValue1 = null;
        try {
            proFormaInvoiceValue1 = proFormaInvoiceService.editProFormaInvoice(proFormaInvoiceValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity<ProFormaInvoiceValue>(proFormaInvoiceValue1, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/updateConfirmThrough/{tenantUuid}/{proFormaInvoiceUuid}/{confirmThroughUuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ProFormaInvoiceValue> updateConfirmThrough(@PathVariable String tenantUuid,
                                                                     @PathVariable String proFormaInvoiceUuid,
                                                                     @PathVariable String confirmThroughUuid) {
        ProFormaInvoiceValue proFormaInvoiceValue1 = null;
        try {
            proFormaInvoiceValue1 = proFormaInvoiceService.updateConfirmThrough(tenantUuid, proFormaInvoiceUuid, confirmThroughUuid);
        } catch (Exception e) {
            Logger.error("Error while updating Confirm Through:", e);
        }
        return new ResponseEntity<ProFormaInvoiceValue>(proFormaInvoiceValue1, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/updatePIStatus/{tenantUuid}/{proFormaInvoiceUuid}/{currentStatus}/{newStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ProFormaInvoiceValue> updatePIStatus(@PathVariable String tenantUuid,
                                                               @PathVariable String proFormaInvoiceUuid,
                                                               @PathVariable ProformaInvoiceStatusEnum currentStatus,
                                                               @PathVariable ProformaInvoiceStatusEnum newStatus,
                                                               @RequestParam(required = false) String statusDetails
    ) {
        ProFormaInvoiceValue proFormaInvoiceValue = null;
        try {
            proFormaInvoiceValue = proFormaInvoiceService.updatePIStatus(tenantUuid, proFormaInvoiceUuid, currentStatus, newStatus, statusDetails);
        } catch (Exception e) {
            Logger.error("Error while update PI Status:", e);
        }
        return new ResponseEntity<ProFormaInvoiceValue>(proFormaInvoiceValue, HttpStatus.CREATED);
    }

    @GetMapping("/{tenantUuid}/{proFormaInvoiceUuid}")
    public ResponseEntity<ProFormaInvoiceValue> getProFormaInvoice(@PathVariable String tenantUuid,
                                                                   @PathVariable String proFormaInvoiceUuid) {
        ProFormaInvoiceValue proFormaInvoiceValue = null;
        try {
            proFormaInvoiceValue = proFormaInvoiceService.getProFormaInvoice(tenantUuid, proFormaInvoiceUuid);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(proFormaInvoiceValue, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}/{proFormaInvoiceUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Integer> deleteProFormaInvoice(@PathVariable String tenantUuid,
                                                         @PathVariable String proFormaInvoiceUuid) {
        int deleteProFormaInvoice = 0;
        try {
            deleteProFormaInvoice = proFormaInvoiceService.deleteProFormaInvoice(tenantUuid, proFormaInvoiceUuid);
        } catch (Exception e) {
            Logger.error("Error while deleting Seller:", e);
        }
        return new ResponseEntity<Integer>(deleteProFormaInvoice, HttpStatus.ACCEPTED);
    }
    //TODO: Remove this code lateron
/*    @GetMapping("/{tenantUuid}")
    public ResponseEntity<List<ProFormaInvoiceValue>> getAllProFormaInvoice(@PathVariable String tenantUuid) {
        List<ProFormaInvoiceValue> proFormaInvoiceValues = null;
        try {
            proFormaInvoiceValues = proFormaInvoiceService.getAllProFormaInvoice(tenantUuid);
            Logger.info("records " + proFormaInvoiceValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(proFormaInvoiceValues, HttpStatus.ACCEPTED);
    }*/

    @GetMapping("/{tenantUuid}/{companyUuid}/{startDate}/{endDate}")
    public ResponseEntity<List<ProFormaInvoiceHomeDetails>> getAllProFormaInvoice(@PathVariable String tenantUuid, @PathVariable String companyUuid, @PathVariable(name = "startDate") String startDate, @PathVariable(name = "endDate") String endDate, @RequestParam(required = false) String creationType) {
        List<ProFormaInvoiceHomeDetails> proFormaInvoiceHomeDetails = null;
        try {
            DateTimeFormatter formatter = FORMATTER;
            startDate = startDate + BEGINNING;
            endDate = endDate + ENDING;
            LocalDateTime startingDate = LocalDateTime.parse(startDate, formatter);
            LocalDateTime endingDate = LocalDateTime.parse(endDate, formatter);
            proFormaInvoiceHomeDetails = proFormaInvoiceService.getAllProFormaInvoice(tenantUuid, companyUuid, startingDate, endingDate, creationType);
            Logger.info("records " + proFormaInvoiceHomeDetails.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(proFormaInvoiceHomeDetails, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{tenantUuid}/{companyUuid}/{month}")
    public ResponseEntity<List<ProFormaInvoiceHomeDetails>> getAllProFormaInvoiceByMonth(@PathVariable String tenantUuid, @PathVariable String companyUuid, @PathVariable(name = "month") String month, @RequestParam(required = false) String creationType) {
        List<ProFormaInvoiceHomeDetails> proFormaInvoiceHomeDetails = null;
        try {
            YearMonth yearMonth = YearMonth.parse(month, DateTimeFormatter.ofPattern("yyyy-MM"));

            DateTimeFormatter formatter = FORMATTER;
            String startDate = yearMonth.atDay(1) + BEGINNING;
            String endDate = yearMonth.atEndOfMonth() + ENDING;
            LocalDateTime startingDate = LocalDateTime.parse(startDate, formatter);
            LocalDateTime endingDate = LocalDateTime.parse(endDate, formatter);
            proFormaInvoiceHomeDetails = proFormaInvoiceService.getAllProFormaInvoice(tenantUuid, companyUuid, startingDate, endingDate, creationType);
            Logger.info("records " + proFormaInvoiceHomeDetails.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(proFormaInvoiceHomeDetails, HttpStatus.ACCEPTED);
    }

    @GetMapping("/work-order-list/{tenantUuid}/{deptType}")
    public ResponseEntity<List<ProFormaInvoiceOrdersProjection>> getAllProFormOrdersDetails(@PathVariable String tenantUuid, @PathVariable String deptType) {
        List<ProFormaInvoiceOrdersProjection> proFormaInvoiceOrdersProjections = proFormaInvoiceService.getAllProFormOrdersDetails(tenantUuid, deptType);
        return new ResponseEntity<>(proFormaInvoiceOrdersProjections, HttpStatus.ACCEPTED);
    }

    @GetMapping("/work-order-individuals-list/{tenantUuid}/{workOrderNo}/{deptType}")
    public ResponseEntity<List<ProFormaInvoiceIndividualsOrdersProjection>> getAllProFormOrdersIndividualsDetails(@PathVariable String tenantUuid, @PathVariable Integer workOrderNo, @PathVariable String deptType) {
        List<ProFormaInvoiceIndividualsOrdersProjection> proFormaInvoiceIndividualsOrdersProjections = proFormaInvoiceService.getAllProFormIndividualsOrdersDetails(tenantUuid, workOrderNo, deptType);
        return new ResponseEntity<>(proFormaInvoiceIndividualsOrdersProjections, HttpStatus.ACCEPTED);
    }

    @GetMapping("/pi-register/{tenantUuid}")
    public ResponseEntity<List<Map<String, Object>>> getAllPiOrdersDetails(@PathVariable String tenantUuid, @RequestParam(required = false) String companyUuid, @RequestParam(required = false) String partyUuid, @RequestParam LocalDate fromDate, @RequestParam LocalDate toDate, @RequestParam(required = false) String status, @RequestParam(required = false) String creationType) {
        List<Map<String, Object>> proformaInvoiceDetails = proFormaInvoiceService.getAllPiOrdersDetails(tenantUuid, companyUuid, partyUuid, fromDate, toDate, status,creationType);
        return new ResponseEntity<>(proformaInvoiceDetails, HttpStatus.ACCEPTED);
    }

    @GetMapping("/work-order-details/{tenantUuid}")
    public ResponseEntity<List<Map<String, Object>>> getAllPiOrdersDetailsWithWorkOrderDetails(@PathVariable String tenantUuid, @RequestParam(required = false) String workOrderUuid, @RequestParam LocalDate fromDate, @RequestParam LocalDate toDate) {
        List<Map<String, Object>> allPiOrdersDetailsWithWorkOrderDetails = proFormaInvoiceService.getAllPiOrdersDetailsWithWorkOrderDetails(tenantUuid, workOrderUuid, fromDate, toDate);
        return new ResponseEntity<>(allPiOrdersDetailsWithWorkOrderDetails, HttpStatus.ACCEPTED);
    }

    @PostMapping("/proforma-invoice-excel")
    public ResponseEntity<String> getProformaInvoiceExcel(@RequestBody ProFormaInvoiceExcelReport proFormaInvoiceExcelReport) throws IOException {
        List<ProFormaInvoiceExcelReportData> reportData = proFormaInvoiceService.getProFormaInvoice(proFormaInvoiceExcelReport);
        String base64Excel = generateExcelService.generateProformaInvoiceReportsExcelSheet(reportData, proFormaInvoiceExcelReport);
        return ResponseEntity.ok(base64Excel);
    }

    @PostMapping("/work-order-excel")
    public ResponseEntity<String> getWorkOrderDetailsExcel(@RequestBody WorkOrderExcelReport workOrderExcelReport) throws IOException {
        List<WorkOrderExcelReportData> reportData = proFormaInvoiceService.getWorkOrderDetails(workOrderExcelReport);
        String base64Excel = generateExcelService.generateWorkOrderDetailsExcelSheet(reportData, workOrderExcelReport);
        return ResponseEntity.ok(base64Excel);
    }
}
