package com.sowermate.tenantService.controllers;


import com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue;
import com.sowermate.tenantService.services.ServiceRateInvoiceService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-rate-invoices")
@CrossOrigin(origins = "http://localhost:3000/")
public class ServiceRateInvoiceController {

    @Autowired
    private ServiceRateInvoiceService serviceRateInvoiceService;


    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(ServiceRateInvoiceController.class);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ServiceRateInvoiceValue> createServiceRateInvoice(@RequestBody ServiceRateInvoiceValue serviceRateInvoiceValue) {
        try {
            serviceRateInvoiceValue = serviceRateInvoiceService.createServiceRateInvoice(serviceRateInvoiceValue);
        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
        return new ResponseEntity<ServiceRateInvoiceValue>(serviceRateInvoiceValue, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ServiceRateInvoiceValue> editServiceRateInvoice(@RequestBody ServiceRateInvoiceValue serviceRateInvoiceValue) {
        ServiceRateInvoiceValue serviceRateInvoiceValue1 = null;
        try {
            serviceRateInvoiceValue1 = serviceRateInvoiceService.editServiceRateInvoice(serviceRateInvoiceValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity<ServiceRateInvoiceValue>(serviceRateInvoiceValue1, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{proFormaInvoiceUuid}/{serviceRateInvoiceUuid}")
    public ResponseEntity<ServiceRateInvoiceValue> getServiceRateInvoice(@PathVariable String proFormaInvoiceUuid,
                                                                         @PathVariable String serviceRateInvoiceUuid) {
        ServiceRateInvoiceValue serviceRateInvoiceValue = null;
        try {
            serviceRateInvoiceValue = serviceRateInvoiceService.getServiceRateInvoice(proFormaInvoiceUuid, serviceRateInvoiceUuid);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(serviceRateInvoiceValue, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}/{serviceRateInvoiceUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Integer> deleteServiceRateInvoice(@PathVariable String tenantUuid,
                                                            @PathVariable String serviceRateInvoiceUuid) {
        int rowDeletedCount = 0;
        try {
            rowDeletedCount = serviceRateInvoiceService.deleteServiceRateInvoice(tenantUuid, serviceRateInvoiceUuid);
        } catch (Exception e) {
            Logger.error("Error while deleting Seller:", e);
        }
        return new ResponseEntity<Integer>(rowDeletedCount, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{tenantUuid}")
    public ResponseEntity<List<ServiceRateInvoiceValue>> getAllServiceRateInvoice(@PathVariable String proFormaInvoiceUuid) {
        List<ServiceRateInvoiceValue> serviceRateInvoiceValues = null;
        try {
            serviceRateInvoiceValues = serviceRateInvoiceService.getAllServiceRateInvoice(proFormaInvoiceUuid);
            Logger.info("records " + serviceRateInvoiceValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(serviceRateInvoiceValues, HttpStatus.ACCEPTED);

    }


}
