package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceMinimal;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import com.sowermate.tenantService.services.ProFormaInvoiceService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/proforma-invoices")
public class ProFormaInvoiceController {
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(ProFormaInvoiceController.class);
    @Autowired
    private ProFormaInvoiceService proFormaInvoiceService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ProFormaInvoiceValue> createProFormaInvoice(@RequestBody ProFormaInvoiceValue proFormaInvoiceValue) {
        try {
            proFormaInvoiceValue = proFormaInvoiceService.createProFormaInvoice(proFormaInvoiceValue);
        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
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

    @GetMapping("/{tenantUuid}/{startDate}/{endDate}")
    public ResponseEntity<List<ProFormaInvoiceMinimal>> getAllProFormaInvoice(@PathVariable String tenantUuid, @PathVariable(name = "startDate") String startDate, @PathVariable(name = "endDate") String endDate) {
        List<ProFormaInvoiceMinimal> proFormaInvoiceMinimals = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            startDate = startDate+" 00:00:00";
            endDate = endDate+" 23:59:59";
            LocalDateTime startingDate = LocalDateTime.parse(startDate, formatter);
            LocalDateTime endingDate = LocalDateTime.parse(endDate, formatter);
            proFormaInvoiceMinimals = proFormaInvoiceService.getAllProFormaInvoice(tenantUuid, startingDate, endingDate);
            Logger.info("records " + proFormaInvoiceMinimals.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(proFormaInvoiceMinimals, HttpStatus.ACCEPTED);
    }
}
