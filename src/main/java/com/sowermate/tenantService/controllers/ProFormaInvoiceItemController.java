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
    public ResponseEntity<ProFormaInvoiceItemValue> createproFormaInvoiceItem(@RequestBody ProFormaInvoiceItemValue  proFormaInvoiceItemValue) {
        try {
            proFormaInvoiceItemValue = proFormaInvoiceItemService.createproFormaInvoiceItem(proFormaInvoiceItemValue);
        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
        return new ResponseEntity<ProFormaInvoiceItemValue>(proFormaInvoiceItemValue, HttpStatus.CREATED);
    }
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ProFormaInvoiceItemValue> editproFormaInvoiceItem(@RequestBody ProFormaInvoiceItemValue proFormaInvoiceItemValue) {
        ProFormaInvoiceItemValue proFormaInvoiceItemValue1 = null;
        try {
            proFormaInvoiceItemValue1 = proFormaInvoiceItemService.editproFormaInvoiceItem(proFormaInvoiceItemValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity<ProFormaInvoiceItemValue>(proFormaInvoiceItemValue1, HttpStatus.CREATED);
    }
        @GetMapping("/{tenantUuid}/{proFormaInvoiceItemUuid}")
        public ResponseEntity<ProFormaInvoiceItemValue> getproFormaInvoiceItem(@PathVariable String tenantUuid,
                @PathVariable String proFormaInvoiceItemUuid) {
            ProFormaInvoiceItemValue proFormaInvoiceItemValue = null;
            try {
                proFormaInvoiceItemValue = proFormaInvoiceItemService.getproFormaInvoiceItem(tenantUuid,proFormaInvoiceItemUuid);
            } catch (Exception e) {
                Logger.error("Error while getting Seller:", e);
            }
            return new ResponseEntity<>(proFormaInvoiceItemValue, HttpStatus.ACCEPTED);
        }

    @RequestMapping(value = "/{tenantUuid}/{ProFormaInvoiceItemUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Integer> deleteproFormaInvoiceItem(@PathVariable String tenantUuid,
            @PathVariable String ProFormaInvoiceItemUuid) {
         int deleteProFormaInvoiceItem=0;
        try {
            deleteProFormaInvoiceItem = proFormaInvoiceItemService.deleteproFormaInvoiceItem(tenantUuid,ProFormaInvoiceItemUuid);
        } catch (Exception e) {
            Logger.error("Error while deleting Seller:", e);
        }
        return new ResponseEntity<Integer>(deleteProFormaInvoiceItem, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{proFormaInvoiceUuid}")
    public ResponseEntity<List<ProFormaInvoiceItemValue>> getAllproFormaInvoiceItem(@PathVariable String proFormaInvoiceUuid) {
        List<ProFormaInvoiceItemValue> proFormaInvoiceItemValues = null;
        try {
            proFormaInvoiceItemValues = proFormaInvoiceItemService.getAllproFormaInvoiceItem(proFormaInvoiceUuid);
            Logger.info("records " + proFormaInvoiceItemValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(proFormaInvoiceItemValues, HttpStatus.ACCEPTED);

    }

}
