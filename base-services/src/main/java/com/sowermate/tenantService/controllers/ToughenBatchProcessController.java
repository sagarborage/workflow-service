package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.GeneralParamValue;
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

    @RequestMapping(method = RequestMethod.POST, path = "/cancelBatchItem/{tenantUuid}/{uuid}")
    @ResponseBody
    public ResponseEntity<List<ToughenBatchProcessValue>> toughenBatchProcessItemCancel(@PathVariable("tenantUuid") String tenantUuid, @PathVariable("uuid") String uuid) {
        List<ToughenBatchProcessValue> list = toughenBatchProcessService.toughenBatchProcessItemCancel(tenantUuid, uuid);
        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/markComplete")
    @ResponseBody
    public ResponseEntity<List<ToughenBatchProcessValue>> markToughenBatchProcessComplete(@RequestBody GeneralParamValue generalParamValue) {
        List<ToughenBatchProcessValue> list = toughenBatchProcessService.markToughenBatchProcessComplete(generalParamValue);

        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getToughenBatchProcessByStatus/{status}")
    @ResponseBody
    public ResponseEntity<List<ToughenBatchProcessValue>> getToughenBatchProcessByStatus(@PathVariable("status") ToughenBatchProcessStatusEnum status) {
        List<ToughenBatchProcessValue> list = toughenBatchProcessService.getToughenBatchProcessByStatus(status);
        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }
}
