package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.minimal.ProFormaInvoiceIndividualsOrdersProjection;
import com.sowermate.tenantService.entities.value.BucketManipulationValue;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.entities.value.ProformaInvoiceItemReqParam;
import com.sowermate.tenantService.services.ProFormaInvoiceItemService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

;

@RestController
@RequestMapping("/proforma-invoice-items")
public class ProFormaInvoiceItemController {

    @Autowired
    private ProFormaInvoiceItemService proFormaInvoiceItemService;

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(ProFormaInvoiceController.class);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ProFormaInvoiceItemValue> createProFormaInvoiceItem(@RequestBody ProFormaInvoiceItemValue proFormaInvoiceItemValue) {
        try {
            proFormaInvoiceItemValue = proFormaInvoiceItemService.createProFormaInvoiceItem(proFormaInvoiceItemValue);
        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
        return new ResponseEntity<ProFormaInvoiceItemValue>(proFormaInvoiceItemValue, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ProFormaInvoiceItemValue> editProFormaInvoiceItem(@RequestBody ProFormaInvoiceItemValue proFormaInvoiceItemValue) {
        ProFormaInvoiceItemValue proFormaInvoiceItemValue1 = null;
        try {
            proFormaInvoiceItemValue1 = proFormaInvoiceItemService.editProFormaInvoiceItem(proFormaInvoiceItemValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity<ProFormaInvoiceItemValue>(proFormaInvoiceItemValue1, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/updateProFormaInvoiceItemStatus", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ProFormaInvoiceItemValue> updateProFormaInvoiceItemStatus(@RequestBody ProformaInvoiceItemReqParam proformaInvoiceItemReqParam) {

        ProFormaInvoiceItemValue proFormaInvoiceItemValue1 = null;
        try {
            proFormaInvoiceItemValue1 = proFormaInvoiceItemService.updateProformaInvoiceItemStatus(proformaInvoiceItemReqParam);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity<ProFormaInvoiceItemValue>(proFormaInvoiceItemValue1, HttpStatus.CREATED);
    }


    @GetMapping("/{tenantUuid}/{proFormaInvoiceItemUuid}")
    public ResponseEntity<ProFormaInvoiceItemValue> getProFormaInvoiceItem(@PathVariable String tenantUuid,
                                                                           @PathVariable String proFormaInvoiceItemUuid) {
        ProFormaInvoiceItemValue proFormaInvoiceItemValue = null;
        try {
            proFormaInvoiceItemValue = proFormaInvoiceItemService.getProFormaInvoiceItem(tenantUuid, proFormaInvoiceItemUuid);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(proFormaInvoiceItemValue, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteFile/{tenantUuid}/{proFormaInvoiceItemUuid}/{fileName}")
    public ResponseEntity<String> deleteProFormaInvoiceItemFile(@PathVariable String tenantUuid,@PathVariable String proFormaInvoiceItemUuid,
                                                                           @PathVariable String fileName) {
        String isDeleted = null;
        try {
            isDeleted = proFormaInvoiceItemService.deleteProformaInvoiceItemFile(tenantUuid,proFormaInvoiceItemUuid,fileName);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(isDeleted, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}/{ProFormaInvoiceItemUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Integer> deleteProFormaInvoiceItem(@PathVariable String tenantUuid,
                                                             @PathVariable String ProFormaInvoiceItemUuid) {
        int deleteProFormaInvoiceItem = 0;
        try {
            deleteProFormaInvoiceItem = proFormaInvoiceItemService.deleteProFormaInvoiceItem(tenantUuid, ProFormaInvoiceItemUuid);
        } catch (Exception e) {
            Logger.error("Error while deleting Seller:", e);
        }
        return new ResponseEntity<Integer>(deleteProFormaInvoiceItem, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{proFormaInvoiceUuid}")
    public ResponseEntity<List<ProFormaInvoiceItemValue>> getAllProFormaInvoiceItem(@PathVariable String proFormaInvoiceUuid) {
        List<ProFormaInvoiceItemValue> proFormaInvoiceItemValues = null;
        try {
            proFormaInvoiceItemValues = proFormaInvoiceItemService.getAllProFormaInvoiceItem(proFormaInvoiceUuid);
            Logger.info("records " + proFormaInvoiceItemValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(proFormaInvoiceItemValues, HttpStatus.ACCEPTED);

    }

    @RequestMapping(value = "/bucketManipulation/{actionType}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<List<ProFormaInvoiceIndividualsOrdersProjection>> bucketManipulation(@PathVariable String actionType, @RequestBody BucketManipulationValue bucketManipulationValue) {
        List<ProFormaInvoiceIndividualsOrdersProjection> proFormaInvoiceIndividualsOrdersProjections = null;
        try {
            proFormaInvoiceIndividualsOrdersProjections = proFormaInvoiceItemService.bucketManipulation(actionType, bucketManipulationValue);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(proFormaInvoiceIndividualsOrdersProjections, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/bucketManipulationCompleteAll", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<List<ProFormaInvoiceIndividualsOrdersProjection>> bucketManipulationCompleteAll(@RequestBody BucketManipulationValue bucketManipulationValue) {
        List<ProFormaInvoiceIndividualsOrdersProjection> proFormaInvoiceIndividualsOrdersProjections = null;
        try {
            proFormaInvoiceIndividualsOrdersProjections = proFormaInvoiceItemService.bucketManipulationCompleteAll(bucketManipulationValue);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(proFormaInvoiceIndividualsOrdersProjections, HttpStatus.ACCEPTED);
    }
}
