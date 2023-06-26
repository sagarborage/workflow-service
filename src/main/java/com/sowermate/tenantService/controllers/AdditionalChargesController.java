package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.AdditionalChargesValue;
import com.sowermate.tenantService.services.AdditionalChargesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/additional-charges")
public class AdditionalChargesController {

    @Autowired
    private AdditionalChargesService  additionalChargesService;

    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AdditionalChargesValue> createAdditionalCharges(@RequestBody AdditionalChargesValue additionalChargesValue) throws Exception {
        AdditionalChargesValue additionalChargesValue1=additionalChargesService.saveAdditionalCharges(additionalChargesValue);
        return new ResponseEntity<AdditionalChargesValue>( additionalChargesValue1, HttpStatus.CREATED);
    }
    @GetMapping("/{tenantUuid}/{additionalChargesUuid}")
    public ResponseEntity<AdditionalChargesValue> getAdditionalCharges(@PathVariable String tenantUuid,@PathVariable String additionalChargesUuid) throws Exception {
        AdditionalChargesValue additionalChargesValue=additionalChargesService.getAdditionalCharges(tenantUuid,additionalChargesUuid);
        return  new ResponseEntity<>(additionalChargesValue, HttpStatus.ACCEPTED);
    }
    @GetMapping("/{tenantUuid}")
    public ResponseEntity<List<AdditionalChargesValue>> getAllAdditionalCharges(@PathVariable String tenantUuid) throws Exception {
        List<AdditionalChargesValue> additionalChargesValues = additionalChargesService.getAllAdditionalCharges(tenantUuid);
        return new ResponseEntity<> (additionalChargesValues ,HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{tenantUuid}/{additionalChargesUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<AdditionalChargesValue> deleteAdditionalCharges(@PathVariable String tenantUuid ,@PathVariable String additionalChargesUuid) throws Exception {
        AdditionalChargesValue additionalChargesValue =additionalChargesService.deleteAdditionalCharges(tenantUuid,additionalChargesUuid);
        return new ResponseEntity<AdditionalChargesValue>(additionalChargesValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<AdditionalChargesValue> editAdditionalCharges(@RequestBody AdditionalChargesValue additionalChargesValue) throws Exception{
        AdditionalChargesValue  additionalChargesValue1=additionalChargesService.editAdditionalCharges(additionalChargesValue);
        return new ResponseEntity<AdditionalChargesValue> (additionalChargesValue1, HttpStatus.CREATED);
    }

}
