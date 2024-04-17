package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.PiTypeValue;
import com.sowermate.tenantService.entities.value.ToughenBatchProcessValue;
import com.sowermate.tenantService.services.PiTypeService;
import com.sowermate.tenantService.services.ToughenBatchProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/toughen-batch-process", produces = MediaType.APPLICATION_JSON_VALUE)
public class ToughenBatchProcessController {

    @Autowired
    private ToughenBatchProcessService toughenBatchProcessService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ToughenBatchProcessValue> createToughenBatchProcess(@RequestBody ToughenBatchProcessValue toughenBatchProcessValue) {
        ToughenBatchProcessValue piTypeValue1 = toughenBatchProcessService.saveOrUpdate(toughenBatchProcessValue);
        if (piTypeValue1 == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(piTypeValue1, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ToughenBatchProcessValue> getToughenBatchProcess(@RequestBody ToughenBatchProcessValue toughenBatchProcessValue) {
        ToughenBatchProcessValue piTypeValue1 = toughenBatchProcessService.saveOrUpdate(toughenBatchProcessValue);
        if (piTypeValue1 == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(piTypeValue1, HttpStatus.CREATED);
    }
}
