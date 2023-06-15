package com.sowermate.tenantService.controllers;
import com.sowermate.tenantService.entities.value.TenantDetailsValue;
import com.sowermate.tenantService.services.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    //create
    @Autowired
    private TenantService tenantService;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TenantDetailsValue> createTenantDetails(@RequestBody TenantDetailsValue tenantDetailsValue) throws Exception {
           TenantDetailsValue tenantDetailsValue1=tenantService.SaveTenantDetails(tenantDetailsValue);
        return new ResponseEntity<TenantDetailsValue>(tenantDetailsValue1,HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TenantDetailsValue> getSingleTenantDetails(@PathVariable String uuid) throws Exception {
        TenantDetailsValue tenantDetailsValue=tenantService.getTenantDetails(uuid);
        return  new ResponseEntity<>(tenantDetailsValue, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<TenantDetailsValue>> getAllTenantDetails() throws Exception {
        List<TenantDetailsValue> allTenant = tenantService.getAllTenantDetails();
        return new ResponseEntity<> (allTenant ,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<TenantDetailsValue> deleteMessage(@PathVariable int tenantId) throws Exception {
     TenantDetailsValue tenantDetailsValue =tenantService.deleteTenantDetails(tenantId);
     return new ResponseEntity<TenantDetailsValue>(tenantDetailsValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TenantDetailsValue> editTenantDetails1(@RequestBody TenantDetailsValue tenantDetailsValue) throws Exception{
        TenantDetailsValue tenantDetailsValue1=tenantService.editTenantDetails(tenantDetailsValue);
        return new ResponseEntity<TenantDetailsValue> (tenantDetailsValue1, HttpStatus.CREATED);
    }
}


