package com.sowermate.tenantService.controllers;
import com.sowermate.tenantService.entities.value.CompanyValue;
import com.sowermate.tenantService.entities.value.TenantDetailsValue;
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
    public ResponseEntity<TenantDetailsValue> createTenantDetails(@RequestBody TenantDetailsValue tenantDetailsValue) throws Exception {
           TenantDetailsValue tenantDetailsValue1=tenantService.saveTenantDetails(tenantDetailsValue);
        return new ResponseEntity<TenantDetailsValue>(tenantDetailsValue1,HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TenantDetailsValue> getSingleTenantDetails(@PathVariable String uuid) throws Exception {
        TenantDetailsValue tenantDetailsValue=tenantService.getTenantDetails(uuid);
        return  new ResponseEntity<>(tenantDetailsValue, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<TenantDetailsValue>> getAllTenantDetails() {
        List<TenantDetailsValue> tenantDetailsValues = null;
        try {
            tenantDetailsValues = tenantService.getAllTenantDetails();
            Logger.info("records " + tenantDetailsValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(tenantDetailsValues, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<TenantDetailsValue> deleteMessage(@PathVariable String uuid) throws Exception {
     TenantDetailsValue tenantDetailsValue =tenantService.deleteTenantDetails(uuid);
     return new ResponseEntity<TenantDetailsValue>(tenantDetailsValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TenantDetailsValue> editTenantDetails1(@RequestBody TenantDetailsValue tenantDetailsValue) throws Exception{
        TenantDetailsValue tenantDetailsValue1=tenantService.editTenantDetails(tenantDetailsValue);
        return new ResponseEntity<TenantDetailsValue> (tenantDetailsValue1, HttpStatus.CREATED);
    }
}


