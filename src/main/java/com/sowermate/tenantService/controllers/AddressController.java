package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.AddressValue;
import com.sowermate.tenantService.entities.value.CommonValue;
import com.sowermate.tenantService.services.AddressService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    private static final org.slf4j.Logger Logger= LoggerFactory.getLogger(AddressController.class);

    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CommonValue> createAddressDetails(@RequestBody AddressValue addressValue){
        CommonValue commonValue=null;
        try {
            commonValue=addressService.createAddress(addressValue);
        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
        return new ResponseEntity<CommonValue>(commonValue, HttpStatus.CREATED);
    }
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<CommonValue> editAddressDetails(@RequestBody AddressValue addressValue){
        CommonValue commonValue=null;
        try {
            commonValue=  addressService.editAddress(addressValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity<CommonValue>(commonValue,HttpStatus.CREATED);
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<AddressValue> getAddressDetails(
            @PathVariable String uuid) {
        AddressValue addressValue = null;
        try {
            addressValue = addressService.getAddress(uuid);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(addressValue, HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<CommonValue> deleteAddressDetails(
            @PathVariable String uuid) {
        CommonValue commonValue = null;
        try {
            commonValue = addressService.deleteAddress(uuid);
        } catch (Exception e) {
            Logger.error("Error while deleting Seller:", e);
        }
        return new ResponseEntity<CommonValue>(commonValue, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<AddressValue>> getAllAddress() {
        List<AddressValue> addressValues = null;
        try {
            addressValues = addressService.getAllCompanyAddress();
            Logger.info("records " + addressValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(addressValues, HttpStatus.ACCEPTED);
    }




}
