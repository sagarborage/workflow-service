package com.sowermate.workflow.api.controllers;

import com.sowermate.workflow.domain.entities.minimal.ToughenBatchProcessProjection;
import com.sowermate.workflow.domain.entities.minimal.ViewToughenBatchProcessDetailsProjection;
import com.sowermate.workflow.domain.entities.value.GeneralParamValue;
import com.sowermate.workflow.domain.entities.value.JbCreationValue;
import com.sowermate.workflow.domain.entities.value.ToughReportDto;
import com.sowermate.workflow.domain.entities.value.ToughenBatchProcessDetailsValue;
import com.sowermate.workflow.domain.entities.value.ToughenBatchProcessValue;
import com.sowermate.workflow.domain.enums.ToughenBatchProcessStatusEnum;
import com.sowermate.workflow.service.services.ToughenBatchProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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
    public ResponseEntity<ToughenBatchProcessDetailsValue> toughenBatchProcessItemCancel(@PathVariable("uuid") String uuid, @PathVariable("companyUuid") String companyUuid) {
        ToughenBatchProcessDetailsValue toughenBatchProcessDetailsValue = toughenBatchProcessService.toughenBatchProcessItemCancel(uuid, companyUuid);
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

    @RequestMapping(method = RequestMethod.GET, path = "/getToughenBatchProcessByStatus/{status}")
    @ResponseBody
    public ResponseEntity<List<ToughenBatchProcessProjection>> getToughenBatchProcessByStatus(@PathVariable("status") ToughenBatchProcessStatusEnum status) {
        List<ToughenBatchProcessProjection> list = toughenBatchProcessService.getToughenBatchProcessByStatus(status);
        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<ToughReportDto> getToughenReportForReport(@RequestParam("date") LocalDate date) {
        ToughReportDto list = toughenBatchProcessService.getToughenReportForReport(date);
        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/viewToughenBatchProcessDetails/{tenantUuid}/{batchProcessingDate}")
    @ResponseBody
    public ResponseEntity<List<ViewToughenBatchProcessDetailsProjection>> viewToughenBatchProcessDetails(@PathVariable("tenantUuid") String tenantUuid, @PathVariable("batchProcessingDate") LocalDate batchProcessingDate) {
        List<ViewToughenBatchProcessDetailsProjection> list = toughenBatchProcessService.viewToughenBatchProcessDetails(tenantUuid, batchProcessingDate);
        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }
}
