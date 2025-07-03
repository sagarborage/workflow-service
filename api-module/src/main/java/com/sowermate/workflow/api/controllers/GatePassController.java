package com.sowermate.workflow.api.controllers;

import com.sowermate.workflow.domain.entities.value.GatePassExcelReport;
import com.sowermate.workflow.domain.entities.value.GatePassExcelReportData;
import com.sowermate.workflow.domain.entities.value.GatePassInfo;
import com.sowermate.workflow.domain.entities.value.GatePassValue;
import com.sowermate.workflow.service.services.GatePassService;
import com.sowermate.workflow.service.services.GenerateExcelService;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gatePass")
public class GatePassController {

    @Autowired
    private GatePassService gatePassService;

    @Autowired
    private GenerateExcelService generateExcelService;

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(GatePassController.class);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<GatePassValue> createGatePass(@RequestBody GatePassValue gatePassValue) {
        GatePassValue gatePassValue1 = null;
        try {
            gatePassValue1 = gatePassService.createGatePass(gatePassValue);
        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
        return new ResponseEntity<GatePassValue>(gatePassValue1, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<GatePassValue> updateGatePass(@RequestBody GatePassValue gatePassValue) {
        GatePassValue gatePassValue1 = null;
        try {
            gatePassValue1 = gatePassService.updateGatePass(gatePassValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity<GatePassValue>(gatePassValue1, HttpStatus.CREATED);
    }

    @GetMapping("/byUuid/{gatePassUuid}/{tenantUuid}/{companyUuid}")
    public ResponseEntity<GatePassValue> getGatePass(@PathVariable String gatePassUuid,
                                                     @PathVariable String tenantUuid, @PathVariable String companyUuid) {
        GatePassValue gatePassValue = null;
        try {
            gatePassValue = gatePassService.getGatePass(gatePassUuid, tenantUuid, companyUuid);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(gatePassValue, HttpStatus.OK);
    }

    @GetMapping(value = "/{tenantUuid}/{companyUuid}")
    public ResponseEntity<List<GatePassValue>> getAllGatePass(@PathVariable String tenantUuid, @PathVariable String companyUuid) {
        List<GatePassValue> gatePassValues = null;
        try {
            gatePassValues = gatePassService.getAllGatePass(tenantUuid, companyUuid);
            Logger.info("records " + gatePassValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(gatePassValues, HttpStatus.OK);
    }

    @RequestMapping(value = "/{gatePassUuid}/{tenantUuid}/{companyUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<GatePassValue> deleteGatePass(@PathVariable String gatePassUuid,
                                                        @PathVariable String tenantUuid, @PathVariable String companyUuid) {
        GatePassValue gatePassValue = null;
        gatePassValue = gatePassService.deleteGatePass(gatePassUuid, tenantUuid, companyUuid);
        return new ResponseEntity<GatePassValue>(gatePassValue, HttpStatus.OK);
    }

    @GetMapping("/info/{companyUuid}/{proformaInvoiceUuid}")
    public ResponseEntity<GatePassInfo> getGatePassInfo(@PathVariable String companyUuid,
                                                        @PathVariable String proformaInvoiceUuid) {
        GatePassInfo gatePassInfo = null;
        try {
            gatePassInfo = gatePassService.getGatePassByProformaInvoice(companyUuid, proformaInvoiceUuid);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(gatePassInfo, HttpStatus.OK);
    }

    @GetMapping("/get-pass/{tenantUuid}")
    public ResponseEntity<List<Map<String, Object>>> getAllGatePassWithProformaDetails(@PathVariable String tenantUuid, @RequestParam(required = false) String partyUuid, @RequestParam(required = false) String firmUuid, @RequestParam LocalDate fromDate, @RequestParam LocalDate toDate) {
        List<Map<String, Object>> allGatePassWithProformaDetails = gatePassService.getAllGatePassWithProformaDetails(tenantUuid, partyUuid, firmUuid, fromDate, toDate);
        return new ResponseEntity<>(allGatePassWithProformaDetails, HttpStatus.ACCEPTED);
    }

    @PostMapping("/gate-pass-excel-report")
    public ResponseEntity<String> getGatePassExcel(@RequestBody GatePassExcelReport gatePassExcelReport) throws IOException {
        List<GatePassExcelReportData> gatePassExcelReportData = this.gatePassService.getGatePassExcel(gatePassExcelReport);
        String base64Excel = generateExcelService.generateGatePassExcelSheet(gatePassExcelReportData, gatePassExcelReport);
        return new ResponseEntity<>(base64Excel, HttpStatus.OK);
    }
}
