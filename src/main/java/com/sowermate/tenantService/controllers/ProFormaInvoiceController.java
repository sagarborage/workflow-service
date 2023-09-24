package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import com.sowermate.tenantService.services.ProFormaInvoiceService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proforma-invoices")
@CrossOrigin(origins = "http://localhost:3000/")
public class ProFormaInvoiceController {
    @Autowired
    private ProFormaInvoiceService proFormaInvoiceService;

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(ProFormaInvoiceController.class);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ProFormaInvoiceValue> createproFormaInvoice(@RequestBody ProFormaInvoiceValue proFormaInvoiceValue) {
        try {
            proFormaInvoiceValue = proFormaInvoiceService.createProFormaInvoice(proFormaInvoiceValue);
        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
        return new ResponseEntity<ProFormaInvoiceValue>(proFormaInvoiceValue, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ProFormaInvoiceValue> editproFormaInvoice(@RequestBody ProFormaInvoiceValue proFormaInvoiceValue) {
        ProFormaInvoiceValue proFormaInvoiceValue1 = null;
        try {
            proFormaInvoiceValue1 = proFormaInvoiceService.editProFormaInvoice(proFormaInvoiceValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity<ProFormaInvoiceValue>(proFormaInvoiceValue1, HttpStatus.CREATED);
    }

    @GetMapping("/{tenantUuid}/{proFormaInvoiceUuid}")
    public ResponseEntity<ProFormaInvoiceValue> getproFormaInvoice(@PathVariable String tenantUuid,
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

    @GetMapping("/{tenantUuid}")
    public ResponseEntity<List<ProFormaInvoiceValue>> getAllproFormaInvoice(@PathVariable String tenantUuid) {
        List<ProFormaInvoiceValue> proFormaInvoiceValues = null;
        try {
            proFormaInvoiceValues = proFormaInvoiceService.getAllProFormaInvoice(tenantUuid);
            Logger.info("records " + proFormaInvoiceValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(proFormaInvoiceValues, HttpStatus.ACCEPTED);

    }
}
