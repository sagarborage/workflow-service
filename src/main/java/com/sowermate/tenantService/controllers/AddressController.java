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
@CrossOrigin("*")
public class AddressController {

    @Autowired
    private AddressService addressService;

    private static final org.slf4j.Logger Logger= LoggerFactory.getLogger(AddressController.class);

    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AddressValue> createAddressDetails(@RequestBody AddressValue addressValue){
        AddressValue addressValue1=null;
        try {
            addressValue1=addressService.createAddress(addressValue);
        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
        return new ResponseEntity<AddressValue>(addressValue1, HttpStatus.CREATED);
    }
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<AddressValue> editAddressDetails(@RequestBody AddressValue addressValue){
        AddressValue addressValue1=null;
        try {
            addressValue1=  addressService.editAddress(addressValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity<AddressValue>(addressValue1,HttpStatus.CREATED);
    }
    @GetMapping("/{tenantUuid}/{addressUuid}")
    public ResponseEntity<AddressValue> getAddressDetails(@PathVariable String tenantUuid,
            @PathVariable String addressUuid) {
        AddressValue addressValue = null;
        try {
            addressValue = addressService.getAddress(tenantUuid,addressUuid);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(addressValue, HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{tenantUuid}/{addressUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<AddressValue> deleteAddressDetails(@PathVariable String tenantUuid, @PathVariable String addressUuid) {
        AddressValue addressValue = null;
        try {
            addressValue = addressService.deleteAddress(tenantUuid,addressUuid);
        } catch (Exception e) {
            Logger.error("Error while deleting Seller:", e);
        }
        return new ResponseEntity<AddressValue>(addressValue, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{tenantUuid}")
    public ResponseEntity<List<AddressValue>> getAllAddress(@PathVariable String tenantUuid) {
        List<AddressValue> addressValues = null;//ss
        try {
            addressValues = addressService.getAllCompanyAddress(tenantUuid);
            Logger.info("records " + addressValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(addressValues, HttpStatus.ACCEPTED);
    }
}
