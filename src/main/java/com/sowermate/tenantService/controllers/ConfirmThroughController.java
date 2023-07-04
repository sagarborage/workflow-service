package com.sowermate.tenantService.controllers;
import com.sowermate.tenantService.entities.value.AdditionalChargesValue;
import com.sowermate.tenantService.entities.value.ConfirmThroughValue;
import com.sowermate.tenantService.services.ConfirmThroughService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/confirm-through")
public class ConfirmThroughController {
    @Autowired
    private ConfirmThroughService confirmThroughService;

    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ConfirmThroughValue> createConfirmThrough(@RequestBody ConfirmThroughValue confirmThroughValue) throws Exception {
        ConfirmThroughValue confirmThroughValue1=confirmThroughService.createConfirmThrough(confirmThroughValue);
        if (confirmThroughValue1 == null) {
            return new ResponseEntity<ConfirmThroughValue>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ConfirmThroughValue>( confirmThroughValue1, HttpStatus.CREATED);
    }
    @GetMapping("/{tenantUuid}/{confirmThroughUuid}")
    public ResponseEntity<ConfirmThroughValue> getConfirmThrough(@PathVariable String tenantUuid,@PathVariable String confirmThroughUuid) throws Exception {
        ConfirmThroughValue confirmThroughValue=confirmThroughService.getConfirmThrough(tenantUuid,confirmThroughUuid);
        return  new ResponseEntity<>(confirmThroughValue, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{tenantUuid}")
    public ResponseEntity<List<ConfirmThroughValue>> getAllConfirmThrough(@PathVariable String tenantUuid) throws Exception {
        List<ConfirmThroughValue> confirmThroughValues = confirmThroughService.getAllConfirmThrough(tenantUuid);
        return new ResponseEntity<> (confirmThroughValues ,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}/{confirmThroughUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Integer> deleteConfirmThrough(@PathVariable String tenantUuid,@PathVariable String confirmThroughUuid) throws Exception {
        int deleteConfirmThrough=0;
        deleteConfirmThrough=confirmThroughService.deleteConfirmThrough(tenantUuid,confirmThroughUuid);
        return new ResponseEntity<Integer>(deleteConfirmThrough ,HttpStatus.ACCEPTED);
    }
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ConfirmThroughValue> editConfirmThroughValue(@RequestBody ConfirmThroughValue confirmThroughValue) throws Exception{
        ConfirmThroughValue  confirmThroughValue1=confirmThroughService.editConfirmThrough(confirmThroughValue);
        return new ResponseEntity<ConfirmThroughValue> (confirmThroughValue1, HttpStatus.CREATED);
    }


}
