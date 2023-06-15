package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.ServiceRateValue;
import com.sowermate.tenantService.services.ServiceRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceRateController {
    @Autowired
    private ServiceRateService serviceRateService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ServiceRateValue> createServiceRate(@RequestBody ServiceRateValue serviceRateValue) throws Exception {
        ServiceRateValue serviceRateValue1=serviceRateService.createServiceRate(serviceRateValue);
        return new ResponseEntity<ServiceRateValue>( serviceRateValue1, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ServiceRateValue> getServiceRate(@PathVariable String uuid) throws Exception {
        ServiceRateValue serviceRateValue=serviceRateService.getServiceRate(uuid);
        return  new ResponseEntity<>(serviceRateValue, HttpStatus.ACCEPTED);
    }
    @GetMapping
    public ResponseEntity<List<ServiceRateValue>> getAllServiceRate() throws Exception {
        List<ServiceRateValue> serviceRateValues = serviceRateService.getAllServiceRate();
        return new ResponseEntity<> (serviceRateValues ,HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ServiceRateValue> deleteServiceRate(@PathVariable String uuid) throws Exception {
        ServiceRateValue serviceRateValue =serviceRateService.deleteServiceRate(uuid);
        return new ResponseEntity<ServiceRateValue>(serviceRateValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ServiceRateValue> editServiceRate(@RequestBody ServiceRateValue serviceRateValue) throws Exception{
        ServiceRateValue serviceRateValue1=serviceRateService.editServiceRate(serviceRateValue);
        return new ResponseEntity<ServiceRateValue> (serviceRateValue1, HttpStatus.CREATED);
    }
}
