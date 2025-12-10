package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.GatePassDetailsValue;
import com.sowermate.tenantService.entities.value.GatePassValue;
import com.sowermate.tenantService.services.GatePassDetailsService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gatePassDetails")
public class GatePassDetailsController {

    @Autowired
    private GatePassDetailsService gatePassDetailsService;

    private static final org.slf4j.Logger Logger= LoggerFactory.getLogger(GatePassController.class);

    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<GatePassDetailsValue> createGatePassDetails(@RequestBody GatePassDetailsValue gatePassDetailsValue){
        GatePassDetailsValue gatePassDetailsValue1=null;
        try {
            gatePassDetailsValue1=gatePassDetailsService.createGatePassDetails(gatePassDetailsValue);
        } catch (Exception e) {
            Logger.error("Error while creating Seller:", e);
        }
        return new ResponseEntity<GatePassDetailsValue>(gatePassDetailsValue1, HttpStatus.CREATED);
    }

    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<GatePassDetailsValue> updateGatePassDetails(@RequestBody GatePassDetailsValue gatePassDetailsValue){
        GatePassDetailsValue gatePassDetailsValue1=null;
        try {
            gatePassDetailsValue1=  gatePassDetailsService.updateGatePassDetails(gatePassDetailsValue);
        } catch (Exception e) {
            Logger.error("Error while editing Seller:", e);
        }
        return new ResponseEntity< GatePassDetailsValue>(gatePassDetailsValue1,HttpStatus.CREATED);
    }

    @GetMapping("/byUuid/{gatePassDetailsUuid}/{proFormaInvoiceItemUuid}/{gatePassUuid}")
    public ResponseEntity<GatePassDetailsValue> getGatePassDetails(@PathVariable String gatePassDetailsUuid,
                                                     @PathVariable String proFormaInvoiceItemUuid,
                                                     @PathVariable String gatePassUuid) {
        GatePassDetailsValue gatePassDetailsValue = null;
        try {
            gatePassDetailsValue = gatePassDetailsService.getGatePassDetails(gatePassDetailsUuid,proFormaInvoiceItemUuid, gatePassUuid);
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(gatePassDetailsValue, HttpStatus.OK);
    }

    @GetMapping (value = "/{proFormaInvoiceItemUuid}/{gatePassUuid}")
    public ResponseEntity<List<GatePassDetailsValue>> getAllGatePassDetails(@PathVariable String proFormaInvoiceItemUuid, @PathVariable String gatePassUuid) {
        List<GatePassDetailsValue> gatePassDetailsValues = null;
        try {
            gatePassDetailsValues = gatePassDetailsService.getAllGatePassDetails(proFormaInvoiceItemUuid,gatePassUuid);
            Logger.info("records " + gatePassDetailsValues.size());
        } catch (Exception e) {
            Logger.error("Error while getting Seller:", e);
        }
        return new ResponseEntity<>(gatePassDetailsValues, HttpStatus.OK);
    }

    @RequestMapping(value = "/{gatePassDetailsUuid}/{proFormaInvoiceItemUuid}/{gatePassUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<GatePassDetailsValue> deleteGatePassDetails(@PathVariable String gatePassDetailsUuid,
                                                        @PathVariable String proFormaInvoiceItemUuid,
                                                        @PathVariable String gatePassUuid) {
        GatePassDetailsValue gatePassDetailsValue = null;
        try {
            gatePassDetailsValue = gatePassDetailsService.deleteGatePassDetails(gatePassDetailsUuid, proFormaInvoiceItemUuid,gatePassUuid);
        } catch (Exception e) {
            Logger.error("Error while deleting Seller:", e);
        }
        return new ResponseEntity<GatePassDetailsValue>(gatePassDetailsValue, HttpStatus.OK);
    }

}
