package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.ProFormInvoiceValue;
import com.sowermate.tenantService.services.ProFormaInvoiceService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proforma-invoices")
public class ProFormaInvoiceController {
    @Autowired
    private ProFormaInvoiceService proFormaInvoiceService;

    private static final org.slf4j.Logger Logger= LoggerFactory.getLogger(ProFormaInvoiceController.class);
    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ProFormInvoiceValue> createProFormInvoice(@RequestBody ProFormInvoiceValue  proFormInvoiceValue){
        try {
            proFormInvoiceValue=proFormaInvoiceService.createProFormInvoice(proFormInvoiceValue);
        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
        return new ResponseEntity<ProFormInvoiceValue>(proFormInvoiceValue, HttpStatus.CREATED);
    }
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ProFormInvoiceValue> editProFormInvoice(@RequestBody ProFormInvoiceValue proFormInvoiceValue){
        ProFormInvoiceValue proFormInvoiceValue1=null;
        try {
            proFormInvoiceValue1=  proFormaInvoiceService.editProFormInvoice(proFormInvoiceValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity<ProFormInvoiceValue>(proFormInvoiceValue1,HttpStatus.CREATED);
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<ProFormInvoiceValue> getProFormInvoice(
            @PathVariable String uuid) {
        ProFormInvoiceValue proFormInvoiceValue = null;
        try {
            proFormInvoiceValue = proFormaInvoiceService.getProFormInvoice(uuid);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(proFormInvoiceValue, HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ProFormInvoiceValue> deleteProFormInvoice(
            @PathVariable String uuid) {
        ProFormInvoiceValue proFormInvoiceValue = null;
        try {
            proFormInvoiceValue = proFormaInvoiceService.deleteProFormInvoice(uuid);
        } catch (Exception e) {
            Logger.error("Error while deleting Seller:", e);
        }
        return new ResponseEntity<ProFormInvoiceValue>(proFormInvoiceValue, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<ProFormInvoiceValue>> getAllProFormInvoice() {
        List<ProFormInvoiceValue> proFormInvoiceValues = null;
        try {
            proFormInvoiceValues = proFormaInvoiceService.getAllProFormInvoice();
            Logger.info("records " + proFormInvoiceValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(proFormInvoiceValues, HttpStatus.ACCEPTED);

    }
}
