package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.minimal.ToughenBatchProcessProjection;
import com.sowermate.tenantService.entities.value.GeneralParamValue;
import com.sowermate.tenantService.entities.value.JbCreationValue;
import com.sowermate.tenantService.entities.value.ToughenBatchProcessDetailsValue;
import com.sowermate.tenantService.entities.value.ToughenBatchProcessValue;
import com.sowermate.tenantService.enums.ProformaInvoiceStatusEnum;
import com.sowermate.tenantService.enums.ToughenBatchProcessStatusEnum;
import com.sowermate.tenantService.services.ToughenBatchProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/toughen-batch-process", produces = MediaType.APPLICATION_JSON_VALUE)
public class ToughenBatchProcessController {

    @Autowired
    private ToughenBatchProcessService toughenBatchProcessService;

    @RequestMapping(method = RequestMethod.POST, path = "/toughenBatchProcessItemAdd")
    @ResponseBody
    public ResponseEntity<Void> toughenBatchProcessItemAdd(@RequestBody GeneralParamValue generalParamValue) {
        toughenBatchProcessService.toughenBatchProcessItemAdd(generalParamValue);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/toughenBatchProcessJBAddItems")
    @ResponseBody
    public ResponseEntity<Void> toughenBatchProcessJBAddItems(@RequestBody List<JbCreationValue> jbCreationValues) {
        toughenBatchProcessService.toughenBatchProcessJBAddItems(jbCreationValues);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/cancelBatchItem/{uuid}/{companyUuid}")
    @ResponseBody
    public ResponseEntity<ToughenBatchProcessDetailsValue> toughenBatchProcessItemCancel(@PathVariable("uuid") String uuid,@PathVariable("companyUuid") String companyUuid) {
        ToughenBatchProcessDetailsValue toughenBatchProcessDetailsValue = toughenBatchProcessService.toughenBatchProcessItemCancel(uuid,companyUuid);
        return new ResponseEntity<>(toughenBatchProcessDetailsValue, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/brokeBatchItem")
    @ResponseBody
    public ResponseEntity<ToughenBatchProcessDetailsValue> toughenBatchProcessItemBreak(@RequestBody GeneralParamValue generalParamValue) {
        ToughenBatchProcessDetailsValue toughenBatchProcessDetailsValue = toughenBatchProcessService.toughenBatchProcessItemBroke(generalParamValue);
        return new ResponseEntity<>(toughenBatchProcessDetailsValue, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/markComplete")
    @ResponseBody
    public ResponseEntity<List<ToughenBatchProcessValue>> markToughenBatchProcessComplete(@RequestBody GeneralParamValue generalParamValue) {
        List<ToughenBatchProcessValue> list = toughenBatchProcessService.markToughenBatchProcessComplete(generalParamValue);

        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getToughenBatchProcessByStatus/{companyUuid}/{status}")
    @ResponseBody
    public ResponseEntity<List<ToughenBatchProcessProjection>> getToughenBatchProcessByStatus(@PathVariable("companyUuid") String companyUuid, @PathVariable("status") ToughenBatchProcessStatusEnum status) {
        List<ToughenBatchProcessProjection> list = toughenBatchProcessService.getToughenBatchProcessByStatus(companyUuid,status);
        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }
}
