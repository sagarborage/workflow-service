package com.sowermate.tenantService.controllers;
;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.services.ProFormaInvoiceItemService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proforma-invoice-items")
public class ProFormaInvoiceItemController {

    @Autowired
    private ProFormaInvoiceItemService proFormaInvoiceItemService;

    private static final org.slf4j.Logger Logger= LoggerFactory.getLogger(ProFormaInvoiceController.class);
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ProFormaInvoiceItemValue> createProFormInvoiceItem(@RequestBody ProFormaInvoiceItemValue  proFormaInvoiceItemValue) {
        try {
            proFormaInvoiceItemValue = proFormaInvoiceItemService.createProFormInvoiceItem(proFormaInvoiceItemValue);
        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
        return new ResponseEntity<ProFormaInvoiceItemValue>(proFormaInvoiceItemValue, HttpStatus.CREATED);
    }
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ProFormaInvoiceItemValue> editProFormInvoiceItem(@RequestBody ProFormaInvoiceItemValue proFormaInvoiceItemValue) {
        ProFormaInvoiceItemValue proFormaInvoiceItemValue1 = null;
        try {
            proFormaInvoiceItemValue1 = proFormaInvoiceItemService.editProFormInvoiceItem(proFormaInvoiceItemValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity<ProFormaInvoiceItemValue>(proFormaInvoiceItemValue1, HttpStatus.CREATED);
    }
        @GetMapping("/{ProFormaInvoiceItemUuid}")
        public ResponseEntity<ProFormaInvoiceItemValue> getProFormInvoiceItem(
                @PathVariable String ProFormaInvoiceItemUuid) {
            ProFormaInvoiceItemValue proFormaInvoiceItemValue = null;
            try {
                proFormaInvoiceItemValue = proFormaInvoiceItemService.getProFormInvoiceItem(ProFormaInvoiceItemUuid);
            } catch (Exception e) {
                Logger.error("Error while getting Seller:", e);
            }
            return new ResponseEntity<>(proFormaInvoiceItemValue, HttpStatus.ACCEPTED);
        }

    @RequestMapping(value = "/{ProFormaInvoiceItemUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ProFormaInvoiceItemValue> deleteProFormInvoiceItem(
            @PathVariable String ProFormaInvoiceItemUuid) {
        ProFormaInvoiceItemValue proFormaInvoiceItemValue = null;
        try {
            proFormaInvoiceItemValue = proFormaInvoiceItemService.deleteProFormInvoiceItem(ProFormaInvoiceItemUuid);
        } catch (Exception e) {
            Logger.error("Error while deleting Seller:", e);
        }
        return new ResponseEntity<ProFormaInvoiceItemValue>(proFormaInvoiceItemValue, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<ProFormaInvoiceItemValue>> getAllProFormInvoiceItem() {
        List<ProFormaInvoiceItemValue> proFormaInvoiceItemValues = null;
        try {
            proFormaInvoiceItemValues = proFormaInvoiceItemService.getAllProFormInvoiceItem();
            Logger.info("records " + proFormaInvoiceItemValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(proFormaInvoiceItemValues, HttpStatus.ACCEPTED);

    }

}
