package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.AdditionalChargesValue;
import com.sowermate.tenantService.services.AdditionalChargesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/additional")
public class AdditionalChargesController {

    @Autowired
    private AdditionalChargesService  additionalChargesService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AdditionalChargesValue> createAdditionalCharges(@RequestBody AdditionalChargesValue additionalChargesValue) throws Exception {
        AdditionalChargesValue additionalChargesValue1=additionalChargesService.SaveAdditionalCharges(additionalChargesValue);
        return new ResponseEntity<AdditionalChargesValue>( additionalChargesValue1, HttpStatus.CREATED);
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<AdditionalChargesValue> getAdditionalCharges(@PathVariable String uuid) throws Exception {
        AdditionalChargesValue additionalChargesValue=additionalChargesService.getAdditionalCharges(uuid);
        return  new ResponseEntity<>(additionalChargesValue, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<AdditionalChargesValue>> getAllAdditionalCharges() throws Exception {
        List<AdditionalChargesValue> additionalChargesValues = additionalChargesService.getAllAdditionalCharges();
        return new ResponseEntity<> (additionalChargesValues ,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<AdditionalChargesValue> deleteAdditionalCharges(@PathVariable String uuid) throws Exception {
        AdditionalChargesValue additionalChargesValue =additionalChargesService.deleteAdditionalCharges(uuid);
        return new ResponseEntity<AdditionalChargesValue>(additionalChargesValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{uuid}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<AdditionalChargesValue> editAdditionalCharges(@RequestBody AdditionalChargesValue additionalChargesValue) throws Exception{
        AdditionalChargesValue  additionalChargesValue1=additionalChargesService.editAdditionalCharges(additionalChargesValue);
        return new ResponseEntity<AdditionalChargesValue> (additionalChargesValue1, HttpStatus.CREATED);
    }

}
