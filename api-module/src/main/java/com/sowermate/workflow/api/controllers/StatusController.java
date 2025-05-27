package com.sowermate.workflow.api.controllers;

import com.sowermate.workflow.domain.entities.value.StatusValue;
import com.sowermate.workflow.service.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statuses")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<StatusValue> createStatus(@RequestBody StatusValue statusValue) {
        StatusValue statusValue1 = statusService.createStatus(statusValue);
        return new ResponseEntity<StatusValue>(statusValue, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{tenantUuid}/{statusUuid}")
    public ResponseEntity<StatusValue> getSingleStatus(@PathVariable String tenantUuid, @PathVariable String statusUuid) {
        StatusValue statusValue = statusService.getStatus(tenantUuid, statusUuid);
        return new ResponseEntity<>(statusValue, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{tenantUuid}")
    public ResponseEntity<List<StatusValue>> getAllStatus(@PathVariable String tenantUuid) {
        List<StatusValue> statusValues = statusService.getAllStatus(tenantUuid);
        return new ResponseEntity<>(statusValues, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{tenantUuid}/{statusUuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<StatusValue> deleteStatus(@PathVariable String tenantUuid, @PathVariable String statusUuid) {
        StatusValue statusValue = statusService.deleteStatus(tenantUuid, statusUuid);
        return new ResponseEntity<StatusValue>(statusValue, HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<StatusValue> editStatus(@RequestBody StatusValue statusValue) throws Exception {
        StatusValue statusValue1 = statusService.editStatus(statusValue);
        return new ResponseEntity<StatusValue>(statusValue1, HttpStatus.CREATED);
    }

}
