package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.AddressTypeValue;
import com.sowermate.tenantService.services.AddressTypeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address-types")
public class AddressTypeController {

    private static final org.slf4j.Logger Logger= LoggerFactory.getLogger(AddressTypeController.class);
    @Autowired
    private AddressTypeService addressTypeService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AddressTypeValue> createAddressType(@RequestBody AddressTypeValue addressTypeValue) {
        AddressTypeValue addressTypeValue1 = addressTypeService.createAddressType(addressTypeValue);
        return new ResponseEntity<AddressTypeValue>(addressTypeValue1, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{tenantUuid}/{addressTypeUuid}")
    public ResponseEntity<AddressTypeValue> getAddressType(@PathVariable String tenantUuid, @PathVariable String addressTypeUuid) {
        AddressTypeValue addressTypeValue = addressTypeService.getAddressType(tenantUuid, addressTypeUuid);
        return new ResponseEntity<>(addressTypeValue, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{tenantUuid}")
    public ResponseEntity<List<AddressTypeValue>> getAllAddressType(@PathVariable String tenantUuid) {
        List<AddressTypeValue> addressTypeValues = addressTypeService.getAllAddressType(tenantUuid);
        return new ResponseEntity<>(addressTypeValues, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}/{addressTypeUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<AddressTypeValue> deleteAddressType(@PathVariable String tenantUuid, @PathVariable String addressTypeUuid) {
        AddressTypeValue addressTypeValue = addressTypeService.deleteAddressType(tenantUuid, addressTypeUuid);
        return new ResponseEntity<AddressTypeValue>(addressTypeValue, HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<AddressTypeValue> editAddressType(@RequestBody AddressTypeValue addressTypeValue) {
        AddressTypeValue addressTypeValue1 = null;
        try {
             addressTypeValue1 = addressTypeService.editAddressType(addressTypeValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity<AddressTypeValue>(addressTypeValue1, HttpStatus.CREATED);
    }

}
