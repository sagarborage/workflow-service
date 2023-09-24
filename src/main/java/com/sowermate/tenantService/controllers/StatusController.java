package com.sowermate.tenantService.controllers;
import com.sowermate.tenantService.entities.value.StatusValue;
import com.sowermate.tenantService.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statuses")
@CrossOrigin("*")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<StatusValue> createStatus(@RequestBody StatusValue statusValue) {
        StatusValue statusValue1=statusService.createStatus(statusValue);
        return new ResponseEntity<StatusValue>( statusValue, HttpStatus.CREATED);
    }
    @GetMapping(value = "/{tenantUuid}/{statusUuid}")
    public ResponseEntity<StatusValue> getSingleStatus(@PathVariable String tenantUuid,@PathVariable String statusUuid) {
        StatusValue statusValue=statusService.getStatus(tenantUuid,statusUuid);
        return  new ResponseEntity<>(statusValue, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{tenantUuid}")
    public ResponseEntity<List<StatusValue>> getAllStatus(@PathVariable String tenantUuid) {
        List<StatusValue> statusValues = statusService.getAllStatus(tenantUuid);
        return new ResponseEntity<> (statusValues ,HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/{tenantUuid}/{statusUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<StatusValue> deleteStatus(@PathVariable String tenantUuid,@PathVariable String statusUuid) {
        StatusValue statusValue =statusService.deleteStatus(tenantUuid,statusUuid);
        return new ResponseEntity<StatusValue>(statusValue ,HttpStatus.ACCEPTED);
    }
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<StatusValue> editStatus(@RequestBody StatusValue statusValue) throws Exception {
        StatusValue statusValue1=statusService.editStatus(statusValue);
        return new ResponseEntity<StatusValue> (statusValue1, HttpStatus.CREATED);
    }

}
