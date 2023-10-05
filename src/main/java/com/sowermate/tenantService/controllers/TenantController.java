package com.sowermate.tenantService.controllers;
import com.sowermate.tenantService.entities.value.TenantValue;
import com.sowermate.tenantService.services.TenantService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenants")
public class TenantController {

    //create
    @Autowired
    private TenantService tenantService;

    private static final org.slf4j.Logger Logger= LoggerFactory.getLogger(TenantController.class);
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TenantValue> createTenantDetails(@RequestBody TenantValue tenantValue) {
           TenantValue tenantValue1 = tenantService.saveTenantDetails(tenantValue);
        return new ResponseEntity<TenantValue>(tenantValue1,HttpStatus.CREATED);
    }

    @GetMapping("/{tenantUuid}")
    public ResponseEntity<TenantValue> getSingleTenantDetails(@PathVariable String tenantUuid) {
        TenantValue tenantValue =tenantService.getTenantDetails(tenantUuid);
        return  new ResponseEntity<>(tenantValue, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<TenantValue>> getAllTenantDetails() {
        List<TenantValue> tenantValues = null;
        try {
            tenantValues = tenantService.getAllTenantDetails();
            Logger.info("records " + tenantValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(tenantValues, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<TenantValue> deleteMessage(@PathVariable String tenantUuid) {
     TenantValue tenantValue =tenantService.deleteTenantDetails(tenantUuid);
     return new ResponseEntity<TenantValue>(tenantValue,HttpStatus.ACCEPTED);
    }
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TenantValue> editTenantDetails1(@RequestBody TenantValue tenantValue) throws Exception{
        TenantValue tenantValue1 =tenantService.editTenantDetails(tenantValue);
        return new ResponseEntity<TenantValue> (tenantValue1, HttpStatus.CREATED);
    }
}


