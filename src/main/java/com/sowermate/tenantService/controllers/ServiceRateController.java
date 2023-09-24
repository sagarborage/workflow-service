package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.ServiceRateValue;
import com.sowermate.tenantService.services.ServiceRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-rates")
@CrossOrigin("*")
public class ServiceRateController {
    @Autowired
    private ServiceRateService serviceRateService;

    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ServiceRateValue> createServiceRate(@RequestBody ServiceRateValue serviceRateValue) {
        ServiceRateValue serviceRateValue1=serviceRateService.createServiceRate(serviceRateValue);
        return new ResponseEntity<ServiceRateValue>( serviceRateValue1, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{tenantUuid}/{serviceRateUuid}")
    public ResponseEntity<ServiceRateValue> getServiceRate(@PathVariable String  tenantUuid,@PathVariable String serviceRateUuid) {
        ServiceRateValue serviceRateValue=serviceRateService.getServiceRate(tenantUuid,serviceRateUuid);
        return  new ResponseEntity<>(serviceRateValue, HttpStatus.ACCEPTED);
    }
    @GetMapping(value = "/{tenantUuid}")
    public ResponseEntity<List<ServiceRateValue>> getAllServiceRate(@PathVariable String  tenantUuid) {
        List<ServiceRateValue> serviceRateValues = serviceRateService.getAllServiceRate(tenantUuid);
        return new ResponseEntity<> (serviceRateValues ,HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{tenantUuid}/{serviceRateUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ServiceRateValue> deleteServiceRate(@PathVariable String  tenantUuid,@PathVariable String serviceRateUuid) {
        ServiceRateValue serviceRateValue =serviceRateService.deleteServiceRate(tenantUuid,serviceRateUuid);
        return new ResponseEntity<ServiceRateValue>(serviceRateValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ServiceRateValue> editServiceRate(@RequestBody ServiceRateValue serviceRateValue) throws Exception{
        ServiceRateValue serviceRateValue1=serviceRateService.editServiceRate(serviceRateValue);
        return new ResponseEntity<ServiceRateValue> (serviceRateValue1, HttpStatus.CREATED);
    }
}
