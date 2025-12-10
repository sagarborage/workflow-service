package com.sowermate.tenantService.controllers;

import com.sowermate.tenantService.entities.value.WorkOrderValue;
import com.sowermate.tenantService.services.WorkOrderService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/work-order", produces = MediaType.APPLICATION_JSON_VALUE)
public class WorkOrderController {

    private static final org.slf4j.Logger Logger= LoggerFactory.getLogger(WorkOrderController.class);

    @Autowired
    private WorkOrderService workOrderService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<WorkOrderValue> createWorkOrder(@RequestBody WorkOrderValue workOrderValue) {
        WorkOrderValue workOrderValue1 = null;
        try {
            workOrderValue1= workOrderService.createWorkOrder(workOrderValue);
        } catch (Exception e) {
        Logger.error("Error while getting Seller:", e);
    }
        return new ResponseEntity<WorkOrderValue>(workOrderValue1, HttpStatus.CREATED);
    }
}
