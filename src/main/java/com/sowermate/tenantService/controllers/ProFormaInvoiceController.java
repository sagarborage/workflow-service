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
public class ProFormaInvoiceController {
    @Autowired
    private ProFormaInvoiceService proFormaInvoiceService;

    private static final org.slf4j.Logger Logger= LoggerFactory.getLogger(ProFormaInvoiceController.class);
    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ProFormaInvoiceValue> createProFormInvoice(@RequestBody ProFormaInvoiceValue proFormInvoiceValue){
        try {
            proFormInvoiceValue=proFormaInvoiceService.createProFormInvoice(proFormInvoiceValue);
        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
        return new ResponseEntity<ProFormaInvoiceValue>(proFormInvoiceValue, HttpStatus.CREATED);
    }
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ProFormaInvoiceValue> editProFormInvoice(@RequestBody ProFormaInvoiceValue proFormInvoiceValue){
        ProFormaInvoiceValue proFormInvoiceValue1=null;
        try {
            proFormInvoiceValue1=  proFormaInvoiceService.editProFormInvoice(proFormInvoiceValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity<ProFormaInvoiceValue>(proFormInvoiceValue1,HttpStatus.CREATED);
    }
    @GetMapping("/{tenantUuid}/{proFormInvoiceUuid}")
    public ResponseEntity<ProFormaInvoiceValue> getProFormInvoice(@PathVariable String tenantUuid,
                                                                  @PathVariable String proFormInvoiceUuid) {
        ProFormaInvoiceValue proFormInvoiceValue = null;
        try {
            proFormInvoiceValue = proFormaInvoiceService.getProFormInvoice(tenantUuid,proFormInvoiceUuid);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(proFormInvoiceValue, HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{tenantUuid}/{proFormInvoiceUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Integer> deleteProFormInvoice(@PathVariable String tenantUuid,
            @PathVariable String proFormInvoiceUuid) {
          int deleteProFormaInvoice = 0;
        try {
            deleteProFormaInvoice = proFormaInvoiceService.deleteProFormInvoice(tenantUuid,proFormInvoiceUuid);
        } catch (Exception e) {
            Logger.error("Error while deleting Seller:", e);
        }
        return new ResponseEntity<Integer>(deleteProFormaInvoice, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{tenantUuid}")
    public ResponseEntity<List<ProFormaInvoiceValue>> getAllProFormInvoice(@PathVariable String tenantUuid) {
        List<ProFormaInvoiceValue> proFormInvoiceValues = null;
        try {
            proFormInvoiceValues = proFormaInvoiceService.getAllProFormInvoice(tenantUuid);
            Logger.info("records " + proFormInvoiceValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(proFormInvoiceValues, HttpStatus.ACCEPTED);

    }
}
