package com.sowermate.workflow.api.controllers;

import com.sowermate.workflow.domain.entities.value.GlassBreakageDetailsValue;
import com.sowermate.workflow.service.services.GlassBreakageDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/glass-breakage-details")
public class GlassBreakageDetailsController {

    @Autowired
    private GlassBreakageDetailsService glassBreakageDetailsService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<GlassBreakageDetailsValue> createGlassBreakageDetails(@RequestBody GlassBreakageDetailsValue glassBreakageDetailsValue) {
        GlassBreakageDetailsValue glassBreakageDetailsValue1 = glassBreakageDetailsService.createGlassBreakageDetails(glassBreakageDetailsValue);
        if (glassBreakageDetailsValue1 == null) {
            return new ResponseEntity<GlassBreakageDetailsValue>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<GlassBreakageDetailsValue>(glassBreakageDetailsValue1, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<GlassBreakageDetailsValue> editConfirmThroughValue(@RequestBody GlassBreakageDetailsValue glassBreakageDetailsValue) throws Exception {
        GlassBreakageDetailsValue glassBreakageDetailsValue1 = glassBreakageDetailsService.editGlassBreakageDetails(glassBreakageDetailsValue);
        return new ResponseEntity<GlassBreakageDetailsValue>(glassBreakageDetailsValue1, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GlassBreakageDetailsValue>> getAllConfirmThrough(@RequestParam(name = "tenantUuid") String tenantUuid, @RequestParam(name = "companyUuid") String companyUuid, @RequestParam(name = "proFormaInvoiceUuid") String proFormaInvoiceUuid, @RequestParam(name = "workOrderUuid") String workOrderUuid, @RequestParam(name = "proFormaInvoiceItemUuid") String proFormaInvoiceItemUuid) {
        List<GlassBreakageDetailsValue> glassBreakageDetailsValues = glassBreakageDetailsService.getAllGlassBreakageDetails(tenantUuid, companyUuid, proFormaInvoiceUuid, workOrderUuid, proFormaInvoiceItemUuid);
        return new ResponseEntity<>(glassBreakageDetailsValues, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{glassBreakageDetailsUuid}")
    public ResponseEntity<GlassBreakageDetailsValue> getGlassBreakageDetails(@PathVariable String glassBreakageDetailsUuid, @RequestParam(name = "proFormaInvoiceItemUuid") String proFormaInvoiceItemUuid) {
        GlassBreakageDetailsValue glassBreakageDetailsValue = glassBreakageDetailsService.getGlassBreakageDetails(glassBreakageDetailsUuid, proFormaInvoiceItemUuid);
        return new ResponseEntity<>(glassBreakageDetailsValue, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{glassBreakageDetailsUuid}")
    public ResponseEntity<Integer> deleteGlassBreakageDetails(@PathVariable String glassBreakageDetailsUuid, @RequestParam(name = "tenantUuid") String tenantUuid, @RequestParam(name = "companyUuid") String companyUuid, @RequestParam(name = "proFormaInvoiceUuid") String proFormaInvoiceUuid, @RequestParam(name = "workOrderUuid") String workOrderUuid, @RequestParam(name = "proFormaInvoiceItemUuid") String proFormaInvoiceItemUuid) {
        int deleteCharges = 0;
        deleteCharges = glassBreakageDetailsService.deleteGlassBreakageDetails(glassBreakageDetailsUuid, tenantUuid, companyUuid, workOrderUuid, proFormaInvoiceUuid, proFormaInvoiceItemUuid);
        return new ResponseEntity<Integer>(deleteCharges, HttpStatus.ACCEPTED);
    }

}
